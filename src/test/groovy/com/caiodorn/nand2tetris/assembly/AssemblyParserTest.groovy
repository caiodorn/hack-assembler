package com.caiodorn.nand2tetris.assembly

import spock.lang.Specification

class AssemblyParserTest extends Specification {

    def "should parse basic A-instructions"(String aInst) {
        def asmCode = new ArrayList<>()
        asmCode.add(aInst)
        def aInstNumOnly = Integer.parseInt(aInst.replace("@", ""))
        def expectedBinaryCode = String.format("%16s", Integer.toBinaryString(aInstNumOnly)).replace(' ', '0')

        expect:
            def actualBinaryCode = new AssemblyParser().parse(asmCode).get(0)
            expectedBinaryCode.equals(actualBinaryCode)

        where:
            aInst << ["@1", "@4", "@1234", "@10000", "@9999"]
    }

    def "should parse A-instructions containing custom symbols"() {
        given:
            def asmCode = new ArrayList<>()
            asmCode.add("@i")
            asmCode.add("@t")
            asmCode.add("@END")
            asmCode.add("@z")

            def memAddr = new HashMap<>()
            memAddr.put(asmCode.get(0), 16)
            memAddr.put(asmCode.get(1), 17)
            memAddr.put(asmCode.get(2), 18)
            memAddr.put(asmCode.get(3), 19)

        when:
            def actualBinaryCode = new AssemblyParser().parse(asmCode)

        then:
            actualBinaryCode.size().equals(asmCode.size())

            for (def cmd : actualBinaryCode) {
                actualBinaryCode.contains(Integer.toBinaryString(Integer.parseInt(cmd)))
            }
    }

    def "should map L-instructions to next line of code"() {
        given:
            def asmCode = new ArrayList<>()
            asmCode.add("(LOOP)")
            asmCode.add("@i")
            asmCode.add("@LOOP")

            def memAddr = new HashMap<>()
            memAddr.put(asmCode.get(0), 1)
            memAddr.put(asmCode.get(1), 16)
            memAddr.put(asmCode.get(2), 1)

            def numberOfLinesWithoutLabelDeclarations = 2

        when:
            def actualBinaryCode = new AssemblyParser().parse(asmCode)

        then:
            actualBinaryCode.size().equals(numberOfLinesWithoutLabelDeclarations)

            for (def cmd : actualBinaryCode) {
                actualBinaryCode.contains(Integer.toBinaryString(Integer.parseInt(cmd)))
            }
    }

}
