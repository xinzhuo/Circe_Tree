import java.util.*;


public abstract class TreeNode {
	protected String typeId;
	protected String id;
	protected ArrayList<TreeNode> children;
	
	public TreeNode(String typeId, String id) {
		this.typeId = typeId;
		this.id = id;
		children = new ArrayList<TreeNode>();
	}
	
	//Check if the LabTreeNode contains certain TabTreeNode
	public boolean containTreeNode(String typeId, String nodeId){
		for (TreeNode node: children){
			if (node.getId().equals(nodeId) && node.getTypeId().equals(typeId))
				return true;
		}
		return false;
	}
	
	public TreeNode getTreeNode(String nodeId){
		for (TreeNode node: children){
			if (node.getId().equals(nodeId))
				return node;
		}
		return null;
	}
	
	//Find response with provided data
	public abstract String findResponse(RequestData request);
	//Insert treeNode into children of thetreeNode (after checking contain)
	public abstract void insertTreeNode(String nodeId);
	public abstract void insert(RowData toInsert);
	
	public void printTree(){
		printTreeHelper(0);
	}
	
	private void printTreeHelper(int depth){
		for(int i=0; i<depth; i++) System.out.print("\t");
		System.out.print(this.toString());
		for(TreeNode node: children){
			node.printTreeHelper(depth+1);
		}
	}
	
	public String toString(){
		return "TypeId: " + typeId + "\tid: " + id + "\n";
	}
	
	public ArrayList<TreeNode> getChildren() {
		return children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
}
