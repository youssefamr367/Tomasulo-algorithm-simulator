package micro;

public class TimeDetails {
    private int executionTime;
    private int writeResultTime;

    public TimeDetails(int executionTime, int writeResultTime) {
        this.executionTime = executionTime;
        this.writeResultTime = writeResultTime;
    }

    // Getters and setters
    public int getExecutionTime() {
        return executionTime;
    }

    public void setExecutionTime(int executionTime) {
        this.executionTime = executionTime;
    }

    public int getWriteResultTime() {
        return writeResultTime;
    }

    public void setWriteResultTime(int writeResultTime) {
        this.writeResultTime = writeResultTime;
    }
}
