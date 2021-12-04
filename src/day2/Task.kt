package day2

import readInput
//https://adventofcode.com/2021/day/2
fun main() {
    val forward = "forward"
    val up = "up"
    val down = "down"

    fun part1(input: List<String>): Int {
        var depth = 0
        var horizontal = 0
        input.forEach {
            val value = "\\d+".toRegex().find(it)?.value?.toIntOrNull() ?: error("Input line should have a number")
            when {
                it.contains(forward) -> horizontal += value
                it.contains(up) -> depth -= value
                it.contains(down) -> depth += value
            }
        }
        return depth * horizontal
    }

    fun part2(input: List<String>): Int {
        var depth = 0
        var horizontal = 0
        var aim = 0
        input.forEach {
            val value = "\\d+".toRegex().find(it)?.value?.toIntOrNull() ?: error("Input line should have a number")
            when {
                it.contains(forward) -> {
                    horizontal += value
                    depth += aim * value
                }
                it.contains(up) -> {
                    aim -= value
                }
                it.contains(down) -> {
                    aim += value
                }
            }
        }
        return depth * horizontal
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day2/Task_test")
    check(part1(testInput) == 150)
    val input = readInput("day2/Task")
    println(part1(input))

    check(part2(testInput) == 900)
    println(part2(input))
}
