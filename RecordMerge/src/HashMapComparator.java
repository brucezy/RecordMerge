import java.util.Comparator;
import java.util.HashMap;

public class HashMapComparator implements Comparator<HashMap<String, String>> {

	private final String key;
	
	public HashMapComparator(String key) {
		this.key = key;
	}

	@Override
	public int compare(HashMap<String, String> arg0,
			HashMap<String, String> arg1) {
		return Integer.parseInt(arg0.get(this.key).toString())- Integer.parseInt(arg1.get(this.key).toString());
	}

}