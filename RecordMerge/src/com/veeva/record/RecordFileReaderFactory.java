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
			System.err.println("Doesn't support File Type:" + fileType.toUpperCase() + ", Skip to process the file");
			instance = null;
			break;
		}
	}
	
	public RecordFileReader getInstance(){
		return instance;
	}

}
