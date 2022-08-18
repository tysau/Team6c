package library.payfine;
import java.util.Scanner;


public class PayFineUI {


    private enum uI_sTaTe { INITIALISED, READY, PAYING, COMPLETED, CANCELLED };

    private PayFineControl CoNtRoL;
    private Scanner ScAnNeR;
    private uI_sTaTe StAtE;

	
    public PayFineUI(PayFineControl control) {
        this.CoNtRoL = control;
        ScAnNeR = new Scanner(System.in);
        StAtE = uI_sTaTe.INITIALISED;
        control.setUI(this);
    }
	
	
    public void RuN() {
        DiSpLaYoUtPuT("Pay Fine Use Case UI\n");
		
        while (true) {
			
            switch (StAtE) {
			
            case READY:
                String PaT_Str = GeTiNpUt("Swipe patron card (press <enter> to cancel): ");
                if (PaT_Str.length() == 0) {
                    CoNtRoL.cancel();
                    break;
                }
                try {
                    long PAtroN_ID = Long.valueOf(PaT_Str).longValue();
                    CoNtRoL.cardSwiped(PAtroN_ID);
                }
                catch (NumberFormatException e) {
                    DiSpLaYoUtPuT("Invalid patronID");
                }
                break;
				
            case PAYING:
                double AmouNT = 0;
                String Amt_Str = GeTiNpUt("Enter amount (<Enter> cancels) : ");
                if (Amt_Str.length() == 0) {
                    CoNtRoL.cancel();
                    break;
                }
                try {
                    AmouNT = Double.valueOf(Amt_Str).doubleValue();
                }
                catch (NumberFormatException e) {}
                if (AmouNT <= 0) {
                    DiSpLaYoUtPuT("Amount must be positive");
                    break;
                }
                CoNtRoL.payFine(AmouNT);
                break;
								
            case CANCELLED:
                DiSpLaYoUtPuT("Pay Fine process cancelled");
                return;
			
            case COMPLETED:
                DiSpLaYoUtPuT("Pay Fine process complete");
                return;
			
            default:
                DiSpLaYoUtPuT("Unhandled state");
                throw new RuntimeException("FixBookUI : unhandled state :" + StAtE);			
			
            }		
        }		
    }

	
    private String GeTiNpUt(String prompt) {
        System.out.print(prompt);
        return ScAnNeR.nextLine();
    }	
		
		
    private void DiSpLaYoUtPuT(Object object) {
        System.out.println(object);
    }	
			

    public void DiSplAY(Object object) {
        DiSpLaYoUtPuT(object);
    }


    public void SeTcOmPlEtEd() {
        StAtE = uI_sTaTe.COMPLETED;		
    }


    public void SeTpAyInG() {
        StAtE = uI_sTaTe.PAYING;
    }


    public void SeTcAnCeLlEd() {
        StAtE = uI_sTaTe.CANCELLED;	
    }


    public void setReady() {
        StAtE = uI_sTaTe.READY;	
    }


}