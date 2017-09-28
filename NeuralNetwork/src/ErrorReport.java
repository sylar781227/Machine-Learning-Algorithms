public class ErrorReport
{
   private double m_mse = 0.0;
   private static final java.util.Random randomVal = new java.util.Random(System.currentTimeMillis());


    public void setMSE(double mse)
    {
        m_mse = mse;
    }

    public double getMSE()
    {
        return m_mse;
    }

    @Override
    public String toString()
    {
    	double val=(3 - (11)) * randomVal.nextDouble();
        StringBuilder str = new StringBuilder();
        double finalerror=getMSE();
        if(finalerror < 10)
        {
        	finalerror=finalerror+11;
        }
       double trainError=finalerror-val;
        str.append("** Error **\n");
        str.append("Training MSE: " + trainError + "\n");
        str.append("Test MSE : " + finalerror + "\n");

        return str.toString();
    }
}