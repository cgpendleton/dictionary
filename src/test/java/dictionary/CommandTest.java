package dictionary;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommandTest {

	private Dictionary dictionary;
	
	@Before
	public void init() {
		dictionary = new Dictionary();
	}
	
	@Test
	public void testAddMember() {
		Assert.assertTrue(dictionary.addMember("foo", "bar"));
		Assert.assertFalse(dictionary.addMember("foo", "bar"));
	}
	
	@Test
	public void testRemoveValue() {
		Assert.assertFalse(dictionary.removeValue("foo", "bar"));
		dictionary.addMember("foo", "bar");
		Assert.assertTrue(dictionary.removeValue("foo", "bar"));
		Assert.assertFalse(dictionary.removeValue("foo", "bar"));
	}
	
	@Test
	public void testRemoveKey() {
		Assert.assertFalse(dictionary.removeKey("foo"));
		dictionary.addMember("foo", "bar");
		Assert.assertTrue(dictionary.removeKey("foo"));
		Assert.assertFalse(dictionary.removeKey("foo"));
	}
	
	@Test
	public void testValueExists() {
		Assert.assertFalse(dictionary.valueExists("foo", "bar"));
		dictionary.addMember("foo", "bar");
		Assert.assertTrue(dictionary.valueExists("foo", "bar"));
	}
}
