package com.veeva.record;

import java.util.ArrayList;
import java.util.HashMap;

public class RecordDataCombination {
	
	public RecordData data;
	private ArrayList<RecordData> dataList;
	
	public RecordDataCombination(ArrayList<RecordData> recordList) {
		this.data = new RecordData();
		this.dataList = recordList;
	}
	
	@SuppressWarnings("unchecked")
	public void consolidateRecordHeaders(){
		int i = 0;
		
		for(RecordData recordData: dataList){
			if(i == 0)
			{
				this.data.recordHeader = (ArrayList<String>) recordData.recordHeader.clone();
			} else {
				for(String s: recordData.recordHeader){
					if(!this.data.recordHeader.contains(s)){
						this.data.recordHeader.add(s);
					}
				}
			}
			i++;
		}
	}
	
	public void consolidateRecordEntries(){
		int i = 0;
		ArrayList<HashMap<String,String>> outputData = null;
		
		for(RecordData recordData : dataList){
			
			ArrayList<HashMap<String,String>> tempOutputData = new ArrayList<HashMap<String,String>>();
			
			if(i == 0){	
				for(HashMap<String, String> map : recordData.recordData){
					HashMap<String, String> outputRow = new HashMap<String, String>();
					
					for(String s: this.data.recordHeader){
						
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
				
				while(indexFirst < outputData.size() && indexSecond < recordData.recordData.size()){
					
					HashMap<String,String> mapFirst = outputData.get(indexFirst);
					HashMap<String,String> mapSecond = recordData.recordData.get(indexSecond);
					HashMap<String, String> outputRow = new HashMap<String, String>();
					
					if (mapFirst.get("ID").compareTo(mapSecond.get("ID")) < 0){
						for(String s: this.data.recordHeader){
							
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
						for(String s: this.data.recordHeader){
							
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
						for(String s: this.data.recordHeader){
							
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
						
						for(String s: this.data.recordHeader){
							
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
				else if(indexSecond < recordData.recordData.size()){
					
					while(indexSecond < recordData.recordData.size()){
						HashMap<String,String> mapSecond = recordData.recordData.get(indexSecond);
						HashMap<String, String> outputRow = new HashMap<String, String>();
						
						for(String s: this.data.recordHeader){
							
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
		this.data.recordData = outputData;
		outputData = null;
	}
}
