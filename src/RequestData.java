import java.util.ArrayList;
/**
 * RequestData is used to find responses
 * @author Xinzhuo Dong
 *
 */
public class RequestData {
	private String labTreeNodeId;
	private String tabTreeNodeId;
	private ArrayList<String> classList;
	/**
	 * The level of Classification (Used to track ClassTreeNode level)
	 */
	private int classLevel;
	/**
	 * Number of tries by the student
	 * (Can be modified based on the data passed to the tree)
	 */
	private int tries;
	
	/**
	 * 
	 * @param labTreeNodeId	Lab ID
	 * @param tabTreeNodeId	Tab ID
	 * @param classList 	The level of Classification (Used to track ClassTreeNode level)
	 * @param tries 		Number of tries by the student
	 */
	public RequestData(String labTreeNodeId, String tabTreeNodeId,
			ArrayList<String> classList, int tries) {
		this.labTreeNodeId = labTreeNodeId;
		this.tabTreeNodeId = tabTreeNodeId;
		this.classList = classList;
		this.tries = tries;
		this.classLevel = 0;
	}

	public int getClassLevel() {
		return classLevel;
	}

	/**
	 * Increment the classLevel (Go to lower level ClassTreeNode)
	 */
	public void addClassLevel() {
		this.classLevel += 1;
	}

	public String getLabTreeNodeId() {
		return labTreeNodeId;
	}

	public String getTabTreeNodeId() {
		return tabTreeNodeId;
	}
	
	public ArrayList<String> getClassList() {
		return classList;
	}
	
	/**
	 * Uses classLevel to return the id of the specified ClassTreeNode
	 * @return id of the specified level ClassTreeNode
	 */
	public String getClassification() {
		return this.classList.get(classLevel);
		
	}

	/**
	 * Get number of tries
	 * @return no. of tries
	 */
	public int getTries() {
		return tries;
	}
}