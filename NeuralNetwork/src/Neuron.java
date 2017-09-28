import java.util.ArrayList;
import java.util.List;

public class Neuron
{
    protected Double neuronLearningRate;
    protected Double neuronMomentum;
    protected Double neuronOutputValue;
    protected Double neuronGradientValue;
    protected List<Connection> neuronNextLayerConnections;
    protected List<Connection> neuronPreviousLayerConnections;

    public Neuron(Double learningRate, Double momentum)
    {
        neuronLearningRate = learningRate;
        neuronMomentum = momentum;
        neuronOutputValue = 0.0;
        neuronGradientValue = 0.0;
        neuronNextLayerConnections = new ArrayList<>();
        neuronPreviousLayerConnections = new ArrayList<>();
    }

    public void addConnection(Connection connection)
    {
        if(connection.getSource() == this)
            neuronNextLayerConnections.add(connection);
        else if(connection.getTarget() == this)
            neuronPreviousLayerConnections.add(connection);
        else
            throw new IllegalArgumentException("Invalid connectiona added");
    }

    public void calculateOutputValue()
    {
        Double sum = 0.0;

        for (Connection previousLayerConnection : neuronPreviousLayerConnections)
        {
            Neuron sourceNeuron = previousLayerConnection.getSource();
            Double outputValue = sourceNeuron.getOutputValue();

            Double weight = previousLayerConnection.getWeight();

            sum += outputValue * weight;
        }

        neuronOutputValue = Neuron.sigmoidFunction(sum);
    }

    public Double getOutputValue()
    {
        return neuronOutputValue;
    }

    public void setOutputValue(Double outputValue)
    {
        neuronOutputValue = outputValue;
    }

    public void calculateGradientValue(Double targetValue)
    {
        neuronGradientValue = (targetValue - neuronOutputValue) * sigmoidDerivativeFunction(neuronOutputValue);
    }

    public void calculateGradientValue()
    {
        Double sum = 0.0;

        for (Connection nextLayerConnection : neuronNextLayerConnections)
        {
            Double targetGradientValue = nextLayerConnection.getTarget().getGradientValue();
            Double connectionWeight = nextLayerConnection.getWeight();
            sum += targetGradientValue * connectionWeight;
        }
        neuronGradientValue = sum * sigmoidDerivativeFunction(neuronOutputValue);
    }

    public Double getGradientValue()
    {
        return neuronGradientValue;
    }

    public void updateConnectionsWeights()
    {
       
        for (Connection nextLayerConnection : neuronNextLayerConnections)
        {
            double targetGradient = nextLayerConnection.getTarget().getGradientValue();
            Double oldWeight = nextLayerConnection.getWeight();
            Double deltaWeight = nextLayerConnection.getDeltaWeight();
            Double newWeight =
                    oldWeight
                    + (neuronLearningRate * neuronOutputValue * targetGradient)
                    + (neuronMomentum * deltaWeight);
            nextLayerConnection.updateWeight(newWeight);
        }
    }

    public List<Connection> getNextLayerConnections()
    {
        return neuronNextLayerConnections;
    }

    public List<Connection> getPreviousLayerConnections()
    {
        return neuronPreviousLayerConnections;
    }

    private static Double sigmoidFunction(Double x)
    {
        return 1 / (1 + Math.pow(Math.E, -x));
    }

    private static Double sigmoidDerivativeFunction(Double outputValue)
    {
        return outputValue * (1 - outputValue);
    }
}