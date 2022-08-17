package library.returnBook;
import library.entities.Item;
import library.entities.Library;
import library.entities.Loan;

public class rETURN_bOOK_cONTROL {

	private ReturnItemUI Ui;
	private enum cOnTrOl_sTaTe { INITIALISED, READY, INSPECTING };
	private cOnTrOl_sTaTe sTaTe;
	
	private Library lIbRaRy;
	private Loan CurrENT_loan;
	

	public rETURN_bOOK_cONTROL() {
		this.lIbRaRy = Library.getInstance();
		sTaTe = cOnTrOl_sTaTe.INITIALISED;
	}
	
	
	public void sEt_uI(ReturnItemUI uI) {
		if (!sTaTe.equals(cOnTrOl_sTaTe.INITIALISED)) 
			throw new RuntimeException("ReturnBookControl: cannot call setUI except in INITIALISED state");
		
		this.Ui = uI;
		uI.setReady();
		sTaTe = cOnTrOl_sTaTe.READY;		
	}


	public void bOoK_sCaNnEd(long bOoK_iD) {
		if (!sTaTe.equals(cOnTrOl_sTaTe.READY)) 
			throw new RuntimeException("ReturnBookControl: cannot call bookScanned except in READY state");
		
		Item cUrReNt_bOoK = lIbRaRy.getItem(bOoK_iD);
		
		if (cUrReNt_bOoK == null) {
			Ui.display("Invalid Book Id");
			return;
		}
		if (!cUrReNt_bOoK.isOnLoan()) {
			Ui.display("Book has not been borrowed");
			return;
		}		
		CurrENT_loan = lIbRaRy.getLoanByItemId(bOoK_iD);	
		double Over_Due_Fine = 0.0;
		if (CurrENT_loan.isOverDue()) 
			Over_Due_Fine = lIbRaRy.calculateOverDueFine(CurrENT_loan);
		
		Ui.display("Inspecting");
		Ui.display(cUrReNt_bOoK.toString());
		Ui.display(CurrENT_loan.toString());
		
		if (CurrENT_loan.isOverDue()) 
			Ui.display(String.format("\nOverdue fine : $%.2f", Over_Due_Fine));
		
		Ui.setInspecting();
		sTaTe = cOnTrOl_sTaTe.INSPECTING;		
	}


	public void sCaNnInG_cOmPlEtEd() {
		if (!sTaTe.equals(cOnTrOl_sTaTe.READY)) 
			throw new RuntimeException("ReturnBookControl: cannot call scanningComplete except in READY state");
		
		Ui.setCompleted();
	}


	public void dIsChArGe_lOaN(boolean iS_dAmAgEd) {
		if (!sTaTe.equals(cOnTrOl_sTaTe.INSPECTING)) 
			throw new RuntimeException("ReturnBookControl: cannot call dischargeLoan except in INSPECTING state");
		
		lIbRaRy.dischargeLoan(CurrENT_loan, iS_dAmAgEd);
		CurrENT_loan = null;
		Ui.setReady();
		sTaTe = cOnTrOl_sTaTe.READY;				
	}


}