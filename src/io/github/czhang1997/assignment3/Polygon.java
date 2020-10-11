package io.github.czhang1997.assignment3;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * @Author: churongzhang
 * @Date: 10/8/20
 * @Time: 4:26 PM
 * @Info: this is a class to show how the point in polygon works
 * 1) Follow the same convention of CGDemo.java, with the major display window on the left and
 * user interaction features on the right. The display window should be a square, divided into NxN
 * grids. In the range of [10, 100], N can be set by the user on a scaler as shown in the figure.
 * 2) Adapt the “draw polygon using mouse” program in Chapter 1, so that every point clicked
 * inside the display window would automatically grip onto the nearest grid intersection (not
 * inside a grid square). Hints: this feature is commonly seen in a drawing software. Each polygon
 * drawn by the user is filled (with a color of your choice).
 *
 * 3) After the polygon is drawn, a prompt message “Please click inside or outside the polygon!”
 * should appear inside the display window. Once the user clicks, the above message disappears, a
 * ‘P’ is displayed near the point, and a red horizontal half-line is drawn from the point to the right
 * (as in the figure). On the right, Button “Inside” or “Outside” is highlight, depending on whether
 * the point is in or out of the polygon. The number of intersections of the half-line with the
 * polygon is then displayed at “No. of intersections:”. The user can select either of the two
 * conditions expressed as Yp value range as in the figure, and the selected condition is
 * highlighted.
 */
public class Polygon extends Frame {
    public static void main(String[] args) {
        new Polygon();
    }

    Polygon() {
        super("Point In Polygon");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setSize(801, 623);
        add("Center", new CanvasPloygon());
        setVisible(true);
    }
}

class CanvasPloygon extends Canvas {

    int maxX, maxY;
    ArrayList<Point> points;
    int scale;
    int topMargin;
    int leftMargin;
    int rightMargin;
    boolean polygonReady;
    int sliderMargin;
    int sliderWidth;
    int sliderOffset;
    int buttonHeight;
    Point point;
    int insideX;
    int insideY;
    int outSideX;
    int buttonWidth;
    int gridWidth;
    int intersectionY;
    int buttonMargin;
    int buttonMarginTop;

    // slider
    int sliderX;
    int sliderY;
    int sliderBoxX;
    int sliderBoxY;

    // use to generate the conditions on the rigght
    int localMinX;
    int localMinY;
    int localMaxX;
    int localMaxY;
    int localWidth;
    int localHeight;
    // count how many of each
    int localMinCount;
    int localMaxCount;
    boolean minSelected;

