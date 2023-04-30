package search

fun main() {

    println("Enter the number of people: ")
    val numPeople = readln().toInt()
    println("Enter all people: ")
    val peopleTextList = mutableListOf<String>()
    for (i in 0..numPeople - 1) {
        peopleTextList.add(readln())
    }
    println("Enter the number of search queries: ")
    val numQueries = readln().toInt()
    for (i in 0..numQueries - 1) {
        println("Enter data to search people: ")
        val searchTerm = readln()
        val result = search(searchTerm, peopleTextList)
        if (result.isNotEmpty()) {
            for (each in result) {
                println("People found:")
                println(each)
            }
        } else {
            println("No matching people found.")
        }
    }

    /*
    val inputWords = readln()
    val words = inputWords.split(" ")
    val inputWord = readln()

    val index = search(words, inputWord)
    if (index != -1) {
        println(index)
    } else {
        println("Not found")
    }
*/
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

private fun search(words: List<String>, word: String) : Int {
    for (index in 0..words.size - 1) {
        if (words[index] == word) {
            return index + 1
        }
    }
    return -1
}
