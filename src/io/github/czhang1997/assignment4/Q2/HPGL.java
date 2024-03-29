package io.github.czhang1997.assignment4.Q2;

/**
 * @Author: churongzhang
 * @Date: 10/19/20
 * @Time: 3:00 PM
 * @Info:
 */
// HPGL.java: Class for export of HP-GL files.
import java.io.*;
class HPGL {
    FileWriter fw;
    HPGL(Obj3D obj) {
        String plotFileName = "", fName = obj.getFName();
        for (int i = 0; i < fName.length(); i++) {
            char ch = fName.charAt(i);
            if (ch == '.') break;
            plotFileName += ch;
        }
        plotFileName += ".plt";
        try {
            fw = new FileWriter(plotFileName);
            fw.write("IN;SP1;\n");
        }
        catch (IOException ioe) {
        }
    }
    void write(String s) {
        try {fw.write(s); fw.flush();} catch (IOException ioe) {}
    }
}