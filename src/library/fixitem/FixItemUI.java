package library.fixitem;
import java.util.Scanner;


public class FixItemUI {

	private enum uI_sTaTe { INITIALISED, READY, INSPECTING, COMPLETED };

	private fIX_iTeM_cONTROL CoNtRoL;
	private Scanner InPuT;
	private uI_sTaTe StAtE;

	
	public FixItemUI(fIX_iTeM_cONTROL CoNtRoL) {
		this.CoNtRoL = CoNtRoL;
		InPuT = new Scanner(System.in);
		StAtE = uI_sTaTe.INITIALISED;
		CoNtRoL.SeT_Ui(this);
	}


	public void RuN() {
		DiSpLaY_OuTpUt("Fix Item Use Case UI\n");
		
		while (true) {
			
			switch (StAtE) {
			
			case READY:
				String ITem_EnTrY_StRiNg = GeTiNpUt("Scan Item (<enter> completes): ");
				if (ITem_EnTrY_StRiNg.length() == 0) 
					CoNtRoL.PrOcEsSiNgCoMpLeTeD();
				
				else {
					try {
						long itEM_Id = Long.valueOf(ITem_EnTrY_StRiNg).longValue();
						CoNtRoL.ItEm_ScAnNeD(itEM_Id);
					}
					catch (NumberFormatException e) {
						DiSpLaY_OuTpUt("Invalid itemId");
					}
				}
				break;	
				
			case INSPECTING:
				String AnS = GeTiNpUt("Fix Item? (Y/N) : ");
				boolean MuStFiX = false;
				if (AnS.toUpperCase().equals("Y")) 
					MuStFiX = true;
				
				CoNtRoL.IteMInSpEcTeD(MuStFiX);
				break;
								
			case COMPLETED:
				DiSpLaY_OuTpUt("Fixing process complete");
				return;
			
			default:
				DiSpLaY_OuTpUt("Unhandled state");
				throw new RuntimeException("FixItemUI : unhandled state :" + StAtE);			
			
			}		
		}
		
	}

	
	private String GeTiNpUt(String prompt) {
		System.out.print(prompt);
		return InPuT.nextLine();
	}	
		
		
	private void DiSpLaY_OuTpUt(Object DiSpLaYoBjEcT) {
		System.out.println(DiSpLaYoBjEcT);
	}
	

	public void dIsPlAy(Object DiSpLaYoBjEcT) {
		DiSpLaY_OuTpUt(DiSpLaYoBjEcT);
	}


	public void SeTiNsPeCtInG() {
		this.StAtE = uI_sTaTe.INSPECTING;
		
	}


	public void SeTrEaDy() {
		this.StAtE = uI_sTaTe.READY;
		
	}


	public void SeTcOmPlEtEd() {
		this.StAtE = uI_sTaTe.COMPLETED;
		
	}
	
	
}
