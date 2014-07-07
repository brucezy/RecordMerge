package com.veeva.record;

public class RecordFileWriterFactory {
	
	public RecordFileWriter instance = null;
	
	public RecordFileWriterFactory(String fileType) {
		switch(fileType){
		case "CSV":
			instance = new CSVRecordFileWriter();
			break;
		default:
			System.err.println("Doesn't support File Type:" + fileType.toUpperCase() + ", can not generate combined file");
			instance = null;
			break;
		}
	}
	
	public RecordFileWriter getInstance(){
		return instance;
	}

}
