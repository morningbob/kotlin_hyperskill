fun solution(strings: List<String>, str: String): Int {
    // put your code here
    var times = 0

    for (each in strings) {
        if (each == str) {
            times += 1
        }
    }

    return times
}