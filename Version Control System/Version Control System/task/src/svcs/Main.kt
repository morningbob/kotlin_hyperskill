package svcs



import java.io.File
import java.lang.Exception
import java.util.*
import javax.swing.JToolBar.Separator
import kotlin.io.path.Path
import kotlin.io.path.createDirectory

fun main(args: Array<String>) {

    setupSystem()

    if (args.isEmpty()) {
        printToConsole(fullHelp())
    } else {

        if (1 <= (args.size - 1)) {
            printToConsole(checkCommand(args[0], args[1]))
            val files = File("vcs").listFiles()
            val names = mutableListOf<String>()
            files.map { file ->
                names.add(file.name)
            }
            //printToConsole(names)
        } else {
            printToConsole(checkCommand(args[0], null))
        }

    }
}

private fun setupSystem() {
    // create vsc directory if it doesn't exist
    val separator = File.separator

    try {
        //path.createDirectory()
        if (!File("vcs").exists()) {
            File("vcs").mkdir()
        }
        if (!File("vcs${separator}commits").exists()) {
            File("vcs${separator}commits").mkdir()
        }
        //println(workingDir())
    } catch (e: Exception) {
        //println("create vcs error ${e.localizedMessage}")
    }

    // create index.txt
    val indexPath = "vcs${separator}index.txt"
    val indexFile = File(indexPath)
    val configPath = "vcs${separator}config.txt"
    val configFile = File(configPath)

    try {
        if (!indexFile.exists()) {
            indexFile.createNewFile()
        }

        //if (!configFile.exists()) {
        //    configFile.createNewFile()
        //}
    } catch (e: Exception) {
        println("error creating index.txt ${e.localizedMessage}")
    }
}

private fun checkCommand(arg1: String?, arg2: String?) : List<String> {
    val outputList = mutableListOf<String>()

    when (arg2) {
        null, "" -> {
            when (commandMap[arg1]) {
                null -> {
                    outputList.add("'$arg1' is not a SVCS command.")
                }

                Command.CONFIG -> {
                    // check if there is a username in config.txt
                    val name = retrieveUserName()
                    if (name == null || name == "") {
                        outputList.add("Please, tell me who you are.")
                    } else {
                        outputList.add("The username is $name.")
                    }
                }

                Command.ADD -> {
                    // check if there is any file tracked
                    // if not, describe add
                    val trackedFiles = retrieveTrackedFiles()
                    if (trackedFiles.isEmpty()) {
                        outputList.add(commandHelp[Command.ADD]!!)
                    } else {
                        outputList.add("Tracked files:")
                        outputList.addAll(trackedFiles)
                    }

                }

                Command.LOG -> {
                    // read log
                    val logs = readLog()
                    if (logs.isEmpty()) {
                        outputList.add("No commits yet.")
                    } else {
                        outputList.addAll(readLog())
                    }
                }

                Command.COMMIT -> {
                    //outputList.add("commit \t\t ${commandHelp[Command.COMMIT]!!}")
                    //outputList.add(commandHelp[Command.COMMIT]!!)
                    outputList.add("Message was not passed.")
                }

                Command.CHECKOUT -> {
                    //outputList.add("checkout \t\t ${commandHelp[Command.CHECKOUT]!!}")
                    outputList.add(commandHelp[Command.CHECKOUT]!!)
                }

                Command.HELP -> {
                    return fullHelp()
                }
                //else -> outputList.add("'$arg1' is not a SVCS command.")
            }
        }
        "--help" -> {
            //} else if (commandTwo == Command.HELP) {
            when (commandMap[arg1]) {
                null -> {
                    outputList.add("'$arg1' is not a SVCS command.")
                }

                Command.CONFIG -> {
                    outputList.add("config \t ${commandHelp[Command.CONFIG]!!}")
                }

                Command.ADD -> {
                    outputList.add("add \t ${commandHelp[Command.ADD]!!}")
                }

                Command.LOG -> {
                    outputList.add("log \t ${commandHelp[Command.LOG]!!}")
                }

                Command.COMMIT -> {
                    outputList.add("commit \t ${commandHelp[Command.COMMIT]!!}")
                }

                Command.CHECKOUT -> {
                    outputList.add("checkout \t ${commandHelp[Command.CHECKOUT]!!}")
                }

                Command.HELP -> {
                    return fullHelp()
                }
            }
        }
        // any text of the 2nd arg
        else -> {
            when (commandMap[arg1]) {
                null -> {
                    outputList.add("'$arg1' is not a SVCS command.")
                }

                Command.CONFIG -> {
                    // create or write to config.txt, the user name
                    saveUserName(arg2)
                    outputList.add("The username is $arg2.")
                }

                Command.ADD -> {
                    //outputList.add("add \t ${commandHelp[Command.ADD]!!}")
                    // check if the 2nd arg is ended with ".txt"
                    if (arg2.endsWith(".txt")) {
                        // create and save the file name in index.txt
                        if (checkAndTrackFile(arg2)) {
                            outputList.add("The file '${arg2}' is tracked.")
                        } else {
                            outputList.add("Can't find '${arg2}'.")
                        }
                    } else {
                        outputList.add("The file is invalid.")
                    }
                }

                Command.LOG -> {
                    outputList.add("log \t ${commandHelp[Command.LOG]!!}")
                }

                Command.COMMIT -> {
                    // check if there is changes before committing
                    if (processCommit(arg2)) {
                        outputList.add("Changes are committed.")
                    }
                }

                Command.CHECKOUT -> {
                    outputList.add("checkout \t ${commandHelp[Command.CHECKOUT]!!}")
                }

                Command.HELP -> {
                    return fullHelp()
                }
            }
        }
    }

    //if (commandTwo == null && arg2 != null) {
    //    outputList.add("'$arg2' is not a SVCS command.")
    //}
    return outputList
}

