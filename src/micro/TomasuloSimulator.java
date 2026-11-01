package micro;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


public class TomasuloSimulator {
    public InstructionQueue instructionQueue = new InstructionQueue();
    public InstructionParser instructionParser = new InstructionParser();
    public ReservationStation addSubReservation = new ReservationStation();
    public RegistersFile registersFile = new RegistersFile();
    public ExecutionTime executionTime = new ExecutionTime();
    
    public int clockCycle;
    public int instqueue;
    public int queuesize;
    public int AddClockcycle; // ADD.D
    public int MULClockcycle; // MUL.d
    public int SUBClockcycle; // SUB.D
    public int DIVClockcycle; // div.d
    
    public TomasuloSimulator() {
        // Initialize your simulator here
        this.clockCycle = 0;
        this.instqueue = 0;
        this.queuesize =0;
       
    }
    
    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    

    public void readInstructionsFromFile(String filename) throws IOException {
        Path instructionFile = Path.of(filename);
        if (!Files.isRegularFile(instructionFile)) {
            instructionFile = Path.of("src", "micro", filename);
        }

        try (BufferedReader br = Files.newBufferedReader(instructionFile)) {
            String line;
            
            while ((line = br.readLine()) != null) {
            	
            	
                Instruction instruction = instructionParser.parseAndCategorizeInstruction(line);
                
               
                if (instruction != null) {
                    instructionQueue.addInstruction(instruction);
                }
                // Handle null instruction (invalid or unrecognized instruction)
            }
        }
    }
    
    public void loadToBus() {
    	System.out.println(clockCycle);
        String name = executionTime.getReservationStationByWriteTime(clockCycle); //A2 for example
        System.out.println(name);
        int place = addSubReservation.findReservationStationIndexByName(name); // index of the reservation station
        
        System.out.println("place"+place);

        int result = 0; // Declare result here to have a wider scope
        int vj =0;
        int vk =0;

        if(name.contains("A")|| name.contains("M")) {
        	
        	
        	if(name.contains("A")) {
        	    vj = Integer.parseInt(addSubReservation.getVjAddSub(place));
        	    vk = Integer.parseInt(addSubReservation.getVkAddSub(place));
        	} else if(name.contains("M")) {
        	    vj = Integer.parseInt(addSubReservation.getVjMULDIV(place));
        	    vk = Integer.parseInt(addSubReservation.getVkMULDIV(place));
        	}
        	
        	
        	if(name.contains("A")) {
        		
        		if(addSubReservation.getobbAddSub(place).equals("ADD.D")||addSubReservation.getobbAddSub(place).equals("ADD.I")) {
                    result = vj + vk; // Update result
                } else if (addSubReservation.getobbAddSub(place).equals("SUB.D") ||addSubReservation.getobbAddSub(place).equals("SUB.I")) {
                    result = vj - vk; // Update result
                }
        		
        		
        		
        	} else if(name.contains("M")) {
        		if(addSubReservation.getobbMULDIV(place).equals("MUL.D")) {
                	result = vj * vk;
                	
                }
                else if(addSubReservation.getobbMULDIV(place).equals("DIV.D")) {
                	result = vj / vk;
                	
                }
        	}
          
           
            
           
          System.out.println(result);
            // Now 'result' can be used here
            //String registeName = registersFile.getRegistersByQi(name);  // name of the register that holds A2
            
            
            //adjust rej file and resrvfile
            registersFile.ChangeRegisterByQi(name, result);  // if he find QI=A2 in register R2->  R2  0  result
            
            addSubReservation.searchStringInQiQj(name, ""+result);  
            
            
            //clear reservation station
            if(name.contains("A")) {
            	instructionQueue.markInstructionAsDone(addSubReservation.getquelocationAddSub(place));
                addSubReservation.clearReservationStationAddSub(place);
                
        	} else if(name.contains("M")) {
        		instructionQueue.markInstructionAsDone(addSubReservation.getquelocationMulDiv(place));
                addSubReservation.clearReservationStationMulDiv(place);
        	}
             
           
            executionTime.clearTimeDetails(name);
            
           
            
            
            String[] stationsWithValues = addSubReservation.getStationsWithNonNullVjVk();  // cheack if after write is there any one ready to execute  a1 m2
        	for (String stationName : stationsWithValues) {
        		if(executionTime.reservationStationExists(stationName)==false) {
        		int idex = addSubReservation.findReservationStationIndexByName(stationName);
        		String opp = addSubReservation.getobbAddSub(idex);
        		if(name.contains("A")) {
            		
            		if(opp.equals("ADD.D")) {
            			 executionTime.setTimeDetails(stationName, clockCycle+AddClockcycle, clockCycle+1+AddClockcycle);
                    } else if (opp.equals("SUB.D") ) {
                    	executionTime.setTimeDetails(stationName, clockCycle+SUBClockcycle, clockCycle+1+SUBClockcycle);
                    }
                    else if(opp.equals("ADD.I")) {
                    	executionTime.setTimeDetails(stationName, clockCycle+1, clockCycle+2);
                    }
                    else if(opp.equals("SUB.I")) {
                    	executionTime.setTimeDetails(stationName, clockCycle+1, clockCycle+2);
                    }
        		}
            		
            		
            		
            	 else if(name.contains("M")) {
            		if(opp.equals("MUL.D")) {
            			executionTime.setTimeDetails(stationName, clockCycle+MULClockcycle, clockCycle+1+MULClockcycle);
                    	
                    }
                    else if(opp.equals("DIV.D")) {
                    	executionTime.setTimeDetails(stationName, clockCycle+DIVClockcycle, clockCycle+1+DIVClockcycle);
                    	
                    }
            	}
        			
        			
        		}
               
        	}
        
        	
        	System.out.println("Clock Cycle:"+clockCycle);
    		System.out.println(registersFile.getAllRegistersInfo());
    		System.out.println();
        	instructionQueue.printAllInstructions();
        	System.out.println();
        	addSubReservation.printAllReservationStations();
        	System.out.println();
        	System.out.println(executionTime.getAllExecutionTimesInfo());
        	System.out.println();
        	System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
        	
        	
        	
            
            //cheack if there is a reservation queueu now have vj and vk  
            
//    	
    }
    	
    }
    
    
    
