package library.returnBook;
import java.util.Scanner;


public class ReturnItemUI {

    private enum ReturnItemUIState { INITIALISED, READY, INSPECTING, COMPLETED };

    private rETURN_bOOK_cONTROL control;
    private Scanner scanner;
        private ReturnItemUIState uiState;

	
    public ReturnItemUI(rETURN_bOOK_cONTROL returnItemControl) {
        this.control = control;
        scanner = new Scanner(System.in);
        uiState = ReturnItemUIState.INITIALISED;
        control.sEt_uI(this);
    }


    public void run() {		
        displayOutput("Return Book Use Case UI\n");
            
        while (true) {
            switch (uiState) {
                    
                case INITIALISED:
                    break;
                    
                case READY:
                    String bookInputString = getInput("Scan Book (<enter> completes): ");
                    if (bookInputString.length() == 0){ 
                        control.sCaNnInG_cOmPlEtEd();
                    }
                    else {
                        try {
                            long bookId = Long.valueOf(bookInputString).longValue();
                            control.bOoK_sCaNnEd(bookId);
                        }
                        catch (NumberFormatException e) {
                            displayOutput("Invalid bookId");
                        }
                    }
                    break;				
                case INSPECTING:
                    String response = getInput("Is book damaged? (Y/N): ");
                    boolean isDamaged = false;
                    if (response.toUpperCase().equals("Y")) {					
                        isDamaged = true;
                    }
                        
                    control.dIsChArGe_lOaN(isDamaged);
			
                case COMPLETED:
                    displayOutput("Return processing complete");
                    return;
			
                default:
                    displayOutput("Unhandled state");
                    throw new RuntimeException("ReturnBookUI : unhandled state :" + uiState);			
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
