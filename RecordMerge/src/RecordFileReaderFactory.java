
public class RecordFileReaderFactory {
	
	public static RecordFileReader create(String fileType) {
		switch(fileType){
		case "HTML":
			return new HTMLRecordFileReader();
		case "CSV":
			return new CSVRecordFileReader();
		default:
			System.err.println("Doesn't support File Type:" + fileType.toUpperCase() + ", Skip to process the file");
			return null;
		}
	}
}