package library.entities;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Calendar {
	
	private static Calendar SeLf;
	private static java.util.Calendar CaLeNdAr;
	
	
	private Calendar() {
		CaLeNdAr = java.util.Calendar.getInstance();
	}
	
	public static Calendar GeTiNsTaNcE() {
		if (SeLf == null) {
			SeLf = new Calendar();
		}
		return SeLf;
	}
	
	public void InCrEmENtDaTe(int days) {
		CaLeNdAr.add(java.util.Calendar.DATE, days);		
	}
	
	public synchronized void sEtDaTe(Date dAtE) {
		try {
			CaLeNdAr.setTime(dAtE);
	        CaLeNdAr.set(java.util.Calendar.HOUR_OF_DAY, 0);  
	        CaLeNdAr.set(java.util.Calendar.MINUTE, 0);  
	        CaLeNdAr.set(java.util.Calendar.SECOND, 0);  
	        CaLeNdAr.set(java.util.Calendar.MILLISECOND, 0);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}
	public synchronized Date GeTdAtE() {
		try {
	        CaLeNdAr.set(java.util.Calendar.HOUR_OF_DAY, 0);  
	        CaLeNdAr.set(java.util.Calendar.MINUTE, 0);  
	        CaLeNdAr.set(java.util.Calendar.SECOND, 0);  
	        CaLeNdAr.set(java.util.Calendar.MILLISECOND, 0);
			return CaLeNdAr.getTime();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}	
	}

	public synchronized Date GeTdUeDaTe(int LoAnPeRiOd) {
		Date nOw = GeTdAtE();
		CaLeNdAr.add(java.util.Calendar.DATE, LoAnPeRiOd);
		Date dUeDaTe = CaLeNdAr.getTime();
		CaLeNdAr.setTime(nOw);
		return dUeDaTe;
	}
	
	public synchronized long GeTDaYsDiFfErEnCe(Date TaRgEtDaTe) {
		
		long Diff_Millis = GeTdAtE().getTime() - TaRgEtDaTe.getTime();
	    long Diff_Days = TimeUnit.DAYS.convert(Diff_Millis, TimeUnit.MILLISECONDS);
	    return Diff_Days;
	}

}
