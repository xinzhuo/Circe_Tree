import java.util.ArrayList;

public class RowData {
	private String labTreeNodeId;
	private String tabTreeNodeId;
	private ArrayList<String> classList;
	private ArrayList<String> responses;
	
	public RowData(String labTreeNodeId, String tabTreeNodeId,
			ArrayList<String> classList, ArrayList<String> responses) {
		this.labTreeNodeId = labTreeNodeId;
		this.tabTreeNodeId = tabTreeNodeId;
		this.classList = classList;
		this.responses = responses;
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
