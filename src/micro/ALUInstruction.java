package micro;

public class ALUInstruction extends Instruction {
    private String rd; // destination register
    private String rs; // source register 1
    private String rt; // source register 2

    public ALUInstruction(String operation, String fullInstruction, String rd, String rs, String rt) {
        super(operation, fullInstruction);
        this.rd = rd;
        this.rs = rs;
        this.rt = rt;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
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

}
