package src;

import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.Graphics;

public class Barley implements Comparable<Barley>{
    private Image image;
    private Point currentPoint;
    private int index;
    private final int value;
   
    Barley(Image image, int x, int y, int index) {
        this.image = image;
        this.index = index;
        this.value = index;
        this.currentPoint = new Point(x, y);
    }

    public Image getImage() {
        return(this.image);
    }

    public int getX() {
        return (int) this.currentPoint.getX();
    }

    public int getY() {
        return (int) this.currentPoint.getY();
    }

    public Point getCurrentPoint(){
        return currentPoint;
    }

    public boolean contains(Point2D p) {
        double x = p.getX();
        double y = p.getY();
        double currentX = currentPoint.getX();
        double currentY = currentPoint.getY();

        if ((x >= currentX && x < currentX + 75) && (y >= currentY && y < currentY + 75)) {
            return true;
        }
        return false;
    }

    public int getIndex() {
        return this.index;
    }

    public int getValue() {
        return this.value;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public final int compareTo(Barley other) {
        return Integer.compare(this.getIndex(), other.getIndex());
    }

    public void move(int x, int y) {
        this.currentPoint.move(x, y);
    }
}