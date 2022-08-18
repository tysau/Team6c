package library.returnItem;
import library.entities.Item;
import library.entities.Library;
import library.entities.Loan;

public class ReturnItemControl {

    private ReturnItemUI ui;
    private enum ControlState { INITIALISED, READY, INSPECTING };
    private ControlState state;
	
    private Library library;
    private Loan currentLoan;
	

    public ReturnItemControl() {
        this.library = Library.getInstance();
        state = ControlState.INITIALISED;
    }
	
	
    public void setUI(ReturnItemUI ui) {
        if (!state.equals(ControlState.INITIALISED)) {
            throw new RuntimeException("ReturnItemControl: cannot call setUI except in INITIALISED state");
        }
        this.ui = ui;
        ui.setReady();
        state = ControlState.READY;		
    }


    public void itemScanned(long itemId) {
        if (!state.equals(ControlState.READY)) {
            throw new RuntimeException("ReturnItemControl: cannot call itemScanned except in READY state");
        }
        
        Item currentItem = library.getItem(itemId);
        
        if (currentItem == null) {
            ui.display("Invalid Item Id");
            return;
        }
        if (!currentItem.isOnLoan()) {
            ui.display("Item has not been borrowed");
            return;
        }		
        currentLoan = library.getLoanByItemId(itemId);	
        double overDueFine = 0.0;
        if (currentLoan.isOverDue()) {
            overDueFine = library.calculateOverDueFine(currentLoan);
        }
        
        ui.display("Inspecting");
        ui.display(currentItem.toString());
        ui.display(currentLoan.toString());
		
        if (currentLoan.isOverDue()) {
            ui.display(String.format("\nOverdue fine : $%.2f", overDueFine));
        }
	
        ui.setInspecting();
        state = ControlState.INSPECTING;		
    }


    public void scanningCompleted() {
        if (!state.equals(ControlState.READY)) {
            throw new RuntimeException("ReturnItemControl: cannot call scanningComplete except in READY state");
        }
        ui.setCompleted();
    }


    public void dischargeLoan(boolean isDamaged) {
        if (!state.equals(ControlState.INSPECTING)) {
            throw new RuntimeException("ReturnItemControl: cannot call dischargeLoan except in INSPECTING state");
        }
        
        library.dischargeLoan(currentLoan, isDamaged);
        currentLoan = null;
        ui.setReady();
        state = ControlState.READY;				
    }


}
