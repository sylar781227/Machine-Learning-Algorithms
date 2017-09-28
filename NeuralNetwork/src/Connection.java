public class Connection
{
	private Double connWeight;
	private Double connLastWeight;
	private Double connDeltaWeight;
    private Neuron connSource;
    private Neuron connTarget;

   

    public void initialize(Neuron source, Neuron target, Double weight)
    {
        connSource = source;
        connTarget = target;
        connWeight = weight;
        connLastWeight = null;
        connDeltaWeight = 0.0;
    }

    public Neuron getSource()
    {
        return connSource;
    }

    public Neuron getTarget()
    {
        return connTarget;
    }

    public Double getWeight()
    {
        return connWeight;
    }

    public Double getDeltaWeight()
    {
        return connDeltaWeight;
    }

    public void setWeight(Double weight)
    {
        connWeight = weight;
    }

    public void updateWeight(Double weight)
    {
        connLastWeight = connWeight;
        connWeight = weight;
        if (connLastWeight != null)
            connDeltaWeight = connWeight - connLastWeight;
    }
}