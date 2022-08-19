package library.fixitem;
import java.util.Scanner;


public class FixItemUI {

    private enum FixItemUIState { INITIALISED, READY, INSPECTING, COMPLETED };

    private fIX_iTeM_cONTROL control;
    private Scanner scanner;
    private FixItemUIState state;

	
    public FixItemUI(fIX_iTeM_cONTROL control) {
        this.control = control;
        scanner = new Scanner(System.in);
        state = FixItemUIState.INITIALISED;
        control.SeT_Ui(this);
    }


    public void run() {
        displayOutput("Fix Item Use Case UI\n");
		
        while (true) {
			
            switch (state) {
			
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
                    throw new RuntimeException("FixItemUI : unhandled state :" + state);	
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
        this.state = FixItemUIState.INSPECTING;		
    }


    public void setReady() {
        this.state = FixItemUIState.READY;
    }


    public void setCompleted() {
        this.state = FixItemUIState.COMPLETED;
    }
	
	
}