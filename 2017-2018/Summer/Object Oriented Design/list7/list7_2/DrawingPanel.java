package list7.list7_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;

class DrawingPanel extends JPanel {
    public final static String RECTANGLE = "RECTANGLE";
    public final static String CIRCLE = "CIRCLE";
    public final static String SQUARE = "SQUARE";
    public final static String MOVE = "MOVE";
    public final static String DELETE = "DELETE";
    public final static String UNDO = "UNDO";
    public final static String REDO = "REDO";


    private List<RectangularShape> shapes;
    private List<RectangularShape> deletedShapes;
    private DrawingPanelMode mode;
    private RectangularShape currentShape;

    DrawingPanel() {
        mode = DrawingPanelMode.NONE;
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        shapes = new ArrayList<>();
        deletedShapes = new ArrayList<>();
        MouseHandler mouseHandler = new MouseHandler();
        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);

    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2D = (Graphics2D) graphics;

        for (Shape shape : shapes) {
            graphics2D.draw(shape);
        }
        if (currentShape != null) {
            graphics2D.draw(currentShape);
        }
    }

    public void setMode(DrawingPanelMode mode) {
        this.mode = mode;
    }

    class MouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent event) {
            switch (mode) {

                case RECTANGLE:
                    currentShape = MyShapesFactory.getRectangle(event.getX(), event.getY());
                    break;
                case CIRCLE:
                    currentShape = MyShapesFactory.getCircle(event.getX(), event.getY());
                    break;
                case SQUARE:
                    currentShape = MyShapesFactory.getSquare(event.getX(), event.getY());
                    break;
                case MOVE:
                    for (RectangularShape shape : shapes) {
                        if (shape.contains(event.getX(), event.getY())) {
                            currentShape = shape;
                            break;
                        }
                    }
                    shapes.remove(currentShape);
                    break;
                case DELETE:
                    for (RectangularShape shape : shapes) {
                        if (shape.contains(event.getX(), event.getY())) {
                            deletedShapes.add(shape);
                        }
                    }
                case NONE:
                    break;
            }
        }


        public void mouseReleased(MouseEvent event) {
            switch (mode) {

                case RECTANGLE:
                case CIRCLE:
                case SQUARE:
                case MOVE:
                    if (currentShape != null) {
                        shapes.add(currentShape);
                    }
                    currentShape = null;
                    repaint();
                    break;
                case DELETE:
                    shapes.removeAll(deletedShapes);
                    deletedShapes.clear();
                    repaint();
                    break;
                case NONE:
                    break;
            }
        }

        public void mouseMoved(MouseEvent event) {
        }

        public void mouseDragged(MouseEvent event) {
            switch (mode) {

                case RECTANGLE:
                case CIRCLE:
                case SQUARE:
                case DELETE:
                case NONE:
                    break;
                case MOVE:
                    if (currentShape != null) {
                        double x = event.getX();
                        double y = event.getY();
                        double width = currentShape.getWidth();
                        double height = currentShape.getHeight();
                        currentShape.setFrame(x - width / 2, y - height / 2, width, height);
                        repaint();
                        break;
                    }
            }

        }

    }
}


class DrawingFrame extends JFrame {

    private DrawingPanel drawingPanel;

    DrawingFrame() throws HeadlessException {
        super("Simple paint");
        setSize(800, 600);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        ButtonPanel buttonPanel = new ButtonPanel(new GridLayout(1, 6, 10, 10), this);
        JPanel widgetPadder = new JPanel();
        widgetPadder.setLayout(new FlowLayout(FlowLayout.LEADING, 20, 5));
        widgetPadder.add(buttonPanel);

        setLayout(new BorderLayout());
        add(widgetPadder, BorderLayout.PAGE_START);
        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

    }

    void processActionEvent(ActionEvent event) {
        drawingPanel.setMode(DrawingPanelMode.valueOf(event.getActionCommand()));
    }

    void processUndoEvent() {

    }

    void processRedoEvent() {

    }


}


enum DrawingPanelMode {
    RECTANGLE(DrawingPanel.RECTANGLE), CIRCLE(DrawingPanel.CIRCLE), SQUARE(DrawingPanel.SQUARE),
    MOVE(DrawingPanel.MOVE), DELETE(DrawingPanel.DELETE), NONE("NONE");

    private final String text;

    DrawingPanelMode(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}