package com.veeva.record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVReader;

public class CSVRecordFileReader extends RecordFileReader {
	
	private CSVReader reader;
	private InputStreamReader inputStream;
	
	public CSVRecordFileReader() {
		super();
	}

	@Override
	public void initialize(File input) {
		try {
			inputStream = new InputStreamReader(new FileInputStream(input));
			this.reader = new CSVReader(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void readHeader() {
		String[] temp = null;
		
		try {
			temp = reader.readNext();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for(String s : temp){
			this.data.recordHeader.add(s);
		}
	}

	@Override
	public void readData() {
		String[] temp = null;
		try {
			while((temp=reader.readNext())!= null){
				int i = 0;
				HashMap<String, String> keyValue = new HashMap<String, String>();
				
				for(String s: temp){	
					keyValue.put(this.data.recordHeader.get(i),s);
					i++;
				}
				
				this.data.recordData.add(keyValue);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void closeFile() {
		try {
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
