package day1

import java.io.File


fun List<Int>.calculateTripleDepth(): List<Int> {
    val list: MutableList<Int> = mutableListOf()
    forEachIndexed { i, _ ->
        if (i < this.lastIndex - 1) {
            val depthSum = this[i] + this[i + 1] + this[i + 2]
            list.add(depthSum)
        }
    }
    return list
}

fun main() {
    val depths = File("src/main/kotlin/day1/input.txt")
        .readLines()
        .map(String::toInt)
        .calculateTripleDepth() // delete this line to solve the first part

    val result = depths.windowed(2).count { (a, b) -> b > a }
    println(result)
}