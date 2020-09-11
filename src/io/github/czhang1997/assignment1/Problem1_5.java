package io.github.czhang1997.assignment1;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Author: churongzhang
 * @Date: 9/5/20
 * @Time: 11:58 PM
 * @Info:
 */

import java.awt.*;
import java.awt.event.*;

public class Problem1_5 extends Frame{

    public static void main(String[] args) {
        new Problem1_5();
    }
    Problem1_5()
    {
        super("Concentric Squares(Straight Lines)");
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
    int iY(float y) {return maxY - Math.round(y);}

    public void paint(Graphics g) {

        initGraphic();
        float side = 0.90F * minMaxXY;
        float sideHalf = 0.5F * side;


        }
    }

    class Lines {

    public static void dashedLine(Graphics g, float xA, float yA, float xB, float yB, float dashLength){
        float startX = xA;
        float startY = yA;
        if(startX > xB)
        {
            if(startY > yB){
                while(startX + dashLength < xB)
                {

                }
            }
            else
            {

            }
        }
        else{

        }
    }
    }
