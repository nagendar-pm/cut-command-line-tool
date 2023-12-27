# cut-command-line-tool
A simple implementation of `cut` command in Java

### Goals:
1. Add basic functionality of the command - As an independent command on files
2. Add support for the command as a pipe - Depending on the output from other commands
3. Support multiple files at a time

### Core-Components:
1. Command Validator: Validates the given command. Whether it is a valid command and fails with 
a related message if it isn't a valid one.<br>
The class diagram for the same can be found below:
![Class diagram for Validator](uml/Validator.png "Class diagram of Validator")
2. Command Executor: Executes the command and outputs the output to the terminal.

### Options and Flags implemented:
1. Implemented all the possible options in the command utility `-c`, `-b`, and `-f`
2. The flags associated with the options are also implemented
   1. `-n` for `-b` option
   2. `-s` and `-w` or `-d` for `-f` option

