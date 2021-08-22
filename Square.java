  import java.awt.*;
import java.awt.geom.*;
public class Square
{
    private int x;
    private int y;
    private int Width_Height;
    private int number;
    private boolean selected;
    private boolean isStarter;
    public Square(int Width_Height, int x, int y, int number)
    {
        this.x = x;
        this.y = y; 
        this.Width_Height = Width_Height;
        this.number = number;
        this.selected = false;
        if(number == 0)
        {
            this.isStarter = false;
        }
        else
        {
            this.isStarter = true;
        }
    }

    public void clear()
    {
        this.number = 0;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }

    public int getNumber()
    {
        return this.number;
    }

    public int getWidth_Height()
    {
        return this.Width_Height;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public boolean getSelected()
    {
        return this.selected;
    }

    public void draw(Graphics2D g2)
    {
        Rectangle2D.Double myRect = new Rectangle2D.Double(this.x, this.y, this.Width_Height, this.Width_Height);
        g2.setColor(new Color(0,0,0));
        g2.draw(myRect);
        Rectangle2D.Double myRect1 = new Rectangle2D.Double(this.x+1, this.y+1, this.Width_Height-2, this.Width_Height-2);
        g2.setColor(new Color(255,255,255));
        g2.fill(myRect1);

        if (selected)
        {
            Rectangle2D.Double highlightedRect = new Rectangle2D.Double(this.x + 1, this.y + 1, this.Width_Height-2, this.Width_Height-2);
            g2.setColor(new Color(164,164,164));
            g2.draw(highlightedRect);
            g2.fill(highlightedRect);
        }
        if (this.number != 0)
        {
            g2.setFont(new Font("Monospaced", Font.BOLD, this.Width_Height/2));
            if(this.isStarter)
            {
                // g2.setColor(new Color(164,164,164));
                g2.setColor(new Color(0, 0, 0));
            }
            else
            {
                // g2.setColor(new Color(0, 0, 0));
                if(this.selected)
                {
                    g2.setColor(new Color(255,255,255));
                    
                }
                else 
                {
                    g2.setColor(new Color(164,164,164));
                }
            }

            g2.drawString(String.valueOf(this.number), this.x + (this.Width_Height/2) - this.Width_Height/6, this.y + (this.Width_Height/2)+this.Width_Height/5);    
        }
    }

    public boolean isHit(int x, int y)
    {
       if(isStarter)
       {
           return false;
       }
       if (x > this.x && x < this.x+this.Width_Height)
       {
          if(y > this.y && y < this.y + this.Width_Height)
          {
             return true;
          }
       }
       return false;
    }

    public boolean isStarter()
    {
        return this.isStarter;
    }
}
