import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class NB {

	protected static LinkedHashMap<String, LinkedHashMap<String, Double>> trainingdata = new LinkedHashMap<String, LinkedHashMap<String,Double>>();
	protected static int matWStopWord[][] = new int[20][20];
	protected static int matNWStopWord[][] = new int[20][20];
	protected static LinkedHashMap<String, LinkedHashMap<String, Double>> trainDataSet = new LinkedHashMap<String, LinkedHashMap<String,Double>>();
	protected static LinkedHashMap<String, LinkedHashMap<String, Double>> condProb = new LinkedHashMap<String, LinkedHashMap<String,Double>>();
	protected static LinkedHashMap<String, LinkedHashMap<String, Double>> testDataSet  = new LinkedHashMap<String, LinkedHashMap<String,Double>>();
	protected static LinkedHashMap<String, LinkedHashMap<String, Double>> classTraining = new LinkedHashMap<String, LinkedHashMap<String,Double>>();
	protected static List<String> testLabel = new ArrayList<String>();
	protected static List<String> trainlabel = new ArrayList<String>();
	protected static List<String> vocabDict = new ArrayList<String>();
	protected static List<Double> trainFreq = new ArrayList<Double>();
	protected static List<Double> trainFreqValue = new ArrayList<Double>();
	protected static List<String> newsgrouplabels = new ArrayList<String>();
	protected static ArrayList<Double> totalValues = new ArrayList<Double>();
	protected static List<String> stopWordList = new ArrayList<String>();
	protected static HashMap<String, Integer> stopWordMap = new HashMap<String, Integer>();
	
	public static LinkedHashMap<String, LinkedHashMap<String, Double>> getTrainingdata() {
		return trainingdata;
	}
	public static void setTrainingdata(LinkedHashMap<String, LinkedHashMap<String, Double>> trainingdata) {
		NB.trainingdata = trainingdata;
	}
	public static LinkedHashMap<String, LinkedHashMap<String, Double>> getTestDataSet() {
		return testDataSet;
	}
	public static void setTestDataSet(LinkedHashMap<String, LinkedHashMap<String, Double>> testingdata) {
		NB.testDataSet = testingdata;
	}
	public static LinkedHashMap<String, LinkedHashMap<String, Double>> getClassTraining() {
		return classTraining;
	}
	public static void setClassTraining(LinkedHashMap<String, LinkedHashMap<String, Double>> trainingclassdata) {
		NB.classTraining = trainingclassdata;
	}
	public static List<String> getTestLabel() {
		return testLabel;
	}
	public static void setTestLabel(List<String> testinglabel) {
		NB.testLabel = testinglabel;
	}
	public static List<String> getTrainlabel() {
		return trainlabel;
	}
	public static void setTrainlabel(List<String> traininglabel) {
		NB.trainlabel = traininglabel;
	}
	public static List<String> getVocabDict() {
		return vocabDict;
	}
	public static void setVocabDict(List<String> vocabList) {
		NB.vocabDict = vocabList;
	}
	public static List<Double> getTrainFreq() {
		return trainFreq;
	}
	public static void setTrainFreq(List<Double> training_frequencey) {
		NB.trainFreq = training_frequencey;
	}
	public static List<Double> getTrainFreqValue() {
		return trainFreqValue;
	}
	public static void setTrainFreqValue(List<Double> training_double_frequence) {
		NB.trainFreqValue = training_double_frequence;
	}
	public static ArrayList<Double> getTotalValues() {
		return totalValues;
	}
	public static void setTotalValues(ArrayList<Double> totalValues) {
		NB.totalValues = totalValues;
	}
	public static List<String> getStopWordList() {
		return stopWordList;
	}
	public static void setStopWordList(List<String> englishstopList) {
		NB.stopWordList = englishstopList;
	}
	public static HashMap<String, Integer> getStopWordMap() {
		return stopWordMap;
	}
	public static void setStopWordMap(HashMap<String, Integer> englishstop_pos) {
		NB.stopWordMap = englishstop_pos;
	}
	public static List<String> getNewsgrouplabels() {
		return newsgrouplabels;
	}
	
	
	
	
}