package day4

import java.io.File


data class Board(var grid: List<List<Field>>, val boardNumber: Int)

data class Field(val number: Int, var Marked: Boolean = false)


private fun checkWinner(grid: List<List<Field>>): Boolean {
    for (i in grid.indices) {
        if (grid[i].all { it.Marked }) return true

        var verticalSum = 0
        for (j in grid.indices) {
            if (!grid[j][i].Marked) break
            else verticalSum += 1
        }
        if (verticalSum == 5)
            return true
    }
    return false
}

fun calculateResult(lastNumber: Int, board: Board): Int {
    val unmarkedSum = board.grid.flatten().filter { !it.Marked }.sumOf { it.number }
    return unmarkedSum * lastNumber
}


private fun setupBingoBoards(
    rawData: List<String>,
): List<Board> {
    val boards: MutableList<Board> = mutableListOf()
    for ((boardNumber, i) in (rawData.indices step 5).withIndex()) {
        val grid: MutableList<List<Field>> = mutableListOf()
        val lastRow = i + 4

        for (elem in i..lastRow) {
            val boardRow: List<Field> =
                rawData[elem].split(" ").mapNotNull { it.toIntOrNull() }.map { Field(it) }
            grid.add(boardRow)
        }
        val board = Board(grid = grid, boardNumber = boardNumber + 1)
        boards.add(board)
    }
    return boards
}

fun part1(input: List<String>): Any {
    val drawnNumbers = input.first().split(',').map { it.toInt() }
    val rawData = input.filter { it.contains(' ') && it.isNotEmpty() }.map { it.replace("  ", " ") }
    val bingoBoards = setupBingoBoards(rawData)

    for (number in drawnNumbers) {
        for (board in bingoBoards) {
            board.grid.forEach { row -> row.find { it.number == number }?.Marked = true }
            val winner = checkWinner(board.grid)
            if (winner) return calculateResult(number, board)
        }
    }
    throw Exception("Nobody won")
}


fun part2(input: List<String>): Any {
    val drawnNumbers = input.first().split(',').map { it.toInt() }
    val rawData = input.filter { it.contains(' ') && it.isNotEmpty() }.map { it.replace("  ", " ") }
    val bingoBoards = setupBingoBoards(rawData)
    var winningBoards: MutableList<Int> = mutableListOf()
    var lastBoard: Board? = null
    var lastDrawnNumber: Int? = null

    for (number in drawnNumbers) {
        for (board in bingoBoards) {
            if (!winningBoards.contains(board.boardNumber)) {
                board.grid.forEach { row -> row.find { it.number == number }?.Marked = true }
                val winner = checkWinner(board.grid)
                if (winner) {
                    winningBoards.add(board.boardNumber)
                    winningBoards = winningBoards.distinct().toMutableList()
                    lastBoard = board
                    lastDrawnNumber = number
                }
            }
        }
    }

    if (lastBoard != null && lastDrawnNumber != null) {
        return calculateResult(lastDrawnNumber, lastBoard)
    }
    throw Exception("Nobody won")
}


fun main() {
    val input = File("src/main/kotlin/day4/input.txt").readLines()
    println(part1(input))
    println(part2(input))
}