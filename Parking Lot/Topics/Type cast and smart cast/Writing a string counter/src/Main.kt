fun countStrings(list: List<Any>): Int {
    // make your code here
    var sum = 0
    for (each in list) {
        if (each is String) {
            sum += 1
        }
    }
    return sum
}