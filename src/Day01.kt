fun main() {
    fun part1(input: List<Int>): Int {
        return input.filterIndexed { index, after ->
            input.getOrNull(index - 1)?.let { before -> after > before } ?: false
        }.size
    }

    fun part1(input: List<String>): Int {
        return part1(input.filter { it.isNotBlank() }.map { it.toInt() })
    }

    fun part2(input: List<String>): Int {
        val windowValues = input.filter { it.isNotBlank() }.mapIndexedNotNull { index, tail ->
            val values = listOf(input.getOrNull(index - 2), input.getOrNull(index - 1), tail)
            if (values.contains(null))
                null
            else {
                values.sumOf { it?.toInt() ?: error("There should be no nulls here") }
            }
        }
        return part1(windowValues)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
