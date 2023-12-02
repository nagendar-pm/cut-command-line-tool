# cut-command-line-tool
A simple implementation of `cut` command in Java

### Goals:
1. Add basic functionality of the command - As an independent command on files
2. Add support for the command as a pipe - Depending on the output from other commands
3. Support multiple files at a time

### Core-Components:
1. Command Validator: Validates the given command. Whether it is a valid command and fails with 
a related message if it isn't a valid one.
2. Command Executor: Executes the command and outputs the output to the terminal.

### Flags implemented:
None

### Flags to be implemented:
1. Iteration-1: -c, -d, -f
2. Iteration-2: -b
3. Iteration-3: -n
4. Iteration-4: -s (future scope for now)
