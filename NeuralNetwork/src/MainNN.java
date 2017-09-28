import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MainNN {

    private String trainPath;
    private Double learningRate;
    private Double momentum;
    private Double maxError;
    private Integer maxIterations;

    public MainNN(String trainPath, String learnRate,String error,String iteration)
    {
        this.trainPath = trainPath;
        learningRate=Double.valueOf(learnRate);
        maxError=Double.valueOf(error);
        maxIterations=Integer.valueOf(iteration);
    }

    public void readParameters()
    {
        momentum=0.4; 
    }

    public void run() throws InstantiationException, IllegalAccessException, IOException
    {
        
        DataSet trainData = DataSet.parseTrainFile(trainPath);
        DataSet testData = DataSet.parseTestFile(trainPath);
        int dataSize=trainData.getExamples().size();
        DataSet finalTrainData=new DataSet();
        finalTrainData.dataInstance=trainData.getExamples().subList(0, (int) ((0.8*dataSize)));
        testData.dataInstance=trainData.getExamples().subList( (int) ((0.8*dataSize)),dataSize-2);
        finalTrainData = new DataSet(finalTrainData.filterByType());
        testData = new DataSet(testData.filterByType());
        NeuralNetwork nn = new NeuralNetwork();
        
        // currently set to 2 hidden layer with 2 neuron within each layer : please add 2,2,2 for 3 layers
        // 1 is the number of output layer currently set to 1
        nn.initialize(Arrays.asList(finalTrainData.getNumAttributes(),2,2,1),learningRate,momentum);
        System.out.println("Please be patient its gonna take some time ...");
        nn.train(finalTrainData,maxError,maxIterations);
        System.out.println(nn.test(testData));
        
 }

    public static void main(String[] args)
    {

        try
        {
            MainNN detector = new MainNN(args[0], args[1],args[2],args[3]);
            detector.readParameters();
            detector.run();
        }
        catch(Exception e)
        {
            System.err.println("Oops something went wrong pls check the read me file: " + e.getMessage());
            e.printStackTrace();
        }
    }
	
	
}
