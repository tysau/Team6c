package library.payfine;
import java.util.Scanner;


public class PayFineUI {


    private enum PayFineUIState { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };

    private PayFineControl control;
    private Scanner scanner;
    private PayFineUIState uiState;

	
    public PayFineUI(PayFineControl control) {
        this.control = control;
        scanner = new Scanner(System.in);
        uiState = PayFineUIState.INITIALISED;
        control.setUI(this);
    }
	
	
    public void run() {
        displayOutput("Pay Fine Use Case UI\n");
		
        while (true) {
			
            switch (uiState) {
			
                case READY: {
                    String patronString = getInput("Swipe patron card (press <enter> to cancel): ");
                    if (patronString.length() == 0) {
                        control.cancel();
                        break;
                    }
                    try {
                        long patronId = Long.valueOf(patronString).longValue();
                        control.cardSwiped(patronId);
                    }
                    catch (NumberFormatException e) {
                        displayOutput("Invalid patronID");
                    }
                    break;
                }
				
                case PAYING: {
                    double amount = 0;
                    String amountString = getInput("Enter amount (<Enter> cancels) : ");
                    if (amountString.length() == 0) {
                        control.cancel();
                        break;
                    }
                    try {
                        amount = Double.valueOf(amountString).doubleValue();
                    }
                    catch (NumberFormatException e) {}
                    if (amount <= 0) {
                        displayOutput("Amount must be positive");
                        break;
                    }
                    control.payFine(amount);
                    break;
                }
								
                case CANCELLED: {
                    displayOutput("Pay Fine process cancelled");
                    return;
                }
			
                case COMPLETED: {
                    displayOutput("Pay Fine process complete");
                    return;
                }
			
                default: {
                    displayOutput("Unhandled state");
                    throw new RuntimeException("FixBookUI : unhandled state :" + uiState);			
                }
            }		
        }		
    }

	
    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }	
		
		
    private void displayOutput(Object object) {
        System.out.println(object);
    }	
			

    public void display(Object object) {
        displayOutput(object);
    }


    public void setCompleted() {
        uiState = PayFineUIState.COMPLETED;		
    }


    public void setPaying() {
        uiState = PayFineUIState.PAYING;
    }


    public void setCancelled() {
        uiState = PayFineUIState.CANCELLED;	
    }


    public void setReady() {
        uiState = PayFineUIState.READY;	
    }


}