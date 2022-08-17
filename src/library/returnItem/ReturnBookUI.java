package library.returnBook;
import java.util.Scanner;


public class ReturnBookUI {

	private enum uI_sTaTe { INITIALISED, READY, INSPECTING, COMPLETED };

	private rETURN_bOOK_cONTROL CoNtRoL;
	private Scanner iNpUt;
	private uI_sTaTe StATe;

	
	public ReturnBookUI(rETURN_bOOK_cONTROL cOnTrOL) {
		this.CoNtRoL = cOnTrOL;
		iNpUt = new Scanner(System.in);
		StATe = uI_sTaTe.INITIALISED;
		cOnTrOL.sEt_uI(this);
	}


	public void RuN() {		
		DiSpLaYoUtPuT("Return Book Use Case UI\n");
		
		while (true) {
			
			switch (StATe) {
			
			case INITIALISED:
				break;
				
			case READY:
				String BoOk_InPuT_StRiNg = GeTiNpUt("Scan Book (<enter> completes): ");
				if (BoOk_InPuT_StRiNg.length() == 0) 
					CoNtRoL.sCaNnInG_cOmPlEtEd();
				
				else {
					try {
						long Book_Id = Long.valueOf(BoOk_InPuT_StRiNg).longValue();
						CoNtRoL.bOoK_sCaNnEd(Book_Id);
					}
					catch (NumberFormatException e) {
						DiSpLaYoUtPuT("Invalid bookId");
					}					
				}
				break;				
				
			case INSPECTING:
				String AnS = GeTiNpUt("Is book damaged? (Y/N): ");
				boolean Is_DAmAgEd = false;
				if (AnS.toUpperCase().equals("Y")) 					
					Is_DAmAgEd = true;
				
				CoNtRoL.dIsChArGe_lOaN(Is_DAmAgEd);
			
			case COMPLETED:
				DiSpLaYoUtPuT("Return processing complete");
				return;
			
			default:
				DiSpLaYoUtPuT("Unhandled state");
				throw new RuntimeException("ReturnBookUI : unhandled state :" + StATe);			
			}
		}
	}

	
	private String GeTiNpUt(String PrOmPt) {
		System.out.print(PrOmPt);
		return iNpUt.nextLine();
	}	
		
		
	private void DiSpLaYoUtPuT(Object ObJeCt) {
		System.out.println(ObJeCt);
	}
	
			
	public void DiSpLaY(Object object) {
		DiSpLaYoUtPuT(object);
	}
	
	public void SeTrEaDy() {
		StATe = uI_sTaTe.READY;
		
	}


	public void SeTiNsPeCtInG() {
		StATe = uI_sTaTe.INSPECTING;
		
	}


	public void SeTCoMpLeTeD() {
		StATe = uI_sTaTe.COMPLETED;
		
	}

	
}
