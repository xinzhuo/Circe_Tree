import java.util.*;
/**
 * Super class for all node in the tree
 * @author Xinzhuo Dong
 *
 */
public abstract class TreeNode {
	protected String typeId;
	protected String id;
	protected ArrayList<TreeNode> children;
	
	/**
	 * Super class TreeNode Constructor
	 * @param typeId 	Type of TreeNode
	 * @param id 		TreeNode id
	 */
	public TreeNode(String typeId, String id) {
		this.typeId = typeId;
		this.id = id;
		children = new ArrayList<TreeNode>();
	}
	
	/**
	 * Check if the LabTreeNode contains certain TabTreeNode
	 * @param typeId Child's type
	 * @param nodeId Child's id
	 * @return true of false
	 */
	public boolean containTreeNode(String typeId, String nodeId){
		for (TreeNode node: children){
			if (node.getId().equals(nodeId) && node.getTypeId().equals(typeId))
				return true;
		}
		return false;
	}
	
	public boolean containTreeNode(String nodeId){
		for (TreeNode node: children){
			if (node.getId().equals(nodeId))
				return true;
		}
		return false;
	}
	
	/**
	 * Search children with node ID
	 * @param nodeId
	 * @return the appropriate children node
	 */
	public TreeNode getTreeNode(String nodeId){
		for (TreeNode node: children){
			if (node.getId().equals(nodeId))
				return node;
		}
		return null;
	}
	
	/**
	 * Abstract method need that find response with request
	 * @param request RequestData
	 * @return Corresponding response
	 */
	public abstract String findResponse(RequestData request);
	/**
	 * Abstract method: Insert treeNode into children of thetreeNode 
	 * @param nodeId
	 */
	public abstract void insertTreeNode(String nodeId);
	/**
	 * AbstractInsert rows in spreadsheet into Data Structure recursively
	 * @param toInsert row to be inserted
	 */
	public abstract void insert(RowData toInsert);
	
	/**
	 * Call printTreeHelper
	 */
	public void printTree(){
		printTreeHelper(0);
	}
	
	/**
	 * Recursively print tree contents
	 * @param depth recursion counter
	 */
	private void printTreeHelper(int depth){
		for(int i=0; i<depth; i++) System.out.print("\t");
		System.out.print(this.toString());
		for(TreeNode node: children){
			node.printTreeHelper(depth+1);
		}
	}
	
	/**
	 * Print out type of the node and it's id
	 */
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
