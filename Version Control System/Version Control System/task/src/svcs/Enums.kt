package svcs

enum class Command {
    CONFIG,
    ADD,
    LOG,
    COMMIT,
    CHECKOUT,
    HELP
}

val commandHelp = mapOf<Command, String>(
    Command.CONFIG to "Get and set a username.",
    Command.ADD to "Add a file to the index.",
    Command.LOG to "Show commit logs.",
    Command.COMMIT to "Save changes.",
    Command.CHECKOUT to "Restore a file.",

)

val commandMap = mapOf<String, Command>(
    "config" to Command.CONFIG,
    "add" to Command.ADD,
    "log" to Command.LOG,
    "commit" to Command.COMMIT,
    "checkout" to Command.CHECKOUT,
    "--help" to Command.HELP

)