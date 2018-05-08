package list7.list7_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;

// problem - who should be the caretaker - DrawingFrame to totally disjoin the implementation of undo/redo with DrawingPanel?
// Mediator properly used - DrawingFrame is a mediator for DrawingPanel and ButtonPanel - they have reference to it?

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
    private RectangularShape addedShape;
    private DrawingFrame mediator;

    DrawingPanel(DrawingFrame mediator) {
        this.mediator = mediator;
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
        if (addedShape != null) {
            graphics2D.draw(addedShape);
        }
    }

    public void setMode(DrawingPanelMode mode) {
        this.mode = mode;
    }

    public void restoreStateUndo(DrawingPanelMemento memento) {
        shapes.addAll(memento.getDeletedShapes());
        shapes.remove(memento.getAddedShape());
        repaint();
    }

    public void restoreStateRedo(DrawingPanelMemento memento) {
        shapes.removeAll(memento.getDeletedShapes());
        RectangularShape addedShape = memento.getAddedShape();
        if (addedShape != null) {
            shapes.add(addedShape);
        }
        repaint();
    }

    class MouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent event) {
            switch (mode) {

                case RECTANGLE:
                    addedShape = MyShapesFactory.getRectangle(event.getX(), event.getY());
                    break;
                case CIRCLE:
                    addedShape = MyShapesFactory.getCircle(event.getX(), event.getY());
                    break;
                case SQUARE:
                    addedShape = MyShapesFactory.getSquare(event.getX(), event.getY());
                    break;
                case MOVE:
                    for (RectangularShape shape : shapes) {
                        if (shape.contains(event.getX(), event.getY())) {
                            addedShape = (RectangularShape) shape.clone();
                            shapes.remove(shape);
                            deletedShapes.add(shape);
                            break;
                        }
                    }
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
                case DELETE:
                    if (addedShape != null) {
                        shapes.add(addedShape);
                    }
                    shapes.removeAll(deletedShapes);
                    mediator.processDrawingPanelStateChangeEvent(new ArrayList<>(deletedShapes), addedShape);
                    addedShape = null;
                    deletedShapes.clear();
                    repaint();
                    break;
                case NONE:
                    break;
            }
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
                    if (addedShape != null) {
                        double x = event.getX();
                        double y = event.getY();
                        double width = addedShape.getWidth();
                        double height = addedShape.getHeight();
                        addedShape.setFrame(x - width / 2, y - height / 2, width, height);
                        repaint();
                        break;
                    }
            }

        }

    }
}


class DrawingFrame extends JFrame {

    private DrawingPanel drawingPanel;
    private DrawingPanelCaretaker caretaker;

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
        drawingPanel = new DrawingPanel(this);
        caretaker = new DrawingPanelCaretaker(drawingPanel);
        add(drawingPanel, BorderLayout.CENTER);

    }

    void processActionEvent(ActionEvent event) {
        drawingPanel.setMode(DrawingPanelMode.valueOf(event.getActionCommand()));
    }

    void processUndoEvent() {
        caretaker.processUndoEvent();
    }

    void processRedoEvent() {
        caretaker.processRedoEvent();
    }


    public void processDrawingPanelStateChangeEvent(List<RectangularShape> deletedShapes, RectangularShape addedShape) {
        caretaker.processStateChangedEvent(deletedShapes, addedShape);
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

class DrawingPanelMemento {
    private List<RectangularShape> deletedShapes;
    private RectangularShape addedShape;

    DrawingPanelMemento(List<RectangularShape> deletedShapes, RectangularShape addedShape) {
        this.deletedShapes = deletedShapes;
        this.addedShape = addedShape;
    }

    public List<RectangularShape> getDeletedShapes() {
        return deletedShapes;
    }

    public RectangularShape getAddedShape() {
        return addedShape;
    }
}