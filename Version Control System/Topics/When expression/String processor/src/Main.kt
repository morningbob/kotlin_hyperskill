fun main() {
    // write your code here
    val stringOne = readln()
    val operation = readln()
    val stringTwo = readln()

    when (operation) {
        "equals" -> println(stringOne == stringTwo)
        "plus" -> println(stringOne + stringTwo)
        "endsWith" -> {
            //if (stringOne.contains(stringTwo)) {
                //println(stringOne.indexOf(stringTwo) == (stringOne.length - stringTwo.length))
                //println(stringOne.substring(stringOne.length - 1 - (stringTwo.length -1)) == stringTwo)
            //} else {
            //    println("false")
            //}
            println(stringOne.endsWith(stringTwo))
        }
        else -> println("Unknown operation")
    }
}