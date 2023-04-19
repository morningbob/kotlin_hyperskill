package parking

import java.io.File
import java.util.*

fun main(args: Array<String>) {

    retrieveData()

    val commandList = readln().split(" ")


    println(parseCommands(commandList))

}

private fun parseCommands(commands: List<String>) : String {

    when (commandsMap[commands[0]]) {
        Commands.PARK -> {
            // expecting 2 other args
            if (commands.size >= 3) {
                val registrationNum = commands[1]
                val color = commands[2]//.lowercase(Locale.getDefault())
                if (registrationNum != "" && color != "") {
                    val newCar = Car(registrationNum, color)
                    return allocateSpot(newCar)
                }
            }
        }
        Commands.LEAVE -> {
            if (commands.size >= 2) {
                val spot = commands[1]
                if (spot != "") {
                    //println("spot $spot")
                    return leaveLot(spot)
                }
            }
        }
        else -> 0
    }
    return ""
}

// we save the status of the parking lot whenever a car is parked
private fun allocateSpot(car: Car) : String {

    val output = ParkingLot.park(car)
    reserveData()

    return output
}

// we save the status of the parking lot whenever a car leaves
private fun leaveLot(spot: String) : String {

    val output = ParkingLot.leave(spot)
    reserveData()
    return output
}


private fun retrieveData() {
    //ParkingLot.spotOne = Spot()
    val logsFile = File("lots.txt")
    if (logsFile.exists()) {
        val lines = logsFile.readLines()
        //var dataLot1 : List<String>? = null
        var cars = lines.map { line ->
            if (line != "null") {
                val data = line.split(" ")
                Car(data[1], data[2])
            } else {
                null
            }
        }
        if (cars[0] != null) {
            ParkingLot.spotOne.space = cars[0]
        }
        if (cars.size >= 2 && cars[1] != null) {
            ParkingLot.spotTwo.space = cars[1]
        }
    } else {
        logsFile.createNewFile()
    }
}

private fun reserveData() {
    var spotOne = "null"
    var spotTwo = "null"
    val lotsFile = File("lots.txt")
    if (ParkingLot.spotOne.space != null) {
        spotOne = "${ParkingLot.spotOne.space!!.registrationNum} ${ParkingLot.spotOne.space!!.color}\n"
        lotsFile.writeText("Lot_1 $spotOne")
    } else {
        lotsFile.writeText(spotOne)
    }
    if (ParkingLot.spotTwo.space != null) {
        spotTwo = "${ParkingLot.spotTwo.space!!.registrationNum} ${ParkingLot.spotTwo.space!!.color}\n"
        lotsFile.appendText("Lot_2 $spotTwo")
    } else {
        lotsFile.appendText(spotTwo)
    }
}


