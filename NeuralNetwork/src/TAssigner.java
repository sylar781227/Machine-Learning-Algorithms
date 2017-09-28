import java.util.List;

public class TAssigner implements IwAssigner
{
    List<Double> m_weights;
    int index = 0;

    public TAssigner(List<Double> weights)
    {
        m_weights = weights;
    }

    public Double assignWeight()
    {
        Double value = m_weights.get(index);
        index++;
        return value;
    }
}