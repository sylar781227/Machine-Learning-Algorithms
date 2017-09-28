import java.util.*;
import java.io.*;

public class NaiveBayesMain extends NB {
	
	public static void main(String args[]) throws Exception {
		
		 System.out.println("Processsing Data Files.......");
		  testLabel = readDataSet("20newsDataSet/test.label");
		  trainlabel = readDataSet("20newsDataSet/train.label");
		  vocabDict = readDataSet("20newsDataSet/vocabulary.txt");
		  newsgrouplabels = readDataSet("20newsDataSet/newsgrouplabels.txt");
		  trainingdata = mapping("20newsDataSet/train.data", trainlabel);
		  testDataSet = mapping("20newsDataSet/test.data", testLabel);
		  stopWordList = readDataSet("20newsDataSet/english.stop");
		  stopWordMap = stopWordPos(vocabDict, stopWordList);
		  System.out.println("Training on the Data Set.......");
		  trainData(trainingdata, trainlabel, vocabDict);
		  freqCalculation();
		  testData();
		
	}

  public NaiveBayesMain() {
	  
	 
  }
  
  private static List<String> readDataSet(String fileName) {
	  List<String> fileData = new ArrayList<String>();
	  
	  try {
		  BufferedReader input = new BufferedReader(new FileReader(fileName));
		  for(String line = input.readLine(); line != null; line = input.readLine()) {
			  fileData.add(line);
		  }
		  input.close();
		  return fileData;
	  } 
	  catch(Exception e) {
		  e.printStackTrace();
		  return null;
	  } 
  }
  
  
  
  private static void trainData(LinkedHashMap<String, LinkedHashMap<String, Double>> data, List<String> label, List<String> vocabList) {
	  
	  for (int i = 0; i < newsgrouplabels.size(); i++ ) {
		  
		double totalValue = 0.0;
		ArrayList<String> position = new ArrayList<String>();
		trainDataSet.put(Integer.toString(i+1), new LinkedHashMap<String, Double>());
		condProb.put(Integer.toString(i+1), new LinkedHashMap<String, Double>());
		
		for (int j = 0; j < trainlabel.size(); j++) {
			String fileLabel = trainlabel.get(j);		  
			if (fileLabel.equals(Integer.toString(i+1)))
				position.add(Integer.toString(j+1));
		}
		
		trainFreq.add(Double.parseDouble(Integer.toString(position.size())));
		for (int j = 0; j < position.size(); j++ ) {
			
			LinkedHashMap<String, Double> singleDoc = data.get(position.get(j));
			Set<String> oneDocSet = singleDoc.keySet();
			Iterator<String> it = oneDocSet.iterator();
			while (it.hasNext()) {
				String word = it.next().toString();
				totalValue = totalValue + data.get(position.get(j)).get(word);
				boolean containsWord = trainDataSet.get(Integer.toString(i+1)).containsKey(word);  
				double count = data.get(position.get(j)).get(word);
				double existcount = 0;
				if (containsWord)
					existcount = trainDataSet.get(Integer.toString(i+1)).get(word);
				else
					trainDataSet.get(Integer.toString(i+1)).put(word, count);

				trainDataSet.get(Integer.toString(i+1)).put(word, count + existcount);
			  }   
		  }
		  
		  totalValues.add(totalValue);

		  for (int k = 0; k < vocabList.size(); k++) { 
			  boolean containsWordInVocabList = trainDataSet.get(Integer.toString(i+1)).containsKey(Integer.toString(k+1));
			  if (containsWordInVocabList) {
				  double prob = Math.log((trainDataSet.get(Integer.toString(i+1)).get(Integer.toString(k+1)) + 1)/(totalValues.get(i) + vocabList.size()));
				  condProb.get(Integer.toString(i+1)).put(Integer.toString(k+1), prob);
			  }
			  else {
				  double prob = Math.log(1 / (totalValues.get(i) + vocabList.size()));
				  condProb.get(Integer.toString(i+1)).put(Integer.toString(k+1), prob);
			  }
		  }
	  }
  }
  
  public static void testData() {
	  System.out.println("Testing on Data Set.............");
	  double multinomialaccuraywithoutstopwords = testNB(testDataSet, testLabel, trainFreqValue, true);
	  System.out.println("Calculated  accuracy : " + Math.round(multinomialaccuraywithoutstopwords*100)  + "%");
  	}
  
