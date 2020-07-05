package com.caiodorn.nand2tetris.assembly

import java.nio.file.Files
import spock.lang.Specification

import java.nio.file.Paths;

class HackAssemblerTest extends Specification {

    def "should generate hack file"() {
        when:
            new HackAssembler().main("input.asm")

        then:
            Files.exists(Paths.get("input.hack"))
    }

    def "should throw exception if no file provided"() {
        when:
            new HackAssembler().main()

        then:
            thrown(RuntimeException)
    }

}
