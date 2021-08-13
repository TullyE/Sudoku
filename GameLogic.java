import javax.swing.*;

import java.awt.geom.*;

import java.awt.Color;

import java.awt.*;
import java.awt.event.*;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.HashMap;
import java.awt.Graphics2D;
// import java.awt.image.BufferedImage;
// import java.io.File;
// import java.io.IOException;
// import javax.imageio.ImageIO;
/**
HomepageMenu.java
has the graphics code for the Homepage
Tully Eva
07/29/2021
*/

public class GameLogic extends JPanel implements MouseListener, KeyListener
{
    // private HashMap<Integer, Integer[]> test1 = new HashMap<>();
    // private ArrayList<String> test2 = new ArrayList<>();
    private int positionSelectedRow = -1;
    private int positionSelectedCol = -1;
    private String numbers = "123456789";
    private String[][] boardData = new String[][]{{"5", "3", "", "", "7", "", "", "", ""},
                                          {"6", "", "", "1", "9", "5", "", "", ""},
                                          {"", "9", "8", "", "", "", "", "6", ""},
                                          {"8", "", "", "", "6", "", "", "", "3"},
                                          {"4", "", "", "8", "", "3", "", "", "1"},
                                          {"7", "", "", "", "2", "", "", "", "6"},
                                          {"", "6", "", "", "", "", "2", "8", ""},
                                          {"", "", "", "4", "1", "9", "", "", "5"},
                                          {"", "", "", "", "8", "", "", "7", "9"}};
    // private String[][] boardData1 = new String[][]{{"0", "1", "2", "3", "4", "5", "6", "7", "8"},
    //                                       {"9", "10", "11", "12", "13", "14", "15", "16", "17"},
    //                                       {"18", "19", "20", "21", "22", "23", "24", "25", "26"},
    //                                       {"27", "28", "29", "30", "31", "32", "33", "34", "35"},
    //                                       {"36", "37", "38", "39", "40", "41", "42", "43", "44"},
    //                                       {"45", "46", "47", "48", "49", "50", "51", "52", "53"},
    //                                       {"54", "55", "56", "57", "58", "59", "60", "61", "62"},
    //                                       {"63", "64", "65", "66", "67", "68", "69", "70", "71"},
    //                                       {"72", "73", "74", "75", "76", "77", "78", "79", "80"}};

    private Square[][] boardPicture = new Square[9][9];
    /**
     * Default constructor for HomepageMenu
     * create/setup all the components and add them to the screen
     */
    public GameLogic()
    {
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);

        initiateBoard();

    }

    /**
     * set the background aswell as the text for the slider
     * @param g the Graphics object to protect
     */
    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(255, 255, 255));
        Rectangle2D.Double background = new Rectangle2D.Double(0, 0, 500, 500);
        g2.draw(background);
        g2.fill(background);

        drawBoard(g2);

    }

    public void drawBoard(Graphics2D g2)
    {
        for(int row = 1; row < boardData.length + 1; row += 1)
        {
            for(int col = 1; col < boardData[0].length + 1; col += 1)
            {
                boardPicture[row-1][col-1].draw(g2);
            }
        }
    }

    public void initiateBoard()
    {
        int SquareSize = 50;
        int x = 25;
        int y = 11;

        for(int row = 1; row < boardData.length + 1; row += 1)
        {
            for(int col = 1; col < boardData[0].length + 1; col += 1)
            {
                boardPicture[row-1][col-1] = new Square(SquareSize, x, y, boardData[row-1][col-1]);
                x += SquareSize;
                if (col % 3 == 0)
                {
                    x += 2;
                    //y += 1;
                }
                if(col % 9 == 0)
                {
                    y += SquareSize;
                    x = 25;
                }
                if (row % 3 == 0 && col == 9)
                {
                    y += 2;
                }
            }
        }
    }

    public boolean checkSpot(int positionx, int positiony)
    {
        boolean returnValue = true;
        String currentNum = boardPicture[positiony][positionx].getNumber();
        if(currentNum.equals(""))
        {
            return true;
        }
        for(int y = 0; y < 9; y +=1) //x changes so moving accross a row
        {
            if(boardPicture[y][positionx].getNumber().equals(currentNum) && positiony != y)
            {
                boardPicture[y][positionx].setSelected(true);
                System.out.println("false");
                returnValue = false;
            }
            continue;
        }

        for(int x = 0; x < 9; x +=1) //x changes so moving accross a row
        {
            if(boardPicture[positiony][x].getNumber().equals(currentNum) && positionx != x)
            {
                boardPicture[positiony][x].setSelected(true);
                System.out.println("false");
                returnValue = false;
            }
            continue;
        }

        int topx = -1;
        int topy = -1;
        if(positionx >= 6)
        {
            topx = 6;
        }
        else if(positionx >= 3)
        {
            topx = 3;
        }
        else if(positionx >=0)
        {
            topx = 0;
        }

        if(positiony >= 6)
        {
            topy = 6;
        }
        else if(positiony >= 3)
        {
            topy = 3;
        }
        else if(positiony >=0)
        {
            topy = 0;
        }

        // System.out.println(topx + "" + topy);
        for(int x = topx; x < topx + 3; x += 1)
        {
            for(int y = topy; y < topy + 3; y += 1)
            {
                
                if(boardPicture[y][x].getNumber().equals(currentNum) && positionx != x && positiony != y)
                {
                    boardPicture[y][x].setSelected(true);
                    System.out.println("false");
                    returnValue = false;
                }
                continue;
            }
        }
        return returnValue;
    }
    public void clearAllSelections()
    {
        for(int row = 1; row < boardData.length + 1; row += 1)
        {
            for(int col = 1; col < boardData[0].length + 1; col += 1)
            {
                boardPicture[row-1][col-1].setSelected(false);
            }
        }
    }

    public void keyTyped(KeyEvent e)
    {
        
    }

    public void keyPressed(KeyEvent e) 
    {
        if(positionSelectedCol != -1 && positionSelectedRow != -1)
        {
            if(numbers.indexOf(KeyEvent.getKeyText(e.getKeyCode())) != -1)
            {
                clearAllSelections();
                boardPicture[positionSelectedRow][positionSelectedCol].setSelected(true);
                boardPicture[positionSelectedRow][positionSelectedCol].setNumber(KeyEvent.getKeyText(e.getKeyCode()));
                repaint();
            }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        checkSpot(positionSelectedCol, positionSelectedRow);
        repaint();
        
    }

    public void mouseClicked(MouseEvent e)
    {

    }

    public void mousePressed(MouseEvent e)
    {
        for(int row = 1; row < boardData.length + 1; row += 1)
        {
            for(int col = 1; col < boardData[0].length + 1; col += 1)
            {
                if(boardPicture[row-1][col-1].isHit(e.getX(), e.getY()))
                {
                    clearAllSelections();
                    positionSelectedRow = row-1;
                    positionSelectedCol = col-1;
                    boardPicture[row-1][col-1].setSelected(true);
                    repaint();
                    break;
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e)
    {

    }

    public void mouseEntered(MouseEvent e)
    {

    }

    public void mouseExited(MouseEvent e)
    {
  
   }
}