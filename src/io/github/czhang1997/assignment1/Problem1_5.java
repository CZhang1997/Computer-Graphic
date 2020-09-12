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

    public void paint(Graphics g) {
        Lines.dashedLine(g, 10,10,50,50,5);
        Lines.dashedLine(g, 80,10,100,200,1);
        Lines.dashedLine(g, 10,50,170,188,8);
        Lines.dashedLine(g, 200,130,77,88,10);

        }
    }

    class Lines {
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

        double rise = (double)yA - yB;
        double run = (double)xA - xB;
        double slope = rise/run;
        double yChange = dashLength * slope;

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
            while(xA > xB)
            {
                // calculate the dash point moving to the left
                int newX = Math.round(xA - dashLength);
                int newY = (int) Math.round(yA + yChange);
                if(newX <= xB) { // if this new point is less than the end point(second point)
                    int diff = (int)(xA - xB); // then calculate enough diff for y change
                    int yEndChange = (int)Math.round(diff * slope);
                    g.drawLine((int)xA,(int)yA, (int)xA - diff,(int)yA + yEndChange);
                }
                else
                    g.drawLine((int)xA,(int)yA, newX,newY);
                // update the new xA yA by two step (1 for dash, 1 for space)
                xA = Math.round(xA - 2*dashLength);
                yA = Math.round(yA + 2*yChange);
            }
    }


}
