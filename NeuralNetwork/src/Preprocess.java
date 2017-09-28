import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Preprocess {
	
	static String[][] data;
	static Map<String, Integer> map = new HashMap<String, Integer>();
	
	//this method normalizes all of the numeric values in the data set
	public static void normalize(String[][] data){
		//int count = 0;
		
		for(int i = 0; i < data.length; i++){
			if(i>data.length){
				break;
			}
			//System.out.println("on row"+i+" data length"+data.length);
				for(int k = 0; k<data[i].length; k++){
					if(IsNumeric(data[i][k])){
						double mean = getMeanForColumn(data, k);
						double stdDev = getStdDev(data, k);
						//normalize
						double value = Math.abs((Double.parseDouble(data[i][k]) - mean))/stdDev;	
						data[i][k] = Double.toString(value);
					}
				}
		}
	}
	//this method checks to see if a string has a numeric value
	private static boolean IsNumeric(String string) {
		DecimalFormatSymbols currentLocaleSymbols = DecimalFormatSymbols.getInstance();
		char period = currentLocaleSymbols.getDecimalSeparator();
		boolean periodFound = false;
		if(string==null){
			//System.out.println("This is null");
		}
		else{
			if(string.isEmpty()){
				System.out.println("Where did this empty string come from");
			}
			else{

				for (char c : string.toCharArray())
			    {
			        if (!Character.isDigit(c)){
			        		if(c==period&&!periodFound){
			        			periodFound = true;
			        			continue;
			        		}
			        	return false;
			        }
			    }
			    return true;
			    
			}
		}
		
		return false;
			
	}
	//this method returns the mean for a column
	public static double getMeanForColumn(String[][] s, int column){
		double sum=0;
		for(int row = 0; row < s.length; row++){
			if(s[row][column]!=null){
				sum = sum+Double.parseDouble(s[row][column]);
			}
			
		}
		return sum/s.length;
		
	}
	
	public static double getStdDev(String[][] s, int column){
		double mean = getMeanForColumn(s, column);
		int squareSumOfDiff = 0;
		for(int row = 0; row<s.length; row++){
			if(s[row][column]!= null){
				squareSumOfDiff += Math.pow(Double.parseDouble(s[row][column])-mean, 2);
			}
			
		}
		double variance = (1/s.length)*squareSumOfDiff;
		double stdDev = Math.pow(variance, 1/2);
		
		
		return stdDev;
	}
	
	//make map should only add an entry to it if it does not already exist
	public static void makeMap(String[][] s, int column){
		int count = 0;
		for(int row = 0; row < s.length; row++){
			if(!IsNumeric(s[row][column])){
				if(!map.containsKey(s[row][column])){
					map.put(s[row][column], count);
					count++;
				}		
			}	
		}
	}
	
	//this method takes replaced categorical data with a number
	public static void categoryToNumber(String[][] s){
		for(int column = 0; column < s[0].length; column++){
			if(!IsNumeric(s[0][column])){
				makeMap(s, column);
				for(int row = 0; row < s.length; row++){	
					s[row][column]=Integer.toString(map.get(s[row][column]));		
				}
				map.clear();
			}
		}	
	}
	
	public static String[][] removeQuestionMarks(String [][] data){
		List<Integer> rowsToRemove = new ArrayList<Integer>();
		for(int i = 0; i < data.length; i++){
			for(int k = 0; k<data[i].length; k++){
				//if it has a question mark	
				if(data[i][k]!=null){
					if(data[i][k].contains("?")){
						//take note of that row
						if(!rowsToRemove.contains(i)){
							rowsToRemove.add(i);
						}
					}
				}
				
			}
		}
		int counter = 0;
		List<String[]> list = new ArrayList<String[]>(Arrays.asList(data));
		int nextRow;
		for(int j = 0; j < rowsToRemove.size(); j++){
			nextRow = rowsToRemove.get(j)-counter;
			list.remove(nextRow);	
			counter++;
		}
		String[][] array2 = list.toArray(new String[][]{});
		return array2;
		
		
	}
	
	public static String[][] cleanData(String[][] data){
		String[][] cleanData = removeQuestionMarks(data);
		System.out.println("removed question marks");
		//System.out.println(cleanData[0][1]);
		normalize(cleanData);
		System.out.println("normalized data");
		categoryToNumber(cleanData);
		System.out.println("took care of categories");
		return cleanData;
	}
	
	public static void main(String [] args) throws IOException{
		String inputfile = args[0];
		String outputfile = args[1];
		ReadInput r = new ReadInput();
		data = r.processInput(inputfile);
		//data = r.processInput("C:/Users/Kristen/workspace/ANN/src/iris.txt");
		System.out.println("cleaning...");
		String[][] newData = cleanData(data);

		System.out.println("now to put this data into a new file");
		BufferedWriter br = new BufferedWriter(new FileWriter(outputfile));
		//BufferedWriter br = new BufferedWriter(new FileWriter("C:/Users/Kristen/workspace/ANN/src/processedData.csv"));
		StringBuilder sb = new StringBuilder();
		for(String[] row : newData){
			for(String element : row){
				if(element==null){
					break;
				}
				sb.append(element);
				sb.append(",");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(System.getProperty("line.separator"));
			
		}
		// resultString = resultString.replaceAll(",$", "");
		br.write(sb.toString());
		br.close();
		
	}
	
}
