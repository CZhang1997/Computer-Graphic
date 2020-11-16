//package io.github.czhang1997.assignment5;
////
/////**
//// * @Author: churongzhang
//// * @Date: 11/15/20
//// * @Time: 2:53 PM
//// * @Info:
//// */
////
//
//import java.io.Serializable;
//
//import java.util.HashMap;
//
//import java.util.Stack;
//
//import java.util.Vector;
//
//@SuppressWarnings("unchecked")
//
//public class Fractal implements Serializable{
//
//    private static final long serialVersionUID = -3281917256291850681L;
//
//    private String axiom;
//
//    private HashMap<String, String> grammar;
//
//    private double incrementAngle;
//
//    private int iteration;
//
//    private BoundingRectangle bounds;
//
//    private Turtle turtle;
//
//    private String description;
//
//    private Vector<double[]> segmentList;
//
//    private boolean valid;
//
//    public Fractal() {
//
//        axiom = null;
//
//        grammar = new HashMap<String, String>();
//
//        incrementAngle = 0;
//
//        iteration = 0;
//
//        bounds = new BoundingRectangle();
//
//        turtle = new Turtle();
//
//        description = null;
//
//        segmentList = null;
//
//        valid = false;
//
//    }
//
//    public Fractal(String axiom, String rules, double incrementAngle, int iteration) {
//
//        setAxiom(axiom);
//
//        grammar = new HashMap<String, String>();
//
//        setRules(rules);
//
//        setIncrementAngle(incrementAngle);
//
//        setIteration(iteration);
//
//        bounds = new BoundingRectangle();
//
//        turtle = new Turtle();
//
//        description = null;
//
//        segmentList = null;
//
//        valid = false;
//
//    }
//
//    public boolean setAxiom(String axiom) {
//
//        axiom = axiom.replaceAll("[ \t\n\f\r]", "");
//
//        if(isAxiom(axiom)) {
//
//            this.axiom = axiom;
//
//            this.valid = false;
//
//        } else {
//
//            return false;
//
//        }
//
//        return true;
//
//    }
//
//    public String getAxiom() {
//
//        return this.axiom;
//
//    }
//
//    public boolean setRules(String rules) {
//
//        HashMap<String, String> saved = (HashMap<String, String>) this.grammar.clone();
//
//        String rule[], pred_succ[];
//
//        rule = rules.split("\n");
//
//        grammar.clear();
//
//        for(int i = 0; i < rule.length; i++) {
//
//            rule[i] = rule[i].replaceAll("[ \t\n\f\r]", "");
//
//            pred_succ = rule[i].split("=");
//
//            if(pred_succ.length != 2) {
//
//                grammar = saved;
//
//                return false;
//
//            }
//
//            if(isPredecessor(pred_succ[0]) && isSuccessor(pred_succ[1])) {
//
//                grammar.put(pred_succ[0], pred_succ[1]);
//
//                valid = false;
//
//            } else {
//
//                grammar = saved;
//
//                return false;
//
//            }
//
//        }
//
//        return true;
//
//    }
//
//    public boolean setRule(String pred, String succ) {
//
//        pred = pred.replaceAll("[ \t\n\f\r]", "");
//
//        succ = succ.replaceAll("[ \t\n\f\r]", "");
//
//        if(isPredecessor(pred) && isSuccessor(succ)) {
//
//            grammar.put(pred, succ);
//
//            valid = false;
//
//        } else {
//
//            return false;
//
//        }
//
//        return true;
//
//    }
//
//    public String getRule(String pred) {
//
//        pred = pred.replaceAll("[ \t\n\f\r]", "");
//
//        if(isPredecessor(pred)) {
//
//            grammar.remove(pred);
//
//            pred = pred.substring(0, 1);
//
//            return grammar.get(pred);
//
//        } else {
//
//            return null;
//
//        }
//
//    }
//
//    public boolean removeRule(String pred) {
//
//        pred = pred.replaceAll("[ \t\n\f\r]", "");
//
//        if(isPredecessor(pred)) {
//
//            grammar.remove(pred);
//
//            valid = false;
//
//        } else {
//
//            return false;
//
//        }
//
//        return true;
//
//    }
//
//    public boolean setIncrementAngle(double incrementAngle) {
//
//        if(incrementAngle < 0 || incrementAngle > 360)
//
//            return false;
//
//        this.incrementAngle = incrementAngle;
//
//        valid = false;
//
//        return true;
//
//    }
//
//        return incrementAngle;
//
//}
//
//    public boolean setIteration(int iteration) {
//
//        if(iteration < 0) {
//
//            return false;
//
//        }
//
//        this.iteration = iteration;
//
//        valid = false;
//
//        return true;
//
//    }
//
//    public int getIteration() {
//
//        return this.iteration;
//
//    }
//
//    private boolean isAxiom(String s) {
//
//        return (isSuccessor(s) && s.length() > 0);
//
//    }
//
//    private boolean isPredecessor(String s) {
//
//        if(s != null && s.length() == 1 && Character.isLetter(s.charAt(0)))
//
//            return true;
//
//        else
//
//            return false;
//
//    }
//
//    private boolean isSuccessor(String s) {
//
//        if(s == null)
//
//            return false;
//
//        for(int i = 0; i < s.length(); i++) {
//
//            char c = s.charAt(i);
//
//            if(!Character.isLetter(c) && c != '+' && c != '-' && c != '[' && c != ']')
//
//                return false;
//
//        }
//
//        return true;
//
//)    public double getX() {
//
//            return bounds.x;
//
//        }
//
//        public double getY() {
//
//            return bounds.y;
//
//        }
//
//        public double getWidth() {
//
//            return bounds.width;
//
//        }
//
//        public double getHeight() {
//
//            return bounds.height;
//
//        }
//
//        public Vector<double[]> getSegmentList() {
//
//            /* If the segmentList does not correspond to the current         * fractal properties, update it. */
//
//            if(!valid)
//
//                update();
//
//            return segmentList;
//
//        }
//
//        if(!valid)
//
//            update();
//
//        return description;
//
//    }
//
//    private void update() {
//
//        /* If the fractal's segmentList is up-to-date, return now */
//
//        if(valid)
//
//            return;
//
//        Stack<Turtle> st = new Stack<Turtle>();
//
//        productionProcess();
//
//        turtle.setPosition(0, 0, 180);
//
//        segmentList = new Vector<double[]>();
//
//        bounds.setBounds(0, 0, 0, 0);
//
//        for(int i = 0; i < description.length(); i++) {
//
//            char c = description.charAt(i);
//
//            if(Character.isLetter(c) && Character.isLowerCase(c)) {
//
//                double[] line = turtle.moveForward();
//
//                if(line == null) {
//
//                    bounds.setBounds(0, 0, 0, 0);
//
//                    segmentList = null;
//
//                    valid = true;
//
//                    return;
//
//                }
//
//                segmentList.add(line);
//
//                bounds.add(line[2], line[3]);
//
//            } else if(c == '+') {
//
//                turtle.turn(-incrementAngle);
//
//            } else if(c == '-') {
//
//                turtle.turn(incrementAngle);
//
//            } else if(c == '[') {
//
//                st.push(turtle);
//
//                turtle = new Turtle(turtle);
//
//            } else if(c == ']') {
//
//                turtle = st.pop();
//
//            }
//
//        }
//
//        valid = true;
//
//    }
//
//    private void productionProcess() {
//
//        String gen, next_gen, pred, succ;
//
//        gen = axiom;
//
//        for(int i = 0; i < iteration; i++) {
//
//            next_gen = new String("");
//
//            for(int j = 0; j < gen.length(); j++) {
//
//                pred = gen.substring(j, j+1);
//
//                succ = grammar.get(pred);
//
//                if(succ != null) {
//
//                    next_gen = next_gen.concat(succ);
//
//                } else {
//
//                    next_gen = next_gen.concat(pred);
//
//                }
//
//            }
//
//            gen = next_gen;
//
//        }
//
//        description = gen;
//
//    }
//
//}
//
//class Turtle implements Serializable{
//
//    /**
//
//     *
//
//     */
//
//    private static final long serialVersionUID = -5406692716427019724L;
//
//    private double x;
//
//    private double y;
//
//    private double angle;
//
//    public Turtle() {
//
//        x = y = angle = 0;
//
//    }
//
//    public Turtle(double x, double y, double angle) {
//
//        this.setPosition(x, y, angle);
//
//    }
//
//    public Turtle(Turtle t) {
//
//        this.setPosition(t.x, t.y, t.angle);
//
//    }
//
//    public void setPosition(double x, double y, double angle) {
//
//        this.x = x;
//
//        this.y = y;
//
//        this.angle = angle;
//
//    }
//
//    public double[] moveForward() {
//
//        double deltax, deltay, cos, sin;
//
//        double line[];
//
//        cos = Math.cos(Math.toRadians(angle));
//
//        if(Double.isNaN(cos) || Double.isInfinite(cos))
//
//            return null;
//
//        deltax = x + cos;
//
//        if(Double.isNaN(deltax) || Double.isInfinite(deltax))
//
//            return null;
//
//        sin = Math.sin(Math.toRadians(angle));
//
//        if(Double.isNaN(sin) || Double.isInfinite(sin))
//
//            return null;
//
//        deltay = y + sin;
//
//        if(Double.isNaN(deltay) || Double.isInfinite(deltay))
//
//            return null;
//
//        line = new double[4];
//
//        line[0] = x;
//
//        line[1] = y;
//
//        line[2] = deltax;
//
//        line[3] = deltay;
//
//        x = deltax;
//
//        y = deltay;
//
//        return line;
//
//    }
//
//    public void turn(double angle) {
//
//        this.angle += angle;
//
//    }
//
//}
//
//class BoundingRectangle implements Serializable{
//
//    private static final long serialVersionUID = 7379298422629492148L;
//
//    public double x;
//
//    public double y;
//
//    public double width;
//
//    public double height;
//
//    public BoundingRectangle() {
//
//        x = y = width = height = 0;
//
//    }
//
//    public BoundingRectangle(double x, double y, double w, double h) {
//
//        setBounds(x, y, w, h);
//
//    }
//
//    public void setBounds(double x, double y, double w, double h) {
//
//        this.x = x;
//
//        this.y = y;
//
//        this.width = w;
//
//        this.height = h;
//
//    }
//
//    public void add(double x, double y) {
//
//        if(in(x,y)) {
//
//            return;
//
//        }
//
//        if(x < this.x) {
//
//            this.width += this.x - x;
//
//            this.x = x;
//
//        } else if(x > this.x + this.width) {
//
//            this.width = x - this.x;
//
//        }
//
//        if(y < this.y) {
//
//            this.height += this.y - y;
//
//            this.y = y;
//
//        } else if(y > this.y + this.height) {
//
//            this.height = y - this.y;
//
//        }
//
//    }
//
//    public boolean in(double x, double y) {
//
//        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
//
//    }
//
//}
//
////import java.io.Serializable;
////
////import java.util.HashMap;
////
////import java.util.Stack;
////
////import java.util.Vector;
////
////@SuppressWarnings("unchecked")
////
////public class Fractal implements Serializable {
////
////    private static final long serialVersionUID = -3281917256291850681L;
////
////    private String axiom;
////
////    private HashMap<String, String> grammar;
////
////    private double incrementAngle;
////
////    private int iteration;
////
////    private BoundingRectangle bounds;
////
////    private Turtle turtle;
////
////    private String description;
////
////    private Vector<double[]> segmentList;
////
////    private boolean valid;
////
////    public Fractal() {
////
////        axiom = null;
////
////        grammar = new HashMap<String, String>();
////
////        incrementAngle = 0;
////
////        iteration = 0;
////
////        bounds = new BoundingRectangle();
////
////        turtle = new Turtle();
////
////        description = null;
////
////        segmentList = null;
////
////        valid = false;
////
////    }
////
////    public Fractal(String axiom, String rules, double incrementAngle, int iteration) {
////
////        setAxiom(axiom);
////
////        grammar = new HashMap<String, String>();
////
////        setRules(rules);
////
////        setIncrementAngle(incrementAngle);
////
////        setIteration(iteration);
////
////        bounds = new BoundingRectangle();
////
////        turtle = new Turtle();
////
////        description = null;
////
////        segmentList = null;
////
////        valid = false;
////
////    }
////
////    public boolean setAxiom(String axiom) {
////
////        axiom = axiom.replaceAll("[ \t\n\f\r]", "");
////
////        if (isAxiom(axiom)) {
////
////            this.axiom = axiom;
////
////            this.valid = false;
////
////        } else {
////
////            return false;
////
////        }
////
////        return true;
////
////    }
////
////    public String getAxiom() {
////
////        return this.axiom;
////
////    }
////
////    public boolean setRules(String rules) {
////
////        HashMap<String, String> saved = (HashMap<String, String>) this.grammar.clone();
////
////        String rule[], pred_succ[];
////
////        rule = rules.split("\n");
////
////        grammar.clear();
////
////        for (int i = 0; i < rule.length; i++) {
////
////            rule[i] = rule[i].replaceAll("[ \t\n\f\r]", "");
////
////            pred_succ = rule[i].split("=");
////
////            if (pred_succ.length != 2) {
////
////                grammar = saved;
////
////                return false;
////
////            }
////
////            if (isPredecessor(pred_succ[0]) && isSuccessor(pred_succ[1])) {
////
////                grammar.put(pred_succ[0], pred_succ[1]);
////
////                valid = false;
////
////            } else {
////
////                grammar = saved;
////
////                return false;
////
////            }
////
////        }
////
////        return true;
////
////    }
////
////    public boolean setRule(String pred, String succ) {
////
////        pred = pred.replaceAll("[ \t\n\f\r]", "");
////
////        succ = succ.replaceAll("[ \t\n\f\r]", "");
////
////        if (isPredecessor(pred) && isSuccessor(succ)) {
////
////            grammar.put(pred, succ);
////
////            valid = false;
////
////        } else {
////
////            return false;
////
////        }
////
////        return true;
////
////    }
////
////    public String getRule(String pred) {
////
////        pred = pred.replaceAll("[ \t\n\f\r]", "");
////
////        if (isPredecessor(pred)) {
////
////            grammar.remove(pred);
////
////            pred = pred.substring(0, 1);
////
////            return grammar.get(pred);
////
////        } else {
////
////            return null;
////
////        }
////
////    }
////
////    public boolean removeRule(String pred) {
////
////        pred = pred.replaceAll("[ \t\n\f\r]", "");
////
////        if (isPredecessor(pred)) {
////
////            grammar.remove(pred);
////
////            valid = false;
////
////        } else {
////
////            return false;
////
////        }
////
////        return true;
////
////    }
////
////    public boolean setIncrementAngle(double incrementAngle) {
////
////        if (incrementAngle < 0 || incrementAngle > 360)
////
////            return false;
////
////        this.incrementAngle = incrementAngle;
////
////        valid = false;
////
////        return true;
////
////    }
////
////        return incrementAngle;
////
////}
////
////    public boolean setIteration(int iteration) {
////
////        if (iteration < 0) {
////
////            return false;
////
////        }
////
////        this.iteration = iteration;
////
////        valid = false;
////
////        return true;
////
////    }
////
////    public int getIteration() {
////
////        return this.iteration;
////
////    }
////
////    private boolean isAxiom(String s) {
////
////        return (isSuccessor(s) && s.length() > 0);
////
////    }
////
////    private boolean isPredecessor(String s) {
////
////        if (s != null && s.length() == 1 && Character.isLetter(s.charAt(0)))
////
////            return true;
////
////        else
////
////            return false;
////
////    }
////
////    private boolean isSuccessor(String s) {
////
////        if (s == null)
////
////            return false;
////
////        for (int i = 0; i < s.length(); i++) {
////
////            char c = s.charAt(i);
////
////            if (!Character.isLetter(c) && c != '+' && c != '-' && c != '[' && c != ']')
////
////                return false;
////
////        }
////
////        return true;
////
////)public double getX () {
////
////            return bounds.x;
////
////        }
////
////        public double getY () {
////
////            return bounds.y;
////
////        }
////
////        public double getWidth () {
////
////            return bounds.width;
////
////        }
////
////        public double getHeight () {
////
////            return bounds.height;
////
////        }
////
////        public Vector<double[]> getSegmentList () {
////
////            /* If the segmentList does not correspond to the current         * fractal properties, update it. */
////
////            if (!valid)
////
////                update();
////
////            return segmentList;
////
////        }
////
////        if (!valid)
////
////            update();
////
////        return description;
////
////    }
////
////    private void update() {
////
////        /* If the fractal's segmentList is up-to-date, return now */
////
////        if (valid)
////
////            return;
////
////        Stack<Turtle> st = new Stack<Turtle>();
////
////        productionProcess();
////
////        turtle.setPosition(0, 0, 180);
////
////        segmentList = new Vector<double[]>();
////
////        bounds.setBounds(0, 0, 0, 0);
////
////        for (int i = 0; i < description.length(); i++) {
////
////            char c = description.charAt(i);
////
////            if (Character.isLetter(c) && Character.isLowerCase(c)) {
////
////                double[] line = turtle.moveForward();
////
////                if (line == null) {
////
////                    bounds.setBounds(0, 0, 0, 0);
////
////                    segmentList = null;
////
////                    valid = true;
////
////                    return;
////
////                }
////
////                segmentList.add(line);
////
////                bounds.add(line[2], line[3]);
////
////            } else if (c == '+') {
////
////                turtle.turn(-incrementAngle);
////
////            } else if (c == '-') {
////
////                turtle.turn(incrementAngle);
////
////            } else if (c == '[') {
////
////                st.push(turtle);
////
////                turtle = new Turtle(turtle);
////
////            } else if (c == ']') {
////
////                turtle = st.pop();
////
////            }
////
////        }
////
////        valid = true;
////
////    }
////
////    private void productionProcess() {
////
////        String gen, next_gen, pred, succ;
////
////        gen = axiom;
////
////        for (int i = 0; i < iteration; i++) {
////
////            next_gen = new String("");
////
////            for (int j = 0; j < gen.length(); j++) {
////
////                pred = gen.substring(j, j + 1);
////
////                succ = grammar.get(pred);
////
////                if (succ != null) {
////
////                    next_gen = next_gen.concat(succ);
////
////                } else {
////
////                    next_gen = next_gen.concat(pred);
////
////                }
////
////            }
////
////            gen = next_gen;
////
////        }
////
////        description = gen;
////
////    }
////
////}
////
////class Turtle implements Serializable {
////
////    /**
////     *
////     */
////
////    private static final long serialVersionUID = -5406692716427019724L;
////
////    private double x;
////
////    private double y;
////
////    private double angle;
////
////    public Turtle() {
////
////        x = y = angle = 0;
////
////    }
////
////    public Turtle(double x, double y, double angle) {
////
////        this.setPosition(x, y, angle);
////
////    }
////
////    public Turtle(Turtle t) {
////
////        this.setPosition(t.x, t.y, t.angle);
////
////    }
////
////    public void setPosition(double x, double y, double angle) {
////
////        this.x = x;
////
////        this.y = y;
////
////        this.angle = angle;
////
////    }
////
////    public double[] moveForward() {
////
////        double deltax, deltay, cos, sin;
////
////        double line[];
////
////        cos = Math.cos(Math.toRadians(angle));
////
////        if (Double.isNaN(cos) || Double.isInfinite(cos))
////
////            return null;
////
////        deltax = x + cos;
////
////        if (Double.isNaN(deltax) || Double.isInfinite(deltax))
////
////            return null;
////
////        sin = Math.sin(Math.toRadians(angle));
////
////        if (Double.isNaN(sin) || Double.isInfinite(sin))
////
////            return null;
////
////        deltay = y + sin;
////
////        if (Double.isNaN(deltay) || Double.isInfinite(deltay))
////
////            return null;
////
////        line = new double[4];
////
////        line[0] = x;
////
////        line[1] = y;
////
////        line[2] = deltax;
////
////        line[3] = deltay;
////
////        x = deltax;
////
////        y = deltay;
////
////        return line;
////
////    }
////
////    public void turn(double angle) {
////
////        this.angle += angle;
////
////    }
////
////}
////
////class BoundingRectangle implements Serializable {
////
////    private static final long serialVersionUID = 7379298422629492148L;
////
////    public double x;
////
////    public double y;
////
////    public double width;
////
////    public double height;
////
////    public BoundingRectangle() {
////
////        x = y = width = height = 0;
////
////    }
////
////    public BoundingRectangle(double x, double y, double w, double h) {
////
////        setBounds(x, y, w, h);
////
////    }
////
////    public void setBounds(double x, double y, double w, double h) {
////
////        this.x = x;
////
////        this.y = y;
////
////        this.width = w;
////
////        this.height = h;
////
////    }
////
////    public void add(double x, double y) {
////
////        if (in(x, y)) {
////
////            return;
////
////        }
////
////        if (x < this.x) {
////
////            this.width += this.x - x;
////
////            this.x = x;
////
////        } else if (x > this.x + this.width) {
////
////            this.width = x - this.x;
////
////        }
////
////        if (y < this.y) {
////
////            this.height += this.y - y;
////
////            this.y = y;
////
////        } else if (y > this.y + this.height) {
////
////            this.height = y - this.y;
////
////        }
////
////    }
////
////    public boolean in(double x, double y) {
////
////        return x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.height;
////
////    }
////
////}