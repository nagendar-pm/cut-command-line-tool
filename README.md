# cut-command-line-tool
A simple implementation of `cut` command in Java

### Options and Flags implemented:
1. Implemented all the possible options in the command utility `-c`, `-b`, and `-f`
2. The flags associated with the options are also implemented
   1. `-n` for `-b` option
   2. `-s` and `-w` or `-d` for `-f` option

### Flow
The flow of the command through various components of the application is shown below. 
1. The input command is 
handled on two major _modes_ as a standalone command (SingleMode in the image) or as a Piped command. 
In any of the modes, the handling of `cut` command is same either as an **intermediate** command or a **terminal** command. 
2. Once the mode is decided, the command is processed for the first time tokenizing every component of the command possible.
In this processing, the command is categorised as cut, non-cut or an exit command. Options, their args and flags are splitted. 
3. Followed by _processing_1_, if the command is cut it is _validated_. Here we validate the command for the potential
invalid options, args or flags. Any invalid param will fail the flow with an appropriate error message. 
4. The validation is followed by _processing_2_. Here the command is processed again for range resolution (main area of work of cut command), 
option and flag parsing. 
5. Next comes the _Execution_, where we execute the command based on the options and flags specified.

![Flow diagram for application](uml/Flow.png "Flow diagram of application")

### Core-Components:
#### **Processor Service**:
1. Both Validation and Execution of the given input command string will be done by Processing
service. First of all, the input is checked for any pipes if present and handled accordingly.
2. In the below image, both `CommandValidatorFactory` and `CommandExecutorFactory` are the entry points
into the Validator and Executor services respectively. These components and explained neatly in the below sections.

![Class diagram for Processor](uml/Architecture.png "Class diagram of Architecture")


#### **Command Validator**: 
1. Validates the given command. Whether it is a valid command and fails with 
a related message if it isn't a valid one. 
2. The validator is obtained based on the type of the command from the factory and type of the command essentially means if it is cut, non-cut or exit command
<br>
The class diagram for the same can be found below:

![Class diagram for Validator](uml/Validator.png "Class diagram of Validator")


#### **Command Executor**: 
1. Executes the command and outputs the output to the terminal.
2. The executor for a given type of command is obtained from the factory and is handled accordingly.
3. In Cut-command, each option has a separate executor to make the handling better.
<br>
The class diagram for the same can be found below:

![Class diagram for Executor](uml/Executor.png "Class diagram of Executor")


### Representation
#### Command
1. `Command` is handled in two different ways. 
2. If the command is not cut, we can simply pass it to System for 
processing further. 
3. Else we will use `InputCommand` while validation and `ProcessedCommand` in case of Execution.

![Class diagram for Command](uml/Command.png "Class diagram of Command")

#### Range
1. The main use-case of cut command lies in its ability to handle the range of columns, which are represented here
as `Range`s.
2. `Range` has _from_ and _to_ attributes.
3. Ranges are resolved from the command by `RangeResolver` and are passed in the `ProcessedCommand` for the execution.

![Class diagram for Range](uml/Range.png "Class diagram of Range")

## Sample Run
### -b option
```commandline
$ nagi_cut -b 1-3 resources/test.txt
Executing the command `nagi_cut -b 1-3 resources/test.txt`...
Executing file: resources/test.txt
ß�
Ŏ�
abc
అ
ioq

$ nagi_cut -b 1,2,3 resources/test.txt
Executing the command `nagi_cut -b 1,2,3 resources/test.txt`...
Executing file: resources/test.txt
ß�
Ŏ�
abc
అ
ioq

$ nagi_cut -b 1- resources/test.txt
Executing the command `nagi_cut -b 1- resources/test.txt`...
Executing file: resources/test.txt
ßßßß
ŎŎŎ
abc:def:ghi
అఆఇఈ
ioqpj

$ nagi_cut -b -4 resources/test.txt
Executing the command `nagi_cut -b -4 resources/test.txt`...
Executing file: resources/test.txt
ßß
ŎŎ
abc:
అ�
ioqp

$ nagi_cut -b 1,2 -n resources/test.txt
Executing the command `nagi_cut -b 1,2 -n resources/test.txt`...
Executing file: resources/test.txt
ß
Ŏ
ab
�
io

$ nagi_cut -b 1,2,3 -n resources/test.txt
Executing the command `nagi_cut -b 1,2,3 -n resources/test.txt`...
Executing file: resources/test.txt
ß
Ŏ
abc
అ
ioq
```

