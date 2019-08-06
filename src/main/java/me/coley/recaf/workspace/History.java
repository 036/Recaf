package me.coley.recaf.workspace;

import me.coley.recaf.util.struct.ListeningMap;
import org.pmw.tinylog.Logger;

import java.time.Instant;
import java.util.Stack;

/**
 * History manager for files.
 *
 * @author Matt
 */
public class History {
	/**
	 * Stack of changed content.
	 */
	private final Stack<byte[]> stack = new Stack<>();
	/**
	 * Stack of when the content was changed.
	 */
	private final Stack<Instant> times = new Stack<>();
	/**
	 * File map to update when the history is rolled back.
	 */
	private final ListeningMap<String, byte[]> map;
	/**
	 * File being tracked.
	 */
	public final String name;

	/**
	 * Constructs a history for an item of the given name in the given map.
	 *
	 * @param map
	 * 		Map containing the item.
	 * @param name
	 * 		Item's key.
	 */
	public History(ListeningMap<String, byte[]> map, String name) {
		this.map = map;
		this.name = name;
	}

	/**
	 * @return Size of history for the current file.
	 */
	public int size() {
		return stack.size();
	}

	/**
	 * Wipe all items from the history.
	 */
	public void clear() {
		stack.clear();
		times.clear();
	}

	/**
	 * Fetch the creation times of all save states.
	 *
	 * @return Array of timestamps of each tracked change.
	 */
	public Instant[] getFileTimes() {
		return times.toArray(new Instant[0]);
	}

	/**
	 * Gets most recent change, deleting it in the process.
	 *
	 * @return Most recent version of the tracked file.
	 */
	public byte[] pop() {
		Instant time = times.pop();
		byte[] content = stack.pop();
		if (content != null) {
			map.put(name, content);
			Logger.info("Reverted '" + name + "'");
			// If the size is now 0, we just pop'd the initial state.
			// Since we ALWAYS want to keep the initial state we will push it back.
			if (size() == 0) {
				times.push(time);
				stack.push(content);
			}
		} else {
			throw new IllegalStateException("No history to revert to!");
		}
		return content;
	}

	/**
	 * @return Most recent version of the tracked file.
	 */
	public byte[] peek() {
		return stack.peek();
	}

	/**
	 * Updates current value, pushing the latest value into the history
	 * stack.
	 *
	 * @param modified
	 * 		Changed value.
	 */
	public void push(byte[] modified) {
		stack.push(modified);
		times.push(Instant.now());
	}
}