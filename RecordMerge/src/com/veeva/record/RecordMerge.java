/**
 * 
 */
package com.veeva.record;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author yongzhang
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
		
		
		//add RecordModle into list and parse header and data in each file
		ArrayList<RecordModle> recordList = new ArrayList<RecordModle>();
		for(ArrayList<String> nameAndType : inputFileList){
			RecordModle recordModle = null;
			FileModelFactory fileFactory = new FileModelFactory(nameAndType.get(1).toString());
			recordModle = fileFactory.getInstance();
			
			if(recordModle != null){
				recordList.add(recordModle);	
				File input = new File(nameAndType.get(0));
				recordModle.initialize(input);
				recordModle.readHeader(input);
				recordModle.readData(input);
			}
		}
		
		
		for(RecordModle recordModle : recordList){
			if(recordModle.recordData.size()>1)
				Collections.sort(recordModle.recordData, new Comparator<HashMap<String, String>>() {
				      @Override
				    public int compare(HashMap<String, String> obj1, HashMap<String,String> obj2) {
		
				    	  return Integer.parseInt(obj1.get("ID").toString())- Integer.parseInt(obj2.get("ID").toString());
					     
					}
			    });
	
		}
		
		
		//Consolidation all file and merge duplicated record
		FileModelFactory fileFactory = new FileModelFactory("CSV");
		RecordModle outputRecord = fileFactory.getInstance();

		//merge header
		int i = 0;
		for(RecordModle recordModle: recordList){
			
			//merge header
			if(i == 0)
			{
				outputRecord.recordHeader = (ArrayList<String>) recordModle.recordHeader.clone();
			} else {
				
				for(String s: recordModle.recordHeader){
					if(!outputRecord.recordHeader.contains(s)){
						outputRecord.recordHeader.add(s);
					}
				}
			}
			
			i++;
		}
		
		//merge data
		i = 0;
		ArrayList<HashMap<String,String>> outputData = null;
		
		for(RecordModle recordModle : recordList){
			
			ArrayList<HashMap<String,String>> tempOutputData = new ArrayList<HashMap<String,String>>();
			
			if(i == 0){	
				for(HashMap<String, String> map : recordModle.recordData){
					HashMap<String, String> outputRow = new HashMap<String, String>();
					
					for(String s: outputRecord.recordHeader){
						
						if(map.containsKey(s)){
							outputRow.put(s,map.get(s).toString());
						}
						else{
							outputRow.put(s,"");
						}
					}
					tempOutputData.add(outputRow);
					outputRow = null;
				}
				outputData = tempOutputData;
				tempOutputData = null;
			}
			else{
				int indexFirst = 0, indexSecond = 0;
				
				while(indexFirst < outputData.size() && indexSecond < recordModle.recordData.size()){
					
					HashMap<String,String> mapFirst = outputData.get(indexFirst);
					HashMap<String,String> mapSecond = recordModle.recordData.get(indexSecond);
					HashMap<String, String> outputRow = new HashMap<String, String>();
					
					if (mapFirst.get("ID").compareTo(mapSecond.get("ID")) < 0){
						for(String s: outputRecord.recordHeader){
							
							if(mapFirst.containsKey(s)){
								outputRow.put(s,mapFirst.get(s).toString());
							}
							else{
								outputRow.put(s,"");
							}
						}
						tempOutputData.add(outputRow);
						outputRow = null;
						
						indexFirst++;
					}
					else if(mapFirst.get("ID").compareTo(mapSecond.get("ID")) == 0){
						for(String s: outputRecord.recordHeader){
							
							if(mapFirst.containsKey(s) && mapFirst.get(s).toString() != ""){
								outputRow.put(s,mapFirst.get(s).toString());
							}
							else if(mapSecond.containsKey(s) && mapSecond.get(s).toString() != ""){
								outputRow.put(s,mapSecond.get(s).toString());
							}
							else{
								outputRow.put(s,"");
							}
				
						}
						tempOutputData.add(outputRow);
						outputRow = null;
						
						indexFirst++;
						indexSecond++;
					}
					else if (mapFirst.get("ID").compareTo(mapSecond.get("ID")) > 0) {
						for(String s: outputRecord.recordHeader){
							
							if(mapSecond.containsKey(s)){
								outputRow.put(s,mapSecond.get(s).toString());
							}
							else{
								outputRow.put(s,"");
							}
						}
						tempOutputData.add(outputRow);
						outputRow = null;

						indexSecond++;
					}
						
				}
				
				if(indexFirst < outputData.size()){
					
					while(indexFirst < outputData.size()){
						HashMap<String,String> mapFirst = outputData.get(indexFirst);
						HashMap<String, String> outputRow = new HashMap<String, String>();
						
						for(String s: outputRecord.recordHeader){
							
							if(mapFirst.containsKey(s)){
								outputRow.put(s,mapFirst.get(s).toString());
							}
							else{
								outputRow.put(s,"");
							}
						}
						tempOutputData.add(outputRow);
						outputRow = null;
						indexFirst++;
					}
				}
				else if(indexSecond < recordModle.recordData.size()){
					
					while(indexSecond < recordModle.recordData.size()){
						HashMap<String,String> mapSecond = recordModle.recordData.get(indexSecond);
						HashMap<String, String> outputRow = new HashMap<String, String>();
						
						for(String s: outputRecord.recordHeader){
							
							if(mapSecond.containsKey(s)){
								outputRow.put(s,mapSecond.get(s).toString());
							}
							else{
								outputRow.put(s,"");
							}
						}
						tempOutputData.add(outputRow);
						outputRow = null;
						indexSecond++;
					}
				}
				
				outputData = tempOutputData;
				tempOutputData = null;
			}
			
			i++;
		}
		outputRecord.recordData = outputData;
		outputData = null;
		
		//Write to .csv file
		OutputStreamWriter outStream = new OutputStreamWriter(new FileOutputStream("combined.csv"));
		@SuppressWarnings("resource")
		CSVWriter csvWriter = new CSVWriter(outStream,',', CSVWriter.DEFAULT_QUOTE_CHARACTER);
		
		//write header
		csvWriter.writeNext(outputRecord.recordHeader.toArray(new String[outputRecord.recordHeader.size()]));
		
		//write data
		ArrayList<String> dataRow = new ArrayList<String>();
		for(HashMap<String, String> map : outputRecord.recordData){
			for(String s : outputRecord.recordHeader){
				dataRow.add(map.get(s));
			}
			csvWriter.writeNext(dataRow.toArray(new String[dataRow.size()]));
			dataRow.clear();
		}
		
		//close
		outStream.close();

	}

}
