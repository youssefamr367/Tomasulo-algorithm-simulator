package micro;
import java.util.ArrayList;


public class InstructionQueue {
    private ArrayList<Instruction> queue;

    public InstructionQueue() {
        this.queue = new ArrayList<>();
    }

    public void addInstruction(Instruction instruction) {
        queue.add(instruction);
    }

    public Instruction getInstruction(int index) {
        if (index >= 0 && index < queue.size()) {
            return queue.get(index);
        }
        return null; // or throw an exception
    }

    public void removeInstruction(int index) {
        if (index >= 0 && index < queue.size()) {
            queue.remove(index);
        }
    }
    
    public void printAllInstructions() {
        if (queue.isEmpty()) {
            System.out.println("Instruction queue is empty.");
            return;
        }

        System.out.println("Instructions in the Queue:");
        for (int i = 0; i < queue.size(); i++) {
            Instruction instruction = queue.get(i);
            System.out.println("Index " + i + ": " + instruction.getFullInstruction());
        }
    }
    public boolean isEmpty() {
        return queue.isEmpty();
    }
    
    public int getSize() {
        return queue.size();
    }
    
    public void markInstructionAsDone(int index) {
        if (index >= 0 && index < queue.size()) {
            Instruction instruction = queue.get(index);
            if (instruction != null) {
                instruction.markAsDone();
            }
        }
    }
    public void markInstructionAsUndone(int index) {
        if (index >= 0 && index < queue.size()) {
            Instruction instruction = queue.get(index);
            if (instruction != null) {
                instruction.markAsUndone();
            }
        }
    }
    
    public boolean areAllInstructionsDone() {
        for (Instruction instruction : queue) {
            if (!instruction.isDone()) {
                return false; // Return false as soon as an undone instruction is found
            }
        }
        return true; // All instructions are done
    }
    
    
}