fun main() {
    // put your code here
    val input = readln().first()

    val all = "abcdefghijklmnopqrstuvwxyz"
    var new = ""
    for (each in all) {
        if (input != each) {
            new += each
        } else {
            break
        }
    }
    println(new)
}