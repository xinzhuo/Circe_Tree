import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class TreeBuilder {
  public static void main(String[] args)
      throws AuthenticationException, MalformedURLException, IOException, ServiceException {
	  System.out.println("Username");
	  Scanner scan = new Scanner(System.in);
	  String USERNAME = scan.nextLine();
	  System.out.println("Password: ");
	  String PASSWORD = scan.nextLine();  
//	  String USERNAME = "";
//	  String PASSWORD = "";
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
		  return;
	  }
	  
	  /**
	   *  Iterate through all of the spreadsheets returned to search for spreadsheet
	   */
	  SpreadsheetEntry circe_spreadsheet = null;
	  for (SpreadsheetEntry spreadsheet : spreadsheets) {
		  // Substitute the file title here
		  if (spreadsheet.getTitle().getPlainText().equals("Copy of Circe Coaching Sheet")){
			  circe_spreadsheet = spreadsheet;
			  break;
		  }
	  }
	  
	  /**
	   * Check if corresponding spreadsheet exists
	   */
	  if (circe_spreadsheet==null){
		  System.out.println("Error: No corresponding spreadsheet");
		  return;
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
		  if (rowNum<76 && rowNum>0){
			  if (colNum==0){
				  String labNum, tabNum;
				  ArrayList<String> classList = new ArrayList<String>(3);
				  ArrayList<String> responseList = new ArrayList<String>(3);
				  labNum = cell.getCell().getValue();
				  tabNum = iter.next().getCell().getValue();
				  iter.next();
				  classList.add(iter.next().getCell().getValue());
				  classList.add(iter.next().getCell().getValue());
				  classList.add(iter.next().getCell().getValue());
				  responseList.add(iter.next().getCell().getValue());
				  responseList.add(iter.next().getCell().getValue());
				  responseList.add(iter.next().getCell().getValue());
				  RowData theRow = new RowData(labNum,tabNum,classList,responseList);
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
/*	  //Test
	  ArrayList<String> classifications = new ArrayList<String>(3);
	  classifications.add("data");
	  classifications.add("range");
	  classifications.add("values differ by factor of 100");
	  RequestData example = new RequestData("1", "1", classifications, 2);
	  System.out.println(root.findResponse(example));
*/
  } 
}