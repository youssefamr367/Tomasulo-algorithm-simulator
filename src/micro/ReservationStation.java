package micro;

import java.util.ArrayList;
import java.util.List;

public class ReservationStation {
    private Object[][] addSubReservation;
    private Object[][] mulDivReservation;

    public ReservationStation() {
        // Initialize ADD/SUB reservation stations
    	this.addSubReservation = new Object[3][8]; // 3 slots, each with 7 components
        initializeReservationStation(addSubReservation, "A");

        // Initialize MUL/DIV reservation stations
        this.mulDivReservation = new Object[2][8]; // 2 reservation stations for MUL/DIV
        initializeReservationStation(mulDivReservation ,"M" );
    }

    private void initializeReservationStation(Object[][] station , String prefix) {
        for (int i = 0; i < station.length; i++) {
        	station[i][0] = prefix + (i + 1); // Name (e.g., A1, A2, A3, M1, M2)
            station[i][1] = false; // busy
            station[i][2] = null;    // operation
            station[i][3] = null;    // vj
            station[i][4] = null;    // vk
            station[i][5] = null;    // qj
            station[i][6] = null;     // qk
            station[i][7] = -1;
        }
    }

    // Method to set values for a reservation station entry
    public void setReservationStationEntryAddSub(int stationIndex, boolean busy, String operation, String vj, String vk, String qj, String qk, int index) {
        if (stationIndex >= 0 && stationIndex < addSubReservation.length) {
            addSubReservation[stationIndex][1] = busy;
            addSubReservation[stationIndex][2] = operation;
            addSubReservation[stationIndex][3] = vj;
            addSubReservation[stationIndex][4] = vk;
            addSubReservation[stationIndex][5] = qj;
            addSubReservation[stationIndex][6] = qk;
            addSubReservation[stationIndex][7] = index;
        }
        
    }
    
    public void clearReservationStationAddSub(int stationIndex) {
        if (stationIndex >= 0 && stationIndex < addSubReservation.length) {
            addSubReservation[stationIndex][1] = false; // busy
            addSubReservation[stationIndex][2] = null;    // operation
            addSubReservation[stationIndex][3] = null;    // vj
            addSubReservation[stationIndex][4] =null;    // vk
            addSubReservation[stationIndex][5] = null;    // qj
            addSubReservation[stationIndex][6] = null;    // qk
            addSubReservation[stationIndex][7] = -1;
            
        }
        // Optionally, handle invalid stationIndex
    }

    public int findFirstEmptyReservationStationAddSub() {
        for (int i = 0; i < addSubReservation.length; i++) {
            if (!(Boolean)addSubReservation[i][1]) { // Check if the station is not busy
                return i; // Return the index of the first empty station
            }
        }
        return -1; // Return -1 or another indicator if no empty station is found
    }
    
    public String getReservationStationNameAddSub(int stationIndex) {
        if (stationIndex >= 0 && stationIndex < addSubReservation.length) {
            return (String) addSubReservation[stationIndex][0]; // Name is stored at index 0
        }
        return "Invalid station index"; // Or you can throw an exception
    }
    
    
    //----------------------------------------------------------------------------
    
    
    
    //mul div functions
    
    public void setReservationStationEntryMulDiv(int stationIndex, boolean busy, String operation, String vj, String vk, String qj, String qk ,int index) {
        if (stationIndex >= 0 && stationIndex < mulDivReservation.length) {
        	mulDivReservation[stationIndex][1] = busy;
        	mulDivReservation[stationIndex][2] = operation;
        	mulDivReservation[stationIndex][3] = vj;
        	mulDivReservation[stationIndex][4] = vk;
        	mulDivReservation[stationIndex][5] = qj;
        	mulDivReservation[stationIndex][6] = qk;
        	mulDivReservation[stationIndex][7] = index;
        	
        }
        
    }
    
    public void clearReservationStationMulDiv(int stationIndex) {
        if (stationIndex >= 0 && stationIndex < mulDivReservation.length) {
        	mulDivReservation[stationIndex][1] = false; // busy
        	mulDivReservation[stationIndex][2] = null;    // operation
        	mulDivReservation[stationIndex][3] = null;    // vj
        	mulDivReservation[stationIndex][4] = null;    // vk
        	mulDivReservation[stationIndex][5] = null;    // qj
        	mulDivReservation[stationIndex][6] = null; 
        	mulDivReservation[stationIndex][7] = -1;// qk
        }
        // Optionally, handle invalid stationIndex
    }

    public int findFirstEmptyReservationStationMulDiv() {
        for (int i = 0; i < mulDivReservation.length; i++) {
            if (!(Boolean)mulDivReservation[i][1]) { // Check if the station is not busy
                return i; // Return the index of the first empty station
            }
        }
        return -1; // Return -1 or another indicator if no empty station is found
    }
    
    
   

