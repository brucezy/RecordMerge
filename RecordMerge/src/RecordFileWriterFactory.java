public class RecordFileWriterFactory {
	
	public static RecordFileWriter create(String fileType) {
		switch(fileType){
		case "CSV":
			return new CSVRecordFileWriter();
		default:
			System.err.println("Doesn't support File Type:" + fileType.toUpperCase() + ", can not generate combined file");
			return null;
		}
	}

}