    private void addToAddSubReservationStation(ALUInstruction aluInstr) {
    	//System.out.println(registersFile.getRegister(aluInstr.getRd()).getQi());
    	
    	if(aluInstr.operation.equals("BNEZ") ) {
    		System.out.println(registersFile.getRegister(aluInstr.getRd()).getQi());
    		System.out.println("R9:"+registersFile.getRegister(aluInstr.getRd()).getValue());
    		
    		if(registersFile.getRegister(aluInstr.getRd()).getValue()!=0 && registersFile.getRegister(aluInstr.getRd()).getQi().equals("0")) {
    			int old = instqueue;
    			instqueue=Integer.parseInt(aluInstr.getRt());
    			//System.out.println(instqueue);
    			//System.out.println("hi");
    			for(int i = instqueue; i<old ; i++) {
    				instructionQueue.markInstructionAsUndone(i);
    			}
    			//clockCycle++;
            	System.out.println("Clock Cycle:"+clockCycle);
        		System.out.println("-------------------------------------------------------");
    		}
    		else {
    			
    			instructionQueue.markInstructionAsDone(instqueue);
    			System.out.println("Clock Cycle:"+clockCycle);
    			System.out.println("LOOP NOT TAKEN");
        		System.out.println("-------------------------------------------------------");
    		}
    		if(registersFile.getRegister(aluInstr.getRd()).getValue()==0) {
    			instqueue++;
    		}
    	
    		return;
    		
    	}
    	
    	
    	
    	if(aluInstr.getOperation().equals("ADD.D")||aluInstr.getOperation().equals("SUB.D")) {
    	
        // Find the first empty space in addSubReservation and add the instruction
        int emptyIndex = addSubReservation.findFirstEmptyReservationStationAddSub();
        //System.out.println(emptyIndex);
        if (emptyIndex != -1) {
        	
        	
        	System.out.println(aluInstr.getRt());
        	//registersFile.getRegister(aluInstr.getRd()).getValue();
        	if(registersFile.getRegister(aluInstr.getRs()).getQi().equals("0") &&  registersFile.getRegister(aluInstr.getRt()).getQi().equals("0")) {  // vj and vk is there
        		
        		
        		
        		addSubReservation.setReservationStationEntryAddSub(emptyIndex, true, aluInstr.getOperation(), ""+registersFile.getRegister(aluInstr.getRs()).getValue(), ""+registersFile.getRegister(aluInstr.getRt()).getValue(), null, null , instqueue );
      
        		registersFile.setRegister(aluInstr.getRd(), addSubReservation.getReservationStationNameAddSub(emptyIndex), registersFile.getRegisterValueOrDefault(aluInstr.getRd())); //value howww
        		//clockCycle++;
        		if(aluInstr.getOperation().equals("ADD.D")) {
        		executionTime.setTimeDetails(addSubReservation.getReservationStationNameAddSub(emptyIndex), clockCycle+AddClockcycle, clockCycle+1+AddClockcycle);
        		}
        		else if(aluInstr.getOperation().equals("SUB.D")) {
        			executionTime.setTimeDetails(addSubReservation.getReservationStationNameAddSub(emptyIndex), clockCycle+SUBClockcycle, clockCycle+1+SUBClockcycle);
        		}
        		
        		System.out.println("Clock Cycle:"+clockCycle);
        		System.out.println(registersFile.getAllRegistersInfo());
        		System.out.println();
            	instructionQueue.printAllInstructions();
            	System.out.println();
            	addSubReservation.printAllReservationStations();
            	System.out.println();
            	System.out.println(executionTime.getAllExecutionTimesInfo());
            	System.out.println();
            	System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
            	if (instqueue<queuesize) {
           		 instqueue++;;
   				
   			}
        		return;
        	}
        	
            if(registersFile.getRegister(aluInstr.getRs()).getQi().equals("0") &&  !registersFile.getRegister(aluInstr.getRt()).getQi().equals("0")) {  // vj and qk is there
        		
        		
        		addSubReservation.setReservationStationEntryAddSub(emptyIndex, true, aluInstr.getOperation(), ""+registersFile.getRegister(aluInstr.getRs()).getValue(), null , null, registersFile.getRegister(aluInstr.getRt()).getQi() , instqueue );
        		registersFile.setRegister(aluInstr.getRd(), addSubReservation.getReservationStationNameAddSub(emptyIndex), registersFile.getRegisterValueOrDefault(aluInstr.getRd()));  // value howw 
        		//clockCycle++;
        		
        		System.out.println("Clock Cycle:"+clockCycle);
        		System.out.println(registersFile.getAllRegistersInfo());
        		System.out.println();
            	instructionQueue.printAllInstructions();
            	System.out.println();
            	addSubReservation.printAllReservationStations();
            	System.out.println();
            	System.out.println(executionTime.getAllExecutionTimesInfo());
            	System.out.println();
            	System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
            	
            	 if (instqueue<queuesize) {
            		 instqueue++;;
    				
    			}
            	 return;
            	
        		
        	}
            
            if( !registersFile.getRegister(aluInstr.getRs()).getQi().equals("0") &&  registersFile.getRegister(aluInstr.getRt()).getQi().equals("0")) {  // vk and qj is there
        		
        		
        		addSubReservation.setReservationStationEntryAddSub(emptyIndex, true, aluInstr.getOperation(), null , ""+registersFile.getRegister(aluInstr.getRt()).getValue() , registersFile.getRegister(aluInstr.getRs()).getQi(), null , instqueue);
        		registersFile.setRegister(aluInstr.getRd(), addSubReservation.getReservationStationNameAddSub(emptyIndex), registersFile.getRegisterValueOrDefault(aluInstr.getRd()));  // value howw 
        		//clockCycle++;
        		
        		System.out.println("Clock Cycle:"+clockCycle);
        		System.out.println(registersFile.getAllRegistersInfo());
        		System.out.println();
            	instructionQueue.printAllInstructions();
            	System.out.println();
            	addSubReservation.printAllReservationStations();
            	System.out.println();
            	System.out.println(executionTime.getAllExecutionTimesInfo());
            	System.out.println();
            	System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
            	
            	 if (instqueue<queuesize) {
            		 instqueue++;;
    				
    			}
            	 return;
        	}
            
            if( !registersFile.getRegister(aluInstr.getRs()).getQi().equals("0") &&  !registersFile.getRegister(aluInstr.getRt()).getQi().equals("0")) {  // vk and qj is there
        		
            	addSubReservation.setReservationStationEntryAddSub(emptyIndex, true, aluInstr.getOperation(), null , null , registersFile.getRegister(aluInstr.getRs()).getQi(), registersFile.getRegister(aluInstr.getRt()).getQi() , instqueue);
        		registersFile.setRegister(aluInstr.getRd(), addSubReservation.getReservationStationNameAddSub(emptyIndex), registersFile.getRegisterValueOrDefault(aluInstr.getRd()));  
        		
            	System.out.println("Clock Cycle:"+clockCycle);
        		System.out.println(registersFile.getAllRegistersInfo());
        		System.out.println();
            	instructionQueue.printAllInstructions();
            	System.out.println();
            	addSubReservation.printAllReservationStations();
            	System.out.println();
            	System.out.println(executionTime.getAllExecutionTimesInfo());
            	System.out.println();
            	System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
            	 if (instqueue<queuesize) {
            		 instqueue++;;
    				
    			}
            	 return;
        		
        	}
            
            
            
            
        
        	
            // Assume setReservationStationEntry is modified to accept ALUInstruction
        	
        }
        
        
        
    }
    	
    	else if(aluInstr.getOperation().equals("MUL.D")||aluInstr.getOperation().equals("DIV.D")) {
        	
            // Find the first empty space in addSubReservation and add the instruction
            int emptyIndex = addSubReservation.findFirstEmptyReservationStationMulDiv();
            //System.out.println(emptyIndex);
            if (emptyIndex != -1) {
            	
            	
            	System.out.println(aluInstr.getRt());
            	//registersFile.getRegister(aluInstr.getRd()).getValue();
            	if(registersFile.getRegister(aluInstr.getRs()).getQi().equals("0") &&  registersFile.getRegister(aluInstr.getRt()).getQi().equals("0")) {  // vj and vk is there
            		
            	
            		
            		addSubReservation.setReservationStationEntryMulDiv(emptyIndex, true, aluInstr.getOperation(), ""+registersFile.getRegister(aluInstr.getRs()).getValue(), ""+registersFile.getRegister(aluInstr.getRt()).getValue(), null, null , instqueue );
          
            		registersFile.setRegister(aluInstr.getRd(), addSubReservation.getReservationStationNameMulDiv(emptyIndex), registersFile.getRegisterValueOrDefault(aluInstr.getRd())); //value howww
            		//clockCycle++;
            		if(aluInstr.getOperation().equals("MUL.D")) {
                		executionTime.setTimeDetails(addSubReservation.getReservationStationNameMulDiv(emptyIndex), clockCycle+AddClockcycle, clockCycle+1+AddClockcycle);
                		}
                		else if(aluInstr.getOperation().equals("DIV.D")) {
                			executionTime.setTimeDetails(addSubReservation.getReservationStationNameMulDiv(emptyIndex), clockCycle+SUBClockcycle, clockCycle+1+SUBClockcycle);
                		}
            		
            		System.out.println("Clock Cycle:"+clockCycle);
            		System.out.println(registersFile.getAllRegistersInfo());
            		System.out.println();
                	instructionQueue.printAllInstructions();
                	System.out.println();
                	addSubReservation.printAllReservationStations();
                	System.out.println();
                	System.out.println(executionTime.getAllExecutionTimesInfo());
                	System.out.println();
                	System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
                	if (instqueue<queuesize) {
               		 instqueue++;;
       				
       			}
            		return;
            	}
            	
                if(registersFile.getRegister(aluInstr.getRs()).getQi().equals("0") &&  !registersFile.getRegister(aluInstr.getRt()).getQi().equals("0")) {  // vj and qk is there
            		
            		
            		addSubReservation.setReservationStationEntryMulDiv(emptyIndex, true, aluInstr.getOperation(), ""+registersFile.getRegister(aluInstr.getRs()).getValue(), null , null, registersFile.getRegister(aluInstr.getRt()).getQi() , instqueue );
            		registersFile.setRegister(aluInstr.getRd(), addSubReservation.getReservationStationNameMulDiv(emptyIndex), registersFile.getRegisterValueOrDefault(aluInstr.getRd()));  // value howw 
            		//clockCycle++;
            		
            		System.out.println("Clock Cycle:"+clockCycle);
            		System.out.println(registersFile.getAllRegistersInfo());
            		System.out.println();
                	instructionQueue.printAllInstructions();
                	System.out.println();
                	addSubReservation.printAllReservationStations();
                	System.out.println();
                	System.out.println(executionTime.getAllExecutionTimesInfo());
                	System.out.println();
                	System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
                	
                	 if (instqueue<queuesize) {
                		 instqueue++;;
        				
        			}
                	 return;
                	
            		
            	}
                
                if( !registersFile.getRegister(aluInstr.getRs()).getQi().equals("0") &&  registersFile.getRegister(aluInstr.getRt()).getQi().equals("0")) {  // vk and qj is there
            		
            		
            		addSubReservation.setReservationStationEntryMulDiv(emptyIndex, true, aluInstr.getOperation(), null , ""+registersFile.getRegister(aluInstr.getRt()).getValue() , registersFile.getRegister(aluInstr.getRs()).getQi(), null , instqueue);
            		registersFile.setRegister(aluInstr.getRd(), addSubReservation.getReservationStationNameMulDiv(emptyIndex), registersFile.getRegisterValueOrDefault(aluInstr.getRd()));  // value howw 
            		//clockCycle++;
            		
            		System.out.println("Clock Cycle:"+clockCycle);
            		System.out.println(registersFile.getAllRegistersInfo());
            		System.out.println();
                	instructionQueue.printAllInstructions();
                	System.out.println();
                	addSubReservation.printAllReservationStations();
                	System.out.println();
                	System.out.println(executionTime.getAllExecutionTimesInfo());
                	System.out.println();
                	System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
                	
                	 if (instqueue<queuesize) {
                		 instqueue++;;
        				
        			}
                	 return;
            	}
                
                if( !registersFile.getRegister(aluInstr.getRs()).getQi().equals("0") &&  !registersFile.getRegister(aluInstr.getRt()).getQi().equals("0")) {  // vk and qj is there
            		
                	addSubReservation.setReservationStationEntryMulDiv(emptyIndex, true, aluInstr.getOperation(), null , null , registersFile.getRegister(aluInstr.getRs()).getQi(), registersFile.getRegister(aluInstr.getRt()).getQi() , instqueue);
            		registersFile.setRegister(aluInstr.getRd(), addSubReservation.getReservationStationNameMulDiv(emptyIndex), registersFile.getRegisterValueOrDefault(aluInstr.getRd()));  
            		
                	System.out.println("Clock Cycle:"+clockCycle);
            		System.out.println(registersFile.getAllRegistersInfo());
            		System.out.println();
                	instructionQueue.printAllInstructions();
                	System.out.println();
                	addSubReservation.printAllReservationStations();
                	System.out.println();
                	System.out.println(executionTime.getAllExecutionTimesInfo());
                	System.out.println();
                	System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
                	 if (instqueue<queuesize) {
                		 instqueue++;;
        				
        			}
                	 return;
            		
            	}
                
                
                
                
            
            	
                // Assume setReservationStationEntry is modified to accept ALUInstruction
            	
            }
            
            
            
        }
    	
    	else if(aluInstr.getOperation().equals("ADD.I")||aluInstr.getOperation().equals("SUB.I")) {
        	
            // Find the first empty space in addSubReservation and add the instruction
            int emptyIndex = addSubReservation.findFirstEmptyReservationStationAddSub();
            //System.out.println(emptyIndex);
            if (emptyIndex != -1) {
            	
            	
            	
            	//registersFile.getRegister(aluInstr.getRd()).getValue();
            	if(registersFile.getRegister(aluInstr.getRs()).getQi().equals("0") ) {  // vj and vk is there
            		
            		
            		
            		addSubReservation.setReservationStationEntryAddSub(emptyIndex, true, aluInstr.getOperation(), ""+registersFile.getRegister(aluInstr.getRs()).getValue(), aluInstr.getRt(), null, null , instqueue );
          
            		registersFile.setRegister(aluInstr.getRd(), addSubReservation.getReservationStationNameAddSub(emptyIndex), registersFile.getRegisterValueOrDefault(aluInstr.getRd())); //value howww
            		//clockCycle++;
            		if(aluInstr.getOperation().equals("ADD.I") || aluInstr.getOperation().equals("SUB.I")) {
            		executionTime.setTimeDetails(addSubReservation.getReservationStationNameAddSub(emptyIndex), clockCycle+1, clockCycle+2);
            		}
            		
            		System.out.println("Clock Cycle:"+clockCycle);
            		System.out.println(registersFile.getAllRegistersInfo());
            		System.out.println();
                	instructionQueue.printAllInstructions();
                	System.out.println();
                	addSubReservation.printAllReservationStations();
                	System.out.println();
                	System.out.println(executionTime.getAllExecutionTimesInfo());
                	System.out.println();
                	System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
                	if (instqueue<queuesize) {
               		 instqueue++;;
       				
       			}
            		return;
            	}
            	
         
                
                if( !registersFile.getRegister(aluInstr.getRs()).getQi().equals("0") ) {  // vk and qj is there
            		
            		                                                                                                            
            		addSubReservation.setReservationStationEntryAddSub(emptyIndex, true, aluInstr.getOperation(), null , aluInstr.getRt()  , registersFile.getRegister(aluInstr.getRs()).getQi() , null , instqueue);
            		registersFile.setRegister(aluInstr.getRd(), addSubReservation.getReservationStationNameAddSub(emptyIndex), registersFile.getRegisterValueOrDefault(aluInstr.getRd()));  // value howw 
            		//clockCycle++;
            		
            		System.out.println("Clock Cycle:"+clockCycle);
            		System.out.println(registersFile.getAllRegistersInfo());
            		System.out.println();
                	instructionQueue.printAllInstructions();
                	System.out.println();
                	addSubReservation.printAllReservationStations();
                	System.out.println();
                	System.out.println(executionTime.getAllExecutionTimesInfo());
                	System.out.println();
                	System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
                	
                	 if (instqueue<queuesize) {
                		 instqueue++;;
        				
        			}
                	 return;
            	}
                
               
                
                
                
                
            
            	
                // Assume setReservationStationEntry is modified to accept ALUInstruction
            	
            }
            
            
            
        }
    	
        else {
        	//clockCycle++;
        	System.out.println("Clock Cycle:"+clockCycle);
    		System.out.println("-------------------------------------------------------");
    		
        }
        
        
        
    }

    
    public void setRegisterValue(String registerName, int value) {
        registersFile.setRegister(registerName, "", value);
    }
    

