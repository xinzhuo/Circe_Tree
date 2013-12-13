import java.util.ArrayList;

public class ClassTreeLeaf extends ClassTreeNode {
	private ArrayList<String> responses;
	
	//The ClassTreeLeaf is the classification 3 node
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
