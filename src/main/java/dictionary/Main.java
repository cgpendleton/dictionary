package dictionary;

import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class Main {
	
	private static volatile boolean running = true;
	private static Dictionary dictionary;
	
	public static void main(String... args) {
		dictionary = new Dictionary();
		printUsage();
		
		Scanner in = new Scanner(System.in);
		String input;
		String[] inputArr;
		while (running) {
			System.out.print("> ");
			input = in.nextLine();
			if (input.isEmpty()) continue;
			inputArr = input.split(" ");

			Command command = null;
			try {
				command = Command.valueOf(inputArr[0].toUpperCase());
				if(inputArr.length - 1 < command.getArgs().length) {
					System.out.println("ERROR: Missing argument(s)");
					System.out.printf("\t%s %s\n", command.name(), command.getArgsString());
					continue;
				} else if (inputArr.length -1 > command.getArgs().length) {
					System.out.println("WARNING: Extra argument(s) will be ignored");
				}
			} catch (IllegalArgumentException e) {
				System.out.printf("Unrecognized command: [%s]\n", inputArr[0]);
				printUsage();
				continue;
			}
			
			switch(command.name()) {
			case "KEYS":
				printKeys();
				break;
			case "MEMBERS":
				printMembers(inputArr[1]);
				break;
			case "ADD":
				addMember(inputArr[1], inputArr[2]);
				break;
			case "REMOVE":
				removeValue(inputArr[1], inputArr[2]);
				break;
			case "REMOVEALL":
				removeKey(inputArr[1]);
				break;
			case "CLEAR":
				clearDictionary();
				break;
			case "KEYEXISTS":
				System.out.println(dictionary.containsKey(inputArr[1]));
				break;
			case "VALUEEXISTS":
				System.out.println(dictionary.valueExists(inputArr[1], inputArr[2]));
				break;
			case "ALLMEMBERS":
				printAllValues();
				break;
			case "ITEMS":
				printItems();
				break;
			case "INTERSECT":
				getCommonValues(inputArr[1], inputArr[2]);
				break;
			case "SAVE":
				save(inputArr[1]);
				break;
			case "LOAD":
				load(inputArr[1]);
				break;
			case "COMMANDS":
				printCommands();
				break;
			case "EXIT":
				running = false;
				break;
			}
		}
		in.close();
	}
	
	private static void printUsage() {
		System.out.println("Usage: <command> [<args>]");
		System.out.println("Enter 'COMMANDS' to see a list of available commands");
	}
	
	private static void printCommands() {
		for (Command command: Command.values()) {
			System.out.printf("\t%-12s%-13s\t%s\n", command.name(), command.getArgsString(), command.getDescription());
		}
	}
	
	private static void printKeys() {
		Set<String> keys = dictionary.getKeys();
		if (keys.isEmpty()) {
			System.out.println("(empty)");
		} else {
			int i = 0;
			for (String key: dictionary.getKeys()) {
				System.out.println(++i + ") " + key);
			}
		}
	}
	
	private static void printMembers(String key) {
		if (dictionary.containsKey(key)) {
			int i = 0;
			for (String value: dictionary.getMembers(key)) {
				System.out.println(++i + ") " + value);
			}
		} else {
			System.out.printf("ERROR: Key [%s] does not exist\n", key);
		}
	}
	
	private static void addMember(String key, String value) {
		if (dictionary.addMember(key, value)) {
			System.out.println("Added");
		} else {
			System.out.printf("ERROR: Value [%s] already exists for [%s]\n", value, key);
		}
	}
	
	private static void removeValue(String key, String value) {
		if (dictionary.containsKey(key)) {
			if (dictionary.removeValue(key, value)) {
				System.out.println("Removed");
			} else {
				System.out.printf("ERROR: Value [%s] does not exist for [%s]\n", value, key);
			}
		} else {
			System.out.printf("ERROR: Key [%s] does not exist\n", key);
		}
	}
	
	private static void removeKey(String key) {
		if (dictionary.removeKey(key)) {
			System.out.println("Removed");
		} else {
			System.out.printf("ERROR: Key [%s] does not exist\n", key);
		}
	}
	
	private static void clearDictionary() {
		dictionary.clear();
		System.out.println("Cleared");
	}

	private static void printAllValues() {
		List<String> values = dictionary.getAllValues();
		if (values.isEmpty()) {
			System.out.println("(empty)");
		} else {
			int i = 0;
			for (String value: values) {
				System.out.printf("%d) %s\n", ++i, value);
			}
		}
	}
	
	private static void printItems() {
		Set<Entry<String, List<String>>> entries = dictionary.entrySet();
		if (entries.isEmpty()) {
			System.out.println("(empty)");
		} else {
			int i = 0;
			for(Entry<String, List<String>> entry: entries) {
				for (String value: entry.getValue()) {
					System.out.printf("%d) %s: %s\n", ++i, entry.getKey(), value);
				}
			}
		}
	}
	
	private static void save(String filepath) {
		if (DictionaryPersistence.saveDictionary(dictionary, filepath)) {
			System.out.println("Saved");
		}
	}
	
	private static void load(String filepath) {
		Dictionary loaded = DictionaryPersistence.loadDictionary(filepath);
		if (loaded != null) {
			dictionary = loaded;
			System.out.println("Loaded");
		}
	}
	
	private static void getCommonValues(String key1, String key2) {
		List<String> commonValues = dictionary.getCommonValues(key1, key2);
		
		if (commonValues.isEmpty()) {
			System.out.println("(empty)");
		} else {
			int i = 0;
			for (String value: commonValues) {
				System.out.printf("%d) %s\n", ++i, value);
			}
		}
	}
}
