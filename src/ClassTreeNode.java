/**
 * Inherit TreeNode, can be child of itself or a TabTreeNode
 * The ClassTreeNode class represent the classifications of a problem
 * A problem can have multiple classifications (multiple levels)
 * @author DongXinzhuo
 *
 */
public class ClassTreeNode extends TreeNode{
	/**
	 * Constructor for ClassTreeNode with specified node type "Class"
	 * @param id
	 */
	public ClassTreeNode(String id) {
		super("Class", id);
	}

	@Override
	public void insertTreeNode(String nodeId) {
		TreeNode node = new ClassTreeNode(nodeId);
		children.add(node);
	}

	@Override
	public void insert(RowData toInsert){
		if(toInsert.getClassList().size()>1){
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
		else if (toInsert.getClassList().size()==1){
			String classId1 = toInsert.getClassList().remove(0);
			ClassTreeLeaf theLeaf = new ClassTreeLeaf(classId1);
			theLeaf.setResponses(toInsert.getResponses());
			children.add(theLeaf);
		}
	}
	@Override
	public String findResponse(RequestData request){
		String classId = request.getClassification();
		request.addClassLevel();
		return this.getTreeNode(classId).findResponse(request);
	}
}
