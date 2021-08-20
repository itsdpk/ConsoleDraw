package com.console.draw.assessment.domain.enums

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class CommandTest{

    @Test
    fun `check enum resolve to values`(){
        assertEquals(Command.CREATE,Command.find("C"))
    }
    @Test
    fun `check enum resolve to NOOP when could not locate value`(){
        assertEquals(Command.NOOP,Command.find("G"))
    }
}