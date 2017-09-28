public class BiasNeuron extends Neuron
{
    public BiasNeuron(Double learningRate, Double momentum)
    {
        super(learningRate, momentum);
        neuronOutputValue = 1.0;
    }

    @Override
    public void calculateOutputValue()
    {
    }
}