package com.veeva.record;

import java.io.File;

public abstract class RecordFileWriter {

	public RecordData data;
	
	public RecordFileWriter() {
		this.data = new RecordData();
	}

	abstract public void initialize(File output);
	
	abstract public void writeHeader();
	
	abstract public void writeData();
	
	abstract public void closeFile();
}
