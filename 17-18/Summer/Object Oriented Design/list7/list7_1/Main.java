package list7.list7_1;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Users");
        frame.setSize(800, 600);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(200);
        JPanel rightPanel = new JPanel();
        TypeTree typeTree = new TypeTree();
        UserList userList = new UserList();
        UserInfo userInfo = new UserInfo();

        EventAggregator.getInstance().addSubscriber(SubscriberType.TREE_SELECTION, userList);
        EventAggregator.getInstance().addSubscriber(SubscriberType.TREE_SELECTION, userInfo);
        EventAggregator.getInstance().addSubscriber(SubscriberType.ADD_USER, typeTree);
        EventAggregator.getInstance().addSubscriber(SubscriberType.MODIFY_USER, typeTree);


        rightPanel.add(userInfo);
        rightPanel.add(userList);
        userInfo.setVisible(false);
        userList.setVisible(false);
        splitPane.setRightComponent(rightPanel);
        splitPane.setLeftComponent(typeTree);
        frame.add(splitPane);
        frame.setVisible(true);
    }
}
