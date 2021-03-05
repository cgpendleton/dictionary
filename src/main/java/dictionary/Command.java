package dictionary;

public enum Command {
	KEYS("Returns all the keys in the dictionary"),
	MEMBERS("Returns the collection of strings for the given key", "key"),
	ADD("Add a member to a collection for a given key", "key", "value"),
	REMOVE("Removes a value from a key", "key", "value"),
	REMOVEALL("Removes all values for a key and removes the key from the dictionary", "key"),
	CLEAR("Removes all keys and all values from the dictionary"),
	KEYEXISTS("Returns whether a key exists or not", "key"),
	VALUEEXISTS("Returns whether a value exists within a key", "key", "value"),
	ALLMEMBERS("Returns all the values in the dictionary"),
	ITEMS("Returns all keys in the dictionary and all of their values"),
	SAVE("Save the current dictionary to a file", "filepath"),
	LOAD("Load a dictionary from a file", "filepath"),
	COMMANDS("See this list of commands"),
	EXIT("Exit the dictionary application");
	
	private String description;
	private String[] args;
	
	private Command(String description, String... args) {
		this.description = description;
		this.args = args;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String[] getArgs() {
		return args;
	}
	
	public String getArgsString() {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < args.length; i++) {
			if (i > 0) {
				sb.append(" ");
			}
			sb.append("<")
				.append(args[i])
				.append(">");
		}
		
		return sb.toString();
	}
}