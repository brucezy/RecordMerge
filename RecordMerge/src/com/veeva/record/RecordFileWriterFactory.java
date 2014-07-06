package com.veeva.record;

public class RecordFileWriterFactory {
	
	public RecordFileWriter instance = null;
	
	public RecordFileWriterFactory(String fileType) {
		switch(fileType){
		case "CSV":
			instance = new CSVRecordFileWriter();
			break;
		default:
			System.err.println("Oops, the type of the file doesn't support right now, stay tuned");
			instance = null;
			break;
		}
	}
	
	public RecordFileWriter getInstance(){
		return instance;
	}

}
