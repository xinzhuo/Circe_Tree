import java.util.ArrayList;
/**
 * RowData contains all the information to build the tree
 * It will be passed from root to leaves
 * @author DongXinzhuo
 *
 */
public class RowData {
	private String labTreeNodeId;
	private String tabTreeNodeId;
	private ArrayList<String> classList;
	private ArrayList<String> responses;
	
	/**
	 * RowData Constructor
	 * @param labTreeNodeId Lab number
	 * @param tabTreeNodeId Tab number
	 * @param classList		List of Classification
	 * @param responses		List of Responses
	 */
	public RowData(String labTreeNodeId, String tabTreeNodeId,
			ArrayList<String> classList, ArrayList<String> responses) {
		this.labTreeNodeId = labTreeNodeId;
		this.tabTreeNodeId = tabTreeNodeId;
		this.classList = classList;
		this.responses = responses;
	}
	public String toString(){
		return labTreeNodeId + " " + tabTreeNodeId + " " + classList.get(0) + " " + classList.get(1) + " "
				+ classList.get(2);
	}
	public String getLabTreeNodeId() {
		return labTreeNodeId;
	}
	public void setLabTreeNodeId(String labTreeNodeId) {
		this.labTreeNodeId = labTreeNodeId;
	}
	public String getTabTreeNodeId() {
		return tabTreeNodeId;
	}
	public void setTabTreeNodeId(String tabTreeNodeId) {
		this.tabTreeNodeId = tabTreeNodeId;
	}
	public ArrayList<String> getClassList() {
		return classList;
	}
	public void setClassList(ArrayList<String> classTreeNode) {
		this.classList = classTreeNode;
	}
	public ArrayList<String> getResponses() {
		return responses;
	}
	public void setResponses(ArrayList<String> responses) {
		this.responses = responses;
	}
}
