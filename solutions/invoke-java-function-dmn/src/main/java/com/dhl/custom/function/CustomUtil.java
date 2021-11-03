package com.dhl.custom.function;

import java.util.ArrayList;
import com.dhl.custom.model.Item;

public class CustomUtil {

	public static ArrayList<Item> getItem(String b) {

		ArrayList<Item> list = new ArrayList<Item>();
		Item item = new Item();
		item.setCode(1341);
		item.setName("AB65");
		list.add(item);

		item = new Item();
		item.setCode(3233);
		item.setName("GH23");
		list.add(item);

		item = new Item();
		item.setCode(7799);
		item.setName("KL43");
		list.add(item);

		item = new Item();
		item.setCode(7768);
		item.setName("HJ79");
		list.add(item);

		item = new Item();
		item.setCode(2729);
		item.setName("FE45");
		list.add(item);

		return list;
	}

	public static double cosine(double a) {
		return Math.cos(a);
	}

	public CustomUtil() {
	}

}