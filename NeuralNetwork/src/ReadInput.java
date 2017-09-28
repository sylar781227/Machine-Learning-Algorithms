import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class ReadInput {

String[][] processInput(String pathDataSet)
{
	
	System.out.println(pathDataSet);
	String[][] dataSet;
	Integer rowCount=0;
	Integer colCount=0;
	Integer dataSize=recordCounter(pathDataSet);
	Integer dataWidth=dataWidthCounter(pathDataSet);
	dataSet=new String[dataSize][dataWidth];
	
	try {
		Scanner sc = new Scanner(new File(pathDataSet));
		//sc.nextLine();
		int Count =0;
		if(sc.nextLine().contains(",")){
			while (sc.hasNextLine()&&Count<32562){
				if (sc.hasNext())
				{
					String[] lineData=sc.nextLine().split(",");
					// converting the string array to an Integer array
					for(colCount=0;colCount<lineData.length;colCount++)
					{
						dataSet[rowCount][colCount]=lineData[colCount];
					}
					rowCount++;
				}
				Count++;
			}
		}
		else{
			while (sc.hasNextLine()&&Count<32562){
				if (sc.hasNext())
				{
					String[] lineData=sc.nextLine().trim().split("\\s+");
					// converting the string array to an Integer array
					for(colCount=0;colCount<lineData.length;colCount++)
					{
						dataSet[rowCount][colCount]=lineData[colCount];
					}
					rowCount++;
				}
				Count++;
			}
		}

//		System.out.println(Arrays.toString(dataSet));
		}catch (FileNotFoundException e) {
		
		e.printStackTrace();
	}
	return dataSet;
}

Map<Integer,String> processHeader(String pathDataSet)
{
	String[] labelInfo = null;
	 Map<Integer,String> headerMap= new HashMap<Integer,String>();
	try {
			Scanner sc = new Scanner(new File(pathDataSet));
			String headerInfo=sc.nextLine();
			labelInfo=headerInfo.split(",");
			for(int i=0;i<labelInfo.length;i++)
			{
				headerMap.put(i, labelInfo[i]);
			}
			sc.close();
		} catch (FileNotFoundException e) {
	
				e.printStackTrace();
		}
	
	return headerMap;
	
}

Integer recordCounter(String pathDataSet)
{
	Integer counter=0;
	try {
		Scanner sc = new Scanner(new File(pathDataSet));
		sc.nextLine();
		
		while (sc.hasNextLine())
		{
			counter++;
			sc.nextLine();
		}
		sc.close();
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
	}
	System.out.println("Number of rows in the dataset : " + counter);
	
	return counter;
	
}
Integer dataWidthCounter(String pathDataSet)
{
	String[] labelInfo = null;
	try {
			Scanner sc = new Scanner(new File(pathDataSet));
			String headerInfo=sc.nextLine();
			System.out.println(headerInfo);
			if(headerInfo.contains(",")){
				labelInfo=headerInfo.split(",");
			}
			else{
				labelInfo=headerInfo.trim().split("\\s+");
			}
		} catch (FileNotFoundException e) {
	
				e.printStackTrace();
		}
	System.out.println("Number of columns in the Training set : "+labelInfo.length);
	return labelInfo.length;
	

}

}
