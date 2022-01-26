package day5

import java.io.File

data class Vent(val firstSegment: Pair<Int, Int>, val secondSegment: Pair<Int, Int>)

sealed class MappingSystem {
    object HorizontalLines : MappingSystem()
    object VerticalLines : MappingSystem()
    object DiagonalLines : MappingSystem()
    object LinesAt45Deg : MappingSystem()
    object BasicPair : MappingSystem()
}

fun getMappingSystem(vent: Vent): MappingSystem {
    if (vent.firstSegment.first == vent.secondSegment.first && vent.firstSegment.second != vent.secondSegment.second)
        return MappingSystem.VerticalLines

    if (vent.firstSegment.second == vent.secondSegment.second && vent.firstSegment.first != vent.secondSegment.first)
        return MappingSystem.HorizontalLines

    if (vent.firstSegment.first == vent.firstSegment.second && vent.secondSegment.first == vent.secondSegment.second)
        return MappingSystem.DiagonalLines

    if (vent.firstSegment.first == vent.secondSegment.second && vent.firstSegment.second == vent.secondSegment.first)
        return MappingSystem.LinesAt45Deg
    else return MappingSystem.BasicPair
}

fun getPairsForHorizontalLines(vent: Vent): List<Pair<Int, Int>> {
    val pairs: MutableList<Pair<Int, Int>> = mutableListOf()

    if (vent.firstSegment.first > vent.secondSegment.first)
        for (i in vent.secondSegment.first..vent.firstSegment.first)
            pairs.add(Pair(i, vent.firstSegment.second))
    else
        for (i in vent.firstSegment.first..vent.secondSegment.first)
            pairs.add(Pair(i, vent.firstSegment.second))

    return pairs
}

fun getPairsForVerticalLines(vent: Vent): List<Pair<Int, Int>> {
    val pairs: MutableList<Pair<Int, Int>> = mutableListOf()

    if (vent.firstSegment.second < vent.secondSegment.second)
        for (i in vent.firstSegment.second..vent.secondSegment.second)
            pairs.add(Pair(vent.firstSegment.first, i))
    else
        for (i in vent.secondSegment.second..vent.firstSegment.second)
            pairs.add(Pair(vent.firstSegment.first, i))

    return pairs
}


fun getPairsForDiagonalLines(vent: Vent): List<Pair<Int, Int>> {
    val pairs: MutableList<Pair<Int, Int>> = mutableListOf()

    if (vent.firstSegment.first < vent.secondSegment.first)
        for (i in vent.firstSegment.first..vent.secondSegment.first)
            pairs.add(Pair(i, i))
    else
        for (i in vent.secondSegment.first..vent.firstSegment.first)
            pairs.add(Pair(i, i))

    return pairs
}

fun getPairsForLinesAt45Deg(vent: Vent): List<Pair<Int, Int>> {
    val pairs: MutableList<Pair<Int, Int>> = mutableListOf()

    if (vent.firstSegment.first < vent.secondSegment.first) {
        var reverse = vent.firstSegment.second
        for (i in vent.firstSegment.first..vent.firstSegment.second) {
            pairs.add(Pair(i, reverse))
            reverse -= 1
        }

    } else {
        var reverse = vent.firstSegment.first
        for (i in vent.firstSegment.second..vent.firstSegment.first) {
            pairs.add(Pair(i, reverse))
            reverse -= 1
        }
        pairs.forEach { println(it) }
    }
    return pairs
}

fun getCoordinates(vent: Vent): List<Pair<Int, Int>> {
    return when (getMappingSystem(vent)) {
        MappingSystem.HorizontalLines -> getPairsForHorizontalLines(vent)
        MappingSystem.VerticalLines -> getPairsForVerticalLines(vent)
        MappingSystem.DiagonalLines -> getPairsForDiagonalLines(vent)
        MappingSystem.LinesAt45Deg -> getPairsForLinesAt45Deg(vent)
        MappingSystem.BasicPair -> listOf(vent.firstSegment,vent.secondSegment)
    }
}

fun getVentCoordinates(vents: List<Vent>): List<Pair<Int, Int>> {
    val coordinates: MutableList<Pair<Int, Int>> = mutableListOf()
    for (vent in vents)
        coordinates.addAll(getCoordinates(vent))
    return coordinates
}


fun part1(vents: List<Vent>): Int {
    val dangerZones = getVentCoordinates(vents)

    val matrix = Array(10) { Array(10) { 0 } }

    for (coordinate in dangerZones)
        matrix[coordinate.second][coordinate.first]++

    /*
    println()
    for (elem in matrix) {
        for (j in matrix.indices) {
            print(elem[j])
            print(" ")
        }
        println()
    }*/

    return dangerZones.groupBy { it }.filter { it.value.size >= 2 }.size


}

fun part2(vents: List<Vent>): Int {
    return 0
}

fun main() {
    val input = File("src/main/kotlin/day5/input.txt").readLines()
        .map { it.split("\\p{Punct}+".toRegex()) }
        .map { line -> line.mapNotNull { value -> value.replace(" ", "").toIntOrNull() } }
    val vents = input.map { Vent(Pair(it[0], it[1]), Pair(it[2], it[3])) }

    println(part1(vents))

}
