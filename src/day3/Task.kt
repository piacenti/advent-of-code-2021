package day3

import readInput

enum class Frequency { MOST_COMMON, LEAST_COMMON }
enum class Type { OXYGEN, CO2 }
//https://adventofcode.com/2021/day/3
fun main() {


    fun bitsForFrequencyType(length: Int, input: List<String>, frequency: Frequency): String {
        var bits = ""
        for (column in 0 until length) {
            val groupBy = input.map { it[column] }.groupBy { it }
            val factor =
                if (frequency == Frequency.MOST_COMMON) groupBy.maxByOrNull { it.value.size } else groupBy.minByOrNull { it.value.size }
            bits += factor?.key.toString()
        }
        return bits
    }

    fun bitsForType(length: Int, input: List<String>, type: Type): String {
        var list = input
        for (column in 0 until length) {
            val groupBy = list.groupBy { it[column] }
            list = if (groupBy.size == 2 && groupBy.values.flatten().size == 2) {
                list.filter { it[column] == if (type == Type.OXYGEN) '1' else '0' }
            } else {
                val factor =
                    if (type == Type.OXYGEN) groupBy.maxByOrNull { it.value.size } else groupBy.minByOrNull { it.value.size }
                factor?.value ?: error("test")
            }
        }
        return list.first()
    }

    fun part1(input: List<String>): Int {
        val length = input.first().length
        val gamaRateBits = bitsForFrequencyType(length, input, Frequency.MOST_COMMON)
        val epsilonRateBits = bitsForFrequencyType(length, input, Frequency.LEAST_COMMON)
        val gamaRate = gamaRateBits.toInt(2)
        val epsilonRate = epsilonRateBits.toInt(2)
        return gamaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        val length = input.first().length
        val oxygenRateBits = bitsForType(length, input, Type.OXYGEN)
        val co2RateBits = bitsForType(length, input, Type.CO2)
        val oxygenRate = oxygenRateBits.toInt(2)
        val co2Rate = co2RateBits.toInt(2)
        return oxygenRate * co2Rate
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day3/Task_test")
    val input = readInput("day3/Task")

    check(part1(testInput) == 198)
    println(part1(input))

    check(part2(testInput) == 230)
    println(part2(input))
}
