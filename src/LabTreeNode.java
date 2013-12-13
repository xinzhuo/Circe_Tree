/**
 * Inherit TreeNode, second level of the tree (under StartTreeNode)
 * Represent the Lab number in the system
 * @author DongXinzhuo
 *
 */
public class LabTreeNode extends TreeNode{	
	/**
	 * Constructor to specify Node type (Lab)
	 * @param labTreeNodeId
	 */
	public LabTreeNode(String labTreeNodeId) {
		super("Lab", labTreeNodeId); 
	}

	@Override
	public void insertTreeNode(String nodeId) {
		TreeNode node = new TabTreeNode(nodeId);
		children.add(node);
	}

	@Override
	public void insert(RowData toInsert){
		if(this.containTreeNode("Tab", toInsert.getTabTreeNodeId())){
			this.getTreeNode(toInsert.getTabTreeNodeId()).insert(toInsert);
		}
		else{
			TabTreeNode toAdd = new TabTreeNode(toInsert.getTabTreeNodeId());
			children.add(toAdd);
			toAdd.insert(toInsert);
		}
	}
	@Override
	public String findResponse(RequestData request){
		return this.getTreeNode(request.getTabTreeNodeId()).findResponse(request);
	}
}
