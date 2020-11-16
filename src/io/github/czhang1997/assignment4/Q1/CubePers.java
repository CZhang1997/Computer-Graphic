package io.github.czhang1997.assignment4.Q1;

/**
 * As Exercise 5.4, but the rotation is to be applied to the two cubes of Exercise
 * 5.3. Use different axes of rotation and increase the rotation angles for the two
 * cubes by different amounts, so that the cubes seem to rotate independently of
 * each other, with different speeds. Figure 5.25 shows a snapshot of the two
 * cubes, each rotating about one of its vertical edges.
 * In the following exercises you are asked to generate input files as discussed
 * in Sect. 5.5. The illustrations below, however, were obtained using program
 * HLines of Chap. 6. You can also try this program to get better graphical results
 * with it than with program Wireframe of Sect. 5.7. The same applies to the two
 * hidden-face programs Painter and ZBuf, also discussed in Chap. 6.
 */
// CubePers.java: A cube in perspective.
// Uses: Point2D (Section 1.4), Point3D (Section 3.9).
import java.awt.*;
import java.awt.event.*;

public class CubePers extends Frame {
    public static void main(String[] args) {
        new CubePers();
    }

    CubePers() {
        super("A cube in perspective");
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        Dimension dim = getSize();
        setSize(500, 300);
//        setLocation(dim.width / 4, dim.height / 4);
        add("Center", new CvCubePers());
        setVisible(true);
    }
}

class CvCubePers extends Canvas implements Runnable {
    int centerX, centerY, centerX2;
    Obj obj;
    Obj obj2;
    Thread thr = new Thread(this);
    float theta;
    float theta2;
    float phi;
    float phi2;

    CvCubePers() {

        theta = 0.3F;
        theta2 = 0.55F;
        phi = 1.3F;
        phi2 = 1.1F;
        obj = new Obj(theta, phi);
        obj2 = new Obj(theta2, phi2);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                obj.rotate(0.15, 2,4);
                repaint();
                Thread.sleep(50);
                obj2.rotate(-0.1, 1, 7);
                repaint();
                Thread.sleep(20);
            }
        } catch (InterruptedException e) {
        }
    }

    int iX(float x) {
        return Math.round(centerX + x);
    }

    int iY(float y) {
        return Math.round(centerY - y);
    }

    int iX2(float x) {
        return Math.round(centerX2 + x);
    }

    void line(Graphics g, int i, int j) {
        Point2D p = obj.vScr[i], q = obj.vScr[j];
        Point2D p2 = obj2.vScr[i], q2 = obj2.vScr[j];
        g.drawLine(iX(p.x), iY(p.y), iX(q.x), iY(q.y));
        g.drawLine(iX2(p2.x), iY(p2.y), iX2(q2.x), iY(q2.y));
    }

    public void paint(Graphics g) {
        Dimension dim = getSize();
        int maxX = dim.width - 1, maxY = dim.height - 1,
                minMaxXY = Math.min(maxX, maxY) / 2;

        centerX = maxX / 4;
        centerX2 = maxX / 4 * 3;
        centerY = maxY / 2;
        obj.d = obj.rho * minMaxXY / obj.objSize;
        obj.eyeAndScreen();
        obj2.d = obj2.rho * minMaxXY / obj2.objSize;
        obj2.eyeAndScreen();
// Horizontal edges at the bottom:
        line(g, 0, 1);
        line(g, 1, 2);
        line(g, 2, 3);
        line(g, 3, 0);
// Horizontal edges at the top:
        line(g, 4, 5);
        line(g, 5, 6);
        line(g, 6, 7);
        line(g, 7, 4);
// Vertical edges:
        line(g, 0, 4);
        line(g, 1, 5);
        line(g, 2, 6);
        line(g, 3, 7);
    }
}

class Obj { // Contains 3D object data
    float rho, theta = 0.3F, phi = 1.3F, d, objSize,
            v11, v12, v13, v21, v22, v23, v32, v33, v43;
    // Elements of viewing matrix V
    Point3D[] w; // World coordinates
    Point2D[] vScr; // Screen coordinates

    Obj() {
        w = new Point3D[8];
        vScr = new Point2D[8];
// Bottom surface:
        w[0] = new Point3D(1, -1, -1);
        w[1] = new Point3D(1, 1, -1);
        w[2] = new Point3D(-1, 1, -1);
        w[3] = new Point3D(-1, -1, -1);
// Top surface:
        w[4] = new Point3D(1, -1, 1);
        w[5] = new Point3D(1, 1, 1);
        w[6] = new Point3D(-1, 1, 1);
        w[7] = new Point3D(-1, -1, 1);
        objSize = (float) Math.sqrt(12F);
// = sqrt(2 * 2 + 2 * 2 + 2 * 2)
// = distance between two opposite vertices.
        rho = 5 * objSize; // For reasonable perspective effect
    }

    Obj(float theta, float phi) {
        this.theta = theta;
        this.phi = phi;
        w = new Point3D[8];
        vScr = new Point2D[8];
// Bottom surface:
        w[0] = new Point3D(1, -1, -1);
        w[1] = new Point3D(1, 1, -1);
        w[2] = new Point3D(-1, 1, -1);
        w[3] = new Point3D(-1, -1, -1);
// Top surface:
        w[4] = new Point3D(1, -1, 1);
        w[5] = new Point3D(1, 1, 1);
        w[6] = new Point3D(-1, 1, 1);
        w[7] = new Point3D(-1, -1, 1);
        objSize = (float) Math.sqrt(12F);
// = distance between two opposite vertices.
        rho = 5 * objSize; // For reasonable perspective effect
    }

    void initPersp() {
        float costh = (float) Math.cos(theta),
                sinth = (float) Math.sin(theta),
                cosph = (float) Math.cos(phi),
                sinph = (float) Math.sin(phi);
        v11 = -sinth;
        v12 = -cosph * costh;
        v13 = sinph * costh;
        v21 = costh;
        v22 = -cosph * sinth;
        v23 = sinph * sinth;
        v32 = sinph;
        v33 = cosph;
        v43 = -rho;
    }

    void rotate(double alpha, int index1, int index2) {
        Rota3D.initRotate(w[index1], w[index2], alpha);
        for (int i = 0; i < w.length; i ++) {
                w[i] = Rota3D.rotate(w[i]);
        }
    }

    void eyeAndScreen() {
        initPersp();
        for (int i = 0; i < 8; i++) {
            Point3D p = w[i];
            float x = v11 * p.x + v21 * p.y,
                    y = v12 * p.x + v22 * p.y + v32 * p.z,
                    z = v13 * p.x + v23 * p.y + v33 * p.z + v43;
            vScr[i] = new Point2D(-d * x / z, -d * y / z);
        }
    }
}