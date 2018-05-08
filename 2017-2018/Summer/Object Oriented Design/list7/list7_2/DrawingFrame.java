package list7.list7_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.RectangularShape;
import java.util.List;

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
        drawingPanel.setMouseHandler(MouseHandlerFactory.getInstance().createMouseHandler(event.getActionCommand(), drawingPanel));
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
