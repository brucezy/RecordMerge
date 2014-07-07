import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVWriter;

public class CSVRecordFileWriter extends RecordFileWriter {
	
	private CSVWriter writer;
	private OutputStreamWriter outputStream;
	
	public CSVRecordFileWriter() {
		super();
	}

	@Override
	public void initialize(File output){
		try {
			outputStream = new OutputStreamWriter(new FileOutputStream(output));
			this.writer = new CSVWriter(outputStream,',', CSVWriter.DEFAULT_QUOTE_CHARACTER);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void writeHeader() {
		this.writer.writeNext(this.data.recordHeader.toArray(new String[this.data.recordHeader.size()]));
	}

	@Override
	public void writeData() {
		ArrayList<String> dataRow = new ArrayList<String>();
		for(HashMap<String, String> map : this.data.recordData){
			for(String s : this.data.recordHeader){
				dataRow.add(map.get(s).toString());
			}
			this.writer.writeNext(dataRow.toArray(new String[dataRow.size()]));
			dataRow.clear();
		}
	}

	@Override
	public void closeFile() {
		
		try {
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}