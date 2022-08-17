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
    private enum ControlState { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };
    private ControlState state;
	
    private List<Item> pendingList;
    private List<Loan> completedList;
    private Item item;	
	
    public BorrowItemControl() {
        this.library = library.getInstance();
        state = ControlState.INITIALISED;
    }
	

    public void setUI(BorrowItemUI ui) {
        if (!state.equals(ControlState.INITIALISED)) {
	   throw new RuntimeException("BorrowItemControl: cannot call setUI except in INITIALISED state");
        }
        this.ui = ui;
        ui.setReady();
        state = ControlState.READY;		
    }

		
    public void cardSwiped(long patronId) {
        if (!state.equals(ControlState.READY)) {
	   throw new RuntimeException("BorrowItemControl: cannot call cardSwiped except in READY state");
        }		
        patron = library.getPatron(patronId);
        if (patron == null) {
            ui.display("Invalid patronId");
            return;
        }
        if (library.canPatronBorrow(patron)) {
            pendingList = new ArrayList<>();
            ui.setScanning();
            state = ControlState.SCANNING; 
        }
        else {
            ui.display("Patron cannot borrow at this time");
            ui.setRestricted(); 
        }
    }
	
	
    public void itemScanned(int itemId) {
        item = null;
        if (!state.equals(ControlState.SCANNING)) {
            throw new RuntimeException("BorrowItemControl: cannot call itemScanned except in SCANNING state");
        }		
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
        for (Item item : pendingList) {
             ui.display(item);
        }	
        if (library.getNumberOfLoansRemainingForPatron(patron) - pendingList.size() == 0) {
            ui.display("Loan limit reached");
            borrowingCompleted();
        }
    }
	
	
    public void borrowingCompleted() {
        if (pendingList.size() == 0) {
            cancel();
        }	
        else {
            ui.display("\nFinal Borrowing List");
            for (Item item : pendingList) {
                ui.display(item);
            }	
            completedList = new ArrayList<Loan>();
            ui.setFinalising();
            state = ControlState.FINALISING;
	}
    }


    public void commitLoans() {
        if (!state.equals(ControlState.FINALISING)) {
            throw new RuntimeException("BorrowItemControl: cannot call commitLoans except in FINALISING state");
        }		
        for (Item B : pendingList) {
            Loan loan = library.issueLoan(B, patron);
            completedList.add(loan);			
        }
        ui.display("Completed Loan Slip");
        for (Loan loan : completedList) {
            ui.display(loan);
        }	
        ui.setCompleted();
        state = ControlState.COMPLETED;
    }

	
    public void cancel() {
        ui.setCancelled();
        state = ControlState.CANCELLED;
    }	
}
