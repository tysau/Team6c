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
	
	
	public void sEt_uI(ReturnBookUI uI) {
		if (!state.equals(ControlState.INITIALISED)) 
			throw new RuntimeException("ReturnBookControl: cannot call setUI except in INITIALISED state");
		
		this.ui = uI;
		uI.SeTrEaDy();
		state = ControlState.READY;		
	}


	public void bOoK_sCaNnEd(long bOoK_iD) {
		if (!state.equals(ControlState.READY)) 
			throw new RuntimeException("ReturnBookControl: cannot call bookScanned except in READY state");
		
		Item cUrReNt_bOoK = library.getItem(bOoK_iD);
		
		if (cUrReNt_bOoK == null) {
			ui.DiSpLaY("Invalid Book Id");
			return;
		}
		if (!cUrReNt_bOoK.isOnLoan()) {
			ui.DiSpLaY("Book has not been borrowed");
			return;
		}		
		currentLoan = library.getLoanByItemId(bOoK_iD);	
		double Over_Due_Fine = 0.0;
		if (currentLoan.isOverDue()) 
			Over_Due_Fine = library.calculateOverDueFine(currentLoan);
		
		ui.DiSpLaY("Inspecting");
		ui.DiSpLaY(cUrReNt_bOoK.toString());
		ui.DiSpLaY(currentLoan.toString());
		
		if (currentLoan.isOverDue()) 
			ui.DiSpLaY(String.format("\nOverdue fine : $%.2f", Over_Due_Fine));
		
		ui.SeTiNsPeCtInG();
		state = ControlState.INSPECTING;		
	}


	public void sCaNnInG_cOmPlEtEd() {
		if (!state.equals(ControlState.READY)) 
			throw new RuntimeException("ReturnBookControl: cannot call scanningComplete except in READY state");
		
		ui.SeTCoMpLeTeD();
	}


	public void dIsChArGe_lOaN(boolean iS_dAmAgEd) {
		if (!state.equals(ControlState.INSPECTING)) 
			throw new RuntimeException("ReturnBookControl: cannot call dischargeLoan except in INSPECTING state");
		
		library.dischargeLoan(currentLoan, iS_dAmAgEd);
		currentLoan = null;
		ui.SeTrEaDy();
		state = ControlState.READY;				
	}


}
