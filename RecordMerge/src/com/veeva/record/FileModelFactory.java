package com.veeva.record;

public class FileModelFactory {

	RecordModle instance = null;
	
	public FileModelFactory(String fileType) {
		switch(fileType){
		case "HTML":
			instance = new HTMLRecordModle();
			break;
		case "CSV":
			instance = new CSVRecordModle();
			break;
		default:
			System.err.println("Oops, the type of the file doesn't support right now, stay tuned");
			instance = null;
		}
		
	}
	
	public RecordModle getInstance(){
		return instance;
	}

}
