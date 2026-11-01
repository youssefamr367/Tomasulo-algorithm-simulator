package micro;

import java.util.HashMap;
import java.util.Map;

public class RegistersFile {
    private final Map<String, Register> registers;

    public RegistersFile() {
        this.registers = new HashMap<>();
       
    }

    
    public void setRegister(String regName, String qi, int value) {
        Register reg = registers.get(regName);
        if (reg == null) {
            // Create a new Register if it doesn't exist
            reg = new Register(regName, qi, value);
            registers.put(regName, reg);
        } else {
            // Update existing Register
            reg.setQi(qi);
            reg.setValue(value);
        }
    }

    public int getRegisterValueOrDefault(String regName) {
        Register reg = registers.get(regName);
        if (reg == null) {
            return 0; // Return 0 or any default value if the register is not found
        } else {
            return reg.getValue(); // Return the register's value if it exists
        }
    }
    
    // Method to get a register value
    public Register getRegister(String regName) {
        return registers.getOrDefault(regName, null);
    }
    
    
    public void ChangeRegisterByQi(String qi , int result) {
        for(Register reg : registers.values()) {
            if (reg.getQi().equals(qi)) {
                 reg.setQi("0");
                 reg.setValue(result);
            }
        }
        
    }
    public String getRegistersByQi(String qi) {
        

        for (Register reg : registers.values()) {
            if (reg.getQi().equals(qi)) {
                return reg.getReg();
            }
        }

        return "hi";
    }
    
    public String getAllRegistersInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("Registers Information:\n");

        for (Map.Entry<String, Register> entry : registers.entrySet()) {
            Register reg = entry.getValue();
            sb.append("Register Name: ").append(reg.getReg())
              .append(", Qi: ").append(reg.getQi())
              .append(", Value: ").append(reg.getValue())
              .append("\n");
        }

        return sb.toString();
    }

    
    public static void main(String[] args) {
		RegistersFile register = new RegistersFile();
		register.setRegister("R2", "A2", 7 );
		register.setRegister("R3", "A2", 0 );
		
		 System.out.print(register.getAllRegistersInfo());
		 
		
	}    
        
    


}
