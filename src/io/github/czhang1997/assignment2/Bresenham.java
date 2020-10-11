package io.github.czhang1997.assignment2;



import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @Author: churongzhang
 * @Date: 9/18/20
 * @Time: 2:40 PM
 * @NetId: cxz173430@utdallas.edu
 * @Info:
 * 4.1 Replace the drawLine method based on Bresenham’s algorithm and listed
 * almost at the end of Sect. 1 with an even faster version that benefits from the
 * symmetry of the two halves of the line. For example, with endpoints P and Q
 * satisfying Eq. (4.1), and using the integer value xMid halfway between xP and
 * xQ, we can let the variable x run from xP to xMid and also use a variable x2,
 * which at the same time runs backward from xQ to xMid. In each iteration of the
 * loop, x is increased by 1 and x2 is decreased by 1. Note that there will be either
 * one point or two points in the middle of the line, depending on the number of
 * pixels to be plotted being odd or even. Be sure that no pixel of the line is
 * omitted and that no pixel is put twice on the screen. To test the latter, you can
 * use XOR mode so that writing the same pixel twice would have the same effect
 * as omitting a pixel.
 */
public class Bresenham extends Frame {

    public static void main(String[] args) {
        new Bresenham();
    }
    Bresenham()
    {
        super("Bresenham lines");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {System.exit(0);}
        });
        setSize(360, 240);
        add("Center", new CanvasBresenham());
        setVisible(true);
    }
}


class CanvasBresenham extends Canvas {

    Point p1;
    Point p2;
    CanvasBresenham(){
        Dimension d = getSize();
        System.out.println(d.width);
        addMouseListener(new MouseAdapter() {
            // part 2: handle two input points from the mouse
            public void mousePressed(MouseEvent event) {
                // get the radius from the mouse press event using distance formula from 0,0
                if(p1 == null){
                    p1 = new Point(event.getX(), event.getY());
                }
                else if(p2 == null)
                    p2 = new Point(event.getX(), event.getY());
                else{
                    // remove the two selected point when click the third time
                    p1 = null;
                    p2 = null;
                }
                repaint();
            }
        });
    }

    void putPixel(Graphics g, int x, int y) {
        g.drawLine(x, y, x, y);
    }
      
    void sleep(){
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // basic bresenham algorithm
    int bresenham(Graphics g, int xP, int yP, int xQ, int yQ){
        // initialize variable
        int x = xP,
                y = yP,
                d = 0,
                dx = xQ - xP,
                dy = yQ - yP,
                c, m,
                xInc = 1,
                yInc = 1;
        int cycle = 0;
        // if line is going from right to left
        if (dx < 0){xInc = -1; dx = -dx;}
        // if the line is going from bottom to top
        if (dy < 0){yInc = -1; dy = -dy;}

        if (dy <= dx) {
            c = 2 * dx; m = 2 * dy;
            if (xInc < 0) dx++;
            for (;;) {
                putPixel(g, x, y);
                if (x == xQ)
                    break;
                x += xInc;
                d += m;
                if (d >= dx)
                {
                    y += yInc;
                    d -= c;
                }
                cycle++;
            }
        }
        else {
            c = 2 * dy; m = 2 * dx;
            if (yInc < 0) dy++;
            for (;;) {
                putPixel(g, x, y);
                if (y == yQ) break;
                y += yInc;
                d += m;
                if (d >= dy){x += xInc; d -= c;}
                cycle++;
            }
        }
        return cycle;
    }


    // 1(4%). Exercise 4.1. in both editions of the textbook.
    int FasterBres(Graphics g, int xP, int yP, int xQ, int yQ) {

        int dx = xQ - xP;
        int dy = yQ - yP;
        int xInc = 1, yInc = 1;
        int c, m;
        int d1 = 0;
        int d2 = 0;
        int cycle = 0;
        if (dx < 0){xInc = -1; dx = -dx;}
        // if the line is going from bottom to top
        if (dy < 0){yInc = -1; dy = -dy;}
        if(dy <= dx) {
            c = 2 * dx; m = 2 * dy;
            if (xInc < 0)
                dx++;
            while(true){
                putPixel(g, xP, yP);
                if(xQ == xP)
                    break;
                xP += xInc;
                d1 += m;
                if(d1 > dx)
                {
                    yP += yInc;
                    d1 -= c;
                }
                putPixel(g, xQ, yQ);
                if(xQ == xP)
                    break;
                xQ -= xInc;
                d2 += m;
                if(d2 > dx){
                    yQ -= yInc;
                    d2 -= c;
                }
                cycle++;
            }
        }
        else
        {
            c = 2 * dy;
            m = 2* dx;
            while(true) {
                putPixel(g, xP, yP);
                if(yQ == yP)
                    break;
                yP += yInc;
                d1 += m;
                if (d1 >= dy){
                    xP += xInc;
                    d1 -= c;
                }
                putPixel(g, xQ, yQ);
                if(yQ == yP)
                    break;
                yQ -= yInc;
                d2 += m;
                if(d2 > dy){
                    xQ -= xInc;
                    d2 -= c;
                }
                cycle++;
            }
        }
        return cycle;
    }

    public void paint(Graphics g) {
        Point textPosition = new Point(50, 50);
        if(p1 == null){
            g.drawString( "Please click on some where to represent the first point.", textPosition.x,textPosition.y);
        }
        else if(p2 == null) {
            putPixel(g, p1.x, p1.y);
            g.drawString("Please click on some where to represent the second point.", textPosition.x, textPosition.y);
        }
        else {
            // part 2: draw the two line input from the mouse
            int breCycle = bresenham(g, p1.x,p1.y,p2.x,p2.y);
            g.setXORMode(Color.red);
            int fastCycle = FasterBres(g, p1.x,p1.y,p2.x,p2.y);
            // draw the result
            g.drawString("FasterBres: "+ fastCycle + " cycles; Bresenham: "+ breCycle +" cycles.", textPosition.x,textPosition.y);
        }



    }
}
class Point{
    int x;
    int y;
    Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}
