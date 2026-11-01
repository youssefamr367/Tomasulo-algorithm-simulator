package micro;

public class MemInstruction extends Instruction {
    private String rs; // source register
    private String rt; // target register
    private int immediate; // immediate value

    public MemInstruction(String operation, String fullInstruction, String rs, String rt, int immediate) {
        super(operation, fullInstruction);
        this.rs = rs;
        this.rt = rt;
        this.immediate = immediate;
    }

    public String getRs() {
        return rs;
    }

    public void setRs(String rs) {
        this.rs = rs;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public int getImmediate() {
        return immediate;
    }

    public void setImmediate(int immediate) {
        this.immediate = immediate;
    }

   
}
