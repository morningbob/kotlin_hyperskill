package search

import java.io.File

fun main(args: Array<String>) {
    //println("Please enter the file name: ")
    var peopleTextList = mutableListOf<String>()
    //val input = readln().split(" ")
    if (args.contains("--data")) {
        peopleTextList = File(args.last()).readLines().toMutableList()
    }
    //val file

    /*
    println("Enter the number of people: ")
    val numPeople = readln().toInt()
    println("Enter all people: ")
    val peopleTextList = mutableListOf<String>()
    for (i in 0..numPeople - 1) {
        peopleTextList.add(readln())
    }
*/
    var goOn = true
    while (goOn) {
        showMenu()
        val option = readln().first()
        if (option == '0') {
            println("\nBye!\n")
            goOn = false
        }
        println(executeOption(option, peopleTextList))
    }



}

private fun showMenu() {
    println("\n=== Menu ===")
    println("1. Search information.")
    println("2. Print all data.")
    println("0. Exit.")
}

private fun executeOption(option: Char, peopleTextList: List<String>) : String {
    var output = ""
    when (option) {
        '1' -> output += findPerson(peopleTextList)
        '2' -> output += printAllPeople(peopleTextList)
        else -> output = "Incorrect option! Try again."
    }
    return output
}

private fun findPerson(peopleTextList: List<String>) : String {
    var output = ""

    println("Enter a name or email to search all suitable people.")

    val searchTerm = readln()
    val result = search(searchTerm, peopleTextList)
    if (result.isNotEmpty()) {
        output += "People found:\n"
        for (each in result) {
            //println(each)
            output += "$each\n"
        }
    } else {
        output += "No matching people found."
    }
    return output
}

private fun search(searchTerm: String, peopleTextList: List<String>) : List<String> {
    val result = mutableListOf<String>()
    for (each in peopleTextList) {
        if (each.toLowerCase().contains(searchTerm.toLowerCase())) {
            result.add(each)
        }
    }
    return result
}

private fun printAllPeople(peopleTextList: List<String>) : String {
    var output = ""
    output += "=== List of people ===\n"
    for (each in peopleTextList) {
        output += "$each\n"
    }
    return output
}

