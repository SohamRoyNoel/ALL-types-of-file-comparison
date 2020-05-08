package main;

import java.io.IOException;
import java.util.StringTokenizer;

import ReportGenerationClasses.ListFilesUtil;

public class WordCatchers {

	public static void WordCatcher(String line1, String line2, String Filename, int lnNo) throws IOException, InterruptedException {

		String s1 = "", s2 = "", s3 = "", s4 = "", y = "", z = "";
		String[] b = new String[100000];
		String[] a = new String[100000];
		s3 += line1;
		s1 += line2;
		int numTokens = 0;
		StringTokenizer st = new StringTokenizer(s1);
		for (int l = 0; l < 100000; l++) {
			a[l] = "";
		}
		int i = 0;
		while (st.hasMoreTokens()) {
			s2 = st.nextToken();
			a[i] = s2;
			i++;
			numTokens++;
		}
		int numTokens1 = 0;
		StringTokenizer st1 = new StringTokenizer(s3);
		for (int k = 0; k < 100000; k++) {
			b[k] = "";
		}
		int j = 0;
		while (st1.hasMoreTokens()) {
			s4 = st1.nextToken();
			b[j] = s4;
			j++;
			numTokens1++;
		}
		int x = 0;
		String e1="",e2="",e3="";
		String createIssueString="";
		for (int m = 0; m < a.length; m++) {
			if (a[m].equals(b[m])) {
			} else {
				x++;
				int n=m;
				createIssueString = "Mismatch on line number - " + lnNo + " and the mismatched index is - " + (m+1) + " Mismatch value is : [Source - "+ (a[m] == null ? "EMPTY LINE" : a[m]) + " target - " + (b[m] == null ? "EMPTY LINE" : b[m])+" ]";
				ListFilesUtil.Converter1(Filename, Filename, line1, line2, line1.equals(line2), createIssueString);
				//				
				e1=e1+""+a[m]+":"+b[m]+";";
				e2=e2+""+String.valueOf((++n))+",";
			}
		}
//		ListFilesUtil.Converter1(Filename, Filename, line1, line2, line1.equals(line2), createIssueString);
		e2=e2.substring(0, e2.lastIndexOf(","));

		System.out.println("No. of differences : " + x);
	}
	
}
