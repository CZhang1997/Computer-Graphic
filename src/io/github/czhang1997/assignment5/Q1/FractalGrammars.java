package io.github.czhang1997.assignment5.Q1;// FractalGrammars.java
// Extended with U and V strings, file format needs to include U and V strings too.

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;

public class FractalGrammars extends Frame {
    public static void main(String[] args) {
//        if (args.length == 0)
//            System.out.println("Use filename as program argument.");
//        else
        new FractalGrammars("koch.txt");
//        new FractalGrammars("dragon.txt");
    }

    FractalGrammars(String fileName) {
        super("Click left or right mouse button to change the level");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(800, 600);
        add("Center", new CvFractalGrammars(fileName));
        setVisible(true);
    }
}

class CvFractalGrammars extends Canvas {
    String fileName, axiom, strF, strf, strX, strY;

    String strU, strV;

    int maxX, maxY, level = 1;
    double xLast, yLast, dir, rotation, dirStart, fxStart, fyStart,
            lengthFract, reductFact;

    Double xBreak, yBreak;

    void error(String str) {
        System.out.println(str);
        System.exit(1);
    }

    CvFractalGrammars(String fileName) {
        Input inp = new Input(fileName);
        if (inp.fails())
            error("Cannot open input file.");
        axiom = inp.readString();
        inp.skipRest();
        strF = inp.readString();
        inp.skipRest();
        strf = inp.readString();
        inp.skipRest();
        strX = inp.readString();
        inp.skipRest();
        strY = inp.readString();
        inp.skipRest();

        strU = inp.readString();
        inp.skipRest();
        strV = inp.readString();
        inp.skipRest();

        rotation = inp.readFloat();
        inp.skipRest();
        dirStart = inp.readFloat();
        inp.skipRest();
        fxStart = inp.readFloat();
        inp.skipRest();
        fyStart = inp.readFloat();
        inp.skipRest();
        lengthFract = inp.readFloat();
        inp.skipRest();
        reductFact = inp.readFloat();
        if (inp.fails()) error("Input file incorrect.");

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent evt) {
                if ((evt.getModifiers() & InputEvent.BUTTON3_MASK) != 0) {
                    level--; // Right mouse button decreases level
                    if (level < 1) level = 1;
                } else
                    level++; // Left mouse button increases level
                repaint();
            }
        });

    }

    Graphics g;

    int iX(double x) {
        return (int) Math.round(x);
    }

    int iY(double y) {
        return (int) Math.round(maxY - y);
    }

    void drawTo(Graphics g, double dx, double dy) {
        double partial = 0.8;
        double partial2 = 1 - partial;
        xLast = xLast + dx * partial2;
        yLast = yLast + dy * partial2;
        g.drawLine(iX(xBreak), iY(yBreak), iX(xLast), iY(yLast));
        xBreak = xLast + dx * partial;
        yBreak = yLast + dy * partial;
        g.drawLine(iX(xLast), iY(yLast), iX(xBreak), iY(yBreak));
        xLast = xLast + dx;
        yLast = yLast + dy;
    }

    void moveTo(Graphics g, double x, double y) {
        xLast = x;
        yLast = y;
    }

    public void paint(Graphics g) {
        Dimension d = getSize();
        maxX = d.width - 1;
        maxY = d.height - 1;
        xLast = fxStart * maxX;
        yLast = fyStart * maxY;
        xBreak = xLast;
        yBreak = yLast;
        dir = dirStart; // Initial direction in degrees
        turtleGraphics(g, axiom, level, lengthFract * maxY);
    }


    public void turtleGraphics(Graphics g, String instruction,
                               int depth, double len) {
        double xMark = 0, yMark = 0, dirMark = 0;
        for (int i = 0; i < instruction.length(); i++) {
            char ch = instruction.charAt(i);
            double rad = Math.PI / 180 * dir, // Degrees -> radians
                    dx = len * Math.pow(reductFact, depth) * Math.cos(rad), dy = len * Math.pow(reductFact, depth) * Math.sin(rad);
            switch (ch) {
                case 'F': // Step forward and draw
                    // Start: (xLast, yLast), direction: dir, steplength: len
                    if (depth == 0) {
                        drawTo(g, dx, dy);
                    } else
                        turtleGraphics(g, strF, depth - 1, reductFact * len);
                    break;
                case 'f': // Step forward without drawing
                    // Start: (xLast, yLast), direction: dir, steplength: len
                    if (depth == 0) {
                        moveTo(g, xLast + dx, yLast + dy);
                    } else
                        turtleGraphics(g, strf, depth - 1, reductFact * len);
                    break;
                case 'X':
                    if (depth > 0)
                        turtleGraphics(g, strX, depth - 1, reductFact * len);
                    break;
                case 'Y':
                    if (depth > 0)
                        turtleGraphics(g, strY, depth - 1, reductFact * len);
                    break;


                case 'U':
                    if (depth > 0)
                        turtleGraphics(g, strU, depth - 1, reductFact * len);
                    break;
                case 'V':
                    if (depth > 0)
                        turtleGraphics(g, strV, depth - 1, reductFact * len);
                    break;


                case '+': // Turn right
                    dir -= rotation;
                    break;
                case '-':
                    dir += rotation;
                    break;
                case '[': // Save position and direction
                    xMark = xLast;
                    yMark = yLast;
                    dirMark = dir;
                    break;
                case ']': // Back to saved position and direction
                    xLast = xMark;
                    yLast = yMark;
                    dir = dirMark;
                    break;
            }
        }
    }
}