fun main() {
    // put your code here
    val input = readln()
    var i = 0
    while (i < input.length) {
        if (input[i].isDigit()) {
            println(input[i])
            break
        }
        i++
    }
}
