package library.entities;

import java.util.HashMap;
import java.util.Map;

public enum ItemType {
	BOOK, DVD, VHS, CD, CASSETTE;
	
	private static final ItemType[] VALUES = values();

	private static final Map<ItemType, String> VALUE_REPR_MAP = new HashMap<ItemType, String>();
	static {
		VALUE_REPR_MAP.put(ItemType.BOOK, "Book");
		VALUE_REPR_MAP.put(ItemType.DVD, "DVD");
		VALUE_REPR_MAP.put(ItemType.VHS, "VHS Video cassette");
		VALUE_REPR_MAP.put(ItemType.CD, "CD Audio disk");
		VALUE_REPR_MAP.put(ItemType.CASSETTE, "Audio cassette");
	}
	
	public  String toString() {
		return VALUE_REPR_MAP.get(this);
	}

}
