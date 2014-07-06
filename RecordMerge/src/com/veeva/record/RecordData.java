package com.veeva.record;

import java.util.ArrayList;
import java.util.HashMap;

public class RecordData{
		
		public ArrayList<String> recordHeader;
		public ArrayList<HashMap<String,String>> recordData;
		
		public RecordData(){
			this.recordHeader = new ArrayList<String>();
			this.recordData = new ArrayList<HashMap<String,String>>();
		}
}
