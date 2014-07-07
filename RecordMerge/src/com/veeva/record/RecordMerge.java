/**
 * 
 */
package com.veeva.record;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * @author Yong Zhang
 *
 */
public class RecordMerge {
	
	public static final String FILENAME_COMBINED = "combined.csv";


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
		
		/*
		System.out.println(projectPath);
		System.out.println(dataFilePath);
		*/
		
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
		
		
		//add RecordData into list and read header and data entry in each file
		ArrayList<RecordData> recordList = new ArrayList<RecordData>();
		for(ArrayList<String> nameAndType : inputFileList){
			RecordFileReader fileReader = null;
			RecordFileReaderFactory fileReaderFactory = new RecordFileReaderFactory(nameAndType.get(1).toString());
			fileReader = fileReaderFactory.getInstance();
			
			if(fileReader != null){
				recordList.add(fileReader.data);	
				File input = new File(nameAndType.get(0));
				fileReader.initialize(input);
				fileReader.readHeader();
				fileReader.readData();
				if(fileReader.data.recordData.size()>1)
					fileReader.data.sortByKey("ID");
			}
		}
		
		for(RecordData recordData : recordList){
			if(recordData.recordData.size()>1)
				recordData.sortByKey("ID");
		}
		
		RecordDataCombination dataCombine = new RecordDataCombination(recordList);
		
		dataCombine.consolidateRecordHeaders();
		dataCombine.consolidateRecordEntries();
		
		RecordFileWriter fileWriter = null;
		String fileExtension = FILENAME_COMBINED.substring(FILENAME_COMBINED.lastIndexOf('.')+1).toUpperCase();
		RecordFileWriterFactory fileWriterFactory = new RecordFileWriterFactory(fileExtension);
		fileWriter = fileWriterFactory.getInstance();
		
		//Write to .csv file
		fileWriter.data = dataCombine.data;
		File output = new File(FILENAME_COMBINED);
		fileWriter.initialize(output);
		
		//Write Header
		fileWriter.writeHeader();
		
		//Write Data Entry
		fileWriter.writeData();
		
		fileWriter.fileClose();

	}

}
