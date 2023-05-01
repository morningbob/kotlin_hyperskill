enum class DangerLevel(val lvl: Int) {
    HIGH(3),
    MEDIUM(2),
    LOW(1),


}

fun DangerLevel.getLevel() : Int {
    return lvl
}

