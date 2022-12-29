
# General Info
This program is a college assignment which is meant to simulate the context switches of a processor.

##Project Specifics
- Iterates 3,000 times to complete all processes.
- Processes can be ready, suspended, or finished.
- Ready processes follow FIFO algorithm. 
- Blocked processes unblock with a 30% probability after each iteration.
- Processor idles when remaining processes are all blocked. 
- Context switch occurs when the quantum of 5 is completed.
- With each context switch, the Process Control Block is upadted with the four register values and current instruction of the previous process.
- Process Control Block restores next ready process on the processor using saved registers and current instruction.

