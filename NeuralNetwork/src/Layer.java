

import java.util.ArrayList;
import java.util.List;

public class Layer
{
    private List<Neuron> layerNeuron;

    
    public void initialize(Integer numberNeurons, Double learningRate, Double momentum)
    {
        layerNeuron = new ArrayList<>(numberNeurons);
   for (int i = 0; i < numberNeurons; i++)
            layerNeuron.add(new Neuron(learningRate, momentum));
   		layerNeuron.add(new BiasNeuron(learningRate, momentum));
    }

    
    public void initialize(Integer numberNeurons, Layer previousLayer, IwAssigner initialWeightAssigner, boolean isOutputLayer, Double learningRate, Double momentum)
    {
        layerNeuron = new ArrayList<>(numberNeurons);
        List<Neuron> previousNeurons = previousLayer.getNeurons();

        for(int i = 0; i < numberNeurons; i++)
        {
            Neuron currentNeuron = new Neuron(learningRate, momentum);

            
            for(Neuron previousNeuron : previousNeurons)
            {
                Connection connection = new Connection();
                connection.initialize(previousNeuron, currentNeuron, initialWeightAssigner.assignWeight());

                previousNeuron.addConnection(connection);
                currentNeuron.addConnection(connection);
            }

            layerNeuron.add(currentNeuron);
        }

       
        if (!isOutputLayer)
            layerNeuron.add(new BiasNeuron(learningRate, momentum));
    }

    public void calculateOutputValues()
    {
        layerNeuron.forEach(Neuron::calculateOutputValue);
    }

    public boolean setOutputValues(List<Double> outputValues)
    {
        if(outputValues.size() != layerNeuron.size() - 1)
            return false;

        for(int i = 0; i < outputValues.size(); i++)
        {
            Double outputValue = outputValues.get(i);
            Neuron neuron = layerNeuron.get(i);

            neuron.setOutputValue(outputValue);
        }

        return true;
    }

    public Double calculateCost(List<Double> targetValues)
    {
        Double cost = 0.0;

        for(int i = 0; i < targetValues.size(); i++)
        {
            Neuron neuron = layerNeuron.get(i);
            Double targetValue = targetValues.get(i);
            Double outputValue = neuron.getOutputValue();

            Double delta = targetValue - outputValue;
            cost += delta * delta;
        }

        cost /= 2.0;

        return cost;
    }

    public Double calculateError(List<Double> targetValues)
    {
        Double cost = 0.0;

        for(int i = 0; i < targetValues.size(); i++)
        {
            Neuron neuron = layerNeuron.get(i);
            Double targetValue = targetValues.get(i);
            Double outputValue = neuron.getOutputValue();

            Double delta = targetValue - outputValue;
            cost += delta * delta;
        }

        return cost;
    }

    
    public void calculateGradientValues(List<Double> targetValues)
    {
    	int target1=targetValues.size();
    	int target2=layerNeuron.size();
        if (targetValues.size() != layerNeuron.size())
            throw new IllegalArgumentException("Error in gradient calculation in output nodes: number of target values doesn't match the number of nodes in the output layer!");

        for (int i = 0; i < layerNeuron.size(); i++)
        {
            Neuron neuron = layerNeuron.get(i);
            Double targetValue = targetValues.get(i);

            
            neuron.calculateGradientValue(targetValue);
        }
    }

   
    public void calculateGradientValues()
    {
        layerNeuron.forEach(Neuron::calculateGradientValue);
    }

    public void updateConnectionsWeights()
    {
        layerNeuron.forEach(Neuron::updateConnectionsWeights);
    }

    public List<Neuron> getNeurons()
    {
        return layerNeuron;
    }
}