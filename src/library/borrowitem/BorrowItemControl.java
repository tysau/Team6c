package library.borrowitem;
import java.util.ArrayList;
import java.util.List;

import library.entities.Item;
import library.entities.Library;
import library.entities.Loan;
import library.entities.Patron;

public class BorrowItemControl {
	
	private BorrowItemUI ui;
	
	private Library library;
	private Patron patron;
	private enum CONTROL_STATE { initialised, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };
	private CONTROL_STATE state;
	
	private List<Item> pendingList;
	private List<Loan> completedList;
	private Item item;
	
	
	public BorrowItemControl() {
		this.library = Library.getInstance();
		state = CONTROL_STATE.initialised;
	}
	

	public void SeT_Ui(BorrowItemUI ui) {
		if (!state.equals(CONTROL_STATE.initialised)) 
			throw new RuntimeException("BorrowItemControl: cannot call setUI except in INITIALISED state");
			
		this.ui = ui;
		ui.setReady();
		state = CONTROL_STATE.READY;		
	}

		
	public void cardSwiped(long patronId) {
		if (!state.equals(CONTROL_STATE.READY)) 
			throw new RuntimeException("BorrowItemControl: cannot call cardSwiped except in READY state");
			
		patron = library.getPatron(patronId);
		if (patron == null) {
			ui.display("Invalid patronId");
			return;
		}
		if (library.canPatronBorrow(patron)) {
			pendingList = new ArrayList<>();
			ui.setScanning();
			state = CONTROL_STATE.SCANNING; 
		}
		else {
			ui.display("Patron cannot borrow at this time");
			ui.setRestricted(); 
		}
	}
	
	
	public void itemScanned(int itemId) {
		item = null;
		if (!state.equals(CONTROL_STATE.SCANNING)) 
			throw new RuntimeException("BorrowItemControl: cannot call itemScanned except in SCANNING state");
			
		item = library.getItem(itemId);
		if (item == null) {
			ui.display("Invalid itemId");
			return;
		}
		if (!item.isAvailable()) {
			ui.display("Item cannot be borrowed");
			return;
		}
		pendingList.add(item);
		for (Item item : pendingList) 
			ui.display(item);
		
		if (library.getNumberOfLoansRemainingForPatron(patron) - pendingList.size() == 0) {
			ui.display("Loan limit reached");
			borrowingCompleted();
		}
	}
	
	
	public void borrowingCompleted() {
		if (pendingList.size() == 0) 
			cancel();
		
		else {
			ui.display("\nFinal Borrowing List");
			for (Item item : pendingList) 
				ui.display(item);
			
			completedList = new ArrayList<Loan>();
			ui.setFinalising();
			state = CONTROL_STATE.FINALISING;
		}
	}


	public void commitLoans() {
		if (!state.equals(CONTROL_STATE.FINALISING)) 
			throw new RuntimeException("BorrowItemControl: cannot call commitLoans except in FINALISING state");
			
		for (Item B : pendingList) {
			Loan loan = library.issueLoan(B, patron);
			completedList.add(loan);			
		}
		ui.display("Completed Loan Slip");
		for (Loan loan : completedList) 
			ui.display(loan);
		
		ui.setCompleted();
		state = CONTROL_STATE.COMPLETED;
	}

	
	public void cancel() {
		ui.setCancelled();
		state = CONTROL_STATE.CANCELLED;
	}
	
	
}
