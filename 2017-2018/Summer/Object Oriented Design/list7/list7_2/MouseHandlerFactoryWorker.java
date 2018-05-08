package list7.list7_2;

public interface MouseHandlerFactoryWorker {
    boolean acceptParameters(String handlerName);

    GeneralMouseHandler createMouseHandler(String handlerName, DrawingPanel drawingPanel);
}


class RectangleWorker implements MouseHandlerFactoryWorker {

    @Override
    public boolean acceptParameters(String handlerName) {
        return DrawingPanel.RECTANGLE.equals(handlerName);
    }

    @Override
    public GeneralMouseHandler createMouseHandler(String handlerName, DrawingPanel drawingPanel) {
        return new RectangleMouseHandler(drawingPanel);
    }
}

class SquareWorker implements MouseHandlerFactoryWorker {

    @Override
    public boolean acceptParameters(String handlerName) {
        return DrawingPanel.SQUARE.equals(handlerName);
    }

    @Override
    public GeneralMouseHandler createMouseHandler(String handlerName, DrawingPanel drawingPanel) {
        return new SquareMouseHandler(drawingPanel);
    }
}

class CircleWorker implements MouseHandlerFactoryWorker {

    @Override
    public boolean acceptParameters(String handlerName) {
        return DrawingPanel.CIRCLE.equals(handlerName);
    }

    @Override
    public GeneralMouseHandler createMouseHandler(String handlerName, DrawingPanel drawingPanel) {
        return new CircleMouseHandler(drawingPanel);
    }
}

class DeleteWorker implements MouseHandlerFactoryWorker {

    @Override
    public boolean acceptParameters(String handlerName) {
        return DrawingPanel.DELETE.equals(handlerName);
    }

    @Override
    public GeneralMouseHandler createMouseHandler(String handlerName, DrawingPanel drawingPanel) {
        return new DeleteMouseHandler(drawingPanel);
    }
}

class MoveWorker implements MouseHandlerFactoryWorker {

    @Override
    public boolean acceptParameters(String handlerName) {
        return DrawingPanel.MOVE.equals(handlerName);
    }

    @Override
    public GeneralMouseHandler createMouseHandler(String handlerName, DrawingPanel drawingPanel) {
        return new MoveMouseHandler(drawingPanel);
    }
}
