fun main() {
    // write your code here
    val input = readln()

    var resultChar = mutableListOf<Char>()
    var resultTimes = mutableListOf<Int>()

    for (i in input.indices) {
        //var count = 1
        //if (input[i] != resultChar[])
        resultChar.add(input[i])
        resultTimes.add(1)
        for (j in i..input.length - 2) {
            //if (i + 1 <= input.length - 1) {
            if (input[j] == input[j + 1]) {
                //count += 1
                resultTimes[i] += 1
            } else {
                break
            }
            //}
        }
    }
    var one = 0
    for (i in 0..resultChar.size - 1) {
        print("${resultChar[one]}${resultTimes[one]}")
        one += resultTimes[one]
        if (one > resultChar.size - 1) {
            break
        }
    }
}