package me.kolinger.pgrf2.grapher.graphics.model;

/**
 * @author Tomáš Kolinger <tomas@kolinger.name>
 */
public class Quad {

    private Point a;
    private Point b;
    private Point c;
    private Point d;
    private Point normal;

    public Quad(Point a, Point b, Point c, Point d, Point normal) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.normal = normal;
    }

    public Point getA() {
        return a;
    }

    public void setA(Point a) {
        this.a = a;
    }

    public Point getB() {
        return b;
    }

    public void setB(Point b) {
        this.b = b;
    }

    public Point getC() {
        return c;
    }

    public void setC(Point c) {
        this.c = c;
    }

    public Point getD() {
        return d;
    }

    public void setD(Point d) {
        this.d = d;
    }

    public Point getNormal() {
        return normal;
    }

    public void setNormal(Point normal) {
        this.normal = normal;
    }
}
