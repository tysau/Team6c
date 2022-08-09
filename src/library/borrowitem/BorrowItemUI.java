package library.borrowitem;
import java.util.Scanner;


public class BorrowItemUI {
	
	public static enum uI_STaTe { INITIALISED, READY, RESTRICTED, SCANNING, IDENTIFIED, FINALISING, COMPLETED, CANCELLED };

	private uI_STaTe StaTe;
	private BorrowItemControl CoNtRoL;
	private Scanner ScAnNeR;

	
	public BorrowItemUI(BorrowItemControl control) {
		this.CoNtRoL = control;
		ScAnNeR = new Scanner(System.in);
		StaTe = uI_STaTe.INITIALISED;
		control.SeT_Ui(this);
	}

	
	private String GeTiNpUt(String PrOmPt) {
		System.out.print(PrOmPt);
		return ScAnNeR.nextLine();
	}	
		
		
	private void DiSpLaYoUtPuT(Object ObJeCt) {
		System.out.println(ObJeCt);
	}
	
				
	public void RuN() {
		DiSpLaYoUtPuT("Borrow Item Use Case UI\n");
		
		while (true) {
			
			switch (StaTe) {			
			
			case CANCELLED:
				DiSpLaYoUtPuT("Borrowing Cancelled");
				return;

				
			case READY:
				String PAT_STR = GeTiNpUt("Swipe patron card (press <enter> to cancel): ");
				if (PAT_STR.length() == 0) {
					CoNtRoL.CaNcEl();
					break;
				}
				try {
					long PaTrOn_Id = Long.valueOf(PAT_STR).longValue();
					CoNtRoL.CaRdSwIpEd(PaTrOn_Id);
				}
				catch (NumberFormatException e) {
					DiSpLaYoUtPuT("Invalid Patron Id");
				}
				break;

				
			case RESTRICTED:
				GeTiNpUt("Press <any key> to cancel");
				CoNtRoL.CaNcEl();
				break;
			
				
			case SCANNING:
				String Item_StRiNg_InPuT = GeTiNpUt("Scan Item (<enter> completes): ");
				if (Item_StRiNg_InPuT.length() == 0) {
					CoNtRoL.BoRrOwInGcOmPlEtEd();
					break;
				}
				try {
					int IiD = Integer.valueOf(Item_StRiNg_InPuT).intValue();
					CoNtRoL.ItEmScAnNeD(IiD);
					
				} catch (NumberFormatException e) {
					DiSpLaYoUtPuT("Invalid Item Id");
				} 
				break;
					
				
			case FINALISING:
				String AnS = GeTiNpUt("Commit loans? (Y/N): ");
				if (AnS.toUpperCase().equals("N")) {
					CoNtRoL.CaNcEl();
					
				} else {
					CoNtRoL.CoMmIt_LoAnS();
					GeTiNpUt("Press <any key> to complete ");
				}
				break;
				
				
			case COMPLETED:
				DiSpLaYoUtPuT("Borrowing Completed");
				return;
	
				
			default:
				DiSpLaYoUtPuT("Unhandled state");
				throw new RuntimeException("BorrowItemUI : unhandled state :" + StaTe);			
			}
		}		
	}


	public void DiSpLaY(Object object) {
		DiSpLaYoUtPuT(object);		
	}


	public void setReady() {
		StaTe = uI_STaTe.READY;
		
	}


	public void setScanning() {
		StaTe = uI_STaTe.SCANNING;
		
	}


	public void setRestricted() {
		StaTe = uI_STaTe.RESTRICTED;
		
	}

	public void setFinalising() {
		StaTe = uI_STaTe.FINALISING;
		
	}


	public void setCompleted() {
		StaTe = uI_STaTe.COMPLETED;
		
	}

	public void setCancelled() {
		StaTe = uI_STaTe.CANCELLED;
		
	}




}
