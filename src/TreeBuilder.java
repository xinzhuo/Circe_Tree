import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

import java.io.IOException;
import java.net.*;
import java.util.*;
/**
 * 
 * @author DongXinzhuo
 *
 */


public class TreeBuilder {
	//Map for decision making
	static Map<String, Map<String, Float>> one_to_two;
	static Map<String, Map<String, Float>> one_to_three;
	static Map<String, Map<String, Float>> two_to_one;
	static Map<String, Map<String, Float>> two_to_three;
	static Map<String, Map<String, Float>> three_to_one;
	static Map<String, Map<String, Float>> three_to_two;
	
	
	public static void main(String[] args) throws AuthenticationException, MalformedURLException, IOException, ServiceException{
		TreeNode root = build_tree();
		if (root==null){
			System.out.println("Unable to access the request spreadsheet");
			System.exit(1);
		}
		RequestData example = build_data("1", "2", "", "", "", 2);
		if (example == null){
			System.out.println("Insufficient data");
			System.exit(1);
		}
		//String response = root.findResponse(example);
		System.out.println("\nRequest Response is: "+root.findResponse(example));
	} 
	//This will handle incomplete data
	public static RequestData build_data(String lab, String tab, String class1, String class2, String class3, int tries){
		//Both 1 and 2 are missing
		if (class1.equals("")&&class2.equals("")&&class3.equals("")){
			return null;
		}
		if (class1.equals("")&&class2.equals("")){
			Map<String, Float> search = three_to_two.get(class3);
			float temp = 0;
			for (String to: search.keySet()){
				if (search.get(to)>temp){
					temp = search.get(to);
					class2 = to;
				}
			}
			search = three_to_one.get(class3);
			temp = 0;
			for (String to: search.keySet()){
				if (search.get(to)>temp){
					temp = search.get(to);
					class1 = to;
				}
			}
		}
		//Both 1 and 3 are missing
		else if (class1.equals("")&&class3.equals("")){
			Map<String, Float> search = two_to_one.get(class2);
			float temp = 0;
			for (String to: search.keySet()){
				if (search.get(to)>temp){
					temp = search.get(to);
					class1 = to;
				}
			}
			search = two_to_three.get(class2);
			temp = 0;
			for (String to: search.keySet()){
				if (search.get(to)>temp){
					temp = search.get(to);
					class3 = to;
				}
			}
		}
		//Both 2 and 3 are missing
		else if (class2.equals("")&&class3.equals("")){
			Map<String, Float> search = one_to_two.get(class1);
			float temp = 0;
			for (String to: search.keySet()){
				if (search.get(to)>temp){
					temp = search.get(to);
					class2 = to;
				}
			}
			search = one_to_three.get(class1);
			temp = 0;
			for (String to: search.keySet()){
				if (search.get(to)>temp){
					temp = search.get(to);
					class3 = to;
				}
			}
		}
		
		//Only missing class 1
		if (class1.equals("")){
			Map<String, Float> search = three_to_one.get(class3);
			float temp = 0;
			for (String to: search.keySet()){
				if (search.get(to)>temp){
					temp = search.get(to);
					class1 = to;
				}
			}
		}
		//Only missing class 2
		else if (class2.equals("")){
			Map<String, Float> search = three_to_two.get(class3);
			float temp = 0;
			for (String to: search.keySet()){
				if (search.get(to)>temp){
					temp = search.get(to);
					class2 = to;
				}
			}
		}
		//only missing class 3
		else if (class3.equals("")){
			Map<String, Float> search = one_to_three.get(class1);
			float temp1 = 0;
			String temp_one = "";
			for (String to: search.keySet()){
				if (search.get(to)>temp1){
					temp1 = search.get(to);
					temp_one = to;
				}
			}
			
			search = two_to_three.get(class2);
			float temp2 = 0;
			String temp_two = "";
			for (String to: search.keySet()){
				if (search.get(to)>temp2){
					temp2 = search.get(to);
					temp_two = to;
				}
			}
			
			if (temp1>temp2)
				class3 = temp_one;
			else
				class3 = temp_two;
		}
		
		
		ArrayList<String> classifications = new ArrayList<String>(3);
		classifications.add(class1);
		classifications.add(class2);
		classifications.add(class3);
		RequestData example = new RequestData(lab, tab, classifications, tries);
		return example;
	}
	