    public String getReservationStationNameMulDiv(int stationIndex) {
        if (stationIndex >= 0 && stationIndex < mulDivReservation.length) {
            return (String) mulDivReservation[stationIndex][0]; // Name is stored at index 0
        }
        return "Invalid station index"; // Or you can throw an exception
    }
    
    
    public Integer findReservationStationIndexByName(String name) {
        // Search in addSubReservation
        for (int i = 0; i < addSubReservation.length; i++) {
            if (addSubReservation[i][0].equals(name)) {
                return i;
            }
        }

        // Search in mulDivReservation
        for (int i = 0; i < mulDivReservation.length; i++) {
            if (mulDivReservation[i][0].equals(name)) {
                return i;
            }
        }

        return null; // Return null or another indicator if the station is not found
    }
    
    
    public String getVjAddSub(int stationIndex) {
        return (String) addSubReservation[stationIndex][3];
    }
    public String getVjMULDIV(int stationIndex) {
        return (String) mulDivReservation[stationIndex][3];
    }

    public void setVjAddSub(int stationIndex, String vj) {
        addSubReservation[stationIndex][3] = vj;
    }

    public String getVkAddSub(int stationIndex) {
        return (String) addSubReservation[stationIndex][4];
    }
    
    public String getVkMULDIV(int stationIndex) {
        return (String) mulDivReservation[stationIndex][4];
    }

    public void setVkAddSub(int stationIndex, String vk) {
        addSubReservation[stationIndex][4] = vk;
    }

    public String getQjAddSub(int stationIndex) {
        return (String) addSubReservation[stationIndex][5];
    }

    public void setQjAddSub(int stationIndex, String qj) {
        addSubReservation[stationIndex][5] = qj;
    }

    public String getQkAddSub(int stationIndex) {
        return (String) addSubReservation[stationIndex][6];
    }
    
    public String getobbAddSub(int stationIndex) {
        return (String) addSubReservation[stationIndex][2];
    }
    
    public String getobbMULDIV(int stationIndex) {
        return (String) mulDivReservation[stationIndex][2];
    }
    
    public int getquelocationAddSub(int stationIndex) {
        return (int) addSubReservation[stationIndex][7];
    }
    
    public int getquelocationMulDiv(int stationIndex) {
        return (int) mulDivReservation[stationIndex][7];
    }

    public void setQkAddSub(int stationIndex, String qk) {
        addSubReservation[stationIndex][6] = qk;
    }
    

    public void searchStringInQiQj(String searchString , String result) {
        // Search in addSubReservation
        for (Object[] reservation : addSubReservation) {
            if (searchString.equals(reservation[5]) ) {
            	reservation[5]=null;
            	reservation[3]= result ;
            	
                // String found in qi or qj
            }
            if (searchString.equals(reservation[6]) ) {
            	reservation[6]=null;
            	reservation[4]= result ;
            }
            
        }

        // Search in mulDivReservation
        for (Object[] reservation : mulDivReservation) {
        	if (searchString.equals(reservation[5]) ) {
            	reservation[5]=null;
            	reservation[3]= result ;
            	
                // String found in qi or qj
            }
            if (searchString.equals(reservation[6]) ) {
            	reservation[6]=null;
            	reservation[4]= result;
            }
        }

       
    }
    
    public String[] getStationsWithNonNullVjVk() {
        List<String> matchingStations = new ArrayList<>();

        // Check in addSubReservation
        for (Object[] station : addSubReservation) {
            if (station[3] != null && station[4] != null) { // vj and vk are not null
                matchingStations.add((String) station[0]); // Add station name
            }
        }

        // Check in mulDivReservation
        for (Object[] station : mulDivReservation) {
            if (station[3] != null && station[4] != null) { // vj and vk are not null
                matchingStations.add((String) station[0]); // Add station name
            }
        }

        return matchingStations.toArray(new String[0]); // Convert list to array and return
    }
    
    
    public void printAllReservationStations() {
        System.out.println("ADD/SUB Reservation Stations:");
        printReservationStations(addSubReservation);

        System.out.println("\nMUL/DIV Reservation Stations:");
        printReservationStations(mulDivReservation);
    }

    private void printReservationStations(Object[][] stations) {
        for (Object[] station : stations) {
            System.out.println("Station Name: " + station[0]
                    + ", Busy: " + station[1]
                    + ", Operation: " + station[2]
                    + ", Vj: " + station[3]
                    + ", Vk: " + station[4]
                    + ", Qj: " + station[5]
                    + ", Qk: " + station[6]
                    + ", Index: " + station[7]);
        }
    }
    
    public static void main(String[] args) {
    	ReservationStation res = new ReservationStation();
    	res.setReservationStationEntryAddSub(0, true, "ADD", "R2", "R3", null, null, 0);
    
    	
    	String[] stationsWithValues = res.getStationsWithNonNullVjVk();
    	for (String stationName : stationsWithValues) {
            System.out.println(stationName);
            }
    	
	}
    

   
}