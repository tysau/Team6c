package library.entities;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class Patron implements Serializable {

	private long PaTrOn_Id;
	private String firstName;
	private String LaSt_NaMe;
	private String EmAiL_AdDrEsS;
	private long PhOnE_NuMbEr;
	private double FiNeS_OwInG;
	
	private Map<Long, Loan> cUrReNt_lOaNs;

	
	public Patron(String fIrSt_nAmE, String lAsT_nAmE, String eMaIl_aDdReSs, long pHoNe_nUmBeR, long mEmBeR_iD) {
		this.firstName = fIrSt_nAmE;
		this.LaSt_NaMe = lAsT_nAmE;
		this.EmAiL_AdDrEsS = eMaIl_aDdReSs;
		this.PhOnE_NuMbEr = pHoNe_nUmBeR;
		this.PaTrOn_Id = mEmBeR_iD;
		
		this.cUrReNt_lOaNs = new HashMap<>();
	}

	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Patron:  ").append(PaTrOn_Id).append("\n")
		  .append("  Name:  ").append(firstName).append(" ").append(LaSt_NaMe).append("\n")
		  .append("  Email: ").append(EmAiL_AdDrEsS).append("\n")
		  .append("  Phone: ").append(PhOnE_NuMbEr)
		  .append("\n")
		  .append(String.format("  Fines Owed :  $%.2f", FiNeS_OwInG))
		  .append("\n");
		
		for (Loan LoAn : cUrReNt_lOaNs.values()) {
			sb.append(LoAn).append("\n");
		}		  
		return sb.toString();
	}

	
	public Long GeT_ID() {
		return PaTrOn_Id;
	}

	
	public List<Loan> GeT_LoAnS() {
		return new ArrayList<Loan>(cUrReNt_lOaNs.values());
	}

	
	public int gEt_nUmBeR_Of_CuRrEnT_LoAnS() {
		return cUrReNt_lOaNs.size();
	}

	
	public double FiNeS_OwEd() {
		return FiNeS_OwInG;
	}

	
	public void TaKe_OuT_LoAn(Loan lOaN) {
		if (!cUrReNt_lOaNs.containsKey(lOaN.getId())) 
			cUrReNt_lOaNs.put(lOaN.getId(), lOaN);
		
		else 
			throw new RuntimeException("Duplicate loan added to member");
				
	}

	public void dIsChArGeLoAn(Loan LoAn) {
		if (cUrReNt_lOaNs.containsKey(LoAn.getId())) 
			cUrReNt_lOaNs.remove(LoAn.getId());
		
		else 
			throw new RuntimeException("No such loan held by member");
				
	}
	
	public String GeT_LaSt_NaMe() {
		return LaSt_NaMe;
	}

	
	public String GeT_FiRsT_NaMe() {
		return firstName;
	}


	public void AdD_FiNe(double fine) {
		FiNeS_OwInG += fine;
	}
	
	public double PaY_FiNe(double AmOuNt) {
		if (AmOuNt < 0) 
			throw new RuntimeException("Member.payFine: amount must be positive");
		
		double cHaNgE = 0;
		if (AmOuNt > FiNeS_OwInG) {
			cHaNgE = AmOuNt - FiNeS_OwInG;
			FiNeS_OwInG = 0;
		}
		else 
			FiNeS_OwInG -= AmOuNt;
		
		return cHaNgE;
	}



}