private fun fullHelp() : List<String> {
    val outList = mutableListOf<String>()
    outList.add("These are SVCS commands:")
    outList.add("config \t ${commandHelp[Command.CONFIG]!!}")
    outList.add("add \t ${commandHelp[Command.ADD]!!}")
    outList.add("log \t ${commandHelp[Command.LOG]!!}")
    outList.add("commit \t ${commandHelp[Command.COMMIT]!!}")
    outList.add("checkout \t ${commandHelp[Command.CHECKOUT]!!}")
    return outList
}

private fun printToConsole(list: List<String>) {
    var output = ""
    for (each in list) {
        if (each != "") {
            output += "${each}\n"
        }
    }
    print(output)
}

private fun saveUserName(username: String) {
    val separator = File.separator
    val path = "vcs${separator}config.txt"
    val configFile = File(path)
    try {
        configFile.writeText(username)
    } catch (e: Exception) {
        println("can't write username ${e.localizedMessage}")
    }
}

private fun retrieveUserName() : String? {
    val separator = File.separator
    val path = "vcs${separator}config.txt"
    val configFile = File(path)
    var username : String? = null
    try {
        username = configFile.readText()
    } catch (e: Exception) {
        //println("can't retrieve username ${e.localizedMessage}")
    }
    return username
}

private fun checkAndTrackFile(filename: String) : Boolean {
    val separator = File.separator
    val path = filename
    val targetFile = File(path)

    var resultExist = false
    var resultAppend = false

    try {
        if (targetFile.exists()) {
            resultExist = true
        }
    } catch(e: Exception) {

    }

/*
    try {
        targetFile.createNewFile()
        resultCreate = true
    } catch (e: Exception) {
        println("error creating target file ${e.localizedMessage}")
    }
*/
    if (resultExist) {
        val indexPath = "vcs${separator}index.txt"
        val indexFile = File(indexPath)

        try {
            indexFile.appendText("${filename}\n")
            resultAppend = true
        } catch (e: Exception) {
            println("error appending index.txt ${e.localizedMessage}")
        }
    }
    return resultExist && resultAppend
}

private fun retrieveTrackedFiles() : List<String> {
    val separator = File.separator
    val path = "vcs${separator}index.txt"
    val indexFile = File(path)

    var list = listOf<String>()
    try {
        list = indexFile.readLines()
    } catch (e: Exception) {
        println("error retrieving tracked files ${e.localizedMessage}")

    }

    return list
}

// provide the history of commits
// read log file
private fun readLog() : List<String> {
    val separator = File.separator
    val path = "vcs${separator}log.txt"
    val logFile = File(path)

    var list = listOf<String>()
    try {
        list = logFile.readLines()
    } catch (e: Exception) {

    }
    return list
}

// create commit directory with commit id as name
// create files involve in the commit and store it in the directory
private fun processCommit(message: String) : Boolean {
    // create a unique id
    val id = UUID.randomUUID().toString()
    // get username
    val author = retrieveUserName()
    // create directory
    val separator = File.separator
    val path = "vcs${separator}commits${separator}${id}"
    val dir = File(path)
    dir.mkdir()
    // create files
    val trackedFiles = retrieveTrackedFiles()
    for (each in trackedFiles) {
        copyTrackedFile(each)
    }
    // save to log
    return saveCommitToLog(id = id, author = author!!, message = message)
}

private fun copyTrackedFile(filename: String) {
    val separator = File.separator
    //val path = "vcs${separator}commits${separator}id"
    val file = File(filename)
    file.copyTo(File("vcs${separator}commits${separator}${filename}"), overwrite = true)
}
private fun saveCommitToLog(id: String, author: String, message: String) : Boolean {
    val separator = File.separator
    val path = "vcs${separator}log.txt"
    val logFile = File(path)

    try {
        logFile.appendText("commit ${id}\n" +
                "Author: $author\n" +
                "$message\n\n")
        return true

    } catch (e: Exception) {
        return false
    }
}

private fun workingDir() : String? {
    return System.getProperty("user.dir")
}

