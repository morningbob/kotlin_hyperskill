package parking

fun main() {

    //val commandList = mutableListOf<String>()
    var condition = false
    while (!condition) {
        val commandList = mutableListOf<String>()
        commandList.addAll(readln().split(" "))
        val output = parseCommands(commandList)
        if (output == "exit") {
            condition = true
        } else {
            println(output)
        }

    }
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
        Commands.EXIT -> {
            //exitProgram()
            return "exit"
        }
        else -> 0
    }
    return ""
}

// we save the status of the parking lot whenever a car is parked
private fun allocateSpot(car: Car) : String {

    val output = ParkingLot.park(car)
    ///reserveData()

    return output
}

// we save the status of the parking lot whenever a car leaves
private fun leaveLot(spot: String) : String {

    val output = ParkingLot.leave(spot)
    //reserveData()
    return output
}

private fun saveData() {
    //ParkingLot
}

/*
private fun retrieveData() {
    //ParkingLot.spotOne = Spot()
    val logsFile = File("lots.txt")
    if (logsFile.exists()) {
        val lines = logsFile.readLines()
        //for (each in lines) {
        //    println(each)
        //}
        //var dataLot1 : List<String>? = null
        var cars = lines.map { line ->
            if (line != "null" && line != "") {
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
        spotOne = "${ParkingLot.spotOne.space!!.registrationNum} ${ParkingLot.spotOne.space!!.color}"
        lotsFile.writeText("Lot_1 $spotOne")
    } else {
        lotsFile.writeText("${spotOne}")
    }
    if (ParkingLot.spotTwo.space != null) {
        spotTwo = "${ParkingLot.spotTwo.space!!.registrationNum} ${ParkingLot.spotTwo.space!!.color}"
        lotsFile.appendText("\nLot_2 $spotTwo")
    } else {
        //lotsFile.appendText("\n")
        lotsFile.appendText("\n${spotTwo}")
    }
}

private fun checkRegistrationNum(num: String) : Boolean {
    val parts = num.split("-")
    if (parts.size != 4) {
        return false
    } else {
        if (!parts[0][0].isLetter() || !parts[0][1].isLetter() ||
            !parts[1][0].isDigit() || !parts[1][1].isDigit() ||
                !parts[2][0].isLetter() || !parts[2][1].isLetter()) {
            return false
        } else {
            for (each in parts[3]) {
                if (!each.isDigit()) {
                    return false
                }
            }
        }
        return true
    }
}

 */


