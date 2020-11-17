package io.github.czhang1997.assignment5.Q2;

/**
 * @Author: churongzhang
 * @Date: 11/15/20
 * @Time: 3:18 PM
 * @Info:
 */

//import io.github.czhang1997.assignment5.Q1.Input;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

public class FractalTree extends Frame {
    public static void main(String[] args) {
//        if (args.length == 0)
//            System.out.println("Use filename as program argument.");
//        else
        new FractalTree("tree.txt");
    }

    FractalTree(String fileName) {
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

    final int ANGLE_MARGIN = 20;
    String strU, strV;

    int maxX, maxY, level = 4;
    double xLast, yLast, dir, rotation, dirStart, fxStart, fyStart,
            lengthFract, reductFact;
    HashMap<Point, Double> map;
    Stack<Double> stack;

    void error(String str) {
        System.out.println(str);
        System.exit(1);
    }

    CvFractalGrammars(String fileName) {
        map = new HashMap<>();
        stack = new Stack<>();
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

    void drawTo(Graphics g, double x, double y) {

        Graphics2D g2 = (Graphics2D) g;
        int xD = iX(xLast);
        int yD = iY(yLast);
        double sWidth = map.getOrDefault(new Point(xD, yD), 1.0);
        g2.setStroke(new BasicStroke((int) sWidth));
        g.drawLine(xD, yD, iX(x), iY(y));
        xLast = x;
        yLast = y;
        Point newP = new Point(iX(xLast), iY(yLast));
        if (!map.containsKey(newP)) {
            double newWidth = sWidth - 1;
            if (newWidth <= 0) {
                map.put(newP, 1.0);
            } else {
                map.put(newP, newWidth);
            }
        }
    }

    void moveTo(Graphics g, double x, double y) {
        xLast = x;
        yLast = y;
    }

    public void paint(Graphics g) {
        Dimension d = getSize();
        g.setColor(Color.gray);
        maxX = d.width - 1;
        maxY = d.height - 1;
        xLast = fxStart * maxX;
        yLast = fyStart * maxY;
        dir = dirStart; // Initial direction in degrees
        map.clear();
        map.put(new Point(iX(xLast), iY(yLast)), (double) (level * 8));
        turtleGraphics(g, axiom, level, lengthFract * maxY);
    }

    public void turtleGraphics(Graphics g, String instruction,
                               int depth, double len) {
        double xMark = 0, yMark = 0, dirMark = 0;
        for (int i = 0; i < instruction.length(); i++) {
            char ch = instruction.charAt(i);
            switch (ch) {
                case 'F': // Step forward and draw
                    // Start: (xLast, yLast), direction: dir, steplength: len
                    if (depth == 0) {
                        double rad = Math.PI / 180 * dir, // Degrees -> radians
                                dx = len * Math.cos(rad), dy = len * Math.sin(rad);
                        drawTo(g, xLast + dx, yLast + dy);
                    } else {
                        turtleGraphics(g, strF, depth - 1, reductFact * len);
                    }
                    break;
                case 'f': // Step forward without drawing
                    // Start: (xLast, yLast), direction: dir, steplength: len
                    if (depth == 0) {
                        double rad = Math.PI / 180 * dir, // Degrees -> radians
                                dx = len * Math.cos(rad), dy = len * Math.sin(rad);
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
                    dir -= (rotation - ANGLE_MARGIN / 2 + Math.random() * ANGLE_MARGIN + 1);
                    break;
                case '-': // Turn left
                    dir += (rotation - ANGLE_MARGIN / 2 + Math.random() * ANGLE_MARGIN + 1);
                    break;
                case '[': // Save position and direction
                    xMark = xLast;
                    yMark = yLast;
                    dirMark = dir;
                    Point pMain = new Point(iX(xLast), iY(yLast));
                    double mainWidth = map.get(pMain);
                    stack.push(mainWidth);
                    map.put(pMain, mainWidth * 0.7);
                    break;
                case ']': // Back to saved position and direction
                    xLast = xMark;
                    yLast = yMark;
                    dir = dirMark;
                    Point pMain2 = new Point(iX(xLast), iY(yLast));
                    double mainWidth2 = stack.pop();
                    map.put(pMain2, mainWidth2);
                    break;
            }
        }
        // draw the leaves
        int leaveWidth = 10;
        int leaveHeight = 8;
        g.setColor(Color.green);
        g.fillOval(iX(xLast) - (leaveWidth / 2), iY(yLast) - leaveHeight + 1, leaveWidth, leaveHeight);
        g.setColor(Color.gray);
    }
}

class Point {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}