package day4

import readInput

//https://adventofcode.com/2021/day/4
typealias Row = List<Value>
typealias Column = List<Value>

class Value(
    val value: String,
    var matched: Boolean = false,
    var row: Row = emptyList(),
    var column: Column = emptyList()
)

data class Board(
    val rows: List<Row>,
    val columns: List<Column>,
    val valueMap: Map<String, Value>,
    var winningNumber: String? = null
) {
}

class Game private constructor(val numberSequence: List<String>, val boards: List<Board>) {
    companion object Factory {
        fun createGame(input: List<String>): Game {
            val fullInput = input.joinToString("\n")
            val parts = fullInput.split("(?m)^\\s*?$".toRegex())
            val numberSequence = parts.first().split(",").map { it.trim() }
            val boards = mutableListOf<Board>()
            parts.drop(1).map {
                val rows = mutableListOf<Row>()
                val columns = mutableListOf<Column>()
                val valueMap = mutableMapOf<String, Value>()
                val board = Board(rows, columns, valueMap)
                rows.addAll(
                    it.trim().split("\\n".toRegex())
                        .map { "\\d+".toRegex().findAll(it).map { Value(it.value) }.toList() })
                columns.addAll((0 until rows.first().size).map { index ->
                    rows.map {
                        it[index]
                    }
                })
                rows.forEach { row ->
                    row.forEach {
                        it.row = row
                        valueMap[it.value] = it
                    }
                }
                columns.forEach { column -> column.forEach { it.column = column } }
                boards.add(board)
            }
            return Game(numberSequence, boards)
        }
    }

    fun winners(): List<Board> {
        val winners= mutableListOf<Board>()
        val boards=boards.toMutableList()
        numberSequence.forEach { currentValue ->
            val toRemove= mutableListOf<Board>()
            boards.forEach { board ->
                val value = board.valueMap[currentValue]
                if(value!=null) {
                    value.matched = true
                    if (value.row.all { it.matched } || value.column.all { it.matched }) {
                        board.winningNumber = currentValue
                        winners.add(board)
                        toRemove.add(board)
                    }
                }
            }
            boards.removeAll(toRemove)
        }
        return winners
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val game = Game.createGame(input)
        val winner = game.winners().first()
        val result = winner.winningNumber!!.toInt() * winner.valueMap.entries.filter { !it.value.matched }
            .sumOf { it.value.value.toInt() }
        return result
    }

    fun part2(input: List<String>): Int {
        val game = Game.createGame(input)
        val winner = game.winners().last()
        val result = winner.winningNumber!!.toInt() * winner.valueMap.entries.filter { !it.value.matched }
            .sumOf { it.value.value.toInt() }
        return result
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day4/Task_test")
    val input = readInput("day4/Task")

    check(part1(testInput) == 4512)
    println(part1(input))

    check(part2(testInput) == 1924)
    println(part2(input))
}
