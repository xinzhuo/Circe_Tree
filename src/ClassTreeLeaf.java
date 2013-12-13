import java.util.ArrayList;
/**
 * Leaves in the tree, inherit ClassTreeNode but with extra field
 * @author DongXinzhuo
 *
 */
public class ClassTreeLeaf extends ClassTreeNode {
	/**
	 * Array of Responses with different number of tries
	 */
	private ArrayList<String> responses;
	/**
	 * Constructor specifies node type of ClassLeaf
	 * @param classTreeLeafId
	 */
	public ClassTreeLeaf(String classTreeLeafId) {
		super(classTreeLeafId);
		this.setTypeId("ClassLeaf");
		this.responses = new ArrayList<String>();
	}

	public ArrayList<String> getResponses() {
		return responses;
	}

	public void setResponses(ArrayList<String> responses) {
		this.responses = responses;
	}

	/**
	 * Add string to the array of responses
	 * @param words
	 */
	public void addResponses(String words){
		responses.add(words);
	}

	@Override
	public String toString(){
		return "TypeId: " + typeId + "\tid: " + id + "\n";
	}
	@Override
	public String findResponse(RequestData request){
		return responses.get(request.getTries());
	}
}
