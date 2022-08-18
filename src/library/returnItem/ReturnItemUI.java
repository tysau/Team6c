package library.returnItem;
import java.util.Scanner;


public class ReturnItemUI {

    private enum ReturnItemUIState { INITIALISED, READY, INSPECTING, COMPLETED };

    private ReturnItemControl control;
    private Scanner scanner;
    private ReturnItemUIState uiState;

	
    public ReturnItemUI(ReturnItemControl returnItemControl) {
        this.control = returnItemControl;
        scanner = new Scanner(System.in);
        uiState = ReturnItemUIState.INITIALISED;
        returnItemControl.setUI(this);
    }


    public void run() {		
        displayOutput("Return Item Use Case UI\n");
            
        while (true) {
            switch (uiState) {
                    
                case INITIALISED: {
                    break;
                }
                    
                case READY: {
                    String itemInputString = getInput("Scan item (<enter> completes): ");
                    if (itemInputString.length() == 0){ 
                        control.scanningCompleted();
                    }
                    else {
                        try {
                            long itemId = Long.valueOf(itemInputString).longValue();
                            control.itemScanned(itemId);
                        }
                        catch (NumberFormatException e) {
                            displayOutput("Invalid itemId");
                        }
                    }
                    break;
                }    
                
                case INSPECTING: {
                    String response = getInput("Is item damaged? (Y/N): ");
                    boolean isDamaged = false;
                    if (response.toUpperCase().equals("Y")) {					
                        isDamaged = true;
                    }
                        
                    control.dischargeLoan(isDamaged);
                }	
                
                case COMPLETED: {
                    displayOutput("Return processing complete");
                    return;
                }	
                
                default: {
                    displayOutput("Unhandled state");
                    throw new RuntimeException("ReturnItemUI : unhandled state :" + uiState);	
                }
            }
        }
    }

	
    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }	
		
		
    private void displayOutput(Object displayObject) {
        System.out.println(displayObject);
    }
	
			
    public void display(Object displayObject) {
        displayOutput(displayObject);
    }
	
    public void setReady() {
        uiState = ReturnItemUIState.READY;
    }


    public void setInspecting() {
        uiState = ReturnItemUIState.INSPECTING;
    }


    public void setCompleted() {
        uiState = ReturnItemUIState.COMPLETED;
    }


}