    CanvasPloygon() {
        points = new ArrayList<>();
        // define constants
        scale = 30;
        topMargin = 25;
        leftMargin = topMargin;
        rightMargin = 200;
        polygonReady = false;
        sliderMargin = 20;
        sliderWidth = 90;
        sliderOffset = 10;
        // inside outside button
        buttonMargin = 12;
        buttonMarginTop = 15;
        localWidth = 160;
        localHeight = 50;
        minSelected = true;

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                int x = event.getX();
                int y = event.getY();
                // check if a click was made inside the grid
                if (x > leftMargin && x < gridWidth && y > topMargin && y < gridWidth) {
                    addPointToList(x, y);
                } else { // check the point is slider, update the scale and reset it
                    if (x > sliderBoxX && x < sliderBoxX + sliderWidth && y > sliderBoxY && y < sliderBoxY + sliderOffset) {
                        scale = x - sliderBoxX + 10;
                        reset();
                    } else if (x >= localMinX && x <= localMinX + localWidth) {
                        // check if the click is made on the to find local min or max
                        if (y >= localMinY && y <= localMinY + localHeight) {
                            minSelected = true;
                        } else if (y >= localMaxY && y <= localMaxY + localHeight) {
                            minSelected = false;
                        }
                    }
                }
                repaint();
            }
        });
    }

    void initGraphic() {
        // if window size changed, calculate each variable again
        Dimension d = getSize();
        maxX = d.width - 1;
        maxY = d.height - 1;

        // define size for the grid
        int width = maxX - rightMargin;
        int height = maxY - topMargin;
        int lineWidth = (width - leftMargin) / scale;
        lineWidth = lineWidth * scale + leftMargin;
        int lineHeight = (height - topMargin) / scale;
        lineHeight = lineHeight * scale + topMargin;
        gridWidth = Math.min(lineWidth, lineHeight);

        // define size for slider
        sliderX = maxX - rightMargin + 10;
        sliderY = maxY - 3 * topMargin;
        sliderBoxX = sliderX + sliderMargin;
        sliderBoxY = sliderY - sliderOffset;

        // define size and position for inside outside button
        buttonWidth = 60;
        buttonHeight = 20;
        insideX = maxX - rightMargin + leftMargin - buttonMargin;
        insideY = topMargin * 2 - buttonMarginTop;
        outSideX = insideX + buttonWidth + buttonMargin;
        // define Y for the string number of intersection
        intersectionY = insideY + buttonHeight + 30;

        // define position for the two conditions
        int localMargin = 5;
        localMinX = insideX + localMargin;
        localMinY = intersectionY + 50;
        localMaxX = localMinX;
        localMaxY = localMinY + localHeight + 20;

    }

    /**
     * reset all points to draw a new polygon
     */
    void reset() {
        points.clear();
        polygonReady = false;
        point = null;
        localMinCount = 0;
        localMaxCount = 0;
    }

    /**
     * add a selected point to the list
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    void addPointToList(int x, int y) {
        // make sure it is within the grid
        if (x > leftMargin && x < gridWidth && y > topMargin && y < gridWidth) {
            // calculate the nearest position for this coordinate base on the scale
            double dX = Math.round((double) (x - leftMargin) / scale);
            double dY = Math.round((double) (y - topMargin) / scale);
            int xP = leftMargin + (int) (dX * scale);
            int yP = topMargin + (int) (dY * scale);
            // if a ploygon has been draw to the screen, then this point is the checking point
            if (polygonReady) {
                point = new Point(xP, yP);
                return;
            }
            // else check if this point is one of the existing point
            // if it is, then the polygon is ready to draw
            for (int i = 0; i < points.size(); i++) {
                if (points.get(i).x == xP && points.get(i).y == yP) {
                    polygonReady = true;
                    return;
                }
            }
            // if it is a new point then add to the list
            points.add(new Point(xP, yP));
        }
    }

    /**
     * draw the slider
     *
     * @param g the graphics to draw on
     */
    void drawSlider(Graphics g) {
        g.setColor(Color.black);
        g.drawString("10", sliderX, sliderY);
        g.drawRect(sliderBoxX, sliderBoxY, sliderWidth, sliderOffset);
        g.drawString("100", sliderX + sliderMargin + sliderWidth, sliderY);
        g.drawString("Grid Scale", sliderX + sliderMargin + sliderWidth / 4, sliderY + 2 * sliderOffset);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g.drawLine(sliderX + sliderMargin + scale - 10, sliderY - sliderOffset, sliderX + sliderMargin + scale - 10, sliderY - sliderOffset + 10);
        g2.setStroke(new BasicStroke(1));
    }

    /**
     * set up the scene, draw the grid
     *
     * @param g the graphic to draw on
     */
    void setUp(Graphics g) {
        int startX = leftMargin;
        int startY = topMargin;
        while (startX <= gridWidth) {
            g.drawLine(startX, startY, startX, gridWidth);
            startX += scale;
        }
        startX = leftMargin;
        while (startY <= gridWidth) {
            g.drawLine(startX, startY, gridWidth, startY);
            startY += scale;
        }
    }

    /**
     * draw the inside and outside button on the top right
     *
     * @param g the graphic to draw on
     */
    void drawInsideOutsideButton(Graphics g) {

        // draw the inside button
        g.setColor(localMinCount % 2 == 1 ? Color.green : Color.red);
        g.fillRect(insideX, insideY, buttonWidth, buttonHeight);
        g.setColor(Color.white);
        g.drawString("Inside", insideX + buttonMargin, insideY + buttonMarginTop);

        // draw the outside button
        g.setColor(localMinCount % 2 == 1 ? Color.red : Color.green);
        g.fillRect(outSideX, insideY, buttonWidth + 15, buttonHeight);
        g.setColor(Color.white);
        g.drawString("Outside", outSideX + buttonMargin, insideY + buttonMarginTop);

        g.setColor(Color.black);
    }

    /**
     * draw the text label "No. of intersections: "
     *
     * @param g     the graphic to draw on
     * @param count the number to added to the end
     */
    void drawIntersectionText(Graphics g, int count) {
        g.setColor(Color.black);
        g.drawString("No. of intersections: " + count, insideX, intersectionY);
    }

    /**
     * draw the two conditions to get local min or max
     *
     * @param g the graphic to draw on
     */
    void drawConditions(Graphics g) {
        Color c = g.getColor();
        g.setColor(minSelected ? Color.red : c);
        g.drawRect(localMinX, localMinY, localWidth, localHeight);
        int conditionSpace = 15;
        g.drawString("yi <= yP < yi+1 or", localMinX + conditionSpace, localMinY + conditionSpace);
        g.drawString("yi+1 <= yP < yi", localMinX + conditionSpace, localMinY + 2 * conditionSpace);
        // check if need to update the color
        g.setColor(minSelected ? c : Color.red);
        g.drawRect(localMaxX, localMaxY, localWidth, localHeight);
        g.drawString("yi < yP <= yi+1 or", localMaxX + conditionSpace, localMaxY + conditionSpace);
        g.drawString("yi+1 < yP <= yi", localMaxX + conditionSpace, localMaxY + 2 * conditionSpace);
        g.setColor(Color.BLUE);
        g.drawString("Blue lines = intersection at", localMaxX, localMaxY + localHeight + 20);
        g.setColor(c);
    }

    /**
     * the paint function to paint on the canvas
     *
     * @param g the graohic to draw on
     */
    public void paint(Graphics g) {
        // initial variable base on screen size
        initGraphic();
        // draw the slider and grid
        drawSlider(g);
        setUp(g);
        // if points is not empty
        if (!points.isEmpty()) {
            g.setColor(Color.green);
            Graphics2D g2 = (Graphics2D) g;
            // check if a polygon is ready to draw
            if (polygonReady) {
                // draw the ploygon first
                int[] xArr = new int[points.size()];
                int[] yArr = new int[points.size()];
                for (int i = 0; i < points.size(); i++) {
                    xArr[i] = points.get(i).x;
                    yArr[i] = points.get(i).y;
                }
                g.fillPolygon(xArr, yArr, points.size());
                // check if a point is selected
                if (point != null) {
                    // draw the horizontal line
                    g.setColor(Color.red);
                    g2.setStroke(new BasicStroke(2));
                    g.drawLine(point.x, point.y, gridWidth, point.y);
                    // draw a "P" beside the line
                    g.setColor(Color.black);
                    g.drawString("P", point.x - sliderOffset, point.y - sliderOffset);
                    // draw the local max min conditions
                    drawConditions(g);
                    // count how many local max and min
                    countLocalMinMax(g2);
                    // check if the point is inside or outside the polyggon
                    drawInsideOutsideButton(g);
                    // draw how many intersections
                    drawIntersectionText(g, minSelected ? localMinCount : localMaxCount);
                } else {
                    // else ask the user to click to enter a point to the grid
                    g.setColor(Color.black);
                    String p = "Please click inside or outside the polygon!";
                    g.drawString(p, 20, 20);
                }
            } else {
                // draw the points that the user enter to form the polygon
                g2.setStroke(new BasicStroke(5));
                for (int i = 0; i < points.size(); i++) {
                    g2.draw(new Line2D.Float(points.get(i).x, points.get(i).y, points.get(i).x, points.get(i).y));
                }
            }
        }
    }

    /**
     * count how many local min and max on this point
     * @param g2 use this g2 to draw the line the form the intersection
     */
    void countLocalMinMax(Graphics2D g2) {
        localMinCount = 0;
        localMaxCount = 0;
        int n = points.size();
        int j = n - 1;
        int y = point.y;
        g2.setColor(Color.blue);
        g2.setStroke(new BasicStroke(2));
        // loop through each point, since current Y is upside down
        // then min will become max, max will become min
        for (int i = 0; i < n; i++) {
            // get the two adjacent points
            Point pj = points.get(j);
            Point pi = points.get(i);
            // check if it is a max point
            if (pj.y <= y && y < pi.y &&
                    area2(pj, pi, point) > 0 ||
                    pi.y <= y && y < pj.y &&
                            area2(pi, pj, point) > 0) {
                localMaxCount++;
                if (!minSelected) {
                    g2.draw(new Line2D.Float(pi.x, pi.y, pj.x, pj.y));
                }

            }
            // check if it is a min point
            if (pj.y < y && y <= pi.y &&
                    area2(pj, pi, point) > 0 ||
                    pi.y < y && y <= pj.y &&
                            area2(pi, pj, point) > 0) {
                localMinCount++;
                if (minSelected) {
                    g2.draw(new Line2D.Float(pi.x, pi.y, pj.x, pj.y));
                }
            }
            j = i;
        }
        g2.setStroke(new BasicStroke(1));
    }

    /**
     * fine the area of 3 points
     * @param A Point A
     * @param B Point B
     * @param C Point C
     * @return the area
     */
    int area2(Point A, Point B, Point C) {
        return (A.x - C.x) * (B.y - C.y) - (A.y - C.y) * (B.x - C.x);
    }
}

/**
 * Point class that have the x-coordinate and y-coordinate
 */
class Point {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
