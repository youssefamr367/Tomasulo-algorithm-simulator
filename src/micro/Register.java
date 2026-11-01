package micro;

public class Register {
    private String reg; // Register name
    private String qi;  // Name of the functional unit to produce the result
    private int value;  // Value of the register

    public Register(String reg, String qi, int value) {
        this.reg = reg;
        this.qi = qi;
        this.value = value;
    }

    // Getters and setters
    public String getAll() {
        return "Register:" + reg + " " + "qi:" + qi + " " + "value:" + value; 
    }
    
    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getQi() {
        return qi;
    }

    public void setQi(String qi) {
        this.qi = qi;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}