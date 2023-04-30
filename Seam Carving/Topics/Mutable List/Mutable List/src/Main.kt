fun names(namesList: List<String>): List<String> {
    var count = 0
    val nameCount = mutableListOf<String>()
    //
    // add your code here
    val nameMap = mutableMapOf<String, Int>()
    val nameSet = mutableSetOf<String>()

    for (each in namesList) {
        if (each in nameMap.keys) {
            nameMap[each] = nameMap[each]!! + 1
        } else {
            nameMap[each] = 1
            nameSet.add(each)
        }
    }

    for (each in nameSet) {
        nameCount.add("The name $each is repeated ${nameMap[each]} times")
    }

    return nameCount
}