### -c option
```commandline
$ nagi_cut -c 1,2,3 resources/sample.tsv
Executing the command `nagi_cut -c 1,2,3 resources/sample.tsv`...
Executing file: resources/sample.tsv
f0	
0	1
5	6
10	
15	
20	

$ nagi_cut -c 1,3 resources/sample.tsv
Executing the command `nagi_cut -c 1,3 resources/sample.tsv`...
Executing file: resources/sample.tsv
f	
01
56
1	
1	
2	

$ nagi_cut -c 1-3 resources/sample.tsv
Executing the command `nagi_cut -c 1-3 resources/sample.tsv`...
Executing file: resources/sample.tsv
f0	
0	1
5	6
10	
15	
20	

$ nagi_cut -c 1- resources/sample.tsv
Executing the command `nagi_cut -c 1- resources/sample.tsv`...
Executing file: resources/sample.tsv
f0	f1	f2	f3	f4
0	1	2	3	4
5	6	7	8	9
10	11	12	13	14
15	16	17	18	19
20	21	22	23	24

$ nagi_cut -c -4 resources/sample.tsv
Executing the command `nagi_cut -c -4 resources/sample.tsv`...
Executing file: resources/sample.tsv
f0	f
0	1	
5	6	
10	1
15	1
20	2

$ nagi_cut -c 2-9 resources/sample.tsv
Executing the command `nagi_cut -c 2-9 resources/sample.tsv`...
Executing file: resources/sample.tsv
0	f1	f2	
	1	2	3	4
	6	7	8	9
0	11	12	
5	16	17	
0	21	22	
```

### -f option
```commandline
$ nagi_cut -f 1,2 resources/fourchords.csv | head -n 5
Executing the command `nagi_cut -f 1,2 resources/fourchords.csv`...
Executing file: resources/fourchords.csv
Executing the command `head -n 5 resources/temp.txt`...
﻿Song title,Artist,Year,Progression,Recorded Key	
"10000 Reasons (Bless the Lord)",Matt Redman and Jonas Myrin,2012,IV–I–V–vi,G major	
"20 Good Reasons",Thirsty Merc,2007,I–V–vi–IV,D♭ major	
"Adore You",Harry Styles,2019,vi−I−IV−V,C minor	
"Africa",Toto,1982,vi−IV–I–V (chorus),F♯ minor (chorus)	

$ nagi_cut -d ',' -f 1,2 resources/fourchords.csv | head -n 5
Executing the command `nagi_cut -d ',' -f 1,2 resources/fourchords.csv`...
Executing file: resources/fourchords.csv
Executing the command `head -n 5 resources/temp.txt`...
﻿Song title,Artist,
"10000 Reasons (Bless the Lord)",Matt Redman and Jonas Myrin,
"20 Good Reasons",Thirsty Merc,
"Adore You",Harry Styles,
"Africa",Toto,

$ nagi_cut -d, -f 1,3 resources/fourchords.csv | head -n 5
Executing the command `nagi_cut -d, -f 1,3 resources/fourchords.csv`...
Executing file: resources/fourchords.csv
Executing the command `head -n 5 resources/temp.txt`...
﻿Song title,Year,
"10000 Reasons (Bless the Lord)",2012,
"20 Good Reasons",2007,
"Adore You",2019,
"Africa",1982,

$ nagi_cut -d , -f 1 resources/fourchords.csv | head -n 5
Executing the command `nagi_cut -d , -f 1 resources/fourchords.csv`...
Executing file: resources/fourchords.csv
Executing the command `head -n 5 resources/temp.txt`...
﻿Song title,
"10000 Reasons (Bless the Lord)",
"20 Good Reasons",
"Adore You",
"Africa",

$ nagi_cut -f 1 -s resources/fourchords.csv | head -n 5
Executing the command `nagi_cut -f 1 -s resources/fourchords.csv`...
Executing file: resources/fourchords.csv
Executing the command `head -n 5 resources/temp.txt`...


$ nagi_cut -d ' ' -f 1 -w resources/fourchords.csv | head -n 5
Executing the command `nagi_cut -d ' ' -f 1 -w resources/fourchords.csv`...
Executing file: resources/fourchords.csv
Exception in thread "main" com.nagendar.learning.exceptions.IllegalFlagException: Expected either -d or -w, Found both
```