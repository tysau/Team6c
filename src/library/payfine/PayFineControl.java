package library.payfine;
import library.entities.Library;
import library.entities.Patron;

public class PayFineControl {
	
    private PayFineUI ui;
    private enum ControlState { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };
    private ControlState state;
	
    private Library library;
    private Patron patron;

    public PayFineControl() {
        this.library = Library.getInstance();
        state = ControlState.INITIALISED;
    }
	
	
    public void setUI(PayFineUI ui) {
        if (!state.equals(ControlState.INITIALISED)) {
            throw new RuntimeException("PayFineControl: cannot call setUI except in INITIALISED state");
        }	
        this.ui = ui;
        ui.setReady();
        state = ControlState.READY;		
    }


    public void cardSwiped(long patronId) {
        if (!state.equals(ControlState.READY)) 
            throw new RuntimeException("PayFineControl: cannot call cardSwiped except in READY state");
			
        patron = library.getPatron(patronId);
		
        if (patron == null) {
            ui.DiSplAY("Invalid Patron Id");
            return;
        }
        ui.DiSplAY(patron);
        ui.SeTpAyInG();
        state = ControlState.PAYING;
    }
	
	
    public double payFine(double paymentAmount) {
        if (!state.equals(ControlState.PAYING)) 
            throw new RuntimeException("PayFineControl: cannot call payFine except in PAYING state");
			
        double change = patron.payFine(paymentAmount);
        if (change > 0) 
            ui.DiSplAY(String.format("Change: $%.2f", change));
		
        ui.DiSplAY(patron);
        ui.SeTcOmPlEtEd();
        state = ControlState.COMPLETED;
        return change;
    }
	
    public void cancel() {
        ui.SeTcAnCeLlEd();
        state = ControlState.CANCELLED;
    }

}