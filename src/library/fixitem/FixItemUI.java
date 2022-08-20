package library.fixitem;
import java.util.Scanner;


public class FixItemUI {

    private enum FixItemUIState { INITIALISED, READY, INSPECTING, COMPLETED };

    private fIX_iTeM_cONTROL control;
    private Scanner scanner;
    private FixItemUIState uiState;

	
    public FixItemUI(fIX_iTeM_cONTROL fixItemControl) {
        this.control = fixItemControl;
        scanner = new Scanner(System.in);
        uiState = FixItemUIState.INITIALISED;
        fixItemControl.SeT_Ui(this);
    }


    public void run() {
        displayOutput("Fix Item Use Case UI\n");
		
        while (true) {
			
            switch (uiState) {
			
                case READY: {
                    String itemEntryString = getInput("Scan Item (<enter> completes): ");
                    if (itemEntryString.length() == 0) 
                        control.PrOcEsSiNgCoMpLeTeD();
				
                    else {
                        try {
                            long itemId = Long.valueOf(itemEntryString).longValue();
                            control.ItEm_ScAnNeD(itemId);
                        }
                        catch (NumberFormatException e) {
                            displayOutput("Invalid itemId");
                        }
                    }
                    break;	
                }
				
                case INSPECTING: {
                    String answer = getInput("Fix Item? (Y/N) : ");
                    boolean answerBoolean = false;
                    if (answer.toUpperCase().equals("Y")) 
                        answerBoolean = true;
				
                    control.IteMInSpEcTeD(answerBoolean);
                    break;
                }
								
                case COMPLETED: {
                    displayOutput("Fixing process complete");
                    return;
                }
			
                default: {
                    displayOutput("Unhandled state");
                    throw new RuntimeException("FixItemUI : unhandled state :" + uiState);	
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


    public void setInspecting() {
        this.uiState = FixItemUIState.INSPECTING;		
    }


    public void setReady() {
        this.uiState = FixItemUIState.READY;
    }


    public void setCompleted() {
        this.uiState = FixItemUIState.COMPLETED;
    }
	
	
}