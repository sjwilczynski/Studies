package list7.list7_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;


class UserDialog extends JDialog {
    JTextField name, dateOfBirth, yearsInUni;

    UserDialog(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        setLayout(new GridLayout(0, 2));
    }
}

public class ModifyUserDialog extends UserDialog implements ActionListener {

    private User user;

    ModifyUserDialog(Frame owner, String title, boolean modal, User user) {
        super(owner, title, modal);
        this.user = user;

        name = new JTextField(user.getName());
        dateOfBirth = new JTextField(user.getBirthDate().toString());
        yearsInUni = new JTextField(String.valueOf(user.getYearsInUni()));

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(this);

        add(new JLabel("Name"));
        add(name);
        add(new JLabel("Date of birth"));
        add(dateOfBirth);
        add(new JLabel("Years at University"));
        add(yearsInUni);
        add(saveButton);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        user.setName(name.getText());
        user.setYearsInUni(Integer.valueOf(yearsInUni.getText()));
        user.setBirthDate(LocalDate.parse(dateOfBirth.getText()));

        EventAggregator.getInstance().publish(SubscriberType.MODIFY_USER, new ModifyEvent(actionEvent, user));
        dispose();
    }
}

class UserActionEvent {
    private ActionEvent event;
    private User user;

    UserActionEvent(ActionEvent event, User user) {
        this.event = event;
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}

class ModifyEvent extends UserActionEvent {

    ModifyEvent(ActionEvent event, User user) {
        super(event, user);
    }
}

class AddUserDialog extends UserDialog implements ActionListener {

    private UserType type;

    AddUserDialog(Frame owner, String title, boolean modal, UserType type) {
        super(owner, title, modal);
        this.type = type;

        name = new JTextField();
        dateOfBirth = new JTextField();
        yearsInUni = new JTextField();

        JButton addButton = new JButton("Add");
        addButton.addActionListener(this);

        add(new JLabel("Name"));
        add(name);
        add(new JLabel("Date of birth"));
        add(dateOfBirth);
        add(new JLabel("Years at University"));
        add(yearsInUni);
        add(addButton);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        User newUser = new User(name.getText(), LocalDate.parse(dateOfBirth.getText()), Integer.valueOf(yearsInUni.getText()), type);
        Database.getInstance().addUser(newUser);
        EventAggregator.getInstance().publish(SubscriberType.ADD_USER, new AddEvent(actionEvent, newUser));
        dispose();
    }
}

class AddEvent extends UserActionEvent {

    AddEvent(ActionEvent event, User user) {
        super(event, user);
    }
}
