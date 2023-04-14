fun main(args: Array<String>) {
    // write your code here
    val shape = readln()
    //if (args.isEmpty()) {
    //    println("There is no such shape!")
    //} else {
        //val shape = args[0]
        var name = ""
        when (shape) {
            "1" -> name = "square"
            "2" -> name = "circle"
            "3" -> name = "triangle"
            "4" -> name = "rhombus"
            else -> println("There is no such shape!")
        }
        if (name != "") {
            println("You have chosen a $name")
        }
    //}
}