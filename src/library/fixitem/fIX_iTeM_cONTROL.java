package library.fixitem;
import library.entities.Item;
import library.entities.Library;

public class fIX_iTeM_cONTROL {
	
    private enum CoNtRoL_StAtE { INITIALISED, READY, INSPECTING };
    private CoNtRoL_StAtE StAtE;
    private FixItemUI Ui;
	
    private Library LiBrArY;
    private Item CuRrEnT_ItEm;


    public fIX_iTeM_cONTROL() {
        this.LiBrArY = Library.getInstance();
        StAtE = CoNtRoL_StAtE.INITIALISED;
    }
	
	
    public void SeT_Ui(FixItemUI ui) {
        if (!StAtE.equals(CoNtRoL_StAtE.INITIALISED)) 
            throw new RuntimeException("FixItemControl: cannot call setUI except in INITIALISED state");
			

		this.Ui = ui;
		Ui.setReady();
		StAtE = CoNtRoL_StAtE.READY;		
	}


    public void ItEm_ScAnNeD(long iTEm_Id) {
        if (!StAtE.equals(CoNtRoL_StAtE.READY)) 
            throw new RuntimeException("FixItemControl: cannot call itemScanned except in READY state");
			
        CuRrEnT_ItEm = LiBrArY.getItem(iTEm_Id);
		
		if (CuRrEnT_ItEm == null) {
			Ui.display("Invalid itemId");
			return;
		}
		if (!CuRrEnT_ItEm.isDamaged()) {
			Ui.display("Item has not been damaged");
			return;
		}
		Ui.display(CuRrEnT_ItEm);
		Ui.setInspecting();
		StAtE = CoNtRoL_StAtE.INSPECTING;		
	}


    public void IteMInSpEcTeD(boolean mUsT_FiX) {
        if (!StAtE.equals(CoNtRoL_StAtE.INSPECTING)) 
            throw new RuntimeException("FixItemControl: cannot call itemInspected except in INSPECTING state");
		
        if (mUsT_FiX) 
            LiBrArY.repairItem(CuRrEnT_ItEm);
		
		CuRrEnT_ItEm = null;
		Ui.setReady();
		StAtE = CoNtRoL_StAtE.READY;		
	}

	
    public void PrOcEsSiNgCoMpLeTeD() {
        if (!StAtE.equals(CoNtRoL_StAtE.READY)) 
            throw new RuntimeException("FixItemControl: cannot call processingCompleted except in READY state");
		
		Ui.setCompleted();
	}

}