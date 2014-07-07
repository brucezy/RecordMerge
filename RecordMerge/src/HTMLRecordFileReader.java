import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLRecordFileReader extends RecordFileReader {

	private Document doc;
	
	public HTMLRecordFileReader() {
		super();
	}

	@Override
	public void initialize(File input) {
		try {
			doc = Jsoup.parse(input, "UTF-8", "");
		} catch (IOException e) {
			System.err.println("Can not open " + input.getName() + ", please check if the file exist");
			e.printStackTrace();
		}
	}

	@Override
	public void readHeader() {
		//Elements tables = doc.getElementsByTag("table");
		Element table = doc.getElementById("directory");
		
		Elements trs = table.getElementsByTag("tr");
			
		for(Element tr: trs){
			Elements ths = tr.getElementsByTag("th");
				
			for(Element th:ths){
				String s = th.text().toString();
				this.data.recordHeader.add(s);
			}
				
		}

	}

	@Override
	public void readData() {
		Element table = doc.getElementById("directory");
			
		Elements trs = table.getElementsByTag("tr");
			
		for(Element tr: trs){
			Elements tds = tr.getElementsByTag("td");
				
			if (tds.size() == 0)
				continue;
				
			int i = 0;
			HashMap<String, String> keyValue = new HashMap<String, String>();
				
			for(Element td:tds){
				String s = td.text().toString();
					
				keyValue.put(this.data.recordHeader.get(i), s);
				i++;
			}
				
			this.data.recordData.add(keyValue);
		}	
	}

	@Override
	public void closeFile() {
		
	}

}