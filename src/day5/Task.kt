package day5

import readInput
import kotlin.math.abs

//https://adventofcode.com/2021/day/5
data class Point(val x: Int, val y: Int)

fun main() {
    fun parsePoint(input1: String) =
        "\\d+".toRegex().findAll(input1).map { matchResult -> matchResult.value.toInt() }.let {
            Point(it.first(), it.last())
        }

    fun printMap(linesMap: MutableMap<Point, Int>) {
        (0..9).forEach { y ->
            (0..9).forEach { x ->
                print(linesMap[Point(x, y)] ?: ".")
            }
            println()
        }
    }

    fun part1(input: List<String>): Int {
        val pointPairs = input.map {
            val split = it.split("->")
            val origin = parsePoint(split.first())
            val destination = parsePoint(split.last())
            origin to destination
        }
        val relevantPoints = pointPairs.filter { it.first.x == it.second.x || it.first.y == it.second.y }
        val linesMap = mutableMapOf<Point, Int>()
        relevantPoints.forEach {
            val dx = abs(it.second.x - it.first.x)
            val dy = abs(it.second.y - it.first.y)
            if (dx > 0) {
                (minOf(it.second.x, it.first.x)..maxOf(it.second.x, it.first.x)).forEach { index ->
                    linesMap.merge(Point(index, it.second.y), 1) { a, b -> a + b }
                }
            } else if (dy > 0) {
                (minOf(it.second.y, it.first.y)..maxOf(it.second.y, it.first.y)).forEach { index ->
                    linesMap.merge(Point(it.second.x, index), 1) { a, b -> a + b }
                }
            }
        }
        return linesMap.filter { it.value > 1 }.size
    }


    fun part2(input: List<String>): Int {
        val pointPairs = input.map {
            val split = it.split("->")
            val origin = parsePoint(split.first())
            val destination = parsePoint(split.last())
            origin to destination
        }
        val linesMap = mutableMapOf<Point, Int>()
        pointPairs.forEach {
            val dx = abs(it.second.x - it.first.x)
            val dy = abs(it.second.y - it.first.y)
            if (dx > 0 && dy > 0) {
                val zip =
                    IntProgression.fromClosedRange(it.first.x, it.second.x, if (it.first.x < it.second.x) 1 else -1)
                        .zip(
                            IntProgression.fromClosedRange(
                                it.first.y,
                                it.second.y,
                                if (it.first.y < it.second.y) 1 else -1
                            )
                        )
                zip.forEach { list ->
                    linesMap.merge(Point(list.first, list.second), 1) { a, b -> a + b }
                }
            } else if (dx > 0) {
                IntProgression.fromClosedRange(it.first.x, it.second.x, if (it.first.x < it.second.x) 1 else -1)
                    .forEach { index ->
                        linesMap.merge(Point(index, it.second.y), 1) { a, b -> a + b }
                    }
            } else if (dy > 0) {
                IntProgression.fromClosedRange(it.first.y, it.second.y, if (it.first.y < it.second.y) 1 else -1)
                    .forEach { index ->
                        linesMap.merge(Point(it.second.x, index), 1) { a, b -> a + b }
                    }
            }
        }
        val result = linesMap.filter { it.value > 1 }.size
        printMap(linesMap)
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day5/Task_test")
    val input = readInput("day5/Task")

    check(part1(testInput) == 5)
    println(part1(input))

    check(part2(testInput) == 12)
    println(part2(input))
}
