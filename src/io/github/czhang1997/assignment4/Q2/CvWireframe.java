package io.github.czhang1997.assignment4.Q2;

/**
 * @Author: churongzhang
 * @Date: 10/19/20
 * @Time: 3:05 PM
 * @Info:
 */
// CvWireframe.java: Canvas class for class Wireframe.


import java.awt.*;
import java.util.*;
class CvWireframe extends Canvas3D {
    private int maxX, maxY, centerX, centerY;
    private Obj3D obj;
    private Point2D imgCenter;
    Obj3D getObj() {return obj;}
    void setObj(Obj3D obj) {this.obj = obj;}
    int iX(float x) {return Math.round(centerX + x - imgCenter.x);}
    int iY(float y) {return Math.round(centerY - y + imgCenter.y);}
    public void paint(Graphics g) {
        if (obj == null) return;
        Vector<Polygon3D> polyList = obj.getPolyList();
        if (polyList == null) return;
        int nFaces = polyList.size();
        if (nFaces == 0) return;
        Dimension dim = getSize();
        maxX = dim.width - 1; maxY = dim.height - 1;
        centerX = maxX / 2; centerY = maxY / 2;
// ze-axis towards eye, so ze-coordinates of
// object points are all negative.
// obj is a java object that contains all data:
// - Vector w (world coordinates)
// - Array e (eye coordinates)
// - Array vScr (screen coordinates)
// - Vector polyList (Polygon3D objects)
// Every Polygon3D value contains:
// - Array ’nrs’ for vertex numbers
// - Values a, b, c, h for the plane ax+by+cz=h.
// (- Array t (with nrs.length-2 elements of type Tria))
        obj.eyeAndScreen(dim);
// Computation of eye and screen coordinates.
        imgCenter = obj.getImgCenter();
        obj.planeCoeff(); // Compute a, b, c and h.
        Point2D[] vScr = obj.getVScr();
        g.setColor(Color.black);
        for (int j = 0; j < nFaces; j++) {
            Polygon3D pol = polyList.elementAt(j);
            int nrs[] = pol.getNrs();
            for (int iA = 0; iA < nrs.length; iA++) {
                int iB = (iA + 1) % nrs.length;
                int na = Math.abs(nrs[iA]), nb = nrs[iB];
// abs in view of minus signs discussed in Section 5.5.
                if (nb >= 0) {
                    Point2D a = vScr[na], b = vScr[nb];
                    g.drawLine(iX(a.x), iY(a.y), iX(b.x), iY(b.y));
                }
            }
        }
    }
}