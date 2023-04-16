package svcs



import java.io.File
import java.lang.Exception
import java.util.*
import javax.swing.JToolBar.Separator
import kotlin.io.path.Path
import kotlin.io.path.createDirectory

fun main(args: Array<String>) {

    setupSystem()
    val sep = File.separator
    //var hash = File("vcs${sep}hashRecord.txt")
    //hash.delete()
    //var index = File("vcs${sep}index.txt")
    //index.delete()
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

    // create index.txt
    val indexPath = "vcs${separator}index.txt"
    val indexFile = File(indexPath)
    val configPath = "vcs${separator}config.txt"
    val configFile = File(configPath)
    val logPath = "vcs${separator}log.txt"
    val logFile = File(logPath)

    try {
        //path.createDirectory()
        if (!File("vcs").exists()) {
            File("vcs").mkdir()
        }
        if (!File("vcs${separator}commits").exists()) {
            File("vcs${separator}commits").mkdir()
        }
        if (!indexFile.exists()) {
            indexFile.createNewFile()
        }
        if (!logFile.exists()) {
            logFile.createNewFile()
        }
        //println(workingDir())
    } catch (e: Exception) {
        //println("create vcs error ${e.localizedMessage}")
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
                    // keep the hashes of files in every commit
                    // if the hashes are different, record the new hash
                    if (processCommit(arg2)) {
                        outputList.add("Changes are committed.")
                    } else {
                        outputList.add("Nothing to commit.")
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
    //val separator = File.separator
    //val path = filename
    val targetFile = File(filename)

    var resultExist = false
    var resultAppend = false

    try {
        if (targetFile.exists()) {
            resultExist = true
            resultAppend = addIfNewFileInIndex(filename)
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

    return resultExist || resultAppend
}

private fun addIfNewFileInIndex(filename: String) : Boolean {
    val separator = File.separator
    val indexPath = "vcs${separator}index.txt"
    val indexFile = File(indexPath)
    // check if the name already existed
    val listName = indexFile.readLines()
    val existed = listName.find { it == filename }

    if (existed == null) {
        try {
            indexFile.appendText("${filename}\n")
            //resultAppend = true
            return true
        } catch (e: Exception) {
            println("error appending index.txt ${e.localizedMessage}")
        }
    }

    return false
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

    val separator = File.separator
    val trackedFiles = retrieveTrackedFiles()
    val newId = UUID.randomUUID().toString()
    val lastCommitId = checkLastCommit()
    var hashRecord : List<String>? = null
    //lastCommitId?.let {
    try {
        val hashFile = File("vcs${separator}hashRecord.txt")
        if (hashFile.exists()) {
            hashRecord = hashFile.readLines()
            //hashRecord?.let {
                for (record in hashRecord) {
                    println("record $record")
                }
            //}
        }
    } catch (e: Exception) {

    }
    //}
    // compare hashes of files

    if (checkChanges(trackedFiles, lastCommitId, hashRecord)) {
        // create a unique id
        // get username
        val author = retrieveUserName()
        for (each in trackedFiles) {
            //println("tracked file: each $each")
            copyFile(filename = each, id = newId)
            recordHash(filename = each, id = newId, hashRecord = hashRecord)
        }
        // create directory
        //val separator = File.separator
        //val path = "vcs${separator}commits${separator}${newId}"
        //val dir = File(path)
        //dir.mkdir()
        return saveCommitToLog(id = newId, author = author!!, message = message)
    }
    // save to log
    return false
}

// need to compare hashes,
// but also need to check if the file already existed in commits/id
// if not, it is also a change, should return true
private fun checkChanges(trackedFiles: List<String>, lastCommitId: String?, hashRecord: List<String>?) : Boolean {
    //var changed = false
    // if tracked files is empty, it will skip this for loop and return false
    for (each in trackedFiles) {
        //copyTrackedFile(each)
        // check last commit's file hashes if there is a commit
        if (lastCommitId != null) {
            //println("lastCommitId : $lastCommitId")
            val result = compareHashes(id = lastCommitId, filename = each, hashRecord = hashRecord)
            if (result == 1) {
                // update hashes
                //println("changed $each")
                //copyFile(each, File(each).hashCode(), newId)
                return true
                // this case, handle tracked file not in commit yet
            } else if (result == -1) {
                //println("tracked file not commited yet $each")
                //copyFile(each, File(each).hashCode(), newId)
                return true
            } else {
                //println("unchanged $each")
                //println("compare hash result 0 for $each")
                //copyFile(each, File(each).hashCode(), newId)
                // notice that changed is not set to true here
            }
            // there is still a case that all the new hash are 0
            // that means all the tracked files didn't change
            // the changed will not be set to true
        } else  {
            //println("no last commit, create hash files")
            // last commit == null,
            // and there are tracked files
            //println("log is empty, update newly tracked files")
            //copyFile(each, File(each).hashCode(), newId)
            return true
        }
    }
    return false
}

// true means file hashes are the same
// false means hashes are different
// 0 - same hashes
// -1 - newly added file
private fun compareHashes(id: String, filename: String, hashRecord: List<String>?) : Int {

    try {
        val lastFileHash = readFileHash(filename = filename, hashRecord = hashRecord)
        //val lastFileHash = readFileHash(retrieveFileInCommit(filename = filename, id = id)).toInt()
        //val lastFileHash = readFileHash(retrieveFileInCommit(filename = filename, id = id), filename)
        //println("old file content: ${lastFile.readText()} hash: ${lastFile.hashCode()}")
        //println("new file content: ${File(filename).readText()} hash: ${File(filename).hashCode()}")
        if (lastFileHash != null) {
            // compare hashes
            if (File(filename).hashCode() == lastFileHash.toInt()) {

                //println("compare hashes: same hashes")
                return 0
            } else {
                //println("id $id")
                //println(filename)
                //println("new ${File(filename).hashCode()}")
                //println("old ${lastFileHash}")
                //println("compare hashes: different hashes")
                return 1
            }
        } else {
            //println("compare hashes: old file doesn't exist in old commit")
            return 1
        }
        //println("$filename hash = $lastFileHash")
        // proceed to commit
        //if (lastFileHash != "") {
            //val lastFileHash = readFileHash(lastFile).toInt()
        /*
        val newFileHash = File(filename).hashCode()
        if (newFileHash == lastFileHash) {
            //println("file unchanged")
            return 0
        } else {
            //println("file changed")
            return -2
        }

         */
        //}
    } catch (e: Exception) {
        val separator = File.separator
        val file = File("vcs${separator}commits${separator}${id}")
        val list = file.listFiles()
        //for (each in list) {
        //    println("file in commit: ${each.name}")
        //}

        val iFile = File("vcs${separator}index.txt")
        //println("index ${iFile.readText()}")

        //println("filename $filename")
        //println("couldn't retrieve file from commit, file may not exist")
        return -1
    }
    // if last file == null, the file is newly added
    //return -1
}

// check log file
private fun checkLastCommit() : String? {
    val logs = readLog()
    if (logs.isNotEmpty()) {
        //println("logs last 3 ${logs[logs.lastIndex - 3].substring(7)}")
        for (each in logs) {
            //println("log: $each")
        }
        return logs[logs.lastIndex - 3].substring(7)
    }
    //if (logs.isNotEmpty() && logs.last() != "") {
        //println(logs[logs.lastIndex - 3].substring(7))

        //}
    return null
}

private fun retrieveFileInCommit(filename: String, id: String) : File {
    val separator = File.separator
    val path = "vcs${separator}commits${separator}${id}${separator}${filename}"
    return File(path)
}

private fun readFileHash(filename: String, hashRecord: List<String>?) : String? {
    //val allLines = file.readLines()

    //var targetLine = ""
    if (hashRecord != null) {
        for (each in hashRecord) {
            if (each.contains(filename)) {
                //targetLine = each
                val list = each.split(" ")
                return list.last()
                //break
            }
        }
    }
    //val targetLine = file.readText()
    return null

}
// we need to save the commit message at the beginning of the file
private fun saveCommitToLog(id: String, author: String, message: String) : Boolean {
    val separator = File.separator
    val path = "vcs${separator}log.txt"
    var logFile = File(path)
    //var logLines = logFile.readLines()

    try {
        logFile.appendText("commit ${id}\n" +
                "Author: $author\n" +
                "$message\n\n")

        return true

    } catch (e: Exception) {
        return false
    }
}

private fun copyFile(filename: String, id: String) {
    //println("copy file, copying file $filename")
    val separator = File.separator

    val commitDir = File("vcs${separator}commits${separator}${id}")
    if (!commitDir.exists()) {
        //println("copy file, make commit dir")
        commitDir.mkdir()
    }
    //val path = "vcs${separator}commits${separator}${id}${separator}${filename}"

    val file = File(filename)

    try {
        //println("$filename writing to vcs${separator}commits${separator}${id}${separator}${filename}")
        //file.writeText(hash.toString())
        //opyTrackedFile(filename = filename, id = id)
        file.copyTo(File("vcs${separator}commits${separator}${id}${separator}${filename}"), overwrite = true)
        val whole = commitDir.listFiles()
        //println("files in commit")
        for (each in whole) {
            //println(each.name)
        }
        //println("copy file succeeded")
    } catch (e: Exception) {
        //println("copy file $filename failed ${e.localizedMessage}")
    }
}

private fun recordHash(filename: String, id: String, hashRecord: List<String>?) {
    val separator = File.separator

    //val hashRecord = File("vcs${separator}commits${separator}${id}${separator}hashRecord.txt")
    //val newRecord = listOf<String>()
    val newRecordFile = File("vcs${separator}hashRecord_temp.txt")
    newRecordFile.delete()

    if (hashRecord != null) {
        //println("hash record is not null")
        for (each in hashRecord) {
            //println("each hash record $each")
            if (each.contains(filename)) {
                //println("record hash: updated hash for $filename")
                newRecordFile.appendText("$filename ${File(filename).hashCode()}\n")
            } else {
                newRecordFile.appendText("$each\n")
            }
        }
    } else {
        //println("record hash: hash record is null")
        newRecordFile.appendText("$filename ${File(filename).hashCode()}\n")
    }
    newRecordFile.copyTo(File("vcs${separator}hashRecord.txt"), overwrite = true)
    //hashRecord.appendText("$filename ${File(filename).hashCode()}\n")
}

private fun workingDir() : String? {
    return System.getProperty("user.dir")
}