	//This method will build the tree and return the root
	public static TreeNode build_tree()
			throws AuthenticationException, MalformedURLException, IOException, ServiceException {
		
		//For Graph_build:
		ArrayList<String> classlist_1 = new ArrayList<String>();
		ArrayList<String> classlist_2 = new ArrayList<String>();
		ArrayList<String> classlist_3 = new ArrayList<String>();

		System.out.print("Username: ");
		Scanner scan = new Scanner(System.in);
		String USERNAME = scan.nextLine();
		System.out.print("Password: ");
		String PASSWORD = scan.nextLine();  
		//		  String USERNAME = "";
		//		  String PASSWORD = "";
		SpreadsheetService service = new SpreadsheetService("MySpreadsheetIntegration-v1");
		service.setUserCredentials(USERNAME, PASSWORD);
		service.setProtocolVersion(SpreadsheetService.Versions.V3);

		URL SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");

		/**
		 * Make a request to the API and get all spreadsheets.
		 */
		SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();

		if (spreadsheets.size() == 0) {
			System.out.println("No spreadsheet affiliated to this account.");
			return null;
		}

		/**
		 *  Iterate through all of the spreadsheets returned to search for spreadsheet
		 */
		SpreadsheetEntry circe_spreadsheet = null;
		for (SpreadsheetEntry spreadsheet : spreadsheets) {
			// Substitute the file title here
			if (spreadsheet.getTitle().getPlainText().equals("Circe Revised")){
				circe_spreadsheet = spreadsheet;
				break;
			}
		}

		/**
		 * Check if corresponding spreadsheet exists
		 */
		if (circe_spreadsheet==null){
			System.out.println("Error: No corresponding spreadsheet");
			return null;
		}

		/**
		 * Choose the right worksheet within the spreadsheet (would depend actual situation)
		 */
		List<WorksheetEntry> worksheets = circe_spreadsheet.getWorksheets();
		WorksheetEntry circe_worksheet = worksheets.get(0);

		/**
		 * Fetch Cell feeds, form row data and put row data into the tree
		 * All empty blocks need to be filled with "null" or some string
		 */
		CellFeed circe_cellFeed = service.getFeed(circe_worksheet.getCellFeedUrl(), CellFeed.class);
		TreeNode root = new StartTreeNode("Circe");
		for (Iterator<CellEntry> iter = circe_cellFeed.getEntries().iterator(); iter.hasNext(); ) { 		  
			CellEntry cell = iter.next();
			String[] locate = cell.getId().substring(cell.getId().lastIndexOf('/') + 1).substring(1).split("C");
			int rowNum = Integer.parseInt(locate[0])-1;
			int colNum = Integer.parseInt(locate[1])-1;
			if (rowNum<75 && rowNum>0){
				if (colNum==0){
					//Construct rowdata
					String labNum, tabNum;
					ArrayList<String> classList = new ArrayList<String>(3);
					ArrayList<String> responseList = new ArrayList<String>(3);
					labNum = cell.getCell().getValue();
					tabNum = iter.next().getCell().getValue();
					
					String c1 = iter.next().getCell().getValue();
					String c2 = iter.next().getCell().getValue();
					String c3 = iter.next().getCell().getValue();
					
					//store all classification 1,2,3 into three arraylists
					classlist_1.add(c1);
					classlist_2.add(c2);
					classlist_3.add(c3);
					
					//Continue constructing rowdata
					classList.add(c1);
					classList.add(c2);
					classList.add(c3);
					responseList.add(iter.next().getCell().getValue());
					responseList.add(iter.next().getCell().getValue());
					responseList.add(iter.next().getCell().getValue());
					classList.trimToSize();
					responseList.trimToSize();
					RowData theRow = new RowData(labNum,tabNum,classList,responseList);
					//System.out.println(theRow.toString());
					root.insert(theRow);			  
				}
			}
		}
		/**
		 * Print Tree recursively
		 */
		root.printTree();
		
		/*    //Code for storing spreadsheet in local 2D-array
		  //We use cell-based feeds and R1C1 notation to fetch data in the spreadsheet
		  String[][] form = new String[79][9];   
		  for (CellEntry cell : circe_cellFeed.getEntries()) {
			  String[] locate = cell.getId().substring(cell.getId().lastIndexOf('/') + 1).substring(1).split("C");
			  int rowNum = Integer.parseInt(locate[0])-1;
			  int colNum = Integer.parseInt(locate[1])-1;
			  if (colNum>2&&colNum<6){

			  }
			  if (rowNum<76&&colNum<9){
				  form[rowNum][colNum] = cell.getCell().getValue();
			  }
		  } 
		  for(int i = 1; i < 76; i++){
		  ArrayList<String> classList = new ArrayList<String>();
		  classList.add(form[i][3]);
		  classList.add(form[i][4]);
		  classList.add(form[i][5]);
		  ArrayList<String> responseList = new ArrayList<String>();
		  responseList.add(form[i][6]);
		  responseList.add(form[i][7]);
		  responseList.add(form[i][8]);
		  RowData theRow = new RowData(form[i][0],form[i][1],classList,responseList);
		  exampleStart.insert(theRow);
	  }
		 */  
		
		one_to_two = Graph_Build.build(classlist_1, classlist_2);
		one_to_three = Graph_Build.build(classlist_1, classlist_3);
		two_to_one = Graph_Build.build(classlist_2, classlist_1);
		two_to_three = Graph_Build.build(classlist_2, classlist_3);
		three_to_one = Graph_Build.build(classlist_3, classlist_1);
		three_to_two = Graph_Build.build(classlist_3, classlist_2);
		
		return root;
	}	
}