package day1

import readInputWithoutBlankLines
//https://adventofcode.com/2021/day/1
fun main() {
    fun part1(input: List<Int>): Int {
        return input.filterIndexed { index, after ->
            input.getOrNull(index - 1)?.let { before -> after > before } ?: false
        }.size
    }

    fun toIntList(input: List<String>) = input.map { it.toInt() }

    fun part1(input: List<String>): Int {
        return part1(toIntList(input))
    }

    fun part2(input: List<String>): Int {
        val intsInput = toIntList(input)
        val windowValues = intsInput.mapIndexedNotNull { index, tail ->
            val values = listOf(intsInput.getOrNull(index - 2), intsInput.getOrNull(index - 1), tail)
            if (values.contains(null))
                null
            else {
                values.sumOf { it ?: error("There should be no nulls here") }
            }
        }
        return part1(windowValues)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInputWithoutBlankLines("day1/Task_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInputWithoutBlankLines("day1/Task")
    println(part1(input))
    println(part2(input))
}
