package io.github.czhang1997.assignment4.Q2;// Polygon3D.java: Polygon in 3D, represented by vertex numbers
//                 referring to coordinates stored in an Obj3D object.
// Uses: Point2D, Tools2D, Tria and Obj3D.

/* CGDemo is a companion of the textbook

L. Ammeraal and K. Zhang, Computer Graphics for Java Programmers, 
2nd Edition, Wiley, 2006.

Copyright (C) 2006  Janis Schubert, Kang Zhang, Leen Ammeraal 

This program is free software; you can redistribute it and/or 
modify it under the terms of the GNU General Public License as 
published by the Free Software Foundation; either version 2 of 
the License, or (at your option) any later version. 

This program is distributed in the hope that it will be useful, 
but WITHOUT ANY WARRANTY; without even the implied warranty of 
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  
See the GNU General Public License for more details.  

You should have received a copy of the GNU General Public 
License along with this program; if not, write to 
the Free Software Foundation, Inc., 51 Franklin Street, 
Fifth Floor, Boston, MA  02110-1301, USA. 
*/

// Polygon3D.java: Polygon in 3D, represented by vertex numbers
// referring to coordinates stored in an Obj3D object.
// Uses: Point2D (Section 1.4), Tria, Obj3D( Section 5.6).
import java.util.*;
class Polygon3D {
   private int[] nrs;
   private double a, b, c, h;
   private Tria[] t;
   Polygon3D(Vector<Integer> vnrs) {
      int n = vnrs.size();
      nrs = new int[n];
      for (int i = 0; i < n; i++)
         nrs[i] = ((Integer) vnrs.elementAt(i)).intValue();
   }
   int[] getNrs() {return nrs;}
   double getA() {return a;}
   double getB() {return b;}
   double getC() {return c;}
   double getH() {return h;}
   void setAbch(double a, double b, double c, double h) {
      this.a = a; this.b = b; this.c = c; this.h = h;
   }
   Tria[] getT() {return t;}
   Tria[] triangulate(Obj3D obj) {
// Successive vertex numbers (CCW) in vector nrs.
// Resulting triangles will be put in array t.
      Point2D[] vScr = obj.getVScr();
      Polygon2D polygon = new Polygon2D(vScr, nrs);
      t = polygon.triangulate();
      return t;
   }
}