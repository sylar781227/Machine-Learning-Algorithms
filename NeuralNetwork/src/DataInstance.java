import java.util.ArrayList;
import java.util.List;


public class DataInstance
{
    protected List<Double> m_attributes = new ArrayList<>();
    protected Integer m_class;

    public DataInstance()
    {
    }

    public DataInstance(List<Double> attributes, Integer dataClass)
    {
       
        m_attributes.addAll(attributes);
        m_class = dataClass;
    }

    public static DataInstance parseTrainExample(String line)
    {
        DataInstance example = new DataInstance();
        String[] fields = line.split(",");
    for (int i = 0; i < fields.length; i++)
        {
             if (i<fields.length-1)
                example.m_attributes.add(Double.parseDouble(fields[i]));
            else if (i==fields.length-1)
            {
                int classValue = (int)Double.parseDouble(fields[i]);
                    example.m_class = classValue;
            }
        }
        return example;
    }

    public static DataInstance parseTestExample(String line)
    {
        DataInstance example = new DataInstance();
        String[] fields = line.split(",");
        for (int i = 0; i < fields.length; i++)
       
        {
              if (i<fields.length-1)
                 example.m_attributes.add(Double.parseDouble(fields[i]));
             else if (i==fields.length-1)
             {
                 int classValue = (int)Double.parseDouble(fields[i]);
                     example.m_class = classValue;
             }
         }

        return example;
    }

    public List<Double> getAttributes()
    {
        return m_attributes;
    }

    public int getNumberAttributes()
    {
        return m_attributes.size();
    }

    public Integer getDataClass()
    {
        return m_class;
    }

    public List<Double> getTargetList()
    {
        List<Double> targetList = new ArrayList<>();
            targetList.add(m_class.doubleValue());
        return targetList;
    }

    @Override
    public String toString()
    {
        StringBuilder string = new StringBuilder();

        for (Double attr: m_attributes)
        {
            string.append(attr);
            string.append(",");
        }

        List<Double> targetList = getTargetList();
        string.append(targetList.get(0)).append(",").append(targetList.get(1));

        return string.toString();
    }
}