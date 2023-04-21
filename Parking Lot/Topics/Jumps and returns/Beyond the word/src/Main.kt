fun main() {
    // put your code here
    val input = readln()
    var all = "abcdefghijklmnopqrstuvwxyz"
    var new = ""
    for (each in input) {
        new = all.filterNot { it in input }
    }
    println(new)
}
