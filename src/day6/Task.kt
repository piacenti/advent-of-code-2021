package day6

import readInput

//https://adventofcode.com/2021/day/6
class Fish(var internalTimer: Int) {
    fun decrementTimer(): Boolean {
        if (internalTimer > 0) {
            internalTimer--
        } else {
            internalTimer = 6
            return true
        }
        return false
    }
}

class SchoolOfFish {
    val fishes: MutableList<Fish> = mutableListOf()
    fun passDay() {
        val fishesCopy = fishes.toList()
        fishesCopy.forEach {
            if (it.decrementTimer())
                fishes.add(Fish(8))
        }
    }
}

// Update for part 2
class SchoolOfFish2 {
    val total: Long
        get() = map.values.sum()
    var map = mutableMapOf<Int, Long>()
    fun passDay() {
        val newMap = mutableMapOf<Int, Long>()
        map.entries.forEach {
            if (it.key > 0) {
                newMap.merge(it.key - 1, it.value) { a, b -> a + b }
            } else {
                newMap.merge(6, it.value) { a, b -> a + b }
                newMap.merge(8, it.value) { a, b -> a + b }
            }
        }
        map = newMap
    }
}


fun main() {

    fun part1(input: List<String>): Long {
        val schoolOfFish = SchoolOfFish()
        val initialFish = input.first().split(",").map { it.trim().toInt() }.map { Fish(it) }
        schoolOfFish.fishes.addAll(initialFish)
        (0 until 80).forEach { schoolOfFish.passDay() }
        return schoolOfFish.fishes.size.toLong()
    }

    fun part2(input: List<String>, days: Int): Long {
        val schoolOfFish = SchoolOfFish2()
        input.first().split(",").map { it.trim().toInt() }.forEach {
            schoolOfFish.map.merge(it, 1) { a, b -> a + b }
        }
        (0 until days).forEach {
            schoolOfFish.passDay()
        }
        val result = schoolOfFish.total
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day6/Task_test")
    val input = readInput("day6/Task")

    check(part1(testInput) == 5934L)
    println(part1(input))

    check(part2(testInput, 18) == 26L)
    check(part2(testInput, 80) == 5934L)
    check(part2(testInput, 256) == 26984457539)
    println(part2(input, 256))
}
