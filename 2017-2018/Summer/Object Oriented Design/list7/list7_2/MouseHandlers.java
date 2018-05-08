package list7.list7_2;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;

abstract class GeneralMouseHandler extends MouseAdapter {

    DrawingPanel drawingPanel;

    GeneralMouseHandler(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        RectangularShape addedShape = drawingPanel.getAddedShape();
        List<RectangularShape> shapes = drawingPanel.getShapes();
        List<RectangularShape> deletedShapes = drawingPanel.getDeletedShapes();
        DrawingFrame mediator = drawingPanel.getMediator();
        if (addedShape != null) {
            shapes.add(addedShape);
        }
        shapes.removeAll(deletedShapes);
        mediator.processDrawingPanelStateChangeEvent(new ArrayList<>(deletedShapes), addedShape);
        drawingPanel.setAddedShape(null);
        deletedShapes.clear();
        drawingPanel.repaint();
    }
}

class RectangleMouseHandler extends GeneralMouseHandler {

    RectangleMouseHandler(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        drawingPanel.setAddedShape(MyShapesFactory.getRectangle(event.getX(), event.getY()));
    }
}


class CircleMouseHandler extends GeneralMouseHandler {

    CircleMouseHandler(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        drawingPanel.setAddedShape(MyShapesFactory.getCircle(event.getX(), event.getY()));
    }
}

class SquareMouseHandler extends GeneralMouseHandler {

    SquareMouseHandler(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        drawingPanel.setAddedShape(MyShapesFactory.getSquare(event.getX(), event.getY()));
    }
}

class DeleteMouseHandler extends GeneralMouseHandler {

    DeleteMouseHandler(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        List<RectangularShape> shapes = drawingPanel.getShapes();
        List<RectangularShape> deletedShapes = drawingPanel.getDeletedShapes();
        for (RectangularShape shape : shapes) {
            if (shape.contains(event.getX(), event.getY())) {
                deletedShapes.add(shape);
            }
        }
    }
}

class MoveMouseHandler extends GeneralMouseHandler {

    MoveMouseHandler(DrawingPanel drawingPanel) {
        super(drawingPanel);
    }

    @Override
    public void mousePressed(MouseEvent event) {
        List<RectangularShape> shapes = drawingPanel.getShapes();
        List<RectangularShape> deletedShapes = drawingPanel.getDeletedShapes();
        for (RectangularShape shape : shapes) {
            if (shape.contains(event.getX(), event.getY())) {
                drawingPanel.setAddedShape((RectangularShape) shape.clone());
                shapes.remove(shape);
                deletedShapes.add(shape);
                break;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent event) {
        RectangularShape addedShape = drawingPanel.getAddedShape();
        if (addedShape != null) {
            double x = event.getX();
            double y = event.getY();
            double width = addedShape.getWidth();
            double height = addedShape.getHeight();
            addedShape.setFrame(x - width / 2, y - height / 2, width, height);
            drawingPanel.repaint();
        }
    }
}

//what is the difference between factory without instance but with just static methods?

class MouseHandlerFactory {

    private ArrayList<MouseHandlerFactoryWorker> workers = new ArrayList<>();
    private static MouseHandlerFactory instance;

    private MouseHandlerFactory() {
        workers.add(new RectangleWorker());
        workers.add(new SquareWorker());
        workers.add(new CircleWorker());
        workers.add(new DeleteWorker());
        workers.add(new MoveWorker());
    }

    public static MouseHandlerFactory getInstance() {
        if (instance == null) {
            instance = new MouseHandlerFactory();
        }
        return instance;
    }

    /*
    public void registerWorker(MouseHandlerFactoryWorker worker) {
        workers.add(worker);
    }
    */

    public GeneralMouseHandler createMouseHandler(String handlerName, DrawingPanel drawingPanel) {
        for (MouseHandlerFactoryWorker worker : workers) {
            if (worker.acceptParameters(handlerName)) {
                return worker.createMouseHandler(handlerName, drawingPanel);
            }
        }
        return null;
    }
}