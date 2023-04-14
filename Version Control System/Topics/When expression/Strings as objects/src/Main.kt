fun main() {
    val input = readLine()!!
    // write code here
    if (input != "") {
        when (input.first()) {
            'i' -> {
                val new = input.drop(1)
                val final = new.toInt() + 1
                println(final)
            }

            's' -> {
                val new = input.drop(1)
                val final = new.reversed()
                println(final)
            }

            else -> println(input)
        }
    } else {
        println("")
    }
}