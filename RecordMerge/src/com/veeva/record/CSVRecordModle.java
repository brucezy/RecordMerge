package com.veeva.record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVReader;

public class CSVRecordModle extends RecordModle {

	private CSVReader reader;

	public CSVRecordModle() {
		super();
	}

	public void initialize(File input){
		
		try {
			InputStreamReader inputStream = new InputStreamReader(new FileInputStream(input));
			reader = new CSVReader(inputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void readHeader(File input) {
		String[] temp = null;
		
		try {
			temp = reader.readNext();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(String s : temp){
			this.recordHeader.add(s);
		}
	}

	@Override
	public void readData(File input) {
		//ArrayList<HashMap<String, String>> tempList = new ArrayList<HashMap<String, String>>();
		String[] temp = null;
		try {
			while((temp=reader.readNext())!= null){
				int i = 0;
				HashMap<String, String> keyValue = new HashMap<String, String>();
				
				for(String s: temp){	
					keyValue.put(recordHeader.get(i),s);
					//tempList.add(keyValue);
					i++;
				}
				
				this.recordData.add(keyValue);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
