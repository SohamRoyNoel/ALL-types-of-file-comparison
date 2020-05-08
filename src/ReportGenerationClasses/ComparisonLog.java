package ReportGenerationClasses;


import java.util.ArrayList;
import java.util.List;

import main.StringHolderPOJO;

public class ComparisonLog {

	private static List<String> builder;

	public static List<String> getBuilder() {
		return builder;
	}

	public static void setBuilder(String builders) {
		builder.add(builders);
	}


	public static List<String> list = new ArrayList<String> ();
	public static StringHolderPOJO StringHolderPOJO=new StringHolderPOJO();

	public static void compare( String sourceFilename, String targetFilename, String sourceStatus, String targetStatus, Boolean issue, String comment) throws InterruptedException{
		String load=sourceFilename +"*"+targetFilename+"*"+sourceStatus.trim() +"*"+targetStatus.trim()+"*"+issue+"*"+comment.trim()+System.lineSeparator();
		StringHolderPOJO.pushToList(load);
	}

}
