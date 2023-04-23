fun <T> getStringsOnly(list: List<T>): List<String> {
    val result = mutableListOf<String>()
    // make your code here
    for (each in list) {
        if (each is String) {
            result.add(each)
        }
    }
    return result
}