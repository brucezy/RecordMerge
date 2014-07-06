package com.veeva.record;

public class RecordFileReaderFactory {

	
	public RecordFileReader instance = null;
	
	public RecordFileReaderFactory(String fileType) {
		switch(fileType){
		case "HTML":
			instance = new HTMLRecordFileReader();
			break;
		case "CSV":
			instance = new CSVRecordFileReader();
			break;
		default:
			System.err.println("Oops, the type of the file doesn't support right now, stay tuned");
			instance = null;
			break;
		}
	}
	
	public RecordFileReader getInstance(){
		return instance;
	}

}