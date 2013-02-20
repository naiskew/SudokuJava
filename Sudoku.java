
import java.util.*;

public class Sudoku 
{

    public static void main(String[] args) 
    {
        Scanner keyBoard = new Scanner(System.in);
        String str="y";
        
        SudokuTable t = new SudokuTable(9);                    
        
        //demo test for valid and invalid sudoku
        //t.test();
        
        while (str.equalsIgnoreCase("y"))
        {      
            System.out.println("Enter level of sudoku");
            System.out.println("1=easy, 2=Medium, 3=Hard");
            System.out.print("Enter 1-3 :");         
            str = keyBoard.nextLine();
            System.out.println();
            t.generate(str);
            System.out.println("\nTotal spot = "+t.getSpotOnBoard());
            //find the solution
            t.findSol();      
            
            if (t.getValidSudoku())
            {
                System.out.println("\nValid 1 solution found");
                t.displaySol();
            }
            else
            {
                System.out.println("\nNot valid, 0 solution or More Than 1 solutions found");                  
            }
            System.out.print("Generate it again Press \"y\" :");
            str = keyBoard.nextLine();
        }//end while    
       
    }
}
