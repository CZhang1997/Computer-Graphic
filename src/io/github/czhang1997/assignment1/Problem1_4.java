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

public class Problem1_4 extends Frame{
    public static void main(String[] args) {
        new Problem1_4();
    }
    Problem1_4()
    {
        super("Concentric Squares(Straight Lines)");
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

    void initGraphic() {
        Dimension d = getSize();
        maxX = d.width - 1; maxY = d.height - 1;
        minMaxXY = Math.min(maxX, maxY);
        xCenter = maxX / 2; yCenter = maxY / 2;
    }
    int iX(float x) {return Math.round(x);}
    int iY(float y) {return maxY - Math.round(y);}

    void drawRect(Graphics g, float[] xList, float[] yList)
    {
        int size = xList.length;
        for(int i = 0; i < xList.length; i ++)
        {
            int x1 = iX(xList[i%size]), y1 = iY(yList[i%size]);
            int x2 = iX(xList[(i + 1)%size]), y2 = iY(yList[(i + 1)%size]);
            g.drawLine(x1, y1, x2, y2);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void paint(Graphics g) {

        initGraphic();
        float side = 0.90F * minMaxXY;
        float sideHalf = 0.5F * side;

        int size = 4;
        float x = xCenter - sideHalf;
        float y =  yCenter + sideHalf;
        // define the initial position for each vertices
        float[] xList = {x, x + side, x + side, x };
        float[] yList = {y, y       , y - side, y - side };
        for(int rect = 0; rect < 20; rect ++) {

            drawRect(g, xList, yList);
            float[] xListNew = new float[size];
            float[] yListNew = new float[size];
            for(int i = 0; i < xListNew.length; i ++){
                xListNew[i] = (xList[i] + xList[(i + 1) % size])/ 2.0F;
                yListNew[i] = (yList[i] + yList[(i +1) % size]) / 2.0F;
            }
            xList = xListNew;
            yList = yListNew;
        }
    }



}