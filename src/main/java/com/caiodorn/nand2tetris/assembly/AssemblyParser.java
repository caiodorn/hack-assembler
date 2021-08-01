package com.caiodorn.nand2tetris.assembly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class AssemblyParser {

    private static final int BASE_ADDRESS = 16;
    private static final Map<String, Integer> PREDEFINED_SYMBOLS;
    private static final String A_CMD_PREFIX = "@";
    private static final String C_INST_BINARY_PREFIX = "111";
    private static final Predicate<String> IS_A_CMD = cmd -> cmd.startsWith(A_CMD_PREFIX);
    private static final Predicate<String> IS_L_CMD = cmd -> cmd.startsWith("(");
    private static final String EMPTY = "";
    private static final String LABEL_REGEXP = "[()]";

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
        for (int i = 0, count = 0; i < asmCommands.size(); i++) {
            final String cmd = asmCommands.get(i);

            if (IS_L_CMD.test(cmd)) {
                final String key = cmd.replaceAll(LABEL_REGEXP, EMPTY);
                customSymbols.put(key, i - count++);
            }
        }
    }

    private void mapSymbols(List<String> asmCommands) {
        int count = 0;
        for (final String cmd : asmCommands) {
            if (IS_A_CMD.test(cmd)) {
                final String key = cmd.replace(A_CMD_PREFIX, EMPTY);

                try {
                    Integer.parseUnsignedInt(key);
                } catch (NumberFormatException nfe) {
                    if (!(PREDEFINED_SYMBOLS.containsKey(key) || customSymbols.containsKey(key))) {
                        customSymbols.put(key, BASE_ADDRESS + count++);
                    }
                }
            }
        }
    }

    private String parseCInstruction(String cmd) {
        String[] parts = cmd.split(";");
        String[] compDest = parts[0].split("=");
        boolean hasJump = parts.length > 1;
        String jump;
        String dest;
        String comp;

        if (hasJump) {
            jump = CInstructions.JUMP.get(parts[1]);

            if (compDest.length > 1) {
                dest = CInstructions.DEST.get(compDest[0]);
                comp = CInstructions.COMP.get(compDest[1]);
            } else {
                dest = CInstructions.DEST.get(null);
                comp = CInstructions.COMP.get(compDest[0]);
            }
        } else {
            jump = CInstructions.JUMP.get(null);
            dest = CInstructions.DEST.get(compDest[0]);
            comp = CInstructions.COMP.get(compDest[1]);
        }

        String aOpCode = CInstructions.getAOpCode(compDest.length > 1 ? compDest[1] : compDest[0]);

        return C_INST_BINARY_PREFIX + aOpCode + comp + dest + jump;
    }

    private List<String> convertToBinary(List<String> asmCommands) {
        final List<String> binaryCode = new ArrayList<>();

        for (final String cmd : asmCommands) {
            if (IS_A_CMD.test(cmd)) {
                final String key = cmd.replaceAll(A_CMD_PREFIX, EMPTY);
                Integer intValue;

                try {
                    intValue = Integer.parseUnsignedInt(key);
                } catch (NumberFormatException nfe) {
                    if (PREDEFINED_SYMBOLS.get(key) != null) {
                        intValue = PREDEFINED_SYMBOLS.get(key);
                    } else {
                        intValue = customSymbols.get(key);
                    }
                }

                binaryCode.add(String.format("%16s", Integer.toBinaryString(intValue)).replace(' ', '0'));

            } else if (!IS_L_CMD.test(cmd)) {
                binaryCode.add(parseCInstruction(cmd));
            }
        }

        return binaryCode;
    }
}
