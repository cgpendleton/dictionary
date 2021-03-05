package dictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Dictionary {
	
	private static Map<String, List<String>> dictionary;
	private static volatile boolean running = true;
	
	public static void main(String... args) {
		dictionary = new HashMap<>();
		printUsage();
		
		Scanner in = new Scanner(System.in);
		String input;
		String[] inputArr;
		String response;
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
			
			response = null;
			switch(command.name()) {
			case "KEYS":
				getKeys();
				break;
			case "MEMBERS":
				getMembers(inputArr[1]);
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
				keyExists(inputArr[1]);
				break;
			case "VALUEEXISTS":
				valueExists(inputArr[1], inputArr[2]);
				break;
			case "ALLMEMBERS":
				getAllValues();
				break;
			case "ITEMS":
				getItems();
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
	
	private static void getKeys() {
		for (String key: dictionary.keySet()) {
			System.out.println(key);
		}
	}
	
	private static void getMembers(String key) {
		if (dictionary.containsKey(key)) {
			for (String value: dictionary.get(key)) {
				System.out.println(value);
			}
		} else {
			System.out.printf("ERROR: Key [%s] does not exist\n", key);
		}
	}
	
	private static void addMember(String key, String value) {
		List<String> members = dictionary.containsKey(key) ? dictionary.get(key) : new ArrayList<>();
		if (members.contains(value)) {
			System.out.printf("ERROR: Value [%s] already exists for [%s]\n", value, key);
		} else {
			members.add(value);
			dictionary.put(key, members);
			System.out.println("Added");
		}
	}
	
	private static void removeValue(String key, String value) {
		if (dictionary.containsKey(key)) {
			List<String> members = dictionary.get(key);
			if (members.remove(value)) {
				System.out.println("Removed");
			} else {
				System.out.printf("ERROR: Value [%s] does not exist for [%s]\n", value, key);
			}
		} else {
			System.out.printf("ERROR: Key [%s] does not exist\n", key);
		}
	}
	
	private static void removeKey(String key) {
		if (dictionary.remove(key) != null) {
			System.out.println("Removed");
		} else {
			System.out.printf("ERROR: Key [%s] does not exist\n", key);
		}
	}
	
	private static void clearDictionary() {
		dictionary.clear();
		System.out.println("Cleared");
	}
	
	private static void keyExists(String key) {
		System.out.println(dictionary.containsKey(key));
	}
	
	private static void valueExists(String key, String value) {
		if (dictionary.containsKey(key)) {
			System.out.println(dictionary.get(key).contains(value));
		} else {
			System.out.println(false);
		}
	}
	
	private static void getAllValues() {
		if (dictionary.isEmpty()) {
			System.out.println("(empty)");
			return;
		}
		//dictionary.values().stream().forEach(l -> l.forEach(v -> System.out.println(v)));
		int i = 0;
		for(List<String> values: dictionary.values()) {
			for (String value: values) {
				System.out.printf("%d) %s\n", ++i, value);
			}
		}
	}
	
	private static void getItems() {
		if (dictionary.isEmpty()) {
			System.out.println("(empty)");
			return;
		}
		int i = 0;
		for(Entry<String, List<String>> entry: dictionary.entrySet()) {
			for (String value: entry.getValue()) {
				System.out.printf("%d) %s: %s\n", ++i, entry.getKey(), value);
			}
		}
	}
}
