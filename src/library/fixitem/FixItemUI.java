package library.fixitem;
import java.util.Scanner;


public class FixItemUI {

    private enum FixItemUIState { INITIALISED, READY, INSPECTING, COMPLETED };

    private fIX_iTeM_cONTROL CoNtRoL;
    private Scanner scanner;
    private FixItemUIState state;

	
    public FixItemUI(fIX_iTeM_cONTROL CoNtRoL) {
        this.CoNtRoL = CoNtRoL;
        scanner = new Scanner(System.in);
        state = FixItemUIState.INITIALISED;
        CoNtRoL.SeT_Ui(this);
    }


    public void RuN() {
        displayOutput("Fix Item Use Case UI\n");
		
        while (true) {
			
            switch (state) {
			
            case READY:
                String ITem_EnTrY_StRiNg = getInput("Scan Item (<enter> completes): ");
                if (ITem_EnTrY_StRiNg.length() == 0) 
                    CoNtRoL.PrOcEsSiNgCoMpLeTeD();
				
                else {
                    try {
                        long itEM_Id = Long.valueOf(ITem_EnTrY_StRiNg).longValue();
                        CoNtRoL.ItEm_ScAnNeD(itEM_Id);
                    }
                    catch (NumberFormatException e) {
                        displayOutput("Invalid itemId");
                    }
                }
                break;	
				
            case INSPECTING:
                String AnS = getInput("Fix Item? (Y/N) : ");
                boolean MuStFiX = false;
                if (AnS.toUpperCase().equals("Y")) 
                    MuStFiX = true;
				
                CoNtRoL.IteMInSpEcTeD(MuStFiX);
                break;
								
            case COMPLETED:
                displayOutput("Fixing process complete");
                return;
			
            default:
                displayOutput("Unhandled state");
                throw new RuntimeException("FixItemUI : unhandled state :" + state);			
			
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