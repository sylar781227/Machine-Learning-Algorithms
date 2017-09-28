public class RandomAss implements IwAssigner
{
    private static final java.util.Random s_RANDOM = new java.util.Random(System.currentTimeMillis());

    public Double assignWeight()
    {
        return -1 + (1 - (-1)) * s_RANDOM.nextDouble();
       
    }
}