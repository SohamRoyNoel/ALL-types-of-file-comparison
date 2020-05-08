package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import ReportGenerationClasses.ListFilesUtil;

public class DocxFileComparison {

	public static void DocxComparison(String Filename, File source_file, File target_file) throws IOException, InterruptedException {

		ArrayList<String> source_elements = new ArrayList<String>();
		ArrayList<String> target_elements = new ArrayList<String>();
		source_elements = sourceTargetDocxList(source_file);
		target_elements = sourceTargetDocxList(target_file);

		int sourceElementCount = source_elements.size();
		int targetElementCount = target_elements.size();
		int minCounter = Math.min(sourceElementCount, targetElementCount);

		Boolean ifSameFile = source_elements.equals(target_elements);
		int lnno = 0;
		if(ifSameFile == false){
			for (int i=0; i<minCounter; i++) {
				try{
					if (!source_elements.get(i).equals(target_elements.get(i))) {
						String srcString = source_elements.get(i);
						String tgtString = target_elements.get(i);
						lnno = (i+1);
						//LetterCatchers.WordCatcher(srcString,tgtString, Filename, lnno);
						WordCatchers.WordCatcher(srcString, tgtString, Filename, lnno);
					} else {
						String createIssueString = "Matching on LINE no: " + (i+1);
						ListFilesUtil.Converter1(Filename, Filename, source_elements.get(i), target_elements.get(i), true, createIssueString);
					}
				}catch (Exception e) { }
			}

			if (sourceElementCount > targetElementCount) {
				for (int i = targetElementCount; i < sourceElementCount ; i++) {
					 String createIssueString = "Mismatch on line number - " + (i+1)+ " Source - " + source_elements.get(i) + " Target - [No value on TARGET file] <B><i>-- This line exists only on SOURCE File<i><B>";
					ListFilesUtil.Converter1(Filename, Filename, source_elements.get(i), "NULL", false, createIssueString);
				}
			} else {
				for (int i = sourceElementCount; i < targetElementCount ; i++) {
					String createIssueString = "Mismatch on line number - " + (i+1)+ " Source - [No value on Source file] " + "Target - " + target_elements.get(i) + " <B><i>-- This line exists only on TARGET File<i><B>";
					ListFilesUtil.Converter1(Filename, Filename,"NULL", target_elements.get(i), false, createIssueString);
				}
			}
		}
	}

	public static ArrayList<String> sourceTargetDocxList(File source_file){
		ArrayList<String> source_elements = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream(source_file.getAbsolutePath());
			XWPFDocument document = new XWPFDocument(fis);
			List<XWPFParagraph> paragraphs = document.getParagraphs();
			for (XWPFParagraph para : paragraphs) {
				String elements = para.getText();
				source_elements.add(elements);
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return source_elements;
	}
}
