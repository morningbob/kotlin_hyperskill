package parking

val commandsMap = mapOf<String, Commands>(
    "create" to Commands.CREATE,
    "park" to Commands.PARK,
    "leave" to Commands.LEAVE,
    "status" to Commands.STATUS,
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

