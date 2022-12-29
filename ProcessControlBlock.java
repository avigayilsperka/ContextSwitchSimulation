package program1;

public class ProcessControlBlock extends SimProcess {
	private SimProcess process;
	int currentInstruction;
	int register1;
	int register2;
	int register3;
	int register4;

	public ProcessControlBlock(SimProcess p, int i) {
		super(p.getPid(),p.getProcName(),p.getTotalInstructions());
		process =p;
		currentInstruction = i;	
	}
	
	public ProcessControlBlock(SimProcess p) {
		super(p.getPid(),p.getProcName(),p.getTotalInstructions());
		process =p;
	}
	
	public SimProcess getSimProcess() {
		return process;
	}
	
	public int getCurrentInstruction() {
		return currentInstruction;
	}

	public void setCurrentInstruction(int i) {
		currentInstruction = i;
	}
	
	public void setRegister1(int value) {
		
		register1=value;
	}
	
	public void setRegister2(int value) {
		
		register2=value;
	}
	
	public void setRegister3(int value) {
		
		register3=value;
	}
	
	public void setRegister4(int value) {
		
		register4=value;
	}
	
	public int getRegister1() {
		return register1;
	}
	public int getRegister2() {
		return register2;
		
	}
	public int getRegister3() {
		return register3;
	}
	
	public int getRegister4() {
		return register4;
	}
}
