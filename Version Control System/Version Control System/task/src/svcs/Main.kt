package svcs

fun main(args: Array<String>) {

    //val out = mutableListOf<String>()
    if (args.isEmpty()) {
        printToConsole(fullHelp())
    } else {

        var commandTwo: Command? = null
        if (1 <= (args.size - 1)) {
            commandTwo = commandMap[args[1]]
            printToConsole(checkCommand(commandMap[args[0]], commandTwo, args[0], args[1]))
        } else {
            printToConsole(checkCommand(commandMap[args[0]], null, args[0], null))
        }

    }
}

fun checkCommand(commandOne: Command?, commandTwo: Command?, arg1: String?, arg2: String?) : List<String> {
    val outputList = mutableListOf<String>()

    when (commandOne) {
        null -> {
            outputList.add("'$arg1' is not a SVCS command.")
        }
        Command.CONFIG -> {
            //outputList.add("config \t\t ${commandHelp[Command.CONFIG]!!}")
            outputList.add(commandHelp[Command.CONFIG]!!)
        }
        Command.ADD -> {
            //outputList.add("add \t\t ${commandHelp[Command.ADD]!!}")
            outputList.add(commandHelp[Command.ADD]!!)
        }
        Command.LOG -> {
            //outputList.add("log \t\t ${commandHelp[Command.LOG]!!}")
            outputList.add(commandHelp[Command.LOG]!!)
        }
        Command.COMMIT -> {
            //outputList.add("commit \t\t ${commandHelp[Command.COMMIT]!!}")
            outputList.add(commandHelp[Command.COMMIT]!!)
        }
        Command.CHECKOUT -> {
            //outputList.add("checkout \t\t ${commandHelp[Command.CHECKOUT]!!}")
            outputList.add(commandHelp[Command.CHECKOUT]!!)
        }
        Command.HELP -> {
            return fullHelp()
        }
    }

    if (commandTwo == null && arg2 != null) {
        outputList.add("'$arg2' is not a SVCS command.")
    }
    return outputList
}

fun fullHelp() : List<String> {
    val outList = mutableListOf<String>()
    outList.add("These are SVCS commands:")
    outList.add("config \t ${commandHelp[Command.CONFIG]!!}")
    outList.add("add \t ${commandHelp[Command.ADD]!!}")
    outList.add("log \t ${commandHelp[Command.LOG]!!}")
    outList.add("commit \t ${commandHelp[Command.COMMIT]!!}")
    outList.add("checkout \t ${commandHelp[Command.CHECKOUT]!!}")
    return outList
}

fun printToConsole(list: List<String>) {
    var output = ""
    for (each in list) {
        if (each != "") {
            output += "${each}\n"
        }
    }
    print(output)
}

