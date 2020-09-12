package io.github.czhang1997.assignment1;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Author: churongzhang
 * @Date: 9/5/20
 * @Time: 11:47 PM
 * @Info:
 * 1.4 Write a program that draws a pattern of hexagons, as shown in Fig. 1.13.
 * The vertices of a (regular) hexagon lie on its so-called circumscribed circle.
 * The user must be able to specify the radius of this circle by clicking a point near
 * the upper-left corner of the drawing rectangle. Then the distance between that
 * point and that corner is to be used as the radius of the circle just mentioned.
 * There must be as many hexagons of the specified size as possible and the
 * margins on the left and the right must be equal. The same applies to the upper
 * and lower margins, as Fig. 1.13 shows.
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;

public class Problem1_4 extends Frame{
    public static void main(String[] args) {
        new Problem1_4();
    }
    Problem1_4()
    {
        super("Hexagons(click near left-upper corner)");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        setSize(800, 600);
        add("Center", new CanvasHexagons());
        setVisible(true);
    }

}


class CanvasHexagons extends Canvas {

    int maxX, maxY, minMaxXY, xCenter, yCenter;;

    private int radius;

    CanvasHexagons(){
        initGraphic();
        radius = 0;
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                radius = (int)Math.sqrt(Math.pow(evt.getX(), 2) + Math.pow(evt.getY(), 2));
                repaint();
            }
        });
    }
    void initGraphic() {
        Dimension d = getSize();
        maxX = d.width - 1; maxY = d.height - 1;
        minMaxXY = Math.min(maxX, maxY);
        xCenter = maxX / 2; yCenter = maxY / 2;
    }
    int iX(float x) {return Math.round(x);}
    int iY(float y) {return maxY - Math.round(y);}

    void drawHexagon(Graphics g)
    {
        float x = radius < 50 ? 50: radius;
        float y = maxY - radius;
        int degree = 60;
        int yChange = (int)Math.round(Math.sin(Math.toRadians(degree)) * radius);
        int xChange = (int)Math.round(Math.cos(Math.toRadians(degree)) * radius);
        float hexagonDiameter = radius + 2 * xChange;
        float yMarginBottom =  hexagonDiameter;
        float xMarginRight = maxX - hexagonDiameter;
        int lineOfHexagonDraw = 0;
        while(y >yMarginBottom){
            float topX = x;
            boolean canDraw = topX < xMarginRight;
            // draw the upper half of the hexagon
            while(canDraw){
                int X = iX(topX);
                int Y = iY(y);
                // draw the top line for the first row of hexagon
                if(lineOfHexagonDraw == 0)
                    g.drawLine(X,Y,X + radius,Y);

                // draw the left top line
                g.drawLine(X,Y,X - xChange,Y + yChange);
                // draw the right top line
                g.drawLine(X + radius,Y,X + radius + xChange,Y + yChange);
                // if condition match, draw the center line
                if(xMarginRight > X +  radius + hexagonDiameter)
                    g.drawLine(X + radius + xChange,Y + yChange, X + 2* radius + xChange,Y + yChange);
                // update the topX
                topX = topX + 2 * radius + 2 * xChange;
                canDraw = topX < xMarginRight;
            }
            float botX = x;
            y = y - (float)(2 * yChange);
            // draw the lower part of the hexagon
            while(botX < xMarginRight){
                int X = iX(botX);
                int Y = iY(y);
                // draw the bottom line
                g.drawLine(X,Y,X+ radius,Y);
                // draw the left bottom line
                g.drawLine(X,Y,X - xChange,Y - yChange);
                // draw the right bottom line
                g.drawLine(X + radius,Y,X + radius + xChange,Y - yChange);
                botX = botX + 2 * radius + 2 * xChange;
            }
            lineOfHexagonDraw ++;
        }
    }

    public void paint(Graphics g) {

        initGraphic();
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        // draw the top left + sign
        g2.draw(new Line2D.Float(10, 40, 30, 40));
        g2.draw(new Line2D.Float(20, 30, 20, 50));

        String tips = "click any where on the top left corner to define the radius (distance from (0,0))";

        g2.setStroke(new BasicStroke(2));
        if(radius != 0)
            drawHexagon(g);
        else
            g.drawString(tips, 20, 20);

    }
}
