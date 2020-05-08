package main;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import Paths.Resourcepath;

public class ComparisonEntryPoint {
	public static final Set<String> sets = new HashSet<String>();
	private String prevFileName = "";
	public static String noFileIssueString = "";

	public static void main(String[] args) {

		String receivedFilename;
		String getExtension;
		Instant start = Instant.now();

		File folder = new File(Resourcepath.sourcepath);
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			String actualFilename = listOfFiles[i].getName();
			if (listOfFiles[i].isFile()) {
				receivedFilename = listOfFiles[i].getName();
				getExtension = listOfFiles[i].getName().substring(listOfFiles[i].getName().lastIndexOf("."), listOfFiles[i].getName().length());
				if (getExtension.endsWith(".bat")) {
					initiator(receivedFilename, actualFilename);
				} else if(getExtension.endsWith(".dat")){
					initiator(receivedFilename, actualFilename);
				} else if(getExtension.endsWith(".txt")){				
					initiator(receivedFilename, actualFilename);
//					System.out.println("TXT INITIATED");
				} else if(getExtension.endsWith(".docx")){
					try {
						//Source File
						File source_file = new File(Resourcepath.sourcepath+"\\"+receivedFilename);

						// If target file exists in Target Folder
						if(hasFile(listOfFiles[i].getName())){
							File target_file = new File(Resourcepath.targetpath+"\\"+listOfFiles[i].getName());
							DocxFileComparison.DocxComparison(receivedFilename, source_file, target_file); 
						}else{
							System.out.println("No file exists in TARGET folder named : " + listOfFiles[i].getName());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else if(getExtension.endsWith(".pdf")){
					/*//System.out.println("Format is already in PDF Format");
					try {
						//Source File
						System.out.println("Source file name : " + listOfFiles[i].getName());
						String source_file = Resourcepath.sourcepath+"\\"+receivedFilename;

						// If target file exists in Target Folder
						if(hasFile(listOfFiles[i].getName())){
							String target_file = Resourcepath.targetpath+"\\"+listOfFiles[i].getName();
							//DocxFileComparison.DocxComparison(temp_file1,receivedFilename, source_file, target_file);
							PDFFileComparison.PDFComparater(source_file, target_file, temp_file1, receivedFilename);
						}else{
							System.out.println("No file exists in TARGET folder named : " + listOfFiles[i].getName());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}*/
				} else if(getExtension.contains(".csv")){
					CSVinitiator(receivedFilename, actualFilename);
				}
			}
		}

		String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());
		// Excel Report LOGGER
		try {
			String[] temp;
			String[] temps;
			String[] tempr;

			StringHolderPOJO strPG = new StringHolderPOJO();
			// Writer on LIST
			List<String> listOfCases = strPG.getLoadString();

			
			ExtentHtmlReporter extentHtmlReporter = new ExtentHtmlReporter(Resourcepath.extentReportGenerationPath+"\\"+timeStamp+"_ComparisonReport.html");

			ExtentReports extentReports = new ExtentReports();
			extentReports.attachReporter(extentHtmlReporter);

			extentReports.setSystemInfo("Host Name", "File Comparison Report");
			String username = System.getProperty("user.name");
			extentReports.setSystemInfo("User Name", username);
			extentHtmlReporter.config().setDocumentTitle("Comparison Report"); 
			extentHtmlReporter.config().setReportName("Comparison Status"); 
			// Name of the report
			extentHtmlReporter.config().setReportName("File Validation Report"); 

			Set<String> s = new HashSet<>();
			for(int i=0; i<listOfCases.size(); i++){
				String ss = listOfCases.get(i);
				tempr = ss.split("\\*");
				s.add(tempr[0]);
			}

			int noOfParent = s.size(); int counter = 1;

			for (String str : s) {
				if (counter <= noOfParent) {

//					System.out.println("S : " + str);
					temps = str.split("\\*");
					ExtentTest extentTest = extentReports.createTest("Test Report "+ temps[0]);
//					System.out.println("list of cases : " + listOfCases);
					for(int i=0; i < listOfCases.size(); i++){
						temp = listOfCases.get(i).split("\\*");
//						System.out.println("******"+temp[0]+System.lineSeparator());
						if (str.contains(temp[0])) {
							ExtentTest childTest = extentTest.createNode("Test Report of file " + temps[0]);
							childTest.log(Status.INFO, "<B><I>TARGET - </B></I>"+ temp[2]);
							childTest.log(Status.INFO, "<B><I>SOURCE - </B></I>"+ temp[3]);
							if (temp[4].equalsIgnoreCase("false")) {
								childTest.log(Status.FAIL, temp[5]);
							} else {
								childTest.log(Status.PASS, temp[5]);
							}
//							System.out.println("Hello"+i);
						}

//						System.out.println("Out Side if");
					}
					extentReports.flush();
				}
				counter++;
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			File file = new File(Resourcepath.extentReportGenerationPath+"\\"+timeStamp+"_ComparisonReport.html");
			Desktop.getDesktop().browse(file.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);
		JOptionPane.showMessageDialog(null, "Total Execution Time : "+ timeElapsed.toMillis() +" Minutes", "Action Time", JOptionPane.INFORMATION_MESSAGE);
	}	

	public void setFileName(String str){
		this.prevFileName=str;
	}

	public String getFileName(){
		return this.prevFileName;
	}

	// Check if the file name exists in SOURCE folder 
	public static boolean hasFile(String filename){
		String path = Resourcepath.targetpath+"\\"+filename;
		boolean checkifExists = new File(path).exists();
		return checkifExists;
	}

	public static void initiator(String receivedFilename, String actualFilename){
		// Source File
		File source_file = new File(Resourcepath.sourcepath+"\\"+receivedFilename);
		if(hasFile(actualFilename)){
			File target_file = new File(Resourcepath.targetpath+"\\"+actualFilename);
			try {
				TextFileComparison.TxtComparison(receivedFilename, source_file, target_file);
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			} 
		}else{
			System.out.println("No file exists in TARGET folder named : " + actualFilename);
		}
	}
	
	// Log NO FILE in either source OR target a different file
	public static void noFileFoundIssue(String str){
		String temp_file1= Resourcepath.operationPath+"\\NO_File_Found.txt";
		new File(temp_file1);
		noFileIssueString = noFileIssueString + str + System.lineSeparator();
		BufferedWriter out;
		try {
			out = new BufferedWriter(new FileWriter(Resourcepath.noFileFoundPath+"\\NO_File_Found.txt", true));
			out.write(noFileIssueString);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		
	}
	

	public static void CSVinitiator(String receivedFilename, String actualFilename){
		// Source File
		String CSVsource_file = Resourcepath.sourcepath+"\\"+receivedFilename;

		// If target file exists in Target Folder
		if(hasFile(actualFilename)){
			String CSVtarget_file = Resourcepath.targetpath+"\\"+actualFilename;
			try {
				CSVFileComparison.CSVComparison(receivedFilename, CSVsource_file, CSVtarget_file);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else{
			System.out.println("No file exists in TARGET folder named : " + actualFilename);
		}
	}
}
