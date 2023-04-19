package parking

val commandsMap = mapOf<String, Commands>(
    "park" to Commands.PARK,
    "leave" to Commands.LEAVE
)

class Car(val registrationNum:
           String, val color: String) {

    var spot : Int = 0

    fun printCar() {
        println("Registration: $registrationNum")
        println("Color: $color")
        println("Spot: $spot")
    }
}

object ParkingLot {
    var spotOne : Spot = Spot("1")
    var spotTwo : Spot = Spot("2")

    fun park(car: Car) : String {
        if (spotTwo.space == null) {
            spotTwo.settle(car)
            return "${car.color} car parked in spot 2."
        } else if (spotOne.space == null) {
            spotOne.settle(car)
            return "${car.color} car parked in spot 1."
        } else {
            return "Parking lot is full."
        }

    }

    fun leave(spot: String) : String {

        when (spot) {
            spotOne.name -> {
                if (spotOne.space != null) {
                    //if (spotOne.space?.registrationNum == car.registrationNum) {
                        spotOne.space = null // leave
                        return "Spot 1 is free."
                    //} else {
                        //return "The car parked in spot 1 is a different car."
                    //}
                } else {
                    return "There is no car in spot 1."
                }

            }
            spotTwo.name -> {
                if (spotTwo.space != null) {
                    //if (spotTwo.space?.registrationNum == car.registrationNum) {
                        spotTwo.space = null // leave
                        return "Spot 2 is free."
                    //} else {
                        //return "The car parked in spot 2 is a different car."
                    //}
                } else {
                    return "There is no car in spot 2."
                }
            }
        }
        /*
        if (spotOne.space != null && spotOne.space?.registrationNum == car.registrationNum) {
            spotOne.space = null // leave
            return "Spot 1 is free."
        } else if (spotTwo.space != null && spotTwo.space?.registrationNum == car.registrationNum) {
            spotTwo.space = null // leave
            return "Spot 2 is free."
        } else if (spotOne.space == null)

         */
        return ""
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