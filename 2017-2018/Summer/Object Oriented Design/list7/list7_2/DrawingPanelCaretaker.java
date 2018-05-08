package list7.list7_2;

import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DrawingPanelCaretaker {
    private DrawingPanel drawingPanel;
    private Stack<DrawingPanelMemento> undoStack;
    private Stack<DrawingPanelMemento> redoStack;

    DrawingPanelCaretaker(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
        undoStack = new Stack<>();
        undoStack.push(new DrawingPanelMemento(new ArrayList<>(), null));
        redoStack = new Stack<>();
    }

    public void processStateChangedEvent(List<RectangularShape> deletedShapes, RectangularShape addedShape) {
        DrawingPanelMemento memento = new DrawingPanelMemento(deletedShapes, addedShape);
        undoStack.push(memento);
        redoStack.clear();
    }

    public void processUndoEvent() {
        if (undoStack.size() > 0) {
            DrawingPanelMemento memento = undoStack.pop();
            redoStack.push(memento);
            drawingPanel.restoreStateUndo(memento);
        }
    }

    public void processRedoEvent() {
        if (redoStack.size() > 0) {
            DrawingPanelMemento memento = redoStack.pop();
            undoStack.push(memento);
            drawingPanel.restoreStateRedo(memento);
        }
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
