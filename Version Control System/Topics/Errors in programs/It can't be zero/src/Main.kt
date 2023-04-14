fun main() {
    val line1 = readln()!!.toInt()
    val line2 = readln()!!.toInt()

    val product = line1 * line2
    if (product == 0) {
        println("It can't be zero!")
    } else {
        println(product)
    }
}