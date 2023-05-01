package search

import java.io.File

fun main(args: Array<String>) {
    //println("Please enter the file name: ")
    var peopleTextList = mutableListOf<String>()
    //val input = readln().split(" ")
    if (args.contains("--data")) {
        peopleTextList = File(args.last()).readLines().toMutableList()
    }
    val invertedTable = createInvertedIndex(peopleTextList)

    var goOn = true
    while (goOn) {

        val option = showMenu()
        //val strategyOption = getStrategy()

        if (option == '0') {
            println("\nBye!\n")
            goOn = false
        }
        println(executeOption(option, peopleTextList, invertedTable))
    }

}

enum class Strategy {
    ALL,
    ANY,
    NONE
}

private fun showMenu() :  Char {
    println("\n=== Menu ===")
    println("1. Search information.")
    println("2. Print all data.")
    println("0. Exit.")

    return readln().first()
}

private fun getStrategy() : Strategy? {
    //var strategyOption : Strategy? = null
    println("Select a matching strategy: ALL, ANY, NONE")
    val strategyInput = readln()
    for (strategy in Strategy.values()) {
        if (strategy.name.toLowerCase() == strategyInput.toLowerCase()) {
            return strategy
        }
    }
    return null
}

private fun executeOption(option: Char, peopleTextList: List<String>,
                          invertedTable: MutableMap<String, List<Int>>) : String {
    var output = ""
    when (option) {
        '1' -> {
            val strategy = getStrategy()!!
            output += findPerson(peopleTextList, invertedTable, strategy)
        }
        '2' -> output += printAllPeople(peopleTextList)
        else -> output = "Incorrect option! Try again."
    }
    return output
}

private fun findPerson(peopleTextList: List<String>,
                       invertedTable: MutableMap<String, List<Int>>,
                       strategy: Strategy) : String {

    println("Enter a name or email to search all suitable people.")

    val searchTerm = readln()
    return searchTable(invertedTable, searchTerm, peopleTextList, strategy)

}

private fun createInvertedIndex(peopleTextList: List<String>) : MutableMap<String, List<Int>> {
    var invertedTable = mutableMapOf<String, List<Int>>()

    for (i  in 0..peopleTextList.size - 1) {
        val list = peopleTextList[i].split(" ")
        for (word in list) {
            // check if there is existing entry for it
            if (invertedTable[word.toLowerCase()] == null) {
                invertedTable.put(word.toLowerCase(), listOf(i))
            } else {
                val oldList = invertedTable[word.toLowerCase()]
                val newList = oldList!!.toMutableList()
                newList.add(i)
                invertedTable.put(word.toLowerCase(), newList)
            }
        }
    }
    return invertedTable
}

private fun searchTable(invertedTable: MutableMap<String, List<Int>>,
                        searchTerm: String, peopleTextList: List<String>,
                        strategy: Strategy) : String {
    var output = ""
    val searchTermList = searchTerm.split(" ")
    var tempResult = mutableListOf<List<Int>>()
    //var count = 0

    for (term in searchTermList) {
        var result = invertedTable[term.toLowerCase()]
        if (result != null) {
            tempResult.add(result)
        }
    }

    var resultIndexList = mutableListOf<Int>()
    for (i in 0..peopleTextList.size - 1) {
        var count = 0
        for (each in tempResult) {
            if (each.contains(i)) {
                count++
                //println("found 1 ")
            }
        }
        when (strategy) {
            Strategy.ALL -> {
                if (count >= searchTermList.size) {
                    //println("added 1 to result")
                    resultIndexList.add(i)
                }
            }
            Strategy.ANY, Strategy.NONE -> {
                if (count > 0) {
                    resultIndexList.add(i)
                }
            }

        }

    }
    val newResultList = mutableListOf<Int>()
    if (strategy == Strategy.NONE) {

        for (i in 0..peopleTextList.size -1) {
            if (!resultIndexList.contains(i)) {
                newResultList.add(i)
            }
        }
        resultIndexList = newResultList
    }


    //val resultIndexList = invertedTable[searchTerm]
    if (resultIndexList.isNotEmpty()) {
        for (each in resultIndexList) {
            output += "${peopleTextList[each]}\n"
        }
    } else {
        output += "No matching people found."
    }
    return output
}

private fun printAllPeople(peopleTextList: List<String>) : String {
    var output = ""
    output += "=== List of people ===\n"
    for (each in peopleTextList) {
        output += "$each\n"
    }
    return output
}

