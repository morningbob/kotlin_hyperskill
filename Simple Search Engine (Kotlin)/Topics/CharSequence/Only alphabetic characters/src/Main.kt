fun containsOnlyAlphabets(charSequence: CharSequence): Boolean {
    // write your code here
    //var result = true
    for (each in charSequence) {
        if (!each.isLetter()) {
            return false
        }
    }
    return true
}