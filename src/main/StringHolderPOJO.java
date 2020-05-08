package main;

import java.util.ArrayList;
import java.util.List;

public class StringHolderPOJO {

	private String loadString;
	public static List<String> loadDetails =new ArrayList<String>();
	
	public StringHolderPOJO() {
		
	}
	
	public List<String> getLoadString() {
		return loadDetails;
	}

	
	public void pushToList(String loadString){
//		System.out.println("Loader : " + loadString);
//		System.out.println("list size NEXT: " + loadDetails.size());
		loadDetails.add(loadString);
//		System.out.println("list size : " + loadDetails.size());
	}
	
}
