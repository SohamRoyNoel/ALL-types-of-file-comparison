package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ReportGenerationClasses.ListFilesUtil;


public class TextFileComparison {

	public static void TxtComparison(String Filename, File source_file, File target_file) throws IOException, InterruptedException {


		ArrayList<String> source_elements = new ArrayList<String>();
		ArrayList<String> target_elements = new ArrayList<String>();
		source_elements = sourceTargetList(source_file);
		target_elements = sourceTargetList(target_file);

		int sourceElementCount = source_elements.size();
		int targetElementCount = target_elements.size();
		int maxCounter = Math.max(sourceElementCount, targetElementCount);
		Boolean ifSameFile = source_elements.equals(target_elements);
		int lnno = 0;
		int passLineCounter=0; 
		int failCounter = 0;
		String createIssueString = "";
//		System.out.println("Txt target Comparison");

		for (int i=0; i<maxCounter; i++) {
			//			passLineCounter = (i+1);
			try{
				if (!source_elements.get(i).equals(target_elements.get(i))) {

					String srcString = source_elements.get(i);
					String tgtString = target_elements.get(i);
					lnno = (i+1);
					LetterCatchers.WordCatcher(srcString, tgtString, Filename, lnno);

				} else {
					passLineCounter++;
//					System.out.println("Txt target Comparison"+ i);

					 createIssueString = createIssueString + "Matching on LINE no: " + (i+1);
//					 ListFilesUtil.Converter1(Filename, Filename, source_elements.get(i), target_elements.get(i), true, createIssueString);
				}
			}catch (Exception e) { }
		}

//		System.out.println("**********************************************************"+ source_elements);
		String totalPassCase = "Total Matched Lines " +  (passLineCounter);
		ListFilesUtil.Converter1(Filename, Filename, "Source Pass Cases", "Target Pass Cases", true, totalPassCase);
//		System.out.println("Txt target Comparison************************################");

		if (sourceElementCount > targetElementCount) {
			for (int i = targetElementCount; i < sourceElementCount ; i++) {
				String createIssueStrings = "Mismatch on line number - " + (i+1)+ " Source - " + source_elements.get(i) + " Target - [No value on TARGET file] <B><i>-- This line exists only on SOURCE File<i><B>";
				ListFilesUtil.Converter1(Filename, Filename, source_elements.get(i), "NULL", false, createIssueStrings);
			}
		} else {
			for (int i = sourceElementCount; i < targetElementCount ; i++) {
				String createIssueStrings = "Mismatch on line number - " + (i+1)+ " Source - [No value on Source file] " + "Target - " + target_elements.get(i) + " <B><i>-- This line exists only on TARGET File<i><B>";
				ListFilesUtil.Converter1(Filename, Filename, "NULL", target_elements.get(i), false, createIssueStrings);
			}
		}
	}

	public static ArrayList<String> sourceTargetList(File source_file){
		ArrayList<String> source_elements = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(source_file));
			String st; 
			try {
				while ((st = br.readLine()) != null){ 
					source_elements.add(st);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return source_elements;
	}	
}
