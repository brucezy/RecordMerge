package com.veeva.record;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLRecordModle extends RecordModle{

	Document doc;
	
	public HTMLRecordModle() {
		super();
	}
	
	public void initialize(File input){
		try {
			//doc = Jsoup.parse(input, "UTF-8");
			doc = Jsoup.parse(input, "UTF-8", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void readHeader(File input) {
		Elements tables = doc.getElementsByTag("table");
		
		for(Element table:tables){
			Elements trs = table.getElementsByTag("tr");
			
			for(Element tr: trs){
				Elements ths = tr.getElementsByTag("th");
				
				for(Element th:ths){
					String s = th.text().toString();
					this.recordHeader.add(s);
				}
				
			}
		}
		
	}

	@Override
	public void readData(File input) {
		
		
		Elements tables = doc.getElementsByTag("table");
		
		for(Element table:tables){
			
			Elements trs = table.getElementsByTag("tr");
			
			for(Element tr: trs){
				Elements tds = tr.getElementsByTag("td");
				
				if (tds.size() == 0)
					continue;
				
				int i = 0;
				HashMap<String, String> keyValue = new HashMap<String, String>();
				
				for(Element td:tds){
					String s = td.text().toString();
					
					keyValue.put(recordHeader.get(i), s);
					i++;
				}
				
				this.recordData.add(keyValue);
			}	
		}
	}
}
