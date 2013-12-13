import java.util.ArrayList;

public class RequestData {
	private String labTreeNodeId;
	private String tabTreeNodeId;
	private ArrayList<String> classList;
	private int classLevel;
	private int tries;
	
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
	
	public String getClassification() {
		return this.classList.get(classLevel);
		
	}

	public int getTries() {
		return tries;
	}
}