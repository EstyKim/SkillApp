package com.skill.ui;

import javax.swing.*;
import com.skill.db.MongoConnection; 
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginPage extends UIComponents {

    public LoginPage() {
        super("SkillMate - Login", true, false, false);

        frame.add(createLabel("Redefining the boundaries of studying and fun!", 600, 110, 354, 15, 13, Font.ITALIC));
        frame.add(createLabel("Welcome to SkillMate! Please log in to continue.", 260, 150, 900, 40, 18, Font.BOLD));

        JTextField userText = addTextField("Username:", 200);
        JPasswordField passText = addPasswordField("Password:", 250);

        JButton loginButton = createStyledButton("Login", 380, 320, 140, 45, "#050246", "#0707b7");
        loginButton.addActionListener(e-> {
            String username = userText.getText();
            String password = new String(passText.getPassword());

            if (MongoConnection.validateUser(username, password)) {
                frame.dispose();
                new HomePage().showPage();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid username or password!", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.add(loginButton);

        JLabel registerLabel = createLinkLabel();
        frame.add(registerLabel);
    }

    private JTextField addTextField(String label, int y) {
        frame.add(createLabel(label, 180, y, 100, 40, 14, Font.BOLD));
        JTextField field = new JTextField();
        field.setBounds(290, y, 300, 40);
        frame.add(field);
        return field;
    }

    private JPasswordField addPasswordField(String label, int y) {
        frame.add(createLabel(label, 180, y, 100, 40, 14, Font.BOLD));
        JPasswordField pass = new JPasswordField();
        pass.setBounds(290, y, 300, 40);
        frame.add(pass);
        return pass;
    }

    private JLabel createLinkLabel() {
        JLabel label = new JLabel(
            "<html><span style='color:#0707b7;'>Don't have an account? </span>" +
            "<span style='color:#f108f1;'>Register Here</span></html>"
        );
        label.setBounds(342, 380, 341, 49);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new SignUp().showPage();
            }

            public void mouseEntered(MouseEvent e) {
                label.setText("<html><center>Don't have an account? <u><font color='#ff00ff'>Register Here</font></u></center></html>");
            }

            public void mouseExited(MouseEvent e) {
                label.setText("<html><center>Don't have an account? <u><font color='#f108f1'>Register Here</font></u></center></html>");
            }
        });

        return label;
    }
}
