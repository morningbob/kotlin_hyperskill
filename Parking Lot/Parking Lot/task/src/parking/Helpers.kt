package parking

val commandsMap = mapOf<String, Commands>(
    "park" to Commands.PARK,
    "leave" to Commands.LEAVE,
    "exit" to Commands.EXIT
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
    //var spotOne : Spot = Spot("1")
    //var spotTwo : Spot = Spot("2")
    var parkingSpots = mutableListOf<Spot>()

    // initialized to one car is parked at lot 1
    init {
        //spotOne.space = Car("KA-01-HH-1234", "Blue")

        for (i in 1..20) {
            parkingSpots.add(Spot(name = (i).toString()))
        }
    }

    fun park(car: Car) : String {
        for (spot in parkingSpots) {
            if (spot.space == null) {
                spot.settle(car)
                return "${car.color} car parked in spot ${spot.name}."
            }
        }

        return "Sorry, the parking lot is full."
    }


    fun leave(spot: String) : String {
        val targetSpot = parkingSpots[spot.toInt() - 1]
        if (targetSpot.space != null) {
            targetSpot.space = null
            return "Spot ${targetSpot.name} is free."
        } else {
            return "There is no car in spot ${targetSpot.name}."
        }
        //return ""
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

/*
    fun park1(car: Car) : String {
        if (spotOne.space == null) {
            spotOne.settle(car)
            return "${car.color} car parked in spot 1."
        } else if (spotTwo.space == null) {
            spotTwo.settle(car)
            return "${car.color} car parked in spot 2."
        } else {
            return "Parking lot is full."
        }

    }
    fun leave1(spot: String) : String {

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
*/