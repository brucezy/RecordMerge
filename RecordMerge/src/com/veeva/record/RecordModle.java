package com.veeva.record;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public abstract class RecordModle {
	
	public ArrayList<String> recordHeader;
	public ArrayList<HashMap<String,String>> recordData;
	
	public RecordModle(){
		this.recordHeader = new ArrayList<String>();
		this.recordData = new ArrayList<HashMap<String,String>>();
	}
	
	abstract public void initialize(File input);
	
	abstract public void readHeader(File input);
	
	abstract public void readData(File input);


}
