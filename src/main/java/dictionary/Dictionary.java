package dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

public class Dictionary {
	
	private Map<String, List<String>> dictionary;
	
	public Dictionary() {
		dictionary = new HashMap<>();
	}
	
	public boolean containsKey(String key) {
		return dictionary.containsKey(key);
	}
	
	public Set<String> getKeys() {
		return dictionary.keySet();
	}
	
	public List<String> getMembers(String key) {
		return dictionary.get(key);
	}
	
	public boolean addMember(String key, String value) {
		List<String> members = dictionary.containsKey(key) ? dictionary.get(key) : new ArrayList<>();
		if (members.contains(value)) {
			return false;
		} else {
			members.add(value);
			dictionary.put(key, members);
			return true;
		}
	}
	
	public boolean removeValue(String key, String value) {
		boolean removed = false;
		if (dictionary.containsKey(key)) {
			List<String> members = dictionary.get(key);
			removed =  members.remove(value);
			if (members.isEmpty()) {
				removeKey(key);
			}
		}
		return removed;
	}
	
	public boolean removeKey(String key) {
		return dictionary.remove(key) != null;
	}
	
	public void clear() {
		dictionary.clear();
	}
	
	public boolean valueExists(String key, String value) {
		if (dictionary.containsKey(key)) {
			return dictionary.get(key).contains(value);
		}
		return false;
	}
	
	public List<String> getAllValues() {
		return dictionary.values().stream()
				.flatMap(l -> l.stream())
				.collect(Collectors.toList());
	}
	
	public Set<Entry<String, List<String>>> entrySet() {
		return dictionary.entrySet();
	}
	
	public List<String> getCommonValues(String key1, String key2) {
		List<String> commonValues = new ArrayList<>();
		if (dictionary.containsKey(key1) && dictionary.containsKey(key2)) {
			List<String> values1 = dictionary.get(key1);
			List<String> values2 = dictionary.get(key2);
			
			for(String value: values1) {
				if (values2.contains(value)) {
					commonValues.add(value);
				}
			}
		}
		return commonValues;
	}
}
