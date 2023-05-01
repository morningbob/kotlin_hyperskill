fun main() {
    // put your code here

    val countries = readln().split(" ")
    var currencyOne = ""
    var currencyTwo = ""
    for (each in Currency.values()) {
        if (each.country == countries[0]) {
            currencyOne = each.cur
        }
        if (each.country == countries[1]) {
            currencyTwo = each.cur
        }
    }
    println(currencyOne == currencyTwo)
}

enum class Currency(val country: String, val cur: String) {
    GERMANY("Germany","Euro"),
    MALI("Mali", "CFA franc"),
    DOMINICA("Dominica","Eastern Caribbean dollar"),
    CANADA("Canada","Canadian dollar"),
    SPAIN("Spain","Euro"),
    AUSTRALIA("Australia","Australian dollar"),
    BRAZIL("Brazil", "Brazilian real"),
    SENEGAL("Senegal","CFA franc"),
    FRANCE("France","Euro"),
    GRENADA("Grenada","Eastern Caribbean dollar"),
    KIRIBATI("Kiribati", "Australian dollar")
}