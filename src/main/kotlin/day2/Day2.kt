package day2

import java.io.File

fun part1(input: File) {
    var horizontal = 0
    var depth = 0

    input.forEachLine {
        val (instruction, _amount) = it.split(' ')
        val amount = _amount.toInt()
        when (instruction) {
            "forward" -> horizontal += amount
            "down" -> depth += amount
            "up" -> depth -= amount
        }
    }
    println(horizontal * depth)
}

fun part2(input: File) {
    var horizontal = 0
    var depth = 0
    var aim = 0

    input.forEachLine {
        val (instruction, _amount) = it.split(' ')
        val amount = _amount.toInt()
        when (instruction) {
            "forward" -> {
                horizontal += amount
                depth += aim * amount
            }
            "down" -> aim += amount
            "up" -> aim -= amount
        }
    }
    println(horizontal * depth)
}

fun main() {
    val input = File("src/main/kotlin/day2/input.txt")
    part1(input)
    part2(input)
}