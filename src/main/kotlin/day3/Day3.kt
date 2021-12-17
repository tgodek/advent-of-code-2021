package day3

import java.io.File


/*********************** PART 1 ***********************/

data class Diagnostic(val zero: Int, val one: Int)

fun part1(input: List<String>): Int {

    val columnCount = input.first().length
    val report: MutableList<Diagnostic> = mutableListOf()
    var zero = 0
    var one = 0
    var gamma = ""
    var epsilon = ""

    for (column in 0 until columnCount) {
        for (row in input) {
            if (row[column] == '0') zero++
            if (row[column] == '1') one++
        }
        report.add(Diagnostic(zero, one))
        zero = 0
        one = 0
    }

    report.forEach { binary ->
        if (binary.zero > binary.one) {
            gamma += "0"
            epsilon += "1"
        }
        if (binary.zero < binary.one) {
            gamma += "1"
            epsilon += "0"
        }
    }
    return gamma.toInt(2) * epsilon.toInt(2)
}


/*********************** PART 2 ***********************/

fun MutableList<Char>.getMaxOccurrence(): Char? {
    return this
        .groupBy { it }
        .mapValues { it.value.size }.entries
        .sortedByDescending { it.key }
        .maxByOrNull { it.value }?.key
}

fun MutableList<Char>.getMinOccurrence(): Char? {
    return this
        .groupBy { it }
        .mapValues { it.value.size }.entries
        .sortedBy { it.key }
        .minByOrNull { it.value }?.key
}


fun part2(input: List<String>) : Int {

    val columnCount = input.first().length
    var oxygenMatches: MutableList<String> = input.toMutableList()
    var co2Matches: MutableList<String> = input.toMutableList()
    lateinit var oxygenDigits: MutableList<Char>
    lateinit var co2Digits: MutableList<Char>

    for (column in 0 until columnCount) {
        oxygenDigits = mutableListOf()
        co2Digits = mutableListOf()

        for (row in oxygenMatches)
            oxygenDigits.add(row[column])
        for (row in co2Matches)
            co2Digits.add(row[column])

        val maxOccurrence = oxygenDigits.getMaxOccurrence()
        val minOccurrence = co2Digits.getMinOccurrence()
        oxygenMatches = oxygenMatches.filter { it[column] == maxOccurrence }.toMutableList()
        co2Matches = co2Matches.filter { it[column] == minOccurrence }.toMutableList()
    }

    val oxygenGeneratorRating = oxygenMatches.first().toInt(2)
    val co2ScrubberRating = co2Matches.first().toInt(2)

    return oxygenGeneratorRating * co2ScrubberRating
}


fun main() {
    val input = File("src/main/kotlin/day3/input.txt").readLines()
    println(part1(input))
    println(part2(input))
}