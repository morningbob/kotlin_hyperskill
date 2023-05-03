fun countVowels(charSequence: CharSequence): Int {
    // write your code here
    var count = 0
    for (each in charSequence) {
        if (each in listOf<Char>('a', 'e', 'i', 'o', 'u')) {
            count++
        }
    }
    return count
}