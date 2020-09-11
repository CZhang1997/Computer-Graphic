package io.github.czhang1997.assignment1;
/**
 * @Author: churongzhang
 * @Date: 9/5/20
 * @Time: 11:47 PM
 * @Info:
 * 1.3 Draw a set of concentric pairs of squares, each consisting of a square with
 * horizontal and vertical edges and one rotated through 45. Except for the
 * outermost square, the vertices of each square are the midpoints of the edges
 * of its immediately surrounding square, as Fig. 1.12 shows. It is required that all
 * lines are exactly straight, and that vertices of smaller squares lie exactly on the
 * edges of larger ones.
 */

import java.awt.*;
import java.awt.event.*;

public class Problem1_3 extends Frame {

    public static void main(String[] args) {
        new Problem1_3();
    }
    Problem1_3()
    {
        super("Concentric Squares(Straight Lines)");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        setSize(800, 600);
        add("Center", new CanvasSquare());
        setVisible(true);
    }
}

class CanvasSquare extends Canvas {

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
                Thread.sleep(100);
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
//            float f1 = (float)(Math.random() * 156.0F);
//            float f2 = (float)(Math.random() * 100.0F);
//            float f3 = (float)(Math.random() * 156.0F);
//            g.setColor(Color.getHSBColor(f1,f2,f3));

            float[] xListNew = new float[size];
            float[] yListNew = new float[size];
            for(int i = 0; i < xListNew.length; i ++){
                xListNew[i] = (xList[i] + xList[(i + 1) % size])/ 2.0F;
                yListNew[i] = (yList[i] + yList[(i +1) % size]) / 2.0F;
            }
            xList = xListNew;
            yList = yListNew;

//            try {
//                Thread.sleep(300);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }



}
