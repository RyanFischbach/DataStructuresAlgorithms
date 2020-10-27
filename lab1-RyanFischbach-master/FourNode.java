public class FourNode {

    //the number of occurences of this Four Node in the file
    private int Frequency = 1;

    //the four-byte String
    private String node;

    //default constructor
    public FourNode(String x)
    {
        assert x.length()==4: "Length is not 4";
        node = x;
    }

    //increment the frequency value
    public void incrementFrequency()
    {
        Frequency++;
    }

    //return the node
    public String getNode()
    {
        return node;
    }

    //return the Frequency
    public int getFrequency()
    {
        return Frequency;
    }




}
