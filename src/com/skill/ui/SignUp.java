package com.skill.ui;

import com.skill.db.MongoConnection;
import com.skill.model.User;

import javax.swing.*;
import java.awt.*;

public class SignUp extends UIComponents {
    private JTextField nameField, emailField, usernameField;
    private JPasswordField passwordField, confirmPassField;

    public SignUp() {
        super("Sign Up", true, false, false);

        frame.add(createLabel("Redefining the boundaries of studying and fun!", 600, 110, 354, 15, 13, Font.ITALIC));
        frame.add(createLabel("Sign Up to SkillMate", 350, 140, 450, 40, 20, Font.BOLD));

        addField("Full Name:", 190);
        addField("Email Address:", 230);
        addField("Username:", 270);
        addPasswordField("Password:", 310);
        addPasswordField("Confirm Password:", 350);

        // Back to Login Button
        JButton backBtn = createStyledButton("Back to Login", 80, 410, 150, 45, "#F108F1", "#C800C8");
        backBtn.addActionListener(e -> {
            frame.dispose();
            new LoginPage().showPage();
        });
        frame.add(backBtn);

        // Create Account Button
        JButton createBtn = createStyledButton("Create Account", 650, 410, 170, 45, "#050246", "#0707b7");
        createBtn.addActionListener(e -> handleSignUp());
        frame.add(createBtn);
    }

    private void handleSignUp() {
        String fullName = nameField.getText().trim();
        String email = emailField.getText().trim();
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirm = new String(confirmPassField.getPassword());

        if (fullName.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
            return;
        }

        if (!password.equals(confirm)) {
            JOptionPane.showMessageDialog(frame, "Passwords do not match.");
            return;
        }

        // 🧠 Create and save User to MongoDB
        User user = new User(fullName, email, username, password);
        boolean success = MongoConnection.insertUser(user);

        if (success) {
            JOptionPane.showMessageDialog(frame, "Account created successfully!");
            frame.dispose();
            new LoginPage().showPage();
        } else {
            JOptionPane.showMessageDialog(frame, "Failed to create account. Please try again.");
        }
    }

    private void addField(String label, int y) {
        frame.add(createLabel(label, 180, y, 200, 30, 13, Font.BOLD));
        JTextField field = new JTextField();
        field.setBounds(290, y, 300, 30);
        frame.add(field);

        switch (label) {
            case "Full Name:" -> nameField = field;
            case "Email Address:" -> emailField = field;
            case "Username:" -> usernameField = field;
        }
    }

    private void addPasswordField(String label, int y) {
        frame.add(createLabel(label, 170, y, 200, 30, 13, Font.BOLD));
        JPasswordField pass = new JPasswordField();
        pass.setBounds(290, y, 300, 30);
        frame.add(pass);

        if (label.contains("Confirm")) {
            confirmPassField = pass;
        } else {
            passwordField = pass;
        }
    }
}
