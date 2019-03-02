package list7.list7_1;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

class TypeTree extends JTree implements Subscriber {

    TypeTree() {
        super(createNodes());
        getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        addTreeSelectionListener(treeSelectionEvent -> EventAggregator.getInstance().publish(SubscriberType.TREE_SELECTION, treeSelectionEvent));
    }

    private static DefaultMutableTreeNode createNodes() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Categories");
        DefaultMutableTreeNode category;
        Database db = Database.getInstance();
        for (UserType type : UserType.values()) {
            category = new DefaultMutableTreeNode(type.toString());
            for (User user : db.getUsers(type)) {
                category.add(new DefaultMutableTreeNode(user.getName()));
            }
            if (!category.isLeaf()) {
                top.add(category);
            }
        }
        return top;
    }

    @Override
    public void handle(Object notification) {
        System.out.println(notification.toString());
        if (notification instanceof AddEvent) {
            handle((AddEvent) notification);
        } else if (notification instanceof ModifyEvent) {
            handle((ModifyEvent) notification);
        }
    }

    private void handle(ModifyEvent notification) {

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) getLastSelectedPathComponent();
        User user = notification.getUser();
        setVisible(false);
        node.setUserObject(user.getName());
        setVisible(true);
    }

    private void handle(AddEvent notification) {

        User user = notification.getUser();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) getModel().getRoot();
        for (int i = 0; i < root.getChildCount(); i++) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(i);
            if (UserType.valueOf(node.toString().toUpperCase()).equals(user.getType())) {
                DefaultTreeModel model = (DefaultTreeModel) getModel();
                model.insertNodeInto(new DefaultMutableTreeNode(user.getName()), node, node.getChildCount());
            }
        }
    }
}

class UserList extends JPanel implements Subscriber, ActionListener {
    UserList() {
        setLayout(new GridLayout(0, 2));
    }

    private UserType type;

    @Override
    public void handle(Object notification) {
        if (notification instanceof TreeSelectionEvent) {
            handle((TreeSelectionEvent) notification);
        }
    }

    private void handle(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = ((DefaultMutableTreeNode) e.getPath().getLastPathComponent());
        String name = node.toString();
        if (node.isLeaf() || !UserType.contains(name)) {
            setVisible(false);
        } else {
            setVisible(true);
            removeAll();
            type = UserType.valueOf(name.toUpperCase());
            List<User> users = Database.getInstance().getUsers(type);
            for (User user : users) {
                add(new JTextField(user.getName()));
                add(new JTextField(String.valueOf(user.getYearsInUni())));
            }
            JButton add = new JButton("Add user");
            add.addActionListener(this);
            add(add);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        AddUserDialog dialog = new AddUserDialog(null, "Modify user", true, type);
        dialog.setSize(300, 200);
        dialog.setVisible(true);
        setVisible(false);
    }
}

class UserInfo extends JPanel implements Subscriber, ActionListener {

    private User user;

    UserInfo() {
        setLayout(new GridLayout(0, 3));
    }

    @Override
    public void handle(Object notification) {
        if (notification instanceof TreeSelectionEvent) {
            handle((TreeSelectionEvent) notification);
        }
    }

    private void handle(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = ((DefaultMutableTreeNode) e.getPath().getLastPathComponent());
        String name = node.toString();
        if (!node.isLeaf()) {
            setVisible(false);
        } else {
            setVisible(true);
            removeAll();
            user = Database.getInstance().getUser(UserType.valueOf(node.getParent().toString().toUpperCase()), name);
            add(new JTextField(user.getName()));
            add(new JTextField(String.valueOf(user.getYearsInUni())));
            add(new JTextField(user.getBirthDate().toString()));
            JButton modify = new JButton("Modify user");
            modify.addActionListener(this);
            add(modify);
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        ModifyUserDialog dialog = new ModifyUserDialog(null, "Modify user", true, user);
        dialog.setSize(300, 200);
        dialog.setVisible(true);
        setVisible(false);
    }
}
