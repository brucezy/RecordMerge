/**
 * 
 */
package com.veeva.record;

import java.io.File;
import java.util.ArrayList;

/**
 * @author Yong Zhang
 *
 */
public class RecordMerge {
	
	public static final String FILENAME_COMBINED = "combined.csv";
	private static final String SORTWITHKEY = "ID";

	/**
	 * Entry point of this test.
	 * 
	 * @param args command line arguments: first.html and second.csv.
	 * 
	 * @throws Exception bad things had happened.
	 */
	public static void main(String[] args) throws Exception{
		
		if(args.length == 0){
			System.err.println("Usage: java RecordMerger file1 [ file2 [...] ]");
			System.exit(1);
		}
		
		File directory = new File(""); 
		String projectPath = directory.getCanonicalPath() ;
		String dataFilePath = projectPath + File.separator + "data" + File.separator;
		
		ArrayList<ArrayList<String>> inputFileList = new ArrayList<ArrayList<String>>();
		
		for(String s: args){
			String pathAndFileName = dataFilePath + s;
			int i = pathAndFileName.lastIndexOf('.');
			String fileExtension = pathAndFileName.substring(i+1).toUpperCase();
			
			ArrayList<String> nameAndType = new ArrayList<String>();
			nameAndType.add(pathAndFileName);
			nameAndType.add(fileExtension);
			inputFileList.add(nameAndType);
		}
		
		/* Read header and record data entries from files, and sort data entries according to key of "ID" */
		ArrayList<RecordData> recordList = new ArrayList<RecordData>();
		for(ArrayList<String> nameAndType : inputFileList){
			RecordFileReader fileReader = null;
			RecordFileReaderFactory fileReaderFactory = new RecordFileReaderFactory(nameAndType.get(1).toString());
			fileReader = fileReaderFactory.getInstance();
			
			if(fileReader != null){	
				File input = new File(nameAndType.get(0));
				fileReader.initialize(input);
				fileReader.readHeader();
				
				if(fileReader.data.recordHeader.size() == 0)
				{
					System.err.println("File:" + input.getName().toUpperCase() + " doesn't contain header, skip to process the file");
					fileReader.closeFile();
				}else if (!fileReader.data.recordHeader.contains(SORTWITHKEY)){
					System.err.println("File:" + input.getName().toUpperCase() + " doesn't contain \'ID\' field, skip to process the file");
					fileReader.closeFile();
				}else{
					fileReader.readData();
					fileReader.closeFile();
					recordList.add(fileReader.data);
					if(fileReader.data.recordData.size()>1)
						fileReader.data.sortByKey(SORTWITHKEY);
				}
			}
		}
		
		/* Combine headers and record data entries from different files */
		RecordDataCombination dataCombine = new RecordDataCombination(recordList);
		dataCombine.consolidateRecordHeaders();
		dataCombine.consolidateRecordEntries();
		
		/* Write combined headers and record data entries into "combined.csv" */
		RecordFileWriter fileWriter = null;
		String fileExtension = FILENAME_COMBINED.substring(FILENAME_COMBINED.lastIndexOf('.')+1).toUpperCase();
		RecordFileWriterFactory fileWriterFactory = new RecordFileWriterFactory(fileExtension);
		fileWriter = fileWriterFactory.getInstance();
		
		/* initialize file writer environment: set up data source and open a file to write */ 
		if(fileWriter != null){
			fileWriter.data = dataCombine.data;
			File output = new File(FILENAME_COMBINED);
			fileWriter.initialize(output);
			
			/* Write Header */
			fileWriter.writeHeader();
			
			/* Write Data Entries */
			fileWriter.writeData();
			
			fileWriter.closeFile();
		}

	}

}
