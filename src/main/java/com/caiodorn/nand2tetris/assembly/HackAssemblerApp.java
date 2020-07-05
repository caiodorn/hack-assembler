package com.caiodorn.nand2tetris.assembly;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class HackAssemblerApp {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("A file must be provided for parsing!");
        }

        final String fullyQualifiedFileName = args[0];
        final int fileNamePosition = fullyQualifiedFileName.split("/").length - 1;
        final String fileName = fullyQualifiedFileName.split("/")[fileNamePosition];
        final String outputFileName = fileName.split("\\.")[0].concat(".hack");

        try {
            List<String> rawLines = Files.readAllLines(Paths.get(fullyQualifiedFileName));
            List<String> binaryCode = new AssemblyParser().parse(removeBlanks(rawLines));
            Files.write(Paths.get(outputFileName), binaryCode);
        } catch (IOException e) {
            log.error("An error occurred!", e);
            throw new RuntimeException(e);
        }
    }

    // TODO remove also comments
    private static List<String> removeBlanks(List<String> rawLines) {
        List<String> asmCommands = new ArrayList<>();

        List<String> nonEmptyLines = rawLines.stream()
                .filter(line -> !line.trim().isEmpty())
                .collect(Collectors.toList());

        nonEmptyLines.forEach(line -> asmCommands.add(line.replaceAll("\\s","")));

        return asmCommands;
    }

}
