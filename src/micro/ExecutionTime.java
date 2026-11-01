package micro;

import java.util.HashMap;
import java.util.Map;

public class ExecutionTime {
    private final Map<String, TimeDetails> executionTimes;

    public ExecutionTime() {
        this.executionTimes = new HashMap<>();
    }

    // Method to set time details for a reservation station
    public void setTimeDetails(String reservationName, int executionTime, int writeResultTime) {
        executionTimes.put(reservationName, new TimeDetails(executionTime, writeResultTime));
    }

    // Method to get time details for a reservation station
    public TimeDetails getTimeDetails(String reservationName) {
        return executionTimes.get(reservationName);
    }

    public boolean reservationStationExists(String reservationName) {
        return executionTimes.containsKey(reservationName);
    }

    public Boolean hasWriteResultTime(int time) {
        for (TimeDetails details : executionTimes.values()) {
            if (details.getWriteResultTime() <= time) {
                return true;
            }
        }
        return false;
    }

    public String getReservationStationByWriteTime(int time) {
        String stationWithLeastTime = null;
        int leastTime = Integer.MAX_VALUE;

        for (Map.Entry<String, TimeDetails> entry : executionTimes.entrySet()) {
            int writeTime = entry.getValue().getWriteResultTime();
            if (writeTime <= time && writeTime < leastTime) {
                leastTime = writeTime;
                stationWithLeastTime = entry.getKey();
            }
        }

        return stationWithLeastTime;
    }

    public void clearTimeDetails(String reservationName) {
        executionTimes.remove(reservationName); // Remove the entry from the map
    }

    public boolean isExecutionTimesEmpty() {
        return executionTimes.isEmpty();
    }

    public String getAllExecutionTimesInfo() {
        if (executionTimes.isEmpty()) {
            return "No execution times set.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Execution Times:\n");

        for (Map.Entry<String, TimeDetails> entry : executionTimes.entrySet()) {
            TimeDetails details = entry.getValue();
            sb.append("Reservation Station: ").append(entry.getKey())
              .append(", Execution Time: ").append(details.getExecutionTime())
              .append(", Write Result Time: ").append(details.getWriteResultTime())
              .append("\n");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        ExecutionTime executionTime = new ExecutionTime();
        executionTime.setTimeDetails("A1", 10, 5);
        executionTime.setTimeDetails("A2", 10, 6);
        executionTime.clearTimeDetails("A1");
    }
}
