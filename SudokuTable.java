
import java.util.*;

public class SudokuTable 
{
    private int tSize =9;
    private int countSol=0;
    private int[] spotLvl = {0,50,55,60};
    private int spotOnBoard;
    private boolean stopGen = false;
    private Scell cell[][];
    private boolean validSudoku = false;
    private Random randomNum = new Random();
    private String backupAns[][];
    private String solver[][];
    
    public SudokuTable(int aTSize)
    {
        tSize = aTSize;
        cell = new Scell[tSize+1][tSize+1];
        backupAns = new String[tSize+1][tSize+1];
        solver = new String[tSize+1][tSize+1];
        
        //create cell
        for (int row=1; row<=tSize; row++)
        {
             for (int col=1; col<=tSize; col++)
             {
                 cell[col][row] = new Scell(col,row);
             }
        }
       
    }
    
    //method for testing
    public void test()
    {
        System.out.println("Test on valid sudoku(1 solution)");
        this.clearAns();
        this.setInitial(1);
        this.displayAns();
        this.findSol();
        if (this.getValidSudoku())
        {
            System.out.println("\nValid 1 solution found");
            this.displaySol();
        }
        else
        {
            System.out.println("\nNot valid, 0 solution or More Than 1 solutions found");                  
        }
        
        System.out.println("\nTest on invalid sudoku(3 solutions)");
        this.clearAns();
        this.setInitial(2);
        this.displayAns();
        this.findSol();
        if (this.getValidSudoku())
        {
            System.out.println("\nValid 1 solution found");
            this.displaySol();
        }
        else
        {
            System.out.println("\nNot valid, 0 solution or More Than 1 solutions found");                  
        }
        
        System.out.println("\nTest on invalid sudoku(0 solution)");
        this.clearAns();
        this.setInitial(3);
        this.displayAns();
        this.findSol();
        if (this.getValidSudoku())
        {
            System.out.println("\nValid 1 solution found");
            this.displaySol();
        }
        else
        {
            System.out.println("\nNot valid, 0 solution or More Than 1 solutions found");                  
        }
        
        
        System.out.println();
    }//end test
    
    //method for testing
    public void setInitial(int pattern)
    {
        if (pattern == 1)
        {
            //valid sudoku 1 solution 
            cell[1][1].setAns("4");
            cell[3][2].setAns("9");
            cell[4][1].setAns("7");
            cell[4][3].setAns("9");
            cell[7][1].setAns("6");
            cell[8][1].setAns("1");
            cell[9][1].setAns("9");
            cell[9][3].setAns("2");
        
            cell[2][5].setAns("6");
            cell[1][6].setAns("5");
            cell[2][6].setAns("2");
            cell[4][4].setAns("8");
            cell[5][5].setAns("3");
            cell[6][6].setAns("4");
            cell[8][4].setAns("6");
            cell[9][4].setAns("4");
            cell[8][5].setAns("8");
        
            cell[1][7].setAns("1");
            cell[1][9].setAns("9");
            cell[2][9].setAns("4");
            cell[3][9].setAns("7");
            cell[6][7].setAns("5");
            cell[6][9].setAns("3");
            cell[7][8].setAns("3");
            cell[9][9].setAns("8");
        }
        else
        {
            if (pattern == 2)
            {
            //invalid sudoku 3 solution 
            cell[1][1].setAns("4");
            cell[3][2].setAns("9");
            cell[4][1].setAns("7");
            cell[4][3].setAns("9");
            cell[7][1].setAns("6");
            cell[8][1].setAns("1");
            cell[9][3].setAns("2");
        
            cell[2][5].setAns("6");
            cell[1][6].setAns("5");
            cell[2][6].setAns("2");
            cell[4][4].setAns("8");
            cell[5][5].setAns("3");
            cell[6][6].setAns("4");
            cell[8][4].setAns("6");
            cell[9][4].setAns("4");
            cell[8][5].setAns("8");
        
            cell[1][7].setAns("1");
            cell[1][9].setAns("9");
            cell[2][9].setAns("4");
            cell[3][9].setAns("7");
            cell[6][7].setAns("5");
            cell[6][9].setAns("3");
            cell[7][7].setAns("3");
            cell[9][9].setAns("8");
            }
            else
            {          
            //invalid sudoku 0 solution
            cell[1][1].setAns("4");
            cell[3][2].setAns("9");
            cell[4][1].setAns("7");
            cell[4][3].setAns("9");
            cell[7][1].setAns("6");
            cell[8][1].setAns("1");
            cell[9][1].setAns("9");
            cell[9][3].setAns("2");
        
            cell[2][5].setAns("6");
            cell[1][6].setAns("5");
            cell[2][6].setAns("2");
            cell[4][4].setAns("8");
            cell[5][5].setAns("3");
            cell[6][5].setAns("2");
            cell[6][6].setAns("4");
            cell[7][4].setAns("2");
            cell[8][4].setAns("6");
            cell[9][4].setAns("4");
            cell[8][5].setAns("8");
        
            cell[1][7].setAns("1");
            cell[1][9].setAns("9");
            cell[2][9].setAns("4");
            cell[3][9].setAns("7");
            cell[6][7].setAns("5");
            cell[6][9].setAns("3");
            cell[7][7].setAns("3");
            cell[8][9].setAns("2");
            cell[9][9].setAns("8");
            }
        
        }
    }//end setInitial
      
