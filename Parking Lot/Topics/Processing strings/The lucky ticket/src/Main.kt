fun main() {
    // write your code here
    val input = readln()
    var sum1 = 0
    var sum2 = 0
    for (i in 0..2) {
        sum1 += input[i].toString().toInt()
    }
    for (i in input.length-1 downTo input.length-3) {
        sum2 += input[i].toString().toInt()
    }
    println(if (sum1 == sum2) "Lucky" else "Regular")
}