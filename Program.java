package program1;
import java.util.*;
public class Program {

	public static void main(String args[]) {
		
		final int QUANTUM =5; 
		SimProcessor processor;
		ArrayList <SimProcess> processArray = new ArrayList<SimProcess>();
		ArrayList <ProcessControlBlock> pcbArray= new ArrayList <ProcessControlBlock>();
		ArrayList <ProcessControlBlock> ready = new ArrayList <ProcessControlBlock>();
		ArrayList <ProcessControlBlock> blocked = new ArrayList <ProcessControlBlock>();
		
		//hard code process and pcb
		processArray.add (0, new SimProcess(1, "process1", 223));
		pcbArray.add (0, new ProcessControlBlock(processArray.get(0),1));
		
		processArray.add(1, new SimProcess(2, "process2", 189));
		pcbArray.add (1, new ProcessControlBlock(processArray.get(1),1));
		
		processArray.add(2, new SimProcess(3, "process3", 400));
		pcbArray.add (2, new ProcessControlBlock(processArray.get(2),1));
		
		processArray.add(3, new SimProcess(4, "process4", 100));
		pcbArray.add (3, new ProcessControlBlock(processArray.get(3),1));
		
		processArray.add(4, new SimProcess(5, "process5", 333));
		pcbArray.add (4, new ProcessControlBlock(processArray.get(4),1));
		
		processArray.add(5, new SimProcess(6, "process6", 101));
		pcbArray.add (5, new ProcessControlBlock(processArray.get(5),1));
		
		processArray.add(6, new SimProcess(7, "process7", 256));
		pcbArray.add (6, new ProcessControlBlock(processArray.get(6),1));
		
		processArray.add(7, new SimProcess(8, "process8", 321));
		pcbArray.add (7, new ProcessControlBlock(processArray.get(7),1));
		
		processArray.add(8, new SimProcess(9, "process9", 199));
		pcbArray.add (8, new ProcessControlBlock(processArray.get(8),1));
		
		processArray.add(9, new SimProcess(10, "process10", 355));
		pcbArray.add (9, new ProcessControlBlock(processArray.get(9),1));
		
		//create random register values
		createRegisters(ready,pcbArray);
		
		int j = 0, count =0;
		for (int i=0; i<3000; ++i) {
			
			//processor idling, call unblock method
			while(ready.isEmpty() && !blocked.isEmpty()) {
				System.out.printf("Processor Idling.\n");
				unblock(blocked,ready);
			}
			
			//all processes completed terminate loop
			if (ready.isEmpty()&&blocked.isEmpty())
				break;
			
			//assign current pcb
			ProcessControlBlock currentPcb= ready.get(0);  
			
			//locate current process
			SimProcess currentProcess = null;
			for (int k = 0; k <= 9; k++) { 
				if (processArray.get(k)==currentPcb.getSimProcess()) {
					 currentProcess = new SimProcess(processArray.get(k));
				}
			}
			
			//assign current process to processor 
			processor = new SimProcessor(currentProcess);
			
			//set processor
			setProcessor(processor, currentPcb);
			
			//execute
			ProcessState state= ProcessState.READY;
			System.out.printf("**Context Switch: Starting %s**\n", ready.get(0).getProcName());
			System.out.printf("**current instruction %d\tR1: %d\tR2: %d\tR3: %d\tR4: %d\n",ready.get(0).getCurrentInstruction(),ready.get(0).getRegister1(),ready.get(0).getRegister2(),ready.get(0).getRegister3(),ready.get(0).getRegister4());

			
			while(count!=QUANTUM && state!= ProcessState.FINISHED && state!=ProcessState.BLOCKED) { 
				System.out.printf("Proc %s\tPid %d\ttotal %d\texecuting %d\n",currentProcess.getProcName(),currentProcess.getPid(),currentProcess.getTotalInstructions(),processor.getCurrentInstruction());
				state = execute(processor);
				count++;
			}
			
			//process blocks
			if (state ==ProcessState.BLOCKED) {
				System.out.printf("**Blocked**\n");
				System.out.printf("**Saving Process: %d\tcurrent instruction: %d\tR1: %d\tR2: %d\tR3: %d\tR4: %d\n",currentProcess.getPid(),processor.getCurrentInstruction(), processor.getRegisterValue(1),processor.getRegisterValue(2),processor.getRegisterValue(3),processor.getRegisterValue(4));
				blocked.add(ready.get(j));
				ready.remove(j);
				count=0;
				
				//context switch
				contextSwitch(currentPcb, processor);
			}
			
			//process finished
			if (state == ProcessState.FINISHED) {
				ready.remove(j);
				System.out.printf("**Process Completed**\n");
				count=0;	
			}
		
			//five iterations 
			if(count==QUANTUM) {
				ProcessControlBlock hold= ready.get(j);
				System.out.println("**Quantum Expired**.");
				contextSwitch(currentPcb,processor);
				System.out.printf("**Context Switch: Saving %s**\n", ready.get(0).getProcName());
				System.out.printf("**current instruction %d\tR1: %d\tR2: %d\tR3: %d\tR4: %d\n",ready.get(0).getCurrentInstruction(),ready.get(0).getRegister1(),ready.get(0).getRegister2(),ready.get(0).getRegister3(),ready.get(0).getRegister4());
				ready.remove(j);
				ready.add(hold);
				count = 0;
			}	
			
			//run unblock method
			unblock(blocked,ready);
		}	
	}
	
