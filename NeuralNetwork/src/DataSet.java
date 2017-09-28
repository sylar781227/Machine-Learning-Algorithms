import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class DataSet
{
    protected List<DataInstance> dataInstance = new ArrayList<>();

    public DataSet()
    {}

    public DataSet(List<DataInstance> examples)
    {
        dataInstance = examples;
    }

    public static DataSet parseTrainFile(String filePath) throws IOException
    {
        DataSet trainData = new DataSet();
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line;
        int i = 0;

        while ((line = br.readLine()) != null)
        {
            DataInstance example = DataInstance.parseTrainExample(line);
            trainData.dataInstance.add(example);
        }

        return trainData;
    }

    public static DataSet parseTestFile(String filePath) throws IOException
    {
        DataSet testData = new DataSet();
        BufferedReader br = new BufferedReader(new FileReader(filePath));

        String line;
        int i = 0, j = 0; 

        while ((line = br.readLine()) != null)
        {
            DataInstance example = DataInstance.parseTestExample(line);
            testData.dataInstance.add(example);

            j++;

            if (j == 3)
            {
                j = 0;
                i++;
                i = i % 2;
            }
        }

        return testData;
    }

   
    public List<DataInstance> filterByType() throws IllegalAccessException, InstantiationException
    {
        List<DataInstance> returnTrainInstance = new ArrayList<>();
        returnTrainInstance.addAll(dataInstance.stream().collect(Collectors.toList()));
        return returnTrainInstance;
    }

    public List<DataInstance> getExamples()
    {
        return dataInstance;
    }

    public int getNumAttributes()
    {
        return dataInstance.get(0).getNumberAttributes();
    }

}