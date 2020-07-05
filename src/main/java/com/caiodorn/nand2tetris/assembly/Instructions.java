package com.caiodorn.nand2tetris.assembly;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Instructions {

    public static final Map<String, String> COMP;
    public static final Map<String, String> DEST;
    public static final Map<String, String> JUMP;

    static {
        Map<String, String> c = new HashMap<>();
        c.put("0",   "101010");
        c.put("1",   "111111");
        c.put("-1",  "111010");
        c.put("D",   "001100");
        c.put("A",   "110000");
        c.put("M",   "110000");
        c.put("!D",  "001101");
        c.put("!A",  "110001");
        c.put("!M",  "110001");
        c.put("-D",  "001111");
        c.put("-A",  "110011");
        c.put("-M",  "110011");
        c.put("D+1", "011111");
        c.put("A+1", "110111");
        c.put("M+1", "110111");
        c.put("D-1", "001110");
        c.put("A-1", "110010");
        c.put("M-1", "110010");
        c.put("D+A", "000010");
        c.put("D+M", "000010");
        c.put("D-A", "010011");
        c.put("D-M", "010011");
        c.put("A-D", "000111");
        c.put("M-D", "000111");
        c.put("D&A", "000000");
        c.put("D&M", "000000");
        c.put("D|A", "010101");
        c.put("D|M", "010101");
        COMP = Collections.unmodifiableMap(c);

        Map<String, String> d = new HashMap<>();
        d.put("null", "000");
        d.put("M",    "001");
        d.put("D",    "010");
        d.put("MD",   "011");
        d.put("A",    "100");
        d.put("AM",   "101");
        d.put("AD",   "110");
        d.put("AMD",  "111");
        DEST = Collections.unmodifiableMap(d);

        Map<String, String> j = new HashMap<>();
        j.put("null", "000");
        j.put("JGT",  "001");
        j.put("JEQ",  "010");
        j.put("JGE",  "011");
        j.put("JLT",  "100");
        j.put("JNE",  "101");
        j.put("JLE",  "110");
        j.put("JMP",  "111");
        JUMP = Collections.unmodifiableMap(j);
    }

    private Instructions() {}

}