   public static void main(String[] args) throws IOException {
	  TomasuloSimulator x = new TomasuloSimulator();
	  x.readInstructionsFromFile("Mips.txt");
	 // x.instructionQueue.printAllInstructions();
	  x.registersFile.setRegister("R2", "0", 3 );
	  x.registersFile.setRegister("R3", "0", 2 );
//	  x.registersFile.setRegister("R4", "0", 6 );
//	  x.registersFile.setRegister("R6", "0", 2 );
//	  x.registersFile.setRegister("R8", "0", 1 );
	 // x.registersFile.setRegister("R9", "0", 2 );
	 // System.out.println(x.registersFile.getRegister("R2").getAll());
	  x.instqueue =0;
	  
	  x.AddClockcycle=4;
	  x.MULClockcycle =6;
	  x.DIVClockcycle = 8;
	  x.SUBClockcycle =3;
			  
	  
	  
	   x.queuesize = x.instructionQueue.getSize();
	 
	  
	 
	  InstructionQueue instrq = x.instructionQueue;
	//  instrq.printAllInstructions();
	  
	System.out.println(!instrq.areAllInstructionsDone());
	System.out.println(x.executionTime.isExecutionTimesEmpty());

	    while(!instrq.areAllInstructionsDone() ) {
	    	x.clockCycle++;
//	    	if(x.executionTime.hasWriteResultTime(x.clockCycle)) {
//	    		x.loadToBus();
//	    		
//	    	}
	    	
	    	
	    	
	    	 Instruction instruction = x.instructionQueue.getInstruction(x.instqueue);
	    	 
	    
	    	 if (instruction instanceof ALUInstruction && x.instqueue<x.queuesize) {
	                ALUInstruction aluInstr = (ALUInstruction) instruction;
	            //  System.out.println(aluInstr.getFullInstruction());
	              
	              System.out.println(aluInstr.fullInstruction);
	                if(aluInstr.operation.equals("ADD.D") || aluInstr.operation.equals("SUB.D") ||  aluInstr.operation.equals("BNEZ") ||aluInstr.operation.equals("MUL.D")|| aluInstr.operation.equals("DIV.D") ||aluInstr.operation.equals("SUB.I") ||aluInstr.operation.equals("ADD.I"))
	                x.addToAddSubReservationStation(aluInstr);
	            }
	    	 
	    	 if(x.executionTime.hasWriteResultTime(x.clockCycle)) {
		    		x.loadToBus();
		    		
		    	}
	    	 if (x.instqueue>=x.queuesize) {
	    		 System.out.println("Clock Cycle:"+ x.clockCycle);
	    		 System.out.println("---------------------------------------------------");
	    		//x.clockCycle++;
				
			}
	    	 
	    }
	    while(!x.executionTime.isExecutionTimesEmpty()) {
	    	x.clockCycle++;
	    	if(x.executionTime.hasWriteResultTime(x.clockCycle)) {
	    		x.loadToBus();
	    	}
	    	else {
	    		System.out.println("Clock Cycle:"+x.clockCycle);
	    		System.out.println("-------------------------------------------------------");
	    		//x.clockCycle++;
	    	}
	    
	    }
	    
	    
   }
}
