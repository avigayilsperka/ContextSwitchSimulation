package program1;
import java.util.*;

public class SimProcess {
	private int pid;
	private String procName;
	private int totalInstructions;

	public SimProcess(int id, String name, int totalInstructions ) {
		pid = id;
		procName = name;
		this.totalInstructions = totalInstructions;	
	}
	
	public SimProcess(SimProcess sp) {
		this(sp.getPid(),sp.getProcName(),sp.getTotalInstructions());
	}
	
	public ProcessState execute(SimProcess current, int i) {
		
		if (i==totalInstructions)
			return ProcessState.FINISHED;
		int val = new Random().nextInt(100);
		if (val>=0 && val<15)
			return ProcessState.BLOCKED;
		else
			return ProcessState.READY;
	}
	
	public int getPid() {
		return pid;
	}
	
	public String getProcName() {
		return procName;
	}
	
	public int getTotalInstructions() {
		return totalInstructions;
	}
	
}


