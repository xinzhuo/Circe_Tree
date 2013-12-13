public class StartTreeNode extends TreeNode{
	public StartTreeNode(String startTreeNodeId) {
		super("Start", startTreeNodeId);
	}
	
	@Override
	public void insertTreeNode(String nodeId) {
		TreeNode node = new LabTreeNode(nodeId);
		children.add(node);
	}
	
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
	
	@Override
	public String findResponse(RequestData request){
		return this.getTreeNode(request.getLabTreeNodeId()).findResponse(request);
	}
}
