package micro;


public class InstructionParser {

    public Instruction parseAndCategorizeInstruction(String instructionLine) {
      
        String[] parts = instructionLine.trim().split("\\s+");
        
        if (parts.length < 2) {
            System.out.println("Invalid instruction format.");
            return null;
        }

        String operation = parts[0].toUpperCase(); // Assuming operations are case-insensitive
       
        
        StringBuilder registersPart = new StringBuilder();
        for (int i = 1; i < parts.length; i++) {
            registersPart.append(parts[i]);
            if (i < parts.length - 1) {
                registersPart.append(" ");
            }
        }
        
        String[] registers = registersPart.toString().split(",");
       
        
        // Checking if the instruction has the correct number of parts
   


        if (isALUOperation(operation)) {
        	if (registers.length == 2) {
                String rd = registers[0].trim();
                String rs = registers[0].trim();
                String rt = registers[1].trim();
                return new ALUInstruction(operation, instructionLine, rd, rs, rt);
        	}
            if (registers.length == 3) {
                String rd = registers[0].trim();
                String rs = registers[1].trim();
                String rt = registers[2].trim();
                return new ALUInstruction(operation, instructionLine, rd, rs, rt);
            } else {
                System.out.println("ALU operation does not have the correct number of registers.");
            }
        } else if (isMemoryOperation(operation)) {
            if (registers.length == 2) {
                String[] memParts = registers[1].split("\\(");
                if (memParts.length == 2) {
                    int immediate = Integer.parseInt(memParts[0].trim());
                    String rt = memParts[1].replace(")", "").trim();
                    String rs = registers[0].trim();
                    return new MemInstruction(operation, instructionLine, rs, rt, immediate);
                }
            } else {
                System.out.println("Memory operation does not have the correct number of registers.");
            }
        } else {
            System.out.println("Unrecognized operation or format.");
        }

        return null; // Or throw an exception for unknown instruction types
    }

    private boolean isALUOperation(String operation) {
        return operation.matches("ADD.D|SUB.D|MUL.D|DIV.D|BNEZ|SUB.I|ADD.I");
    }

    private boolean isMemoryOperation(String operation) {
        return operation.matches("LW|SW");
    }
}
