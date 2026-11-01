package micro;

public abstract class Instruction {
    protected String operation;
    protected String fullInstruction;
    protected boolean done; 

    public Instruction(String operation, String fullInstruction) {
        this.operation = operation;
        this.fullInstruction = fullInstruction;
        this.done = false;
       
    }
    
    public void markAsDone() {
        this.done = true;
    }
    public void markAsUndone() {
        this.done = false;
    }
    
    public boolean isDone() {
        return done;
    }

    public String getOperation() {
        return operation;
    }

    
    
    public String getFullInstruction() {
        return fullInstruction;
    }

    // Other common methods...
}



