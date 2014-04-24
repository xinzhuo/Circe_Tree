/**
 * Inherit from TreeNode, first level of the tree (root)
 * @author Xinzhuo Dong
 *
 */
public class StartTreeNode extends TreeNode{
	/**
	 * Override TreeNode constructor by specify tree node type "Start"
	 * @param startTreeNodeId
	 */
	public StartTreeNode(String startTreeNodeId) {
		super("Start", startTreeNodeId);
	}
	
	/**
	 * Add new LabTreeNode into Children
	 * @param nodeId
	 */
	@Override
	public void insertTreeNode(String nodeId) {
		TreeNode node = new LabTreeNode(nodeId);
		children.add(node);
	}
	
	/**
	 * Insert RowData into lower tree node
	 * @param toInsert
	 */
	@Override
	public void insert(RowData toInsert){
		if(this.containTreeNode("Lab", toInsert.getLabTreeNodeId())){
			this.getTreeNode(toInsert.getLabTreeNodeId()).insert(toInsert);
		}
		else{
			LabTreeNode toAdd = new LabTreeNode(toInsert.getLabTreeNodeId());
			children.add(toAdd);
			toAdd.insert(toInsert);
		}
	}
	
	/**
	 * Override findResponse method in TreeNode by go to LabTreeNode children
	 * @param request
	 */
	@Override
	public String findResponse(RequestData request){
		if (this.containTreeNode(request.getLabTreeNodeId()))
			return this.getTreeNode(request.getLabTreeNodeId()).findResponse(request);
		else
			return "failed";
	}
}
