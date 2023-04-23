package processor

fun main() {

    // need to calculate how many lines to read
    // the first line, with 2 digits, the first digit, the number of line
    val dimension = readln().split(" ")

    val totalRow = dimension[0].toInt()
    val totalColumn = dimension[1].toInt()

    //val rows : IntArray = intArrayOf(totalColumn)
    val matrix : Array<IntArray> = Array(totalRow) { IntArray(totalColumn) }

    for (i in 1..totalRow) {
        val input = readln().split(" ")

    }


}
