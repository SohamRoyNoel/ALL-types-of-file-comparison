package ReportGenerationClasses;

import java.io.IOException;


public class ListFilesUtil {
	

	public static void Converter1(String sourceFilename, String targetFilename, String sourceStatus, String targetStatus, Boolean issue, String comment) throws IOException, InterruptedException {
		ComparisonLog.compare(sourceFilename, targetFilename, sourceStatus, targetStatus, issue, comment);
	}
	
	


}
