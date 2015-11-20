package src;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.font.*;
import javax.swing.*;
import java.util.*;

public class BoardComponent extends JComponent {
    private ArrayList<Barley> imageComponents;
    private Barley current;
    private Barley emptyBarley;
    private int winWord = 0;
    private int dx = 0;
    private int dy = 0;

    public BoardComponent() {
        Image image = getToolkit().getImage("src/images/fifteen1.png");

        imageComponents = new ArrayList<>();

        for (int j = 0, index = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++, index ++) {
                int x = i * 75;
                int y = j * 75;

                Image barleyImage = createImage(new FilteredImageSource(image.getSource(), new CropImageFilter(x, y, 75, 75)));
                Barley barley = new Barley(barleyImage, x, y, index);

                imageComponents.add(barley);
            }
        }

        emptyBarley = imageComponents.get(15);

        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());

        newGame();
    }

    public void newGame() {
        Random randInd = new Random();
        int size = imageComponents.size();
        int count = 0;

        for (int i = 0; i < size; i++) {
            int ii = randInd.nextInt(size);
            swap(imageComponents.get(i), imageComponents.get(ii));

            checkBarley(imageComponents.get(i));
            checkBarley(imageComponents.get(ii));
        }

        Collections.sort(imageComponents);

        for (int i = 0; i < size; i++) {
            int currentValue = imageComponents.get(i).getValue();

            if (currentValue != 15) {
                for (int j = 0; j < i; j++) {
                    int nextValue = imageComponents.get(j).getValue();
                    if (nextValue == 15) {
                        nextValue = 0;
                    }

                    if (nextValue > currentValue) {
                        count++;
                    }
                }
            } else {
                count += 1 + i / 4;
            }
        }

        if ( count % 2 != 0 ) {
            Barley tmp1 = null;
            Barley tmp2 = null;

            for (Barley b : imageComponents) {
                if (b.getValue() == 14) {
                    tmp1 = b;
                }
                if (b.getValue() == 13) {
                    tmp2 = b;
                }
            }
            swap(tmp1, tmp2);
            checkBarley(tmp1);
            checkBarley(tmp2);
            checkWin();
        }
    }

    public void checkBarley(Barley b) {
        if (b.getIndex() == b.getValue()) {
            winWord |= 1 << b.getValue();
        } else {
            winWord &= ( ~(1 << b.getValue()));
        }
    }

    public void checkWin() {
        if (winWord == 0xFFFF) {
            System.out.println("You WIN!!!");
            newGame();
        }
    }

    public void swap(Barley first, Barley second) {
        int curentX = first.getX();
        int curentY = first.getY();
        int currentIndex = first.getIndex();

        first.setIndex(second.getIndex());
        first.move(second.getX(), second.getY());

        second.setIndex(currentIndex);
        second.move(curentX, curentY);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4 ; j++) {
                int x = i * 75;
                int y = j * 75;
                g2.drawImage(emptyBarley.getImage(), x, y, this);
            }
        }
        for (Barley b : imageComponents) {
            g2.drawImage(b.getImage(), b.getX(), b.getY(), this);
        }
    }

    public Barley find(Point2D p) {
        for (Barley b : imageComponents) {
            if (b.contains(p)) {
                return b;
            }
        }
        return null;
    }

    private class MouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent event) {
            current = find(event.getPoint());
            if (current != null) {
                dx = event.getX() - current.getX();
                dy = event.getY() - current.getY();
                imageComponents.remove(current);
                imageComponents.add(current);
                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
            }
        }

        public void mouseClicked(MouseEvent event) {
            current = find(event.getPoint());
        }

        public void mouseReleased(MouseEvent event) {
            if (current != null && current != emptyBarley) {
                current.getCurrentPoint().move(((int)Math.round((double)current.getX() / 75.0) * 75 ), current.getY());
                current.getCurrentPoint().move(current.getX(), ((int)Math.round((double)current.getY() / 75.0) * 75 ));
                repaint();
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            checkWin();
        }
    }

    private class MouseMotionHandler implements MouseMotionListener {
        public void mouseMoved(MouseEvent event) {
            if (find(event.getPoint()) == null || find(event.getPoint()) == emptyBarley) {
                setCursor(Cursor.getDefaultCursor());
            } else {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
        }

        public void mouseDragged(MouseEvent event) {
            int route = emptyBarley.getIndex() - current.getIndex();

            if (current != null && (Math.abs(route) == 1 || Math.abs(route) == 4)) {
                int x = current.getX();
                int y = current.getY();

                if (Math.abs(route) == 1) {
                    if (Math.abs(event.getX() - dx - emptyBarley.getX()) <= 75) {
                        x = event.getX() - dx;
                        current.move(x, y);
                    } else {
                        current.move((emptyBarley.getX() - (Math.abs(route)/route) * 75), y);
                    }

                    if (Math.abs(emptyBarley.getX() - current.getX()) <= 75 / 2) {
                        emptyBarley.move((emptyBarley.getX() - (Math.abs(route)/route) * 75), y);
                        emptyBarley.setIndex(current.getIndex());
                        current.setIndex(current.getIndex() + route);
                        checkBarley(emptyBarley);
                        checkBarley(current);
                    }

                } else {
                    if (Math.abs(event.getY() - dy - emptyBarley.getY()) <= 75) {
                        y = event.getY() - dy;
                        current.move(x, y);
                    } else {
                        current.move(x, (emptyBarley.getY() - (Math.abs(route)/route) * 75));
                    }

                    if (Math.abs(emptyBarley.getY() - current.getY()) <= 75 / 2) {
                        emptyBarley.move(x, (emptyBarley.getY() - (Math.abs(route)/route) * 75));
                        emptyBarley.setIndex(current.getIndex());
                        current.setIndex(current.getIndex() + route);
                        checkBarley(emptyBarley);
                        checkBarley(current);
                    }
                }

                repaint();

            }
        }
    }
}