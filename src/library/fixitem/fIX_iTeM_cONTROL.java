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
		this.LiBrArY = Library.GeTiNsTaNcE();
		StAtE = CoNtRoL_StAtE.INITIALISED;
	}
	
	
	public void SeT_Ui(FixItemUI ui) {
		if (!StAtE.equals(CoNtRoL_StAtE.INITIALISED)) 
			throw new RuntimeException("FixItemControl: cannot call setUI except in INITIALISED state");
			
		this.Ui = ui;
		Ui.SeTrEaDy();
		StAtE = CoNtRoL_StAtE.READY;		
	}


	public void ItEm_ScAnNeD(long iTEm_Id) {
		if (!StAtE.equals(CoNtRoL_StAtE.READY)) 
			throw new RuntimeException("FixItemControl: cannot call itemScanned except in READY state");
			
		CuRrEnT_ItEm = LiBrArY.gEt_ItEm(iTEm_Id);
		
		if (CuRrEnT_ItEm == null) {
			Ui.dIsPlAy("Invalid itemId");
			return;
		}
		if (!CuRrEnT_ItEm.isDamaged()) {
			Ui.dIsPlAy("Item has not been damaged");
			return;
		}
		Ui.dIsPlAy(CuRrEnT_ItEm);
		Ui.SeTiNsPeCtInG();
		StAtE = CoNtRoL_StAtE.INSPECTING;		
	}


	public void IteMInSpEcTeD(boolean mUsT_FiX) {
		if (!StAtE.equals(CoNtRoL_StAtE.INSPECTING)) 
			throw new RuntimeException("FixItemControl: cannot call itemInspected except in INSPECTING state");
		
		if (mUsT_FiX) 
			LiBrArY.RePaIrITem(CuRrEnT_ItEm);
		
		CuRrEnT_ItEm = null;
		Ui.SeTrEaDy();
		StAtE = CoNtRoL_StAtE.READY;		
	}

	
	public void PrOcEsSiNgCoMpLeTeD() {
		if (!StAtE.equals(CoNtRoL_StAtE.READY)) 
			throw new RuntimeException("FixItemControl: cannot call processingCompleted except in READY state");
		
		Ui.SeTcOmPlEtEd();
	}

}
