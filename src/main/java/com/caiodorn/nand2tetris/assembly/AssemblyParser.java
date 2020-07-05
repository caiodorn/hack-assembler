package com.caiodorn.nand2tetris.assembly;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AssemblyParser {

    private static final Map<String, String> PREDEFINED_SYMBOLS;
    private final Map<String, String> customSymbols = new HashMap<>();

    static {
        Map<String, String> s = new HashMap<>();
        s.put("SP", "0");
        s.put("LCL", "1");
        s.put("ARG", "2");
        s.put("THIS", "3");
        s.put("THAT", "4");
        s.put("R0", "0");
        s.put("R1", "1");
        s.put("R2", "2");
        s.put("R3", "3");
        s.put("R4", "4");
        s.put("R5", "5");
        s.put("R6", "6");
        s.put("R7", "7");
        s.put("R8", "8");
        s.put("R9", "9");
        s.put("R10", "10");
        s.put("R11", "11");
        s.put("R12", "12");
        s.put("R13", "13");
        s.put("R14", "14");
        s.put("R15", "15");
        s.put("SCREEN", "16384");
        s.put("KBD", "24576");
        PREDEFINED_SYMBOLS = Collections.unmodifiableMap(s);
    }

    public void parse(List<String> asmCommands) {
        //TODO
        // if command starts with 0 - A-instruction
        //   just convert to bits
        // else C - instruction
        //   perform first pass adding symbols to table
        // perform second pass converting everything to binary
    }

    private void parseSymbols() {

    }

    private String parseCInstruction() {
        return null;
    }

    private String parseAInstruction() {
        return null;
    }

    private void generateMachineLanguageCode() {

    }
}
