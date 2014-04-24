import com.google.gdata.client.spreadsheet.*;
import com.google.gdata.data.spreadsheet.*;
import com.google.gdata.util.*;

import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Main class to scan spreadsheet and handle input
 * 
 * @author Xinzhuo Dong
 * 
 */

public class TreeBuilder {
	// Maps for probability relationships
	static Map<String, Map<String, Float>> one_to_two;
	static Map<String, Map<String, Float>> one_to_three;
	static Map<String, Map<String, Float>> two_to_one;
	static Map<String, Map<String, Float>> two_to_three;
	static Map<String, Map<String, Float>> three_to_one;
	static Map<String, Map<String, Float>> three_to_two;

	public static void main(String[] args) throws AuthenticationException,
			MalformedURLException, IOException, ServiceException {

		// Build the tree with root returned
		TreeNode root = build_tree();

		if (root == null) {
			System.out.println("Unable to access the request spreadsheet");
			System.exit(1);
		}
		while (true) {
			// Input goes here
			String lab = "1";
			String tab = "2";
			double sa = 1;
			double ca = 2;
			String class1 = "";
			String class2 = "";
			int num_tries = 2;

			// Need to decide whether inputs are list of answers or single value
			ArrayList<String> class_three = build_class_three_single(sa, ca);// A(SA=100CA||SA=0.01CA)
			for (String class3 : class_three) {

				RequestData example = build_data(lab, tab, class1, class2,
						class3, num_tries);

				if (example == null)
					System.out.println("Insufficient data");

				String result = root.findResponse(example);
				if (!result.equals("failed")) {
					System.out.println("\nRequest Response is: " + result);
					break;
				} else
					System.out
							.println("\nSorry, no appropriate hints available.");
			}
			break;
		}
	}

	/**
	 * This method gets possible classifications for single value answer
	 * 
	 * @param sa
	 * @param ca
	 * @return ArrayList of possible classification, ranked by index
	 */
	public static ArrayList<String> build_class_three_single(double sa,
			double ca) {
		ArrayList<String> class_three = new ArrayList<String>();
		if (is_off_by_hundred(sa, ca))
			class_three.add("SA=100CA||SA=0.01CA");
		if (sa == -ca)
			class_three.add("SA=-CA");
		if (sa <= ca + 0.05 || sa >= ca - 0.05)
			class_three.add("CA-0.05<SA<CA+0.05");
		class_three.add("SA!=CA");
		return class_three;
	}

	/**
	 * This method gets possible classifications for multiple value answers
	 * 
	 * @param student_anwser
	 * @param correct_anwser
	 * @return ArrayList of possible classification, ranked by index
	 */
	public static ArrayList<String> build_class_three_set(
			double[] student_anwser, double[] correct_anwser) {
		ArrayList<String> class_three = new ArrayList<String>();
		if (student_anwser.length == 2) {
			if (!(student_anwser[0] == correct_anwser[0])
					&& student_anwser[1] == correct_anwser[1]) {
				class_three.add("SA1!=CA1&&SA2=CA2");
			} else if (student_anwser[0] == correct_anwser[0]
					&& !(student_anwser[1] == correct_anwser[1])) {
				class_three.add("SA1=CA1&&SA2!=CA2");
			} else if (!(student_anwser[0] == correct_anwser[0])
					&& !(student_anwser[1] == correct_anwser[1])) {
				class_three.add("SA1!=CA1&&SA2!=CA2");
			}
		}
		if (is_off_by_hundred(student_anwser[0], correct_anwser[0])
				&& is_off_by_hundred(student_anwser[1], correct_anwser[1])) {
			class_three.add("A(SA=100CA||SA=0.01CA)");
		}
		class_three.add("E(SA!=CA)");
		return class_three;
	}

	/**
	 * Check if two value is off by factor of a hundred
	 * 
	 * @param sa
	 * @param ca
	 * @return true or false
	 */
	public static boolean is_off_by_hundred(double sa, double ca) {
		return sa == 100 * ca || sa == 0.01 * ca;
	}

