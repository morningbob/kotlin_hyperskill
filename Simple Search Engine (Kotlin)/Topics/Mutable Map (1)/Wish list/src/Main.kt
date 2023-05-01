fun makeMyWishList(wishList: Map<String, Int>, limit: Int): MutableMap<String, Int> {
    // write here
    val newList = mutableMapOf<String, Int>()
    for ((key, value) in wishList) {
        if (value <= limit) {
            newList[key] = value
        }
    }
    return newList
}