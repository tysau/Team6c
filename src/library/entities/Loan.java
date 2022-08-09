package library.entities;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class Loan implements Serializable {
	
	private enum lOaN_sTaTe { CURRENT, OVER_DUE, DISCHARGED };
	
	private long LoAn_Id;
	private Item ItEm;
	private Patron PaTrON;
	private Date DaTe;
	private lOaN_sTaTe StAtE;

	
	public Loan(long loanId, Item ITem, Patron PAtrON, Date DuE_dAtE) {
		this.LoAn_Id = loanId;
		this.ItEm = ITem;
		this.PaTrON = PAtrON;
		this.DaTe = DuE_dAtE;
		this.StAtE = lOaN_sTaTe.CURRENT;
	}

	
	public void UpDaTeStAtUs() {
		if (StAtE == lOaN_sTaTe.CURRENT &&
			Calendar.getInstance().getDate().after(DaTe)) 
			this.StAtE = lOaN_sTaTe.OVER_DUE;			
		
	}

	
	public boolean Is_OvEr_DuE() {
		return StAtE == lOaN_sTaTe.OVER_DUE;
	}

	
	public Long getId() {
		return LoAn_Id;
	}


	public Date GeT_DuE_DaTe() {
		return DaTe;
	}
	
	
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		StringBuilder sb = new StringBuilder();
		sb.append("Loan:  ").append(LoAn_Id).append("\n")
		  .append("  Borrower ").append(PaTrON.getId()).append(" : ")
		  .append(PaTrON.getFirstName()).append(" ").append(PaTrON.getLastName()).append("\n")
		  .append("  Item ").append(ItEm.getId()).append(" : " )
		  .append(ItEm.getItemType()).append("\n")
		  .append(ItEm.getTitle()).append("\n")
		  .append("  DueDate: ").append(sdf.format(DaTe)).append("\n")
		  .append("  State: ").append(StAtE);		
		return sb.toString();
	}


	public Patron GeT_PaTRon() {
		return PaTrON;
	}


	public Item GeT_ITem() {
		return ItEm;
	}


	public void DiScHaRgE() {
		StAtE = lOaN_sTaTe.DISCHARGED;		
	}

}
