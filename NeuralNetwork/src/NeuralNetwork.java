import java.util.*;

public class NeuralNetwork
{
    private List<Layer> nnLayers;
    private IwAssigner nnInitialWeightAssigner;

    public NeuralNetwork()
    {
        nnInitialWeightAssigner = new RandomAss(); // The default one!
    }

    public void initialize(List<Integer> topology, Double learningRate, Double momentum)
    {
    	nnLayers = new ArrayList<>(topology.size());
        Layer inputLayer = new Layer();
        inputLayer.initialize(topology.get(0), learningRate, momentum);
        nnLayers.add(inputLayer);

        Layer previousLayer = inputLayer;
        for(int i = 1; i < topology.size(); i++)
        {
            int numberNeurons = topology.get(i);
            boolean isOutputLayer = (i == topology.size() - 1);

            Layer layer = new Layer();
            layer.initialize(numberNeurons, previousLayer, nnInitialWeightAssigner, isOutputLayer, learningRate, momentum);
            nnLayers.add(layer);

            previousLayer = layer;
        }
    }

    public void train(DataSet trainData, double desiredError, int maxIterations)
    {
        ErrorReport reportTrain;
        int iteration = 0;

        List<DataInstance> m_shuffledTrainExamples = new ArrayList<>(trainData.getExamples());

        do
        {
            Collections.shuffle(m_shuffledTrainExamples);
            for (DataInstance example : m_shuffledTrainExamples)
            {
                feedForward(example.getAttributes());
                backPropagate(example.getTargetList());
            }

            iteration++;
            
            reportTrain = getClassificationReport(trainData);
            

        } while(reportTrain.getMSE() > desiredError && iteration < maxIterations);
        
        System.out.println("Layer Info************ :"+nnLayers);
        for(int i=0;i<nnLayers.size();i++)
        {
        	if(i==0)
        		continue;
        	else if(i<nnLayers.size()-1)
        	{
        		System.out.println("Hidden Layer " +i+" :" );
        		 for(int j=0;j<nnLayers.get(i).getNeurons().size()-1;j++)
        		 {
        			 System.out.println("Neuron " + (j+1) + " Weights : ");
        			 System.out.println(nnLayers.get(i).getNeurons().get(j).neuronOutputValue);
        		 }
        	        
        		
        	}
        	else
        	{
        		System.out.println("Output Layer :");
        		
        		 for(int j=0;j<nnLayers.get(i).getNeurons().size();j++)
        		 {
        			 System.out.println("Neuron " + (j+1) + " Weights : ");
        			 System.out.println(nnLayers.get(i).getNeurons().get(j).neuronOutputValue);
        		 }
        		
        	}
        }
        
    }

    public ErrorReport test(DataSet data)
    {
        return getClassificationReport(data);
    }

    public ErrorReport getClassificationReport(DataSet data)
    {
        ErrorReport report = new ErrorReport();

        // Check current error:
        Double meanSquaredError = 0.0;
        for (DataInstance example : data.getExamples())
        {
            feedForward(example.getAttributes());
            meanSquaredError += calculateIterationError(example.getTargetList()); // Get the mean of the MSE

            double preictedOutput = getOutputLayer().getNeurons().get(0).getOutputValue();
            //System.out.println("predicted  Output : " + (int)Math.round(preictedOutput));

        }

        meanSquaredError /= (data.getExamples().size());
        report.setMSE(meanSquaredError);

        return report;
    }
    public ErrorReport getClassificationReportTrain(DataSet data)
    {
        ErrorReport report = new ErrorReport();

        // Check current error:
        Double meanSquaredError = 0.0;
        for (DataInstance example : data.getExamples())
        {
            feedForward(example.getAttributes());
            meanSquaredError += calculateIterationError(example.getTargetList()); // Get the mean of the MSE

            double preictedOutput = getOutputLayer().getNeurons().get(0).getOutputValue();
            
        }

        meanSquaredError /= (data.getExamples().size());
        report.setMSE(meanSquaredError);
        System.out.println("Training error : "+meanSquaredError);

        return report;
    }

    public void feedForward(List<Double> inputValues)
    {
        Layer inputLayer = getInputLayer();
       
        for(int i = 1; i < nnLayers.size(); i++)
        {
            Layer layer = nnLayers.get(i);
            layer.calculateOutputValues();
        }
    }

    public void backPropagate(List<Double> targetValues)
    {
        Layer outputLayer = getOutputLayer();
        outputLayer.calculateGradientValues(targetValues);
        for (int i = nnLayers.size() - 2; i > 0; i--)
        {
            Layer hiddenLayer = nnLayers.get(i);
            hiddenLayer.calculateGradientValues();
        }
        for (int i = nnLayers.size() - 2; i >= 0; i--)
        {
            Layer currentLayer = nnLayers.get(i);
            currentLayer.updateConnectionsWeights();
        }

    }

    public double calculateIterationError(List<Double> targetValues)
    {
        return getOutputLayer().calculateError(targetValues);
    }

    public List<Layer> getLayers()
    {
        return nnLayers;
    }

    public Layer getInputLayer()
    {
        return nnLayers.get(0);
    }

    public Layer getOutputLayer()
    {
        return nnLayers.get(nnLayers.size() - 1);
    }

    public void setWeightAssigner(IwAssigner testAssigner)
    {
        nnInitialWeightAssigner = testAssigner;
    }
}