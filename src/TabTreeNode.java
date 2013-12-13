/**
 * Third level of the tree, under LabTreeNode
 * Represent the Tab number in the system
 * @author DongXinzhuo
 *
 */
public class TabTreeNode extends TreeNode{
	/**
	 * Constructor for TabTreeNode to specify node type
	 * @param tabTreeNodeId
	 */
	public TabTreeNode(String tabTreeNodeId) {
		super("Tab", tabTreeNodeId);
	}

	@Override
	public void insertTreeNode(String nodeId) {
		TreeNode node = new ClassTreeNode(nodeId);
		children.add(node);
	}

	@Override
	public void insert(RowData toInsert){
		String classId1 = toInsert.getClassList().remove(0);
		if(this.containTreeNode("Class", classId1)){
			this.getTreeNode(classId1).insert(toInsert);
		}
		else{
			ClassTreeNode toAdd = new ClassTreeNode(classId1);
			children.add(toAdd);
			toAdd.insert(toInsert);
		}
	}
	@Override
	public String findResponse(RequestData request){
		String classId = request.getClassification();
		request.addClassLevel();
		return this.getTreeNode(classId).findResponse(request);
	}
}
