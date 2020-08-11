package com.caiodorn.nand2tetris.assembly

import java.nio.file.Files
import spock.lang.Specification

import java.nio.file.Paths

class HackAssemblerAppTest extends Specification {

    def "should generate file containing expected binary code"() {
        when:
            new HackAssemblerApp().main("src/test/resources/input.asm")

        then:
            List<String> actualConvertedFile = Files.readAllLines(Paths.get("input.hack"))
            List<String> expectedConvertedFile = Files.readAllLines(Paths.get("src/test/resources/expectedOutput.hack"))
            actualConvertedFile == expectedConvertedFile
    }

    def "should throw exception if no file provided"() {
        when:
            new HackAssemblerApp().main()

        then:
            thrown(RuntimeException)
    }

}
