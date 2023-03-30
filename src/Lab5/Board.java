package Lab5;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;


public class Board extends JComponent implements MouseInputListener, ComponentListener {
    private static final long serialVersionUID = 1L;
    private Point[][] points;
    private int size = 14;

    private int wall = 2;
    private int lane_length = 3;
    private static float disappearVariable = 0.005f;
    private static float appearVariable = 0.1f;

    public Board(int length, int height) {
        addMouseListener(this);
        addComponentListener(this);
        addMouseMotionListener(this);
        setBackground(Color.WHITE);
        setOpaque(true);
    }

    // single iteration
    public void iteration() {
        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                if (points[x][y].type != 2) {
                    points[x][y].moved = false;
                    points[x][y].changedLane = false;
                }
            }
        }

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                if (points[x][y].type != 2)
                    points[x][y].allType();
            }
        }


//        for (int x = 0; x < points.length; ++x) { //disappearing
//            if (points[x][1].type == 1) {
//                if (Math.random() < disappearVariable) {
//                    points[x][1].type = 0;
//                    points[x][1].velocity = 0;
//                }
//            }
//        }
//
//        if (points[0][1].type == 0 && Math.random() < appearVariable) { //appearing
//            points[0][1].type = 1;
//            points[0][1].velocity = 1;
//        }
        this.repaint();

    }

    // clearing board
    public void clear() {
        for (int x = 0; x < points.length; ++x)
            for (int y = 0; y < points[x].length; ++y) {
                if (points[x][y].type != 2) {
                    points[x][y].setState();
                }
            }
        this.repaint();
    }

    private void initialize(int length, int height) {
        points = new Point[length][height];

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                points[x][y] = new Point();
                points[x][y].x = x;
                points[x][y].y = y;
                if (y < wall || y >= points[x].length - wall) {
                    points[x][y].type = 2;
                }
            }
        }

        for (int x = 0; x < points.length; x++) {
            points[x][wall + lane_length].type = 2; //between wall
        }

        for (int x = 0; x < points.length; ++x) {
            for (int y = 0; y < points[x].length; ++y) {
                if (points[x][y].type != 2) {

                    if (y > wall + lane_length) { //lower
                        points[x][y].prev = points[(x + points.length - 1) % points.length][y];
                        points[x][y].next = points[(x + 1) % points.length][y];

                        if (points[x][y - 1].type != 2) {

                            points[x][y].upPoint = points[x][y - 1];
                        }
                        if (points[x][y + 1].type != 2) {
                            points[x][y].downPoint = points[x][y + 1];
                        }

                    } else { // upper

                        points[x][y].next = points[(x + points.length - 1) % points.length][y];
                        points[x][y].prev = points[(x + 1) % points.length][y];


                        if (points[x][y - 1].type != 2) {

                            points[x][y].downPoint = points[x][y - 1];
                        }
                        if (points[x][y + 1].type != 2) {
                            points[x][y].upPoint = points[x][y + 1];
                        }
                    }


                }
            }
        }
    }

    //paint background and separators between cells
    protected void paintComponent(Graphics g) {
        if (isOpaque()) {
            g.setColor(getBackground());
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
        g.setColor(Color.GRAY);
        drawNetting(g, size);
    }

    // draws the background netting
    private void drawNetting(Graphics g, int gridSpace) {
        Insets insets = getInsets();
        int firstX = insets.left;
        int firstY = insets.top;
        int lastX = this.getWidth() - insets.right;
        int lastY = this.getHeight() - insets.bottom;

        int x = firstX;
        while (x < lastX) {
            g.drawLine(x, firstY, x, lastY);
            x += gridSpace;
        }

        int y = firstY;
        while (y < lastY) {
            g.drawLine(firstX, y, lastX, y);
            y += gridSpace;
        }

        for (x = 0; x < points.length; ++x) {
            for (y = 0; y < points[x].length; ++y) {
                if (points[x][y].getState() != 0) {
                    if (points[x][y].type == 0) {
                        g.setColor(new Color(255, 255, 255));
                    } else if (points[x][y].type == 1) {
                        g.setColor(new Color(0, 0, 0));
                    } else if (points[x][y].type == 2) {
                        g.setColor(new Color(150, 0, 0));
                    }
                    g.fillRect((x * size) + 1, (y * size) + 1, (size - 1), (size - 1));

                }

            }
        }

    }

    public void mouseClicked(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            points[x][y].clicked(x, y);
            this.repaint();
        }
    }

    public void componentResized(ComponentEvent e) {
        int dlugosc = (this.getWidth() / size) + 1;
        int wysokosc = (this.getHeight() / size) + 1;
        initialize(dlugosc, wysokosc);
    }

    public void mouseDragged(MouseEvent e) {
        int x = e.getX() / size;
        int y = e.getY() / size;
        if ((x < points.length) && (x > 0) && (y < points[x].length) && (y > 0)) {
            points[x][y].setState();
            this.repaint();
        }
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

}
