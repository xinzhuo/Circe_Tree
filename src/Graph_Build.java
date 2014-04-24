//import java.io.*;
//import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import java.util.Scanner;

/**
 * This class builds probability relationship graph between two ArrayList of
 * String
 * 
 * @author Xinzhuo Dong
 * 
 */

public class Graph_Build {
	/**
	 * Code for building the graph by scanning csv files public static void
	 * main(String[] args) throws FileNotFoundException { Scanner csv_scan = new
	 * Scanner(new FileReader("circe_rep.csv")); ArrayList<String> rep3 = new
	 * ArrayList<String>(); ArrayList<String> rep2 = new ArrayList<String>();
	 * ArrayList<String> rep1 = new ArrayList<String>();
	 * 
	 * while(csv_scan.hasNext()){ String newLine = csv_scan.nextLine(); String[]
	 * splitLine = newLine.split(","); rep3.add(splitLine[0]);
	 * rep2.add(splitLine[1]); rep1.add(splitLine[2]); } build(rep1, rep2);
	 * csv_scan.close(); }
	 **/
	
	/**
	 * Build graph
	 * @param rep_from
	 * @param rep_to
	 * @return HashMap representing probability relationship
	 */
	public static Map<String, Map<String, Float>> build(
			ArrayList<String> rep_from, ArrayList<String> rep_to) {
		Map<String, Map<String, Integer>> storage = new HashMap<String, Map<String, Integer>>();
		Map<String, Map<String, Float>> result = new HashMap<String, Map<String, Float>>();
		for (int i = 0; i < rep_from.size(); i++) {
			String from = rep_from.get(i);
			String to = rep_to.get(i);
			if (!storage.containsKey(from)) {
				Map<String, Integer> sub_storage = new HashMap<String, Integer>();
				sub_storage.put(to, 1);
				storage.put(from, sub_storage);

				Map<String, Float> sub_result = new HashMap<String, Float>();
				result.put(from, sub_result);
			} else {
				Map<String, Integer> sub_storage = storage.get(from);
				if (sub_storage.containsKey(to)) {
					storage.get(from).put(to, sub_storage.get(to) + 1);
				} else {
					storage.get(from).put(to, 1);
				}
			}
		}
		for (String from : storage.keySet()) {
			Map<String, Integer> sub_storage = storage.get(from);
			float sum = 0;
			for (String to : sub_storage.keySet()) {
				sum += sub_storage.get(to);
			}
			for (String to : sub_storage.keySet()) {
				float x = sub_storage.get(to) / sum;
				result.get(from).put(to, x);
			}
		}
		return result;
	}
}
