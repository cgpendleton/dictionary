package dictionary;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DictionaryPersistence {

	private final static ObjectMapper mapper = new ObjectMapper();
	static {
		mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
		mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
	}
	
	public static boolean saveDictionary(Dictionary dictionary, String filePath) {
		File dictionaryFile = new File(filePath);
		if (dictionaryFile.getParentFile() != null) {
			dictionaryFile.getParentFile().mkdirs();
		}
		try {
			mapper.writeValue(dictionaryFile, dictionary);
		} catch (IOException e) {
			System.out.println("ERROR: Unable to save dictionary. " + e.getMessage());
			return false;
		}
		return true;
	}
	
	public static Dictionary loadDictionary(String filePath) {
		Dictionary dictionary = null;
		File dictionaryFile = new File(filePath);
		try {
			dictionary = mapper.readValue(dictionaryFile, Dictionary.class);
		} catch (IOException e) {
			System.out.println("ERROR: Unable to load dictionary. " + e.getMessage());
		}
		
		return dictionary;
	}
}
