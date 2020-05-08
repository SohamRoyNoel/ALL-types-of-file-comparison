package main;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import ReportGenerationClasses.ListFilesUtil;
import au.com.bytecode.opencsv.CSVReader;

public class CSVFileComparison {

	public static void CSVComparison(String Filename, String source, String target){

		try{
			//			//Build reader instance
			CSVReader reader = new CSVReader(new FileReader(target), ',', '"', 0);
			CSVReader readersrc = new CSVReader(new FileReader(source), ',', '"', 0);
			String srcElement="";
			String tgtElement="";
			int passLineCounter=0;

			//Read all rows at once
			List<String[]> allRows = reader.readAll();
			List<String[]> allRowsS = readersrc.readAll();

			int lenSource = allRows.size();
			int lenTarget = allRowsS.size();

			for(int i=0; i<Math.min(lenSource, lenTarget); i++){
				String[] csvSources = allRows.get(i);
				String[] csvTargets = allRowsS.get(i);
				for(int j=0; j<csvSources.length;j++){
					srcElement = srcElement + " " +csvSources[j];
					tgtElement = tgtElement + " " + csvTargets[j];
				}
				if (srcElement.equals(tgtElement)) {
					passLineCounter++;
				}else {
//					LetterCatchers.WordCatcher(srcElement, tgtElement, Filename, (i+1));
					if (srcElement.length() == tgtElement.length()) {
						LetterCatchers.WordCatcher(srcElement, tgtElement, Filename, (i+1));
					} else {
						WordCatchers.WordCatcher(srcElement, tgtElement, Filename, (i+1));
					}
				}
				srcElement="";
				tgtElement="";
			}
			String totalPassCase = "Total Matched Lines " +  (passLineCounter);
			ListFilesUtil.Converter1(Filename, Filename, "Source Pass Cases", "Target Pass Cases", true, totalPassCase);
		}catch (IOException | InterruptedException ex) {

		}

	}

}
