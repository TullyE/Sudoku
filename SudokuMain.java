/**
musicMain.java
File to run
Tully Eva
07/29/2021
*/
import javax.swing.*;
import java.awt.Dimension;

public class SudokuMain
{
    /**
     * initiate the Frame
     * and create a HomepageMenu panel
     * @param args
     */
    public static void main(String[] args)
    {
        GameLogic myPanel = new GameLogic();
        JFrame myFrame = new JFrame("SUDOKU");
        myFrame.setPreferredSize(new Dimension(500, 500));
        myFrame.setSize(new Dimension(500, 500));
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setResizable(false);
        myFrame.add(myPanel);
        myFrame.pack();
        myFrame.setLocationRelativeTo(null);

        myFrame.setVisible(true);
    }
}
