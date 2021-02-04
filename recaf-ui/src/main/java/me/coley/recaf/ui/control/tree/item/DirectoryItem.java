package me.coley.recaf.ui.control.tree.item;

/**
 * Item for representing directories in the workspace.
 *
 * @author Matt Coley
 */
public class DirectoryItem extends BaseTreeItem {
	private final String directoryName;

	/**
	 * @param directoryName
	 * 		Name of directory.
	 */
	public DirectoryItem(String directoryName) {
		this.directoryName = directoryName;
		init();
	}

	/**
	 * @return Name of directory.
	 */
	public String getDirectoryName() {
		return directoryName;
	}

	@Override
	protected BaseTreeValue createTreeValue() {
		String partialName = directoryName.substring(directoryName.lastIndexOf('/') + 1);
		return new BaseTreeValue(this, partialName, true);
	}
}
