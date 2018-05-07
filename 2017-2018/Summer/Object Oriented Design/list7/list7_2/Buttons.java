package list7.list7_2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

class ButtonPanel extends JPanel {

    ButtonPanel(LayoutManager layoutManager, DrawingFrame mediator) {
        super(layoutManager);
        DrawingFrame mediator1 = mediator;
        JButton rectangleButton = new JButton(DrawingPanel.RECTANGLE);
        JButton circleButton = new JButton(DrawingPanel.CIRCLE);
        JButton squareButton = new JButton(DrawingPanel.SQUARE);
        JButton moveButton = new JButton(DrawingPanel.MOVE);
        JButton deleteButton = new JButton(DrawingPanel.DELETE);
        JButton undoButton = new JButton(DrawingPanel.UNDO);
        JButton redoButton = new JButton(DrawingPanel.REDO);
        ArrayList<JButton> buttons = new ArrayList<>(Arrays.asList(rectangleButton, squareButton, circleButton,
                moveButton, deleteButton));
        for (JButton button : buttons) {
            button.addActionListener(new DrawingButtonHandler(mediator1));
            add(button);
        }
        undoButton.addActionListener(new UndoHandler(mediator1));
        redoButton.addActionListener(new RedoHandler(mediator1));
        buttons.add(undoButton);
        buttons.add(redoButton);
        add(undoButton);
        add(redoButton);
    }
}

abstract class ActionHandler implements ActionListener {
    DrawingFrame mediator;

    ActionHandler(DrawingFrame mediator) {
        this.mediator = mediator;
    }
}

class DrawingButtonHandler extends ActionHandler {

    DrawingButtonHandler(DrawingFrame mediator) {
        super(mediator);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        mediator.processActionEvent(actionEvent);
    }
}

class UndoHandler extends ActionHandler {

    UndoHandler(DrawingFrame mediator) {
        super(mediator);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        mediator.processUndoEvent();
    }
}

class RedoHandler extends ActionHandler {

    RedoHandler(DrawingFrame mediator) {
        super(mediator);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        mediator.processRedoEvent();
    }
}