	public static void createRegisters(ArrayList<ProcessControlBlock> ready, ArrayList<ProcessControlBlock>pcbArray) {
		for (int i=0; i<=9; i++) {
			ready.add(pcbArray.get(i));
			pcbArray.get(i).setRegister1(new Random().nextInt(99999999));
			pcbArray.get(i).setRegister2(new Random().nextInt(99999999));	
			pcbArray.get(i).setRegister3(new Random().nextInt(99999999));	
			pcbArray.get(i).setRegister4(new Random().nextInt(99999999));
		}
	}
	
	public static void setProcessor(SimProcessor processor, ProcessControlBlock currentPcb) {
		processor.setCurrentInstruction(currentPcb.getCurrentInstruction());
		processor.setRegisterValue(currentPcb.getRegister1(),1);
		processor.setRegisterValue(currentPcb.getRegister2(),2);
		processor.setRegisterValue(currentPcb.getRegister3(),3);
		processor.setRegisterValue(currentPcb.getRegister4(),4);
	}
	
	public static void contextSwitch(ProcessControlBlock currentPcb, SimProcessor processor) {
		currentPcb.setRegister1(processor.getRegisterValue(1));
		currentPcb.setRegister2(processor.getRegisterValue(2));
		currentPcb.setRegister3(processor.getRegisterValue(3));
		currentPcb.setRegister4(processor.getRegisterValue(4));
		currentPcb.setCurrentInstruction(processor.getCurrentInstruction());
	}
	
	public static ProcessState execute(SimProcessor processor) {
		return processor.executeCurrentInstruction();
	}
	
	public static void unblock(ArrayList<ProcessControlBlock> blocked,ArrayList<ProcessControlBlock>ready) {
		for (int i=0; i<blocked.size();i++) {
			int value = new Random().nextInt(100);
			if(value>=0 && value <30) {
				ready.add(0, blocked.get(i));
				blocked.remove(i);
				System.out.printf("**Restoring %s**\n", ready.get(0).getProcName());
				System.out.printf("**current instruction %d\tR1: %d\tR2: %d\tR3: %d\tR4: %d\n",ready.get(0).getCurrentInstruction(),ready.get(0).getRegister1(),ready.get(0).getRegister2(),ready.get(0).getRegister3(),ready.get(0).getRegister4());
			}
		}
	}
}
