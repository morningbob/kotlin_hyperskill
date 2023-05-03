fun countUniqueChars(sequence: CharSequence): Int {
    // write your code here
    val charSet = mutableSetOf<Char>()
    for (each in sequence) {
        charSet.add(each)
    }
    //println(charSet.size)
    return charSet.size
}