    //method generate : find all possible candidate in each cell
    //and shuffle candidate to make a random answer solution
    public void generate(String aL)
    {      
        this.clearAns();
        this.findCandidate();
        this.shuffleCandidate();
        stopGen = false;
        this.generateAns(1,1);
        this.generateSpot(aL);
        this.displayAns();
    }//end generate
    
    public void generateAns(int aCol, int aRow)
    {
        int col,row;
        int num,count;
        col = aCol;
        row = aRow;
        
        if (row <= tSize)
        {
            count = 0;
            //2nd method to shuffle
            //while (cell[col][row].getCandLength()>0) 
            while (count < tSize)
            {
                //2nd method to shuffle
                //int n = randomNum.nextInt(cell[col][row].getCandLength());
                //num = Integer.parseInt(cell[col][row].getCandAtIndex(n));
                num = Integer.parseInt(cell[col][row].getCandAtIndex(count));
               
                if (isOk(num,col,row))
                {
                    cell[col][row].setAns(Integer.toString(num));
                    
                    if (col <tSize)
                    {                       
                            generateAns(col+1,row);                                            
                    }
                    else
                    {                      
                            generateAns(1,row+1);                                                           
                    }
                   
                    if (stopGen)
                    {
                        return;
                    }
                }//end if isOk
                //2nd method to shuffle
                //cell[col][row].removeCandidate(Integer.toString(num));
                count = count +1;
                
            }//end while
             cell[col][row].setAns("");
             //2nd method to shuffle
             /*
             for (int i =1; i<=9; i++)
             {
                 cell[col][row].addCandidate(Integer.toString(i));
             }
             */ 
                                     
        }// if row>tSize = found solution
        else
        {
            //System.out.println("Here is Random Answer ");
            //displayAnsOrCand();
            //System.out.println();
            stopGen = true;         
        }   
    }//end generateAns
    
