import javax.swing.*;

import java.awt.geom.*;
import java.util.stream.IntStream;
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
    private int pauseRow = 0;
    private int pauseCol = 0;
    private boolean pauseBool = false;
    private int iters = 0;
    private int positionSelectedRow = -1;
    private int positionSelectedCol = -1;
    private int gothroughs = 0;
    private String numbers = "123456789";
    private int[][] boardData = new int[][]{
                                            {5, 3, 0, 0, 7, 0, 0, 0, 0},
                                            {6, 0, 0, 1, 9, 5, 0, 0, 0},
                                            {0, 9, 8, 0, 0, 0, 0, 6, 0},
                                            {8, 0, 0, 0, 6, 0, 0, 0, 3},
                                            {4, 0, 0, 8, 0, 3, 0, 0, 1},
                                            {7, 0, 0, 0, 2, 0, 0, 0, 6},
                                            {0, 6, 0, 0, 0, 0, 2, 8, 0},
                                            {0, 0, 0, 4, 1, 9, 0, 0, 5},
                                            {0, 0, 0, 0, 8, 0, 0, 7, 9}
                                            };
    private int[][] boardDataToSolve = new int[][]{
                                            {5, 3, 0, 0, 7, 0, 0, 0, 0},
                                            {6, 0, 0, 1, 9, 5, 0, 0, 0},
                                            {0, 9, 8, 0, 0, 0, 0, 6, 0},
                                            {8, 0, 0, 0, 6, 0, 0, 0, 3},
                                            {4, 0, 0, 8, 0, 3, 0, 0, 1},
                                            {7, 0, 0, 0, 2, 0, 0, 0, 6},
                                            {0, 6, 0, 0, 0, 0, 2, 8, 0},
                                            {0, 0, 0, 4, 1, 9, 0, 0, 5},
                                            {0, 0, 0, 0, 8, 0, 0, 7, 9}
                                            };
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

        updateBoard();
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
    public void updateBoard()
    {
        for(int row = 1; row < boardData.length + 1; row += 1)
        {
            for(int col = 1; col < boardData.length + 1; col += 1)
            {
                boardPicture[row-1][col-1].setNumber(boardData[row-1][col-1]);
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
    //https://www.baeldung.com/java-sudoku
    private boolean solve(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int column = 0; column < 9; column++) {
                if (board[row][column] == 0) {
                    for (int k = 1; k <= 9; k++) {
                        board[row][column] = k;
                        if (isValid(board, row, column) && solve(board)) {
                            return true;
                        }
                        board[row][column] = 0;
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int[][] board, int row, int column) {
        return (rowConstraint(board, row)
            && columnConstraint(board, column) 
            && subsectionConstraint(board, row, column));
    }

    private boolean rowConstraint(int[][] board, int row) {
        boolean[] constraint = new boolean[9];
        return IntStream.range(0, 9)
          .allMatch(column -> checkConstraint(board, row, constraint, column));
    }
    private boolean columnConstraint(int[][] board, int column) {
        boolean[] constraint = new boolean[9];
        return IntStream.range(0, 9)
          .allMatch(row -> checkConstraint(board, row, constraint, column));
    }
    private boolean subsectionConstraint(int[][] board, int row, int column) {
        boolean[] constraint = new boolean[9];
        int subsectionRowStart = (row / 3) * 3;
        int subsectionRowEnd = subsectionRowStart + 3;
    
        int subsectionColumnStart = (column / 3) * 3;
        int subsectionColumnEnd = subsectionColumnStart + 3;
    
        for (int r = subsectionRowStart; r < subsectionRowEnd; r++) {
            for (int c = subsectionColumnStart; c < subsectionColumnEnd; c++) {
                if (!checkConstraint(board, r, constraint, c)) return false;
            }
        }
        return true;
    }
    
    boolean checkConstraint(
    int[][] board, 
    int row, 
    boolean[] constraint, 
    int column) {
        if (board[row][column] != 0) {
            if (!constraint[board[row][column] - 1]) {
                constraint[board[row][column] - 1] = true;
            } else {
                return false;
            }
        }
        return true;
    }
    //the ending cases
    //  not backing up and at row = 8 and col = 8
    //  backing up and at row = 0 and col = 0
    //      
    // public void solve(int[][] grid, int row, int col, boolean backingUp)
    // {
    //    if(row == -1 || col == -1)
    //     {
    //         System.out.print("\nrow: ");
    //         System.out.print(row);
    //         System.out.print("\ncol: ");
    //         System.out.print(col);
    //         return;
    //     }
    //     if(row == gothroughs)
    //     {
    //         pauseBool = backingUp;
    //         pauseCol = col;
    //         pauseRow = row;
    //         System.out.println("Solved");
    //         return;
    //     }
    //     if(!backingUp && row == 8 && col == 8)
    //     {
    //         System.out.println("Solved");
    //         return;
    //     }
    //     if(backingUp && (row == 0 && col == 0))
    //     {
    //         System.out.println("Impossible");
    //         return;
    //     }
    //     if(boardPicture[row][col].isStarter())
    //     {
    //         if(backingUp)
    //         {
    //             if(col == 0)
    //             {
    //                 solve(grid, row-1, 8, true);
    //                 return;
    //             }
    //             solve(grid, row, col-1, true);
    //             return;
    //         }
    //         else
    //         {
    //             if(col == 8)
    //             {
    //                 solve(grid, row + 1, 0, false);
    //                 return;
    //             }
    //             solve(grid, row, col + 1, false);
    //             return;
    //         }
    //     }
    //     else
    //     {
    //         if(backingUp)
    //         {
    //             if(boardData[row][col] == 9)
    //             {
    //                 boardData[row][col] = 0;
    //                 boardPicture[row][col].clear();
    //                 if(col == 0)
    //                 {
    //                     solve(grid, row + 1, 0, true);
    //                     return;
    //                 }
    //                 solve(grid, row, col - 1, true);
    //                 return;
    //             }
    //             for(int i = boardData[row][col] + 1; i < 10; i += 1)
    //             {
    //                 boardData[row][col] = i;
    //                 boardPicture[row][col].setNumber(boardData[row][col]);
    //                 if(checkSpot(boardPicture, col, row))
    //                 {
    //                     if(col == 8)
    //                     {
    //                         solve(grid, row + 1, 0, false);
    //                         return;
    //                     }
    //                     solve(grid, row, col + 1, false);
    //                     return;
    //                 }
                    
    //             }
    //             boardPicture[row][col].clear();
    //             if(col == 0)
    //             {
    //                 solve(grid, row + 1, 0, true);
    //                 return;
    //             }
    //             solve(grid, row, col - 1, true);
    //             return;
    //         }
    //         for(int i = 1; i < 10; i += 1)
    //         {
    //             boardData[row][col] = i;
    //             boardPicture[row][col].setNumber(boardData[row][col]);
    //             if(checkSpot(boardPicture, col, row))
    //             {
    //                 if(col == 8)
    //                 {
    //                     solve(grid, row + 1, 0, false);
    //                     return;
    //                 }
    //                 solve(grid, row, col + 1, false);
    //                 return;
    //             }
    //         }
    //         boardData[row][col] = 0;
    //         boardPicture[row][col].clear();
    //         if(col == 0)
    //         {
    //             solve(grid, row - 1, 8, true);
    //             return;
    //         }
    //         solve(grid, row, col - 1, true);
    //         return;
    //     }
    // }

    // public void solve(Square[][] board)
    // {
    //     gothroughs += 1;
    //     System.out.println("Solve");
    //     for(int y = 0; y < 9; y += 1)
    //     {
    //         for(int x = 0; x < 9; x +=1)
    //         {
    //             // System.out.println(board[x][y].getNumber());
    //             if(board[y][x].getNumber() == 0)
    //             {
    //                 firstOpeny = y;
    //                 firstOpenx = x;
    //                 boardPicture = doMagic(x, y, board);
    //                 repaint();
    //                 break;
    //             }
    //         }
    //     }
    // }

    // public Square[][] doMagic(int x, int y, Square[][] board)
    // {
    //     gothroughs += 1;
    //     if(gothroughs > 9000 && gothroughs < 9010 || gothroughs > 10000 && gothroughs < 10010)
    //     {
    //         System.out.println("PAUSE"); 
    //     }
    //     System.out.println("doMagic");
    //     if(board[y][x].getNumber() == 0)
    //     {
    //         board[y][x].setNumber(1);
    //     }
    //     if(checkSpot(x, y) == false)
    //     {
    //         if(board[y][x].getNumber() == 9)
    //         {
                
    //             board[y][x].clear();
    //             return backtrack(x, y, board); //go to the previous value form the curren value
    //         }
    //         return incrementVal(board, x, y);
    //     }
    //     else
    //     {
    //         // System.out.println(board[y][x].getNumber());
    //         return nextSpot(x, y, board);
    //     }
    // }

    // /**
    //  * Start from current value (x, y) and go to eh previous inputed value
    //  * @param x
    //  * @param y
    //  * @param board
    //  * @return
    //  */
    // public Square[][] backtrack(int x, int y, Square[][] board)
    // {
    //     gothroughs += 1;
    //     System.out.println("backtrack");
    //     if(x == firstOpenx && y == firstOpeny) //if we can't backtrack say this is impossible
    //     {
    //         System.out.println("IMPOSSIBLE");
    //         return board;
    //     }
    //     else 
    //     {
    //         boolean isX = true;
    //         for(int prevy = y; prevy >= 0; prevy -= 1)
    //         {   
    //             for(int prevx = 8; prevx >= 0; prevx -= 1)
    //             {
    //                 if(prevy == y)
    //                 {
    //                     if(isX)
    //                     {
    //                         prevx = x-1;
    //                         isX = false;
    //                         if(prevx < 0)
    //                         {
    //                             break;
    //                         }
    //                     }
    //                 }
    //                 if(!board[prevy][prevx].isStarter()) //find the previous non Starter and increase it by 1 then do magic on it
    //                 {
    //                     return incrementVal(board, prevx, prevy);
    //                 }
    //             }
    //         }
    //     }
    //     System.out.println("backtrack failure");
    //     return boardPicture;
    // }

    // public Square[][] incrementVal(Square[][] board, int x, int y)
    // {
    //     gothroughs += 1;
    //     System.out.println("incrementVal");
    //     if(board[y][x].getNumber() == 9)
    //     {
    //         board[y][x].clear();
    //         return backtrack(x, y, board);
    //     }
    //     board[y][x].setNumber(board[y][x].getNumber()+1);
    //     return doMagic(x, y, board);
    // }

    // public Square[][] nextSpot(int x, int y, Square[][] board)
    // {
    //     gothroughs += 1;
    //     System.out.println("nextSpot");
    //     if(x == 8 && y == 8)
    //     {
    //         System.out.println("SOLVED");
    //         return board;
    //     }
    //     if(x == 8)
    //     {
    //         x = 0;
    //         y += 1;
    //         if(board[y][x].isStarter())
    //         {
    //             return nextSpot(x,y, board);
    //         }
    //     }
            
    //     if(board[y][x+1].isStarter())
    //     {
    //         return nextSpot(x + 1, y, board);
    //     }
    //     return doMagic(x + 1, y, board);
    // }

    public boolean checkSpot(Square[][] board, int positionx, int positiony)
    {
        boolean returnValue = true;
        int currentNum = board[positiony][positionx].getNumber();
        if(currentNum == 0)
        {
            return true;
        }
        for(int y = 0; y < 9; y +=1) //x changes so moving accross a row
        {
            if(board[y][positionx].getNumber() == currentNum && positiony != y)
            {
                board[y][positionx].setSelected(true);
                // System.out.println("false");
                returnValue = false;
            }
            continue;
        }

        for(int x = 0; x < 9; x +=1) //x changes so moving accross a row
        {
            if(board[positiony][x].getNumber() == currentNum && positionx != x)
            {
                board[positiony][x].setSelected(true);
                // System.out.println("false");
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
                
                if(board[y][x].getNumber() == currentNum && positionx != x && positiony != y)
                {
                    board[y][x].setSelected(true);
                    // System.out.println("false");
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
        if(e.getKeyCode() == KeyEvent.VK_S )
        {
            solve(boardDataToSolve);
            boardData = boardDataToSolve;
            repaint();
        }

        if(positionSelectedCol != -1 && positionSelectedRow != -1)
        {
            if(numbers.indexOf(KeyEvent.getKeyText(e.getKeyCode())) != -1)
            {
                clearAllSelections();
                boardPicture[positionSelectedRow][positionSelectedCol].setSelected(true);
                boardData[positionSelectedRow][positionSelectedCol] = Integer.parseInt(KeyEvent.getKeyText(e.getKeyCode()));
                repaint();
            }
            else if(e.getKeyCode() == KeyEvent.VK_SPACE )
            {
                System.out.println("yes");
                boardData[positionSelectedRow][positionSelectedCol] = 0;
                repaint();
            }

        }
    }
    @Override
    public void keyReleased(KeyEvent e) {
        // checkSpot(positionSelectedCol, positionSelectedRow);

        // repaint();

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