  private static Double testNB(LinkedHashMap<String, LinkedHashMap<String, Double>> data, List<String> label,  List<Double> wordFrequency, boolean flag) {
	  
	  Integer datasize = data.size();
	  double error = 0;
	  
	  for (int i = 0; i < datasize; i++) {
		  ArrayList<Double> classProbabilityList = new ArrayList<Double>();
		  
		  for (int j = 0; j < condProb.size(); j++ ) {
			  LinkedHashMap<String, Double> singledocument = data.get(Integer.toString(i+1));
			  Set<String> singledocumentset = singledocument.keySet();
			  Iterator<String> it = singledocumentset.iterator();
			  double probability = Math.log(wordFrequency.get(j));
			  
			  while (it.hasNext()) {
				  String word = it.next().toString();
				  boolean containsWord = stopWordMap.containsKey(word);
				  if (flag && containsWord) {  
				  }
				  else
					  probability = probability + condProb.get(Integer.toString(j+1)).get(word);
			  }
			  classProbabilityList.add(probability);
		  }
		  
		  Object obj = Collections.max(classProbabilityList);
		  String predicte_class1 = obj.toString();
		  int index = classProbabilityList.indexOf(Double.parseDouble(predicte_class1));
		  String predicte_class = Integer.toString(index + 1);

		  if (!predicte_class.equals(label.get(i))) {
			  error = error + 1;
			  if (flag)
				  matNWStopWord[Integer.parseInt(label.get(i)) - 1 ][Integer.parseInt(predicte_class) - 1] = matNWStopWord[Integer.parseInt(label.get(i))-1][Integer.parseInt(predicte_class)-1] + 1;
		  }
		  else
		  {
			  if (flag)
				  matNWStopWord[Integer.parseInt(label.get(i)) - 1 ][Integer.parseInt(predicte_class) - 1] = matNWStopWord[Integer.parseInt(label.get(i))-1][Integer.parseInt(predicte_class)-1] + 1;
			  
		  }  
	  }
	  return (1-error/label.size());
  }
  
 
  private static LinkedHashMap<String, LinkedHashMap<String, Double>> mapping(String fileName, List<String> label) {
	
	LinkedHashMap<String, LinkedHashMap<String, Double>> linkedHashMap = new LinkedHashMap<String, LinkedHashMap<String,Double>>();
	
	for (int i = 0; i < label.size(); i++)
		linkedHashMap.put(Integer.toString(i+1), new LinkedHashMap<String, Double>());
	
    try {
      
      BufferedReader input = new BufferedReader(new FileReader(fileName));
      for(String line = input.readLine(); line != null; line = input.readLine()) {
    	  String[] stringlist = line.split(" ");
    	  LinkedHashMap<String, Double> innerHashMap;
    	  if (linkedHashMap.containsKey(stringlist[0]))
    		  innerHashMap = linkedHashMap.get(stringlist[0]);
    	  else {
    		  innerHashMap = new LinkedHashMap<String, Double>();
    		  linkedHashMap.put(stringlist[0], innerHashMap);
    	  }
    	  
    	  double count = 0.0;
    	  if (innerHashMap.containsKey(stringlist[1]))
    		  count = innerHashMap.get(stringlist[1]);
    	  innerHashMap.put(stringlist[1], count + Double.parseDouble(stringlist[2]));  
      }
      input.close();
      return linkedHashMap;
      
    } 
    catch(IOException e) {
      e.printStackTrace();
      return null;
    } 
  }
  
  public static HashMap<String, Integer> stopWordPos(List<String> vocabList, List<String> stopword) {
	  
	  HashMap<String, Integer> wordPos = new HashMap<String, Integer>();
	  for (int i = 0; i < vocabList.size(); i++) {
		  for (int j = 0; j < stopword.size(); j++) {
			  if (vocabList.get(i).toString().equals(stopword.get(j).toString()))
				  wordPos.put(Integer.toString(i+1), i );
		  }
	  }
	  
	  return wordPos;
  }
  
  public static void freqCalculation() {
	
    for (int i = 0; i < trainFreq.size(); i++ ) {
    	double frequency = trainFreq.get(i) / Double.parseDouble(Integer.toString(trainlabel.size()));
    	trainFreqValue.add(frequency);
    }
    
    for (int i = 0; i < 20; i++) {
    	for (int j = 0; j < 20; j++ ) {
    		matWStopWord[i][j] = 0;
    		matNWStopWord[i][j] = 0;
    	}
    }
  }
    
  
}