    public void generateSpot(String alvl)
    {
        int col,row;        
        int n;
        int spot,lvl;
        ArrayList<Integer> position = new ArrayList<>();
        
        if (alvl.equals("1"))
        {
            lvl = 1;
        }
        else
        {
            if(alvl.equals("2"))
            {
                lvl = 2;
            }
            else
            {
                lvl = 3;
            }
        }
        
        spot = spotLvl[lvl];
        
        for (row =1;row <=tSize;row++)
        {
            for(col =1; col<=tSize; col++)
            {
                String str = cell[col][row].getAns();
                backupAns[col][row] = str;
                
                n = col*10+row;
                position.add(n);
            }
        }
        
        col = 1;
        row = 1;
         
        while(position.size()>0 && spot>0)
        {
            n = randomNum.nextInt(position.size());
            col = position.get(n) /10;
            row = position.get(n) -(col*10);
            //System.out.println(col+" "+row+" size="+position.size()+" spot="+spot);
            cell[col][row].setAns("");
            //cell[10-col][10-row].setAns(""); //to make it symmetry
            spot = spot-1;
            position.remove(n);
            findSol();
            if (countSol !=1)
            {
                spot = spot+1;
                cell[col][row].setAns(backupAns[col][row]);
                //cell[10-col][10-row].setAns(backupAns[10-col][10-row]); //to make it symmetry
            }
    
        }//end while position ans spot check >0
        
        this.setSpotOnBoard(spotLvl[lvl]-spot);  
        
    }//end generateSpot
          
    //method to find a sudoku sulution 
    public void findSol()
    {
        validSudoku = true;
        countSol =0;
        recursiveSol(1,1);
        if (countSol==0)
        {
            validSudoku = false;
        }
        //if (validSudoku)
        //{
        //    System.out.println("\nValid 1 solution found");
        //    this.displaySol();
        //}
        //else
        //{
        //    System.out.println("\nNot valid, 0 solution or More Than 1 solutions found");                  
        //}
    }//end findSol
    
    //this recursive method stop when more than 1 solution found 
    public void recursiveSol(int aCol, int aRow)
    {
        int col,row;
        col = aCol;
        row = aRow;
       
        if (row <=tSize)
        {
            if(cell[col][row].getAns().equals(""))
            { 
                for (int num =1; num<=9; num++)
                {    
                     if (isOk(num,col,row))
                     {
                         
                         cell[col][row].setAns(Integer.toString(num));
                         if (col<tSize)
                         {                           
                             recursiveSol(col+1,row);                                                          
                         }
                         else
                         {                            
                             recursiveSol(1,row+1);                                                          
                         }                        
                         cell[col][row].setAns("");
                         if (!validSudoku)
                         {
                            return;                          
                         }
                     }//end if isok                       
                }//end for num
            }//end if this cell no answer yet
            else
            {
                if (col<tSize)
                {                   
                    recursiveSol(col+1,row); 
                                                  
                }
                else
                {                    
                    recursiveSol(1,row+1);                                   
                }                               
                
            }//end else-if this cell no answer yet
        }//end if row>tSize = found solution
        else
        {
            countSol = countSol+1;       
            if (countSol==1)
            {
                validSudoku = true;
                for (row =1;row <=tSize;row++)
                {
                    for(col =1; col<=tSize; col++)
                    {
                        String str = cell[col][row].getAns();
                        solver[col][row] = str;                            
                    }
                }
                //System.out.println("Solution "+countAns);   
                //displayAnsOrCand();
                //System.out.println();
                //System.out.println();
            }
            else
            {
                //System.out.println("More than 1Solution ");
                validSudoku = false;
            }
        }//end else-if row>tSize = found solution 
    }//end recursiveSol
    
    public boolean getValidSudoku()
    {
        return validSudoku;
    }
           
    //method to check isOk to place a number on this cell
    public boolean isOk(int aNum, int aCol, int aRow)
    {
        if (!(findNuminRow(aNum,aRow)))
        {
             if (!(findNuminCol(aNum,aCol)))
             {
                  if (!(findNuminBlock(aNum,aCol,aRow)))
                  {
                      return true;
                  }
                  else
                  {
                      return false;
                  }
             }
             else
             {
                 return false;
             }  
        }
        else
        {
            return false;
        }
    }// end isOk to place numberon this cell;
    
