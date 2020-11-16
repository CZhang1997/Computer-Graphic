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
        new FractalGrammars("dragon.txt");
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

    Double xpoint, ypoint;

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

//      strU = inp.readString(); inp.skipRest();
//      strV = inp.readString(); inp.skipRest();

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

    void drawTo(Graphics g, double x, double y) {
//        ((Graphics2D)g).setStroke(new BasicStroke((int)(Math.random() * 5 + 1)));
        g.drawLine(iX(xLast), iY(yLast), iX(x), iY(y));
        xLast = x;
        yLast = y;
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        dir = dirStart; // Initial direction in degrees
        turtleGraphics(g, axiom, level, lengthFract * maxY);
//        if(xpoint != null){
//
//        }
    }

//    String getStringPattern(String basic, int level){
//        if(level == 0){
//            return
//        }
//    }

    public void turtleGraphics(Graphics g, String instruction,
                               int depth, double len) {
        double xMark = 0, yMark = 0, dirMark = 0;
        double partial = 0.8;
        for (int i = 0; i < instruction.length(); i++) {
            char ch = instruction.charAt(i);
            double rad = Math.PI / 180 * dir, // Degrees -> radians
                    dx = len * Math.pow(reductFact, depth) * Math.cos(rad), dy = len * Math.pow(reductFact, depth) * Math.sin(rad);
            if ((i + 1 == instruction.length()) || ((instruction.charAt(i + 1) == '-' || instruction.charAt(i + 1) == '+') && instruction.charAt(i) == 'F')) {
                xpoint = xLast + dx;
                ypoint = yLast + dy;
                dx *= partial;
                dy *= partial;
            }
            switch (ch) {
                case 'F': // Step forward and draw
                    // Start: (xLast, yLast), direction: dir, steplength: len
                    if (depth == 0) {
                            drawTo(g, xLast + dx, yLast + dy);
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
                    double rad2 = Math.PI / 180 * (dir), // Degrees -> radians
                            dx2 = len * Math.pow(reductFact, depth) * Math.cos(rad2), dy2 = len* Math.pow(reductFact, depth) * Math.sin(rad2);
                    double xp3 = xpoint + dx2 * (1 - partial);
                    double yp3 = ypoint + dy2 * (1 - partial);
                    if(i > 0 && instruction.charAt(i - 1) == 'F' && i != instruction.length() - 1){
                        drawTo(g, xp3, yp3);
                    }
                    break;
                case '-':
                    dir += rotation;
                    double rad3 = Math.PI / 180 * (dir), // Degrees -> radians
                            dx3 = len * Math.pow(reductFact, depth) * Math.cos(rad3), dy3 = len * Math.pow(reductFact, depth) * Math.sin(rad3);
                    double xp32 = xpoint + dx3 * (1 - partial);
                    double yp32 = ypoint + dy3 * (1 - partial);
                    if(i > 0 && instruction.charAt(i - 1) == 'F'&& i != instruction.length() - 1) {
                        drawTo(g, xp32, yp32);
                    }
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