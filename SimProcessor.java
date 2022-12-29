package program1;
import java.util.*;

public class SimProcessor extends SimProcess {

	private SimProcess currentProc;
	private int register1;
	private int register2;
	private int register3;
	private int register4;
	private int currentInstruction;
	
	public SimProcessor(SimProcess currentProcess) {
		super(currentProcess.getPid(),currentProcess.getProcName(),currentProcess.getTotalInstructions());
	}
	
	public void setRegisterValue(int value, int i) {
		
			if (i==1)
				register1 = value;
			else if (i==2)
				register2 = value;
			else if (i==3)
				register3 = value;
			else 
				register4 = value;
	}
	
	public int getRegisterValue(int i) {
		
			if (i==1)
				return register1;
			else if (i==2)
				return register2;
			else if (i==3)
				return register3;
			else 
				return register4;
	}
	
	public void setCurrentProcess(SimProcess cp) {	
		currentProc =cp;
	}
	
	public String getCurrentProcess() {
		return String.format("Process ID: %d\tProcess Name: %s\tInstruction Number: %d",currentProc.getPid(),currentProc.getProcName(),currentProc.getTotalInstructions()); 
	}
	
	public void setCurrentInstruction(int i) {
		currentInstruction = i;
	}
	
	public int getCurrentInstruction() {
		return currentInstruction;
	}
	
	public ProcessState executeCurrentInstruction() {
		ProcessState state = super.execute(currentProc, currentInstruction);
		++currentInstruction;
		register1 = new Random().nextInt();
		register2 = new Random().nextInt();
		register3 = new Random().nextInt();
		register4 = new Random().nextInt();
		
		return state;
		
	}
}


	