package com.caiodorn.nand2tetris.assembly

import java.nio.file.Files
import spock.lang.Specification

import java.nio.file.Paths

class HackAssemblerTest extends Specification {

    def "should generate file containing expected binary code"() {
        when:
            new HackAssemblerApp().main("src/test/resources/input.asm")

        then:
            byte[] actualConvertedFile = Files.readAllBytes(Paths.get("input.hack"))
            byte[] expectedConvertedFile = Files.readAllBytes(Paths.get("src/test/resources/expectedOutput.hack"))
            actualConvertedFile.equals(expectedConvertedFile)
    }

    def "should throw exception if no file provided"() {
        when:
            new HackAssemblerApp().main()

        then:
            thrown(RuntimeException)
    }

}
