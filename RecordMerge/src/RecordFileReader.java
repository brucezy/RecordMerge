import java.io.File;

public abstract class RecordFileReader {

	public RecordData data;
	
	public RecordFileReader() {
		this.data = new RecordData();
	}
	
	abstract public void initialize(File input);
	
	abstract public void readHeader();
	
	abstract public void readData();
	
	abstract public void closeFile();

}