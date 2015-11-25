package src;

import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.Graphics;

public class Barley implements Comparable<Barley>{
    private final int value;

    private Image image;
    private Point currentPoint;
    private int index;
   
    Barley(Image aImage, int x, int y, int aIndex) {
        image = aImage;
        index = aIndex;
        value = aIndex;
        currentPoint = new Point(x, y);
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return (int) this.currentPoint.getX();
    }

    public int getY() {
        return (int) this.currentPoint.getY();
    }

    public boolean contains(Point2D p) {
        return ((p.getX() >= currentPoint.getX() && p.getX() < currentPoint.getX() + 75) && 
            (p.getY() >= currentPoint.getY() && p.getY() < currentPoint.getY() + 75));
    }

    public int getIndex() {
        return index;
    }

    public int getValue() {
        return value;
    }

    public void setIndex(int aIndex) {
        index = aIndex;
    }

    public final int compareTo(Barley other) {
        return Integer.compare(index, other.getIndex());
    }

    public void move(int x, int y) {
        currentPoint.move(x, y);
    }
}
