
import java.util.*;

public class Scell 
{
    private int col,row,block;
    private ArrayList<String> candidate = new ArrayList<>();
    private String ans = "";
    private Random randomNum = new Random();
     
    public Scell(int aCol, int aRow)
    {
        col = aCol;
        row = aRow;
        setBlockWithColRow(col,row);
    }
    
    private void setBlockWithColRow(int aCol,int aRow)
    {
  
	if (aCol<=3 && aRow<=3)
	{
		block = 1;
	}
	if (aCol>3 && aCol <=6 && aRow<=3)
	{
		block = 2;
	}
	if (aCol>6 && aRow<=3)
	{
		block = 3;
	}
	if (aCol<=3 && aRow>3 && aRow<=6)
	{
		block = 4;
	}
	if (aCol>3 && aCol <=6 && aRow>3 && aRow<=6)
	{
		block = 5;
	}
	if (aCol>6 && aRow>3 && aRow<=6)
	{
		block = 6;
	}
	if (aCol<=3 && aRow>6)
	{
		block = 7;
	}
	if (aCol>3 && aCol<=6 && aRow>6)
	{
		block = 8;
	}
	if (aCol>6 && aRow>6)
	{
		block = 9;
	}
    }
    
    public int getBlock()
    {
        return block;
    }
    
    public void setAns(String aAns)
    {
        ans = aAns;
    }
    
    public String getAns()
    {
        return ans;
    }
    
    public String displayAnsOrCand()
    {
        if (!ans.equals(""))
        {
            return ans;
        }
        else
        {
            return getCandidate();
        }
        
    }
    public void clearCandidate()
    {
        candidate.clear();
    }
    
    public void removeCandidate(String aCandidate)
    {
        candidate.remove(aCandidate);
    }
    
    public void addCandidate(String aCandidate)
    {
        candidate.add(aCandidate);
    }
    public String getCandidate()
    {
       String str = "";
       for (int i=0; i<candidate.size(); i++)
       {    
           str = str+candidate.get(i);
       }
       return str;
    }
    
    public String getCandAtIndex(int ai)
    {
        String str = candidate.get(ai);
        return str;
    }
    
    public int getCandLength()
    {
        return candidate.size();
    }
    
    public void shuffleCandidate()
    {
        String[] str =new String[this.getCandLength()];
        int rdmIndex;
        
        for (int i = 0; i<str.length; i++)
        {
            rdmIndex = randomNum.nextInt(this.getCandLength());
            String n = getCandAtIndex(rdmIndex);
            removeCandidate(n);
            str[i] = n;
        }
        for (int i = 0; i<str.length; i++)
        {
            this.addCandidate(str[i]);
        }
    }
    
}