    public boolean findNuminBlock(int aNum,int aCol, int aRow)
    {
        String num;
        int row, col;
        int block = cell[aCol][aRow].getBlock() ;
        
        num =  Integer.toString(aNum);
        row = 1;
        
        while (row<=tSize)
        {
            col = 1;
            while (col<=tSize)
            {
               if (cell[col][row].getBlock() == block)
               {                  
                   if (cell[col][row].getAns().equals(num))
                   {
                       return true;
                   }
               }             
                col = col+1;
            }           
            row = row+1;
        }
        return false;
    }//end findNuminBlock;
    
     public boolean findNuminCol(int aNum,int aCol)
    {
        String num;
        int row, col;
        col = aCol;
        
        num =  Integer.toString(aNum);
        row = 1;
        
        while (row<=tSize)
        {                  
                if (cell[col][row].getAns().equals(num))
                {
                    return true;
                }              
            row = row+1;
        }
        return false;
    }//end findNuminCol
    
    public boolean findNuminRow(int aNum,int aRow)
    {
        String num;
        int row, col;
        
        row = aRow;
        num =  Integer.toString(aNum);
        col = 1;
        
        while (col<=tSize)
        {                      
                if (cell[col][row].getAns().equals(num))
                {
                    return true;
                }              
            col = col+1;
        }
        return false;
    }//end findNuminRow
    
    public void clearAns()
    {
        for (int row=1; row<=tSize; row++)
        {   
            for (int col=1; col<=tSize; col++)
            {
                cell[col][row].setAns("");  
            }
        }       
    }//end clearAns
    
    public void displaySol()
    {
        for (int row=1; row<=tSize; row++)
        {   
            for (int col=1; col<=tSize; col++)
            {
                cell[col][row].setAns(solver[col][row]);  
            }
        }        
        displayAns();
    }
    
    public void displayAns()
    {
        for (int row=1; row<=tSize; row++)
        {
             for (int col=1; col<=tSize; col++)
             {
                 if (cell[col][row].getAns().equals(""))
                 {
                     System.out.print("-");
                 }
                 else
                 {
                    System.out.print(cell[col][row].getAns());
                 }
                 if (col == 3 || col == 6)
                 {
                     System.out.print("  ");
                 }
                 if (col == 9)
                 {
                     System.out.println();
                 }
                 if (col == 9 && (row == 3 || row == 6))
                 {
                      System.out.println();
                 }
             }
        }
    }//end displayAns
    
    public void displayAnsOrCand()
    {
        for (int row=1; row<=tSize; row++)
        {
             for (int col=1; col<=tSize; col++)
             {
                 System.out.printf("%9s ",cell[col][row].displayAnsOrCand());
                 if (col == 3 || col == 6)
                 {
                     System.out.print("  ");
                 }
                 if (col==9)
                 {
                     System.out.println();
                 }
                 if (col ==9 && (row == 3 || row == 6))
                 {
                      System.out.println();
                 }
             }
        }
    }//end displayAnsOrCand
    
    public void findCandidate()
    {
        for (int row=1; row<=tSize; row++)
        {   
            for (int col=1; col<=tSize; col++)
            {
                if (cell[col][row].getAns().equals(""))
                {
                    cell[col][row].clearCandidate();
                    for (int n=1; n<=9;n++)
                    {
                        if (isOk(n,col,row))
                        {
                            cell[col][row].addCandidate(Integer.toString(n));
                        }
                    }                 
                }              
            }
        }
    }//edn findCandidate
    
    public void shuffleCandidate()
    {
        for (int row=1; row<=tSize; row++)
        {
            for (int col=1; col<=tSize; col++)
             {
                 cell[col][row].shuffleCandidate();
             }
        } 
    }//end shuffleCandidate
    
    public void clearCandidate()
    {
        for (int row=1; row<=tSize; row++)
        {   
            for (int col=1; col<=tSize; col++)
            {
                cell[col][row].clearCandidate();  
            }
        }       
    }//end clearCandidate
    public void setSpotOnBoard(int aSpot)
    {
        spotOnBoard = aSpot;
    }
    
    public int getSpotOnBoard()
    {
        return spotOnBoard;
    }
    
}
