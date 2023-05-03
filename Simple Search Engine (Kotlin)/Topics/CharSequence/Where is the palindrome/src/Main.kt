fun isPalindrome(str: CharSequence): Boolean {
    // write your code here
    var backwards = ""
    //var seq = ""
    for (i in (str.length - 1)downTo 0) {
        backwards += str[i]
        //println("added ${str[i]}")
    }
    return backwards == str.toString()
}