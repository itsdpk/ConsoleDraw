package com.console.draw.assessment.domain.enums

enum class Command(private val value: String) {
    CREATE("C"),
    LINE("L"),
    RECTANGLE("R"),
    BUCKET_FILL("B"),
    QUIT("Q"),
    NOOP("N");

    companion object {
        fun find(value: String): Command {
            for (command in values()) {
                if (command.value == value) {
                    return command
                }
            }
            return NOOP
        }
    }
}