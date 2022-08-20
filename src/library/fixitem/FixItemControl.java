package library.fixitem;
import library.entities.Item;
import library.entities.Library;

public class FixItemControl{
	
    private enum ControlState { INITIALISED, READY, INSPECTING };
    private ControlState state;
    private FixItemUI ui;
	
    private Library library;
    private Item currentItem;


    public FixItemControl() {
        this.library = Library.getInstance();
        state = ControlState.INITIALISED;
    }
	
	
    public void setUI(FixItemUI ui) {
        if (!state.equals(ControlState.INITIALISED)) {
            throw new RuntimeException("FixItemControl: cannot call setUI except in INITIALISED state");
        }
        
        this.ui = ui;
        ui.setReady();
        state = ControlState.READY;		
    }


    public void itemScanned(long bookId) {
        if (!state.equals(ControlState.READY)) { 
            throw new RuntimeException("FixItemControl: cannot call itemScanned except in READY state");
        }
        
        currentItem = library.getItem(bookId);
		
        if (currentItem == null) {
            ui.display("Invalid itemId");
            return;
        }
        if (!currentItem.isDamaged()) {
            ui.display("Item has not been damaged");
            return;
        }
        ui.display(currentItem);
        ui.setInspecting();
        state = ControlState.INSPECTING;		
    }


    public void itemInspected(boolean mustFix) {
        if (!state.equals(ControlState.INSPECTING)) {
            throw new RuntimeException("FixItemControl: cannot call itemInspected except in INSPECTING state");
        }
        
        if (mustFix) {
            library.repairItem(currentItem);
        }
		
        currentItem = null;
        ui.setReady();
        state = ControlState.READY;		
    }

	
    public void processingCompleted() {
        if (!state.equals(ControlState.READY)) {
            throw new RuntimeException("FixItemControl: cannot call processingCompleted except in READY state");
        }
		
        ui.setCompleted();
    }
}