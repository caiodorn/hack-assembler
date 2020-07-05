package com.caiodorn.nand2tetris.assembly;

import com.google.common.base.Predicate;

import java.util.*;

public class AssemblyParser {

    private static final int BASE_ADDRESS = 16;
    private static final Map<String, Integer> PREDEFINED_SYMBOLS;
    private static final String A_CMD_PREFIX = "@";
    private static final Predicate<String> IS_A_CMD = cmd -> cmd.startsWith(A_CMD_PREFIX);
    private static final Predicate<String> IS_L_CMD = cmd -> cmd.startsWith("(");

    private final Map<String, Integer> customSymbols = new HashMap<>();

    static {
        Map<String, Integer> s = new HashMap<>();
        s.put("SP", 0);
        s.put("LCL", 1);
        s.put("ARG", 2);
        s.put("THIS", 3);
        s.put("THAT", 4);
        s.put("R0", 0);
        s.put("R1", 1);
        s.put("R2", 2);
        s.put("R3", 3);
        s.put("R4", 4);
        s.put("R5", 5);
        s.put("R6", 6);
        s.put("R7", 7);
        s.put("R8", 8);
        s.put("R9", 9);
        s.put("R10", 10);
        s.put("R11", 11);
        s.put("R12", 12);
        s.put("R13", 13);
        s.put("R14", 14);
        s.put("R15", 15);
        s.put("SCREEN", 16384);
        s.put("KBD", 24576);
        PREDEFINED_SYMBOLS = Collections.unmodifiableMap(s);
    }

    public List<String> parse(List<String> asmCommands) {
        mapLabels(asmCommands);
        mapSymbols(asmCommands);

        return convertToBinary(asmCommands);
    }

    private void mapLabels(List<String> asmCommands) {
        for (int i = 0; i < asmCommands.size(); i++) {
            final String cmd = asmCommands.get(i);

            if (IS_L_CMD.apply(cmd)) {
                final String key = cmd.replaceAll("[()]", "");
                customSymbols.put(key, i + 1);
            }
        }
    }

    private void mapSymbols(List<String> asmCommands) {
        for (int i = 0; i < asmCommands.size(); i++) {
            final String cmd = asmCommands.get(i);

            if (IS_A_CMD.apply(cmd)) {
                final String key = cmd.replaceAll("[@]", "");

                if (!(PREDEFINED_SYMBOLS.containsKey(key) || customSymbols.containsKey(key))) {
                    customSymbols.put(key, BASE_ADDRESS + customSymbols.size());
                }
            }
        }
    }

    private String parseCInstruction(String cmd) {
        // TODO
        return null;
    }

    private List<String> convertToBinary(List<String> asmCommands) {
        final List<String> binaryCode = new ArrayList<>();

        for (int i = 0; i < asmCommands.size(); i++) {
            final String cmd = asmCommands.get(i);

            if (IS_A_CMD.apply(cmd)) {
                final String key = cmd.replaceAll(A_CMD_PREFIX, "");

                if (PREDEFINED_SYMBOLS.get(key) != null) {
                    binaryCode.add(Integer.toBinaryString(PREDEFINED_SYMBOLS.get(key)));
                } else {
                    binaryCode.add(Integer.toBinaryString(customSymbols.get(key)));
                }
            } else if (!IS_L_CMD.apply(cmd)) {
                binaryCode.add(parseCInstruction(cmd));
            }
        }

        return binaryCode;
    }
}
