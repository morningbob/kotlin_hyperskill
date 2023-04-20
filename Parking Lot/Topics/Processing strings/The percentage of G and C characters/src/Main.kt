fun main() {
    // write your code here
    val input = readln()
    var count = 0
    for (each in input) {
        if (each.lowercase() in "gc") {
            count += 1
        }
    }
    var result = (count.toDouble() / input.length ) * 100

    println(result)
}