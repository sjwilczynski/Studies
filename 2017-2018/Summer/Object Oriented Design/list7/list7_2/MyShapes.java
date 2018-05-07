package list7.list7_2;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

class MyShapesFactory {
    public static final int RECTANGLE_HEIGHT = 200;
    public static final int RECTANGLE_WIDTH = 100;
    public static final int SQUARE_WIDTH = 100;
    public static final int CIRCLE_RADIUS = 100;

    static Rectangle2D getRectangle(double x, double y) {
        return new Rectangle2D.Double(x - RECTANGLE_WIDTH / 2, y - RECTANGLE_HEIGHT / 2, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
    }

    static Rectangle2D getSquare(double x, double y) {
        return new Rectangle2D.Double(x - SQUARE_WIDTH / 2, y - SQUARE_WIDTH / 2, SQUARE_WIDTH, SQUARE_WIDTH);
    }

    static Ellipse2D getCircle(double x, double y) {
        return (new Ellipse2D.Double(x - CIRCLE_RADIUS / 2, y - CIRCLE_RADIUS / 2, CIRCLE_RADIUS, CIRCLE_RADIUS));
    }
}

