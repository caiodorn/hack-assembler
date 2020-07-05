package com.caiodorn.nand2tetris.assembly;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class HackAssembler {

    public static void main(String[] args) {
        if (args.length == 0) {
            throw new RuntimeException("A file must be provided for parsing!");
        }

        final String fileName = args[0];

        try {
            List<String> rawLines = Files.readAllLines(Paths.get(fileName));
            new AssemblyParser().parse(removeBlanks(rawLines));
        } catch (IOException e) {
            log.error(String.format("An error occurred while trying to open file [%s]", fileName), e);
            throw new RuntimeException(e);
        }
    }

    private static List<String> removeBlanks(List<String> rawLines) {
        List<String> asmCommands = new ArrayList<>();

        List<String> nonEmptyLines = rawLines.stream()
                .filter(line -> !line.trim().isEmpty())
                .collect(Collectors.toList());

        nonEmptyLines.forEach(line -> asmCommands.add(line.replaceAll("\\s","")));

        return asmCommands;
    }

}
