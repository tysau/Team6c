package library;

import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

import library.entities.Item;
import library.entities.ItemType;
import library.borrowitem.BorrowItemUI;
import library.borrowitem.BorrowItemControl;
import library.entities.Calendar;
import library.entities.Library;
import library.entities.Loan;
import library.entities.Patron;
import library.fixitem.FixItemUI;
import library.fixitem.FixItemControl;
import library.payfine.PayFineUI;
import library.payfine.PayFineControl;
import library.returnItem.ReturnItemUI;
import library.returnItem.ReturnItemControl;


public class Main {
	
    private static Scanner scanner;
    private static Library library;
    private static Calendar calendar;
    private static SimpleDateFormat simpleDateFormat;
	
    private static String menu = """
		Library Main Menu
		
			AP  : add patron
			LP : list patrons
		
			AI  : add item
			LI : list items
			FI : fix item
		
			B  : borrow an item
			R  : return an item
			L  : list loans
		
			P  : pay fine
		
			T  : increment date
			Q  : quit
		
		Choice : 
		""";		

	
    public static void main(String[] args) {		
        try {			
            scanner = new Scanner(System.in);
            library = Library.getInstance();
            calendar = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            
            for (Patron patron : library.listPatrons()) {
                displayOutput(patron);
            }
            displayOutput(" ");
            for (Item item : library.listItems()) {
                displayOutput(item);
            }
						
            boolean finished = false;
			
            while (!finished) {
                Date currentDate = calendar.getDate();
                Object currentDateFormatted = simpleDateFormat.format(currentDate);
                displayOutput("\n" + currentDateFormatted);
                String choice = getInput(menu);
			
                switch (choice.toUpperCase()) {
			
                    case "AP": {
                        addPatron();
                        break;
                    }	
                    case "LP": {
                        listPatrons();
                        break;
                    }	
                    case "AI": {
                        addItem();
                        break;
                    }				
                    case "LI": { 
                        listItems();
                        break;
                    }		
                    case "FI": {
                        fixItems();
                        break;
                    }		
                    case "B": {
                        borrowItem();
                        break;
                    }		
                    case "R": {
                        returnItem();
                        break;
                    }		
                    case "L": {
                        listCurrentLoans();
                        break;
                    }			
                    case "P": {
                        payFines();
                        break;
                    }		
                    case "T": {
                        incrementDate();
                        break;
                    }		
                    case "Q": {
                        finished = true;
                        break;
                    }		
                    default: { 
                        displayOutput("\nInvalid option\n");
                        break;
                    }
                }
				
                Library.save();
            }			
        } catch (RuntimeException e) {
            displayOutput(e);
        }		
        displayOutput("\nEnded\n");
    }	

	
    private static void payFines() {
        PayFineControl payFineControl = new PayFineControl();
        new PayFineUI(payFineControl).run();		
    }


    private static void listCurrentLoans() {
        displayOutput("");
        for (Loan loan : library.listCurrentLoans()) {
            displayOutput(loan + "\n");
        }		
    }


    private static void listItems() {
        displayOutput("");
        for (Item book : library.listItems()) {
            displayOutput(book + "\n");
        }		
    }


    private static void listPatrons() {
        displayOutput("");
        for (Patron member : library.listPatrons()) {
            displayOutput(member + "\n");
        }		
    }


    private static void borrowItem() {
        BorrowItemControl borrowItemControl = new BorrowItemControl();
        new BorrowItemUI(borrowItemControl).run();		
    }


    private static void returnItem() {
        ReturnItemControl returnItemControl = new ReturnItemControl();
        new ReturnItemUI(returnItemControl).run();		
    }


    private static void fixItems() {
        FixItemControl fixItemControl = new FixItemControl();
        new FixItemUI(fixItemControl).run();		
    }


    private static void incrementDate() {
        try {
            String daysString = getInput("Enter number of days: ");
            int days = Integer.valueOf(daysString).intValue();
            calendar.incrementDate(days);
            library.updateCurrentLoansStatus();
            Date currentDate = calendar.getDate();
            Object currentDateFormatted = simpleDateFormat.format(currentDate);
            displayOutput(currentDateFormatted);
			
        } catch (NumberFormatException e) {
            displayOutput("\nInvalid number of days\n");
        }
    }


    private static void addItem() {
		
        ItemType itemType = null;
        String typeMenu = """
			Select item type:
			    B : Book
			    D : DVD video disk
			    V : VHS video cassette
			    C : CD audio disk
			    A : Audio cassette
			   Choice <Enter quits> : """;

        while (itemType == null) {
            String type = getInput(typeMenu);
	
            switch (type.toUpperCase()) {
                case "B": {
                    itemType = ItemType.BOOK;
                    break;
                }		
                case "D": {
                    itemType = ItemType.DVD;
                    break;
                }		
                case "V": {
                    itemType = ItemType.VHS;
                    break;
                }		
                case "C": {
                    itemType = ItemType.CD;
                    break;
                }		
                case "A": {
                    itemType = ItemType.CASSETTE;
                    break;
                }		
                case "": {
                    return;
                }
			
                default: {
                    displayOutput(type + " is not a recognised Item type");
                }
	
            }
        }

        String author = getInput("Enter author: ");
        String title  = getInput("Enter title: ");
        String callNumber = getInput("Enter call number: ");
        Item item = library.addItem(author, title, callNumber, itemType);
        displayOutput("\n" + item + "\n");
        
    }

	
    private static void addPatron() {
        try {
            String firstName  = getInput("Enter first name: ");
            String lastName = getInput("Enter last name: ");
            String emailAddress = getInput("Enter email address: ");
            String phoneNumberString = getInput("Enter phone number: ");
            long phoneNumber = Long.valueOf(phoneNumberString).intValue();
            Patron patron = library.addPatron(firstName, lastName, emailAddress, phoneNumber);
            displayOutput("\n" + patron + "\n");
	
        } catch (NumberFormatException e) {
            displayOutput("\nInvalid phone number\n");
        }
		
    }


    private static String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
	
	
    private static void displayOutput(Object object) {
        System.out.println(object);
    }

	
}
