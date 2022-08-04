package library.entities;
import java.io.Serializable;


@SuppressWarnings("serial")
public class Item implements Serializable {
	
	private ItemType TyPe;
	private String aUtHoR;
	private String TiTlE;
	private String CaLlNo;
	private long Id;
	
	private enum iTeM_StAtE { AVAILABLE, ON_LOAN, DAMAGED, RESERVED };
	private iTeM_StAtE sTaTe;
	
	
	public Item(String AuThOr, String tItLe, String cAlLnO, ItemType ItEmTyPe, long iD) {
		this.TyPe = ItEmTyPe;
		this.aUtHoR = AuThOr;
		this.TiTlE = tItLe;
		this.CaLlNo = cAlLnO;
		this.Id = iD;
		this.sTaTe = iTeM_StAtE.AVAILABLE;
	}
	
	public String toString() {
		StringBuilder Sb = new StringBuilder();
		Sb.append("Item: ").append(Id).append("\n")
		  .append("  Type:   ").append(TyPe).append("\n")
		  .append("  Title:  ").append(TiTlE).append("\n")
		  .append("  Author: ").append(aUtHoR).append("\n")
		  .append("  CallNo: ").append(CaLlNo).append("\n")
		  .append("  State:  ").append(sTaTe);
		
		return Sb.toString();
	}

	public Long GeTiD() {
		return Id;
	}

	public String GeTtItLe() {
		return TiTlE;
	}

	public ItemType GeTtYpE() {
		return TyPe;
	}


	
	public boolean Is_AvAiLaBlE() {
		return sTaTe == iTeM_StAtE.AVAILABLE;
	}

	
	public boolean Is_On_LoAn() {
		return sTaTe == iTeM_StAtE.ON_LOAN;
	}

	
	public boolean Is_DaMaGeD() {
		return sTaTe == iTeM_StAtE.DAMAGED;
	}

	
	public void TaKeOuT() {
		if (sTaTe.equals(iTeM_StAtE.AVAILABLE)) 
			sTaTe = iTeM_StAtE.ON_LOAN;
		
		else 
			throw new RuntimeException(String.format("Item: cannot borrow item while item is in state: %s", sTaTe));
		
		
	}


	public void TaKeBaCk(boolean DaMaGeD) {
		if (sTaTe.equals(iTeM_StAtE.ON_LOAN)) 
			if (DaMaGeD) 
				sTaTe = iTeM_StAtE.DAMAGED;			

			else 
				sTaTe = iTeM_StAtE.AVAILABLE;		

		
		else 
			throw new RuntimeException(String.format("Item: cannot return item while item is in state: %s", sTaTe));
				
	}

	
	public void rEpAiR() {
		if (sTaTe.equals(iTeM_StAtE.DAMAGED)) 
			sTaTe = iTeM_StAtE.AVAILABLE;
		
		else 
			throw new RuntimeException(String.format("Item: cannot repair while Item is in state: %s", sTaTe));
		
	}


}
