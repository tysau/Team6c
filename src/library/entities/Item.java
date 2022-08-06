package library.entities;
import java.io.Serializable;


@SuppressWarnings("serial")
public class Item implements Serializable {
	
    private ItemType type;
    private String author;
    private String title;
    private String callNumber;
    private long id;
	
    private enum itemState { AVAILABLE, ON_LOAN, DAMAGED, RESERVED };
    private itemState state;
	
	
    public Item(String author, String title, String callNumber, ItemType itemType, long id) {
	this.type = itemType;
	this.author = author;
        this.title = title;
        this.callNumber = callNumber;
        this.id = id;
        this.state = itemState.AVAILABLE;
	}
	
    public String toString() {
        StringBuilder Sb = new StringBuilder();
	Sb.append("  Item:   ").append(id).append("\n")
          .append("  Type:   ").append(type).append("\n")
	  .append("  Title:  ").append(title).append("\n")
	  .append("  Author: ").append(author).append("\n")
	  .append("  CallNo: ").append(callNumber).append("\n")
	  .append("  State:  ").append(state);
		
        return Sb.toString();
	}

    public Long getId() {
	return id;
	}

    public String getTitle() {
	return title;
	}

    public ItemType getType() {
	return type;
	}


	
    public boolean isAvailable() {
	return state == itemState.AVAILABLE;
	}

	
    public boolean isOnLoan() {
	return state == itemState.ON_LOAN;
	}

	
    public boolean isDamaged() {
	return state == itemState.DAMAGED;
	}

	
    public void takeOut() {
	if (state.equals(itemState.AVAILABLE)) {
	    state = itemState.ON_LOAN;
        }
		
	else {
	   throw new RuntimeException(String.format("Item: cannot borrow item while item is in state: %s", state));	
           }
	}


    public void takeBack(boolean damaged) {
	if (state.equals(itemState.ON_LOAN)){ 
	    if (damaged){
	        state = itemState.DAMAGED;
                }
            else{ 
		state = itemState.AVAILABLE;	
                }
        }
		
	else {
	   throw new RuntimeException(String.format("Item: cannot return item while item is in state: %s", state));
           }
				
	}

	
    public void repair() {
        if (state.equals(itemState.DAMAGED)) {
	    state = itemState.AVAILABLE;
            }
	else {
	    throw new RuntimeException(String.format("Item: cannot repair while Item is in state: %s", state));
            }	
	}


}
