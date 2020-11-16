package io.github.czhang1997.assignment4.Q2;

/**
 * @Author: churongzhang
 * @Date: 10/19/20
 * @Time: 2:56 PM
 * @Info:
 */
// Canvas3D.java: Abstract class.
import java.awt.*;
abstract class Canvas3D extends Canvas {
    abstract Obj3D getObj();
    abstract void setObj(Obj3D obj);
    boolean specularDesired; // Not used until Chapter 6
}