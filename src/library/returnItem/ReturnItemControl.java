package library.returnItem;
import library.entities.Item;
import library.entities.Library;
import library.entities.Loan;

public class ReturnItemControl {

	private ReturnBookUI ui;
	private enum ControlState { INITIALISED, READY, INSPECTING };
	private ControlState state;
	
	private Library library;
	private Loan currentLoan;
	

	public ReturnItemControl() {
		this.library = Library.getInstance();
		state = ControlState.INITIALISED;
	}
	
	
	public void setUI(ReturnBookUI ui) {
		if (!state.equals(ControlState.INITIALISED)) 
			throw new RuntimeException("ReturnBookControl: cannot call setUI except in INITIALISED state");
		
		this.ui = ui;
		ui.SeTrEaDy();
		state = ControlState.READY;		
	}


	public void itemScanned(long bookID) {
		if (!state.equals(ControlState.READY)) 
			throw new RuntimeException("ReturnBookControl: cannot call bookScanned except in READY state");
		
		Item currentItem = library.getItem(bookID);
		
		if (currentItem == null) {
			ui.DiSpLaY("Invalid Book Id");
			return;
		}
		if (!currentItem.isOnLoan()) {
			ui.DiSpLaY("Book has not been borrowed");
			return;
		}		
		currentLoan = library.getLoanByItemId(bookID);	
		double overDueFine = 0.0;
		if (currentLoan.isOverDue()) 
			overDueFine = library.calculateOverDueFine(currentLoan);
		
		ui.DiSpLaY("Inspecting");
		ui.DiSpLaY(currentItem.toString());
		ui.DiSpLaY(currentLoan.toString());
		
		if (currentLoan.isOverDue()) 
			ui.DiSpLaY(String.format("\nOverdue fine : $%.2f", overDueFine));
		
		ui.SeTiNsPeCtInG();
		state = ControlState.INSPECTING;		
	}


	public void scanningCompleted() {
		if (!state.equals(ControlState.READY)) 
			throw new RuntimeException("ReturnBookControl: cannot call scanningComplete except in READY state");
		
		ui.SeTCoMpLeTeD();
	}


	public void dischargeLoan(boolean isDamaged) {
		if (!state.equals(ControlState.INSPECTING)) 
			throw new RuntimeException("ReturnBookControl: cannot call dischargeLoan except in INSPECTING state");
		
		library.dischargeLoan(currentLoan, isDamaged);
		currentLoan = null;
		ui.SeTrEaDy();
		state = ControlState.READY;				
	}


}
