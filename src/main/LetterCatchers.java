package main;

import java.io.IOException;

import ReportGenerationClasses.ListFilesUtil;

public class LetterCatchers {
	
	public static void WordCatcher(String src, String tgt, String Filename, int lnNo) throws IOException, InterruptedException {
		
		int srcLength = src.length();
		int tgtLength = tgt.length();
		String faultString = "";
		String passString= "";
		String statusString= "";
		
		if (srcLength == tgtLength) {
			for(int i=0; i<srcLength; i++){
				char srcChar =  src.charAt(i);
				char tgtChar = tgt.charAt(i);
				if (srcChar == tgtChar) {
					passString = passString + "Match on line no " +lnNo+ " match on index "+ (i+1) + ";"+"\n";
				} else {
					faultString = faultString + "Mismatch on line no " +lnNo+ " Mismatch on index "+ (i+1) + ";[Source - " + srcChar + " Target - " + tgtChar + "]"+"<br>";
				}
			}
		} 
		
		if (faultString == null) {
			statusString = passString;
			ListFilesUtil.Converter1(Filename, Filename, src, tgt, true, faultString);
		} else {
			statusString = faultString;
			ListFilesUtil.Converter1(Filename, Filename, src, tgt, false, faultString);
		}
		
	}

}
