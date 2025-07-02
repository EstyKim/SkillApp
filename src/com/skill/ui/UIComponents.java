package com.skill.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;
import java.awt.event.*;

public abstract class UIComponents {
    protected JFrame frame;
    protected JPanel headerPanel;

    public UIComponents(String title, boolean showHeader, boolean showLogout, boolean showBack) {
        frame = new JFrame(title);
        frame.setSize(900, 600);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        if (showHeader) addHeader();
        if (showLogout) addLogoutButton();
        if (showBack) addBackHomeButton();
    }

    protected void addHeader() {
        headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#050246"));
        headerPanel.setBounds(-16, -2, 922, 92);
        headerPanel.setLayout(null);
        frame.add(headerPanel);

        // FMTALI logo
        ImageIcon fmtaliIcon = new ImageIcon("images/fmtali logo.png");
        Image fmtaliImg = fmtaliIcon.getImage().getScaledInstance(363, 80, Image.SCALE_SMOOTH);
        JLabel fmtaliLabel = new JLabel(new ImageIcon(fmtaliImg));
        fmtaliLabel.setBounds(20, 14, 363, 80);
        headerPanel.add(fmtaliLabel);

        // SkillMate logo
        ImageIcon skillIcon = new ImageIcon("images/skillmate logo.png");
        Image scaledSkill = skillIcon.getImage().getScaledInstance(137, 79, Image.SCALE_SMOOTH);
        JLabel skillmateLogo = new JLabel(new ImageIcon(scaledSkill));
        skillmateLogo.setBounds(640, 9, 137, 79);
        headerPanel.add(skillmateLogo);

        JLabel skillmateText = new JLabel("SkillMate");
        skillmateText.setFont(new Font("Arial", Font.BOLD, 30));
        skillmateText.setForeground(Color.WHITE);
        skillmateText.setBounds(758, 42, 135, 29);
        headerPanel.add(skillmateText);
    }

    protected void addLogoutButton() {
        JButton logoutBtn = new JButton("Log Out");
        logoutBtn.setBounds(710, 110, 160, 35);
        logoutBtn.setBackground(new Color(190, 30, 0));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        logoutBtn.setFocusPainted(false);
        frame.add(logoutBtn);

        Color original = logoutBtn.getBackground();
        Color hover = new Color(150, 0, 0);

        logoutBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                logoutBtn.setBackground(hover);
            }

            public void mouseExited(MouseEvent e) {
                logoutBtn.setBackground(original);
            }
        });

        logoutBtn.addActionListener(e -> {
            frame.dispose();
            new LoginPage().showPage();
        });
    }

    protected void addBackHomeButton() {
        JButton backBtn = new JButton("Back to Home");
        backBtn.setBounds(30, 110, 160, 35);
        backBtn.setBackground(Color.decode("#FF00B3"));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        backBtn.setFocusPainted(false);
        frame.add(backBtn);

        Color original = backBtn.getBackground();
        Color hover = new Color(255, 0, 179);

        backBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                backBtn.setBackground(hover);
            }

            public void mouseExited(MouseEvent e) {
                backBtn.setBackground(original);
            }
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            new HomePage().showPage();
        });
    }

    public void showPage() {
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }
    
 // Utility to create standard labels
    protected JLabel createLabel(String text, int x, int y, int width, int height, int fontSize, int fontStyle) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, width, height);
        label.setFont(new Font("Arial", fontStyle, fontSize));
        label.setForeground(Color.decode("#050246"));
        return label;
    }

    // Utility to create hover-enabled buttons
    protected JButton createStyledButton(String text, int x, int y, int width, int height, String hexBg, String hexHover) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, width, height);
        btn.setBackground(Color.decode(hexBg));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setFocusPainted(false);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(Color.decode(hexHover));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(Color.decode(hexBg));
            }
        });

        return btn;
    }

    // Utility to create scaled images
    protected ImageIcon scaleAndFitImage(String path, int maxW, int maxH) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage();
        int width = icon.getIconWidth();
        int height = icon.getIconHeight();

        if (width <= 0 || height <= 0) return icon;

        float aspect = (float) width / height;
        if (width > maxW) {
            width = maxW;
            height = (int) (width / aspect);
        }
        if (height > maxH) {
            height = maxH;
            width = (int) (height * aspect);
        }

        return new ImageIcon(img.getScaledInstance(width, height, Image.SCALE_SMOOTH));
    }

    protected void addNavButton(String label, int x, int y, UIComponents targetPage, String hexColor) {
        JButton btn = createStyledButton(label, x, y, 160, 35, hexColor, "#CCCCCC");
        btn.addActionListener(e -> {
            frame.dispose();
            targetPage.showPage();
        });
        frame.add(btn);
    }
    protected void styleComboBox(JComboBox<String> comboBox) {
    	comboBox.setBackground(Color.decode("#050246")); // 🟦 Deep blue background for field
    	comboBox.setForeground(Color.decode("#050246"));         // 🤍 White text in field
    	comboBox.setFont(new Font("SansSerif", Font.BOLD, 13));
    	comboBox.setFocusable(false);                // Removes dotted focus border
    	
        comboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                return new javax.swing.plaf.basic.BasicArrowButton(
                    BasicArrowButton.SOUTH,
                    new Color(5, 2, 70),
                    new Color(5, 2, 70),
                    Color.WHITE,
                    new Color(5, 2, 70)
                );
            }
        });

        comboBox.setRenderer(new DefaultListCellRenderer() {
        	 private static final long serialVersionUID = 1L;
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBackground(isSelected ? new Color(20, 20, 90) : new Color(5, 2, 70));
                label.setForeground(Color.WHITE);
                label.setFont(new Font("SansSerif", Font.PLAIN, 13));
                return label;
            }
        });
    }


}

