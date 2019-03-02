package list7.list7_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;

// problem - who should be the caretaker - DrawingFrame to totally disjoin the implementation of undo/redo with DrawingPanel? - it's ok
// Mediator properly used - DrawingFrame is a mediator for DrawingPanel and ButtonPanel - they have reference to it? - it's ok

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
    private RectangularShape addedShape;
    private DrawingFrame mediator;
    private GeneralMouseHandler mouseHandler;

    DrawingPanel(DrawingFrame mediator) {
        this.mediator = mediator;
        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
        shapes = new ArrayList<>();
        deletedShapes = new ArrayList<>();
        MouseHandler handler = new MouseHandler();
        addMouseListener(handler);
        addMouseMotionListener(handler);
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

    public RectangularShape getAddedShape() {
        return addedShape;
    }

    public List<RectangularShape> getShapes() {
        return shapes;
    }

    public List<RectangularShape> getDeletedShapes() {
        return deletedShapes;
    }

    public DrawingFrame getMediator() {
        return mediator;
    }

    public void setAddedShape(RectangularShape addedShape) {
        this.addedShape = addedShape;
    }

    public void setMouseHandler(GeneralMouseHandler mouseHandler) {
        this.mouseHandler = mouseHandler;
    }

    class MouseHandler extends MouseAdapter {

        public void mousePressed(MouseEvent event) {
            mouseHandler.mousePressed(event);
        }

        public void mouseReleased(MouseEvent event) {
            mouseHandler.mouseReleased(event);
        }

        public void mouseDragged(MouseEvent event) {
            mouseHandler.mouseDragged(event);
        }

    }
}