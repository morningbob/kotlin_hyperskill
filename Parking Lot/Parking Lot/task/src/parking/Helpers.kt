package parking

val commandsMap = mapOf<String, Commands>(
    "create" to Commands.CREATE,
    "park" to Commands.PARK,
    "leave" to Commands.LEAVE,
    "status" to Commands.STATUS,
    "spot_by_color" to Commands.SPOT_BY_COLOR,
    "reg_by_color" to Commands.REG_BY_COLOR,
    "spot_by_reg" to Commands.SPOT_BY_REG,
    "exit" to Commands.EXIT
)

class Car(
    val registrationNum:
           String, val color: String) {

    var spot : Int = 0


    fun printCar() {
        println("Registration: $registrationNum")
        println("Color: $color")
        println("Spot: $spot")
    }
}

object ParkingLot {

    private var parkingSpots = mutableListOf<Spot>()
    //private var cars = mutableListOf<Car>()

    fun create(numSpot: Int) : String {
        // reset the list
        parkingSpots = mutableListOf<Spot>()

        for (i in 1..numSpot) {
            parkingSpots.add(Spot(name = (i).toString()))
        }

        return "Created a parking lot with $numSpot spots."

    }

    fun park(car: Car) : String {
        if (parkingSpots.isNotEmpty()) {
            for (spot in parkingSpots) {
                if (spot.space == null) {
                    spot.settle(car)
                    return "${car.color} car parked in spot ${spot.name}."
                }
            }
            return "Sorry, the parking lot is full."
        } else {
            return "Sorry, a parking lot has not been created."
        }
    }

    fun leave(spot: String) : String {
        if (parkingSpots.isNotEmpty()) {
            val targetSpot = parkingSpots[spot.toInt() - 1]
            if (targetSpot.space != null) {
                targetSpot.space = null
                return "Spot ${targetSpot.name} is free."
            } else {
                return "There is no car in spot ${targetSpot.name}."
            }
        } else {
            return "Sorry, a parking lot has not been created."
        }
    }

    // print all occupied spots
    // spot name, registration number, color
    fun status() : String {
        if (parkingSpots.isNotEmpty()) {
            var output = ""
            for (spot in parkingSpots) {
                if (spot.space != null) {
                    output += "${spot.name} ${spot.space!!.registrationNum} ${spot.space!!.color}\n"
                }
            }
            if (output == "") {
                return "Parking lot is empty."
            } else {
                return output.substring(0, output.length - 1)
            }
        } else {
            return "Sorry, a parking lot has not been created."
        }
    }

    fun spotByColor(color: String) : List<Spot>? {
        if (parkingSpots.isNotEmpty() ) {
            return parkingSpots.filter { spot ->
                //println(spot.space?.registrationNum)
                spot.space?.color?.lowercase() == color.lowercase()
            }
        } else {
            return null
        }
    }

    fun spotByReg(reg: String) : List<Spot>? {
        if (parkingSpots.isNotEmpty() ) {
            val spot = parkingSpots.find { spot -> spot.space?.registrationNum == reg }
            if (spot == null) {
                return listOf()
            } else {
                return listOf(spot)
            }
        } else {
            return null
        }
    }
}

class Spot(val name: String) {

    var space : Car? = null
    fun settle(car : Car) {
        space = car
    }
    fun printSpot() {
        print("Spot $name: ${space}")

    }
}

