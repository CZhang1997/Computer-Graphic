package io.github.czhang1997.assignment1;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Author: churongzhang
 * @Date: 9/5/20
 * @Time: 11:58 PM
 * @Info:
 * 1.5 Write a class Lines containing a static method dashedLine to draw dashed lines,
 * in such a way that we can write
 * Lines.dashedLine(g, xA, yA, xB, yB, dashLength);
 * where g is a variable of type Graphics, xA, yA, xB, yB are the device coordinates
 * of the endpoints A and B, and dashLength is the desired length (in device
 * coordinates) of a single dash. There should be a dash, not a gap, at each
 * endpoint of a dashed line. Figure 1.14 shows eight dashed lines drawn in this
 * way, with dashLength ¼ 20.
 */

import java.awt.*;
import java.awt.event.*;

public class Problem1_5 extends Frame{

    public static void main(String[] args) {
        new Problem1_5();
    }
    Problem1_5()
    {
        super("Dashed lines");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        setSize(500, 300);
        add("Center", new CanvasDashLine());
        setVisible(true);
    }
}

class CanvasDashLine extends Canvas {

    int maxX, maxY, minMaxXY, xCenter, yCenter;;

    void initGraphic() {
        Dimension d = getSize();
        maxX = d.width - 1; maxY = d.height - 1;
        minMaxXY = Math.min(maxX, maxY);
        xCenter = maxX / 2; yCenter = maxY / 2;
    }

    int iX(float x) {return Math.round(x);}
    int iY(float y) {return Math.round(y);}

    void drawRect(Graphics g, float[] xList, float[] yList)
    {
        int size = xList.length;
        // draw 4 line using the 4 vertices
        for(int i = 0; i < size; i ++)
        {
            int x1 = iX(xList[i%size]), y1 = iY(yList[i%size]);
            int x2 = iX(xList[(i + 1)%size]), y2 = iY(yList[(i + 1)%size]);
            g.drawLine(x1, y1, x2, y2);
        }
    }
    void drawDashRect(Graphics g, float[] xList, float[] yList){
        int size = xList.length;
        // draw 4 line using the 4 vertices
        for(int i = 0; i < size; i ++)
        {
            int x1 = iX(xList[i%size]), y1 = iY(yList[i%size]);
            int x2 = iX(xList[(i + 1)%size]), y2 = iY(yList[(i + 1)%size]);
            Lines.dashedLine(g, x1, y1, x2, y2, 5);

        }
    }

    public void paint(Graphics g) {
        initGraphic();

        float x = 0.90F * maxX;
        float y =  0.90F * maxY;
        // define the initial position for each vertices
        float[] xList = {x, maxX - x , maxX  - x, x };
        float[] yList = {y, y       , maxY - y, maxY - y };
        drawRect(g, xList, yList);

        float side = Math.min(x,y) / 5;
        float xInner = xList[0] - side;
        float yInner =  yList[0] - side;
        xInner -= side/3;
        yInner += side/3;
        float factor = 0.6F;
        float width = x * factor;
        float height = y * factor;


        float[] xLisInner = {xInner, xInner - width , xInner - width, xInner};
        float[] yListInner = {yInner, yInner , yInner - height, yInner - height };
        drawDashRect(g, xLisInner, yListInner);

        for(int i = 0; i < xLisInner.length; i++)
        {
            Lines.dashedLine(g, xList[i], yList[i], xLisInner[i], yListInner[i], 5);
        }

        }
    }

    class Lines {

        public static void VerticalDashedLine(Graphics g, float xA, float yA, float xB, float yB, float dashLength){
            if(yA > yB){
                while(yA > yB){
                    g.drawLine((int)xA,(int)yA, (int)xA,(int)(yA - dashLength));
                    yA-= 2* dashLength;
                }
            }else {
                while(yA < yB){
                    g.drawLine((int)xA,(int)yA, (int)xA,(int)(yA + dashLength));
                    yA+= 2* dashLength;
                }
            }

        }

        /**
         * origin at top left (0,0)
         * @param g the graphic to draw on
         * @param xA    the first point of x
         * @param yA    the first point of y
         * @param xB    the second point of x
         * @param yB    the second point of y
         * @param dashLength    the length of each dash
         */
    public static void dashedLine(Graphics g, float xA, float yA, float xB, float yB, float dashLength){
        System.out.println("drawing at " + xA + ", "+ yA + " to " + xB + ", "+ yB);
        double rise = (double)yA - yB;
        double run = (double)xA - xB;
        double slope= rise/run;
        if(run == 0)
        {
            VerticalDashedLine(g,xA,yA,xB,yB,dashLength);
            return;
        }
        double yChange = dashLength * slope;
        System.out.println(slope);
        System.out.println(yChange);
        if(xA < xB) // if first point is on the left side
            while(xA < xB)
            {
                // calculate the dash point moving to the right
                int newX = Math.round(xA + dashLength);
                int newY = (int)Math.round(yA + yChange);
                if(newX >= xB) {    // if this new point is greater than the end point(second point)
                    int diff = (int)(xB - xA);      // then calculate enough diff for y change
                    int yEndChange = (int)Math.round(diff * slope);
                    g.drawLine((int)xA,(int)yA, (int)xA + diff,(int)yA + yEndChange);
                }
                else    // draw the dash
                    g.drawLine((int)xA,(int)yA, newX,newY);
                // update the new xA yA by two step (1 for dash, 1 for space)
                xA = Math.round(xA + 2*dashLength);
                yA = Math.round(yA + 2*yChange);
            }
        else // if first point is on the right side
            while(xA >= xB)
            {
                // calculate the dash point moving to the left
                int newX = Math.round(xA - dashLength);
                int newY = (int) Math.round(yA - yChange);
                if(newX <= xB) { // if this new point is less than the end point(second point)
                    int diff = (int)(xA - xB); // then calculate enough diff for y change
                    int yEndChange = (int)Math.round(diff * slope);
                    g.drawLine((int)xA,(int)yA, (int)xA - diff,(int)yA + yEndChange);
                }
                else
                    g.drawLine((int)xA,(int)yA, newX,newY);
                // update the new xA yA by two step (1 for dash, 1 for space)
                xA = Math.round(xA - 2*dashLength);
                yA = Math.round(yA - 2*yChange);

            }
    }


}
