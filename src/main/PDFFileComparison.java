package main;

import java.io.File;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

import ReportGenerationClasses.ListFilesUtil;

public class PDFFileComparison {

	public static void PDFComparater(String srcFile, String tgtFile, String Filename) {
		try {

			// Get PDFs
			File Src_pdfFile = new File(srcFile);
			File tgt_pdfFile1 = new File(tgtFile);

			// Load PDFs
			PDDocument pdDocument_SRC = PDDocument.load(Src_pdfFile);
			PDDocument pdDocument_TGT = PDDocument.load(tgt_pdfFile1);

			// Get the number of pages
			List allPages_SRC = pdDocument_SRC.getDocumentCatalog().getAllPages();
			List allPages_TGT = pdDocument_TGT.getDocumentCatalog().getAllPages();

			int i =1; int j =1;

			// Until the same amount of lines
			for (i = 1; i <= Math.min(allPages_SRC.size(), allPages_TGT.size()); i++) {
				PDFTextStripper stripper = new PDFTextStripper();
				stripper.setStartPage(i);
				stripper.setEndPage(i);
				String text_SRC = stripper.getText(pdDocument_SRC).replaceAll("javaWhitespace", " ");
				String text_TGT = stripper.getText(pdDocument_TGT).replaceAll("javaWhitespace", " ");

				String[] lines_SRC = text_SRC.split("\n");
				String[] lines_TGT = text_TGT.split("\n");

				for (j = 0; j < Math.min(lines_SRC.length, lines_TGT.length) ; j++) {
					String src =  lines_SRC[j].replace("\n", "*");
					String tgt = lines_TGT[j].replace("\n", "*");
				}
			}

			System.out.println("ODD CASE");
			// When SOURCE has more lines
			if(allPages_SRC.size() > allPages_TGT.size()) {
				for (int x = i; x <= allPages_SRC.size(); x++) {
					PDFTextStripper stripper = new PDFTextStripper();
					stripper.setStartPage(x);
					stripper.setEndPage(x);
					String text_SRC = stripper.getText(pdDocument_SRC).replaceAll("javaWhitespace", " ");

					String[] lines_SRC = text_SRC.split("\n");

					for (int y = 0; y < lines_SRC.length; y++) {
						String src = lines_SRC[y];
						String createIssueString = "Source - " + src + " Target -" + " No value on TARGET file " + "This line exists only on SOURCE File";
						ListFilesUtil.Converter1(Filename, Filename, src, "NULL", false, createIssueString);
					}
				}
			} else {
				// When TARGET has more lines
				for (int x = i; x <= allPages_TGT.size(); x++) {
					PDFTextStripper stripper = new PDFTextStripper();
					stripper.setStartPage(x);
					stripper.setEndPage(x);
					
					String text_TGT = stripper.getText(pdDocument_TGT).replaceAll("visiblespace", " ");

					String[] lines_TGT = text_TGT.split("\n");
					System.out.println("Target is graters : " +j);
					System.out.println("Target is graters : " + lines_TGT.length);
					for ( int y = 0; y < lines_TGT.length; y++) {
						String tgt = lines_TGT[y];
						String createIssueString = "Source - " + "No value on SOURCE file " + "Target - " + tgt + " This line exists only on TARGET File";
						ListFilesUtil.Converter1(Filename, Filename,"NULL", tgt, false, createIssueString);
					}
				}
			}
			pdDocument_SRC.close();
			pdDocument_TGT.close();


		} catch(Exception e){
			System.out.print(e);
		}

	}
}