	/**
	 * This method will build the RequestData from the parameters, it will also
	 * handle incomplete classifications
	 * 
	 * @param lab
	 * @param tab
	 * @param class1
	 * @param class2
	 * @param class3
	 * @param tries
	 * @return RequestData
	 */
	public static RequestData build_data(String lab, String tab, String class1,
			String class2, String class3, int tries) {
		// Both 1 and 2 are missing
		if (class1.equals("") && class2.equals("") && class3.equals("")) {
			return null;
		}
		if (class1.equals("") && class2.equals("")) {
			Map<String, Float> search = three_to_two.get(class3);
			float temp = 0;
			for (String to : search.keySet()) {
				if (search.get(to) > temp) {
					temp = search.get(to);
					class2 = to;
				}
			}
			search = three_to_one.get(class3);
			temp = 0;
			for (String to : search.keySet()) {
				if (search.get(to) > temp) {
					temp = search.get(to);
					class1 = to;
				}
			}
		}
		// Both 1 and 3 are missing
		else if (class1.equals("") && class3.equals("")) {
			Map<String, Float> search = two_to_one.get(class2);
			float temp = 0;
			for (String to : search.keySet()) {
				if (search.get(to) > temp) {
					temp = search.get(to);
					class1 = to;
				}
			}
			search = two_to_three.get(class2);
			temp = 0;
			for (String to : search.keySet()) {
				if (search.get(to) > temp) {
					temp = search.get(to);
					class3 = to;
				}
			}
		}
		// Both 2 and 3 are missing
		else if (class2.equals("") && class3.equals("")) {
			Map<String, Float> search = one_to_two.get(class1);
			float temp = 0;
			for (String to : search.keySet()) {
				if (search.get(to) > temp) {
					temp = search.get(to);
					class2 = to;
				}
			}
			search = one_to_three.get(class1);
			temp = 0;
			for (String to : search.keySet()) {
				if (search.get(to) > temp) {
					temp = search.get(to);
					class3 = to;
				}
			}
		}

		// Only missing class 1
		if (class1.equals("")) {
			Map<String, Float> search = three_to_one.get(class3);
			float temp = 0;
			for (String to : search.keySet()) {
				if (search.get(to) > temp) {
					temp = search.get(to);
					class1 = to;
				}
			}
		}
		// Only missing class 2
		else if (class2.equals("")) {
			Map<String, Float> search = three_to_two.get(class3);
			float temp = 0;
			for (String to : search.keySet()) {
				if (search.get(to) > temp) {
					temp = search.get(to);
					class2 = to;
				}
			}
		}
		// only missing class 3
		else if (class3.equals("")) {
			Map<String, Float> search = one_to_three.get(class1);
			float temp1 = 0;
			String temp_one = "";
			for (String to : search.keySet()) {
				if (search.get(to) > temp1) {
					temp1 = search.get(to);
					temp_one = to;
				}
			}

			search = two_to_three.get(class2);
			float temp2 = 0;
			String temp_two = "";
			for (String to : search.keySet()) {
				if (search.get(to) > temp2) {
					temp2 = search.get(to);
					temp_two = to;
				}
			}

			if (temp1 > temp2)
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

	/**
	 * This method will build the tree and return the root
	 * 
	 * @return
	 * @throws AuthenticationException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ServiceException
	 */
	public static TreeNode build_tree() throws AuthenticationException,
			MalformedURLException, IOException, ServiceException {

		// For Graph_build:
		ArrayList<String> classlist_1 = new ArrayList<String>();
		ArrayList<String> classlist_2 = new ArrayList<String>();
		ArrayList<String> classlist_3 = new ArrayList<String>();

		System.out.print("Username: ");
		Scanner scan = new Scanner(System.in);
		String USERNAME = scan.nextLine();
		System.out.print("Password: ");
		String PASSWORD = scan.nextLine();
		// String USERNAME = "";
		// String PASSWORD = "";
		SpreadsheetService service = new SpreadsheetService(
				"MySpreadsheetIntegration-v1");
		service.setUserCredentials(USERNAME, PASSWORD);
		service.setProtocolVersion(SpreadsheetService.Versions.V3);

		// This part need to be changed in order to scan a different spreadsheet
		URL SPREADSHEET_FEED_URL = new URL(
				"https://spreadsheets.google.com/feeds/spreadsheets/private/full");

		/**
		 * Make a request to the API and get all spreadsheets.
		 */
		SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL,
				SpreadsheetFeed.class);
		List<SpreadsheetEntry> spreadsheets = feed.getEntries();

		if (spreadsheets.size() == 0) {
			System.out.println("No spreadsheet affiliated to this account.");
			return null;
		}

		/**
		 * Iterate through all of the spreadsheets returned to search for
		 * spreadsheet
		 */
		SpreadsheetEntry circe_spreadsheet = null;
		for (SpreadsheetEntry spreadsheet : spreadsheets) {
			// Substitute the file title here
			if (spreadsheet.getTitle().getPlainText().equals("Circe Revised")) {
				circe_spreadsheet = spreadsheet;
				break;
			}
		}

		/**
		 * Check if corresponding spreadsheet exists
		 */
		if (circe_spreadsheet == null) {
			System.out.println("Error: No corresponding spreadsheet");
			return null;
		}

		/**
		 * Choose the right worksheet within the spreadsheet (would depend
		 * actual situation)
		 */
		List<WorksheetEntry> worksheets = circe_spreadsheet.getWorksheets();
		WorksheetEntry circe_worksheet = worksheets.get(0);

		/**
		 * Fetch Cell feeds, form row data and put row data into the tree All
		 * empty blocks need to be filled with "null" or some string
		 */
		CellFeed circe_cellFeed = service.getFeed(
				circe_worksheet.getCellFeedUrl(), CellFeed.class);
		TreeNode root = new StartTreeNode("Circe");
		for (Iterator<CellEntry> iter = circe_cellFeed.getEntries().iterator(); iter
				.hasNext();) {
			CellEntry cell = iter.next();
			String[] locate = cell.getId()
					.substring(cell.getId().lastIndexOf('/') + 1).substring(1)
					.split("C");
			int rowNum = Integer.parseInt(locate[0]) - 1;
			int colNum = Integer.parseInt(locate[1]) - 1;
			if (rowNum < 75 && rowNum > 0) {
				if (colNum == 0) {
					// Construct rowdata
					String labNum, tabNum;
					ArrayList<String> classList = new ArrayList<String>(3);
					ArrayList<String> responseList = new ArrayList<String>(3);
					labNum = cell.getCell().getValue();
					tabNum = iter.next().getCell().getValue();

					String c1 = iter.next().getCell().getValue();
					String c2 = iter.next().getCell().getValue();
					String c3 = iter.next().getCell().getValue();

					// store all classification 1,2,3 into three arraylists
					classlist_1.add(c1);
					classlist_2.add(c2);
					classlist_3.add(c3);

					// Continue constructing rowdata
					classList.add(c1);
					classList.add(c2);
					classList.add(c3);
					responseList.add(iter.next().getCell().getValue());
					responseList.add(iter.next().getCell().getValue());
					responseList.add(iter.next().getCell().getValue());
					classList.trimToSize();
					responseList.trimToSize();
					RowData theRow = new RowData(labNum, tabNum, classList,
							responseList);
					// System.out.println(theRow.toString());
					root.insert(theRow);
				}
			}
		}
		/**
		 * Print Tree recursively
		 */
		root.printTree();

		/*
		 * //Code for storing spreadsheet in local 2D-array //We use cell-based
		 * feeds and R1C1 notation to fetch data in the spreadsheet String[][]
		 * form = new String[79][9]; for (CellEntry cell :
		 * circe_cellFeed.getEntries()) { String[] locate =
		 * cell.getId().substring(cell.getId().lastIndexOf('/') +
		 * 1).substring(1).split("C"); int rowNum =
		 * Integer.parseInt(locate[0])-1; int colNum =
		 * Integer.parseInt(locate[1])-1; if (colNum>2&&colNum<6){
		 * 
		 * } if (rowNum<76&&colNum<9){ form[rowNum][colNum] =
		 * cell.getCell().getValue(); } } for(int i = 1; i < 76; i++){
		 * ArrayList<String> classList = new ArrayList<String>();
		 * classList.add(form[i][3]); classList.add(form[i][4]);
		 * classList.add(form[i][5]); ArrayList<String> responseList = new
		 * ArrayList<String>(); responseList.add(form[i][6]);
		 * responseList.add(form[i][7]); responseList.add(form[i][8]); RowData
		 * theRow = new RowData(form[i][0],form[i][1],classList,responseList);
		 * exampleStart.insert(theRow); }
		 */

		// Build the six maps
		one_to_two = Graph_Build.build(classlist_1, classlist_2);
		one_to_three = Graph_Build.build(classlist_1, classlist_3);
		two_to_one = Graph_Build.build(classlist_2, classlist_1);
		two_to_three = Graph_Build.build(classlist_2, classlist_3);
		three_to_one = Graph_Build.build(classlist_3, classlist_1);
		three_to_two = Graph_Build.build(classlist_3, classlist_2);

		return root;
	}
}