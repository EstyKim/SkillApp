package com.skill.ui;

import javax.swing.*;
import java.awt.*;

public class BrowseSkills extends UIComponents {

    public BrowseSkills() {
        super("Browse Skills", true, true, true); // Header, Logout, Back Home

        // Title
        JLabel title = createLabel("Browse Skills", 300, 150, 300, 40, 24, Font.BOLD);
        title.setOpaque(true);
        title.setBackground(new Color(15, 6, 61));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(title);

        // Card Panel
        JPanel cardsPanel = new JPanel(new GridLayout(1, 3, 30, 10));
        cardsPanel.setBounds(50, 210, 800, 350);
        cardsPanel.setOpaque(false);
        frame.add(cardsPanel);

        // Add Cards
        cardsPanel.add(createSkillCard(
            "Public Speaking",
            "Build confidence in front of an audience. Learn how to structure your thoughts, manage stage anxiety, and deliver powerful speeches.",
            "Learn More",
            "Public SPeaking.png"
        ));

        JPanel timeCard = createSkillCard(
            "Time Management",
            "Master the art of planning your day. Explore techniques to prioritize tasks, avoid distractions, and get more done with less stress.",
            "Learn More",
            "Time Management image.png"
        );
        timeCard.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0)); // Push down
        cardsPanel.add(timeCard);

        cardsPanel.add(createSkillCard(
            "Emotional Intelligence",
            "Improve how you understand and respond to emotions. Strengthen your self-awareness, empathy, and communication skills in any setting.",
            "Learn More",
            "Emotional Intelligence.png"
        ));
    }

    private JPanel createSkillCard(String title, String description, String linkText, String imageFile) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(250, 300));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 230)));

        // Scaled Image
        JLabel imageLabel = new JLabel(scaleAndFitImage("images/" + imageFile, 180, 100));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(10));
        card.add(imageLabel);

        // Title
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 12));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(15, 6, 61));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(10));
        card.add(titleLabel);

        // Description
        JTextArea desc = new JTextArea(description);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        desc.setOpaque(false);
        desc.setFont(new Font("Arial", Font.PLAIN, 14));
        desc.setForeground(Color.DARK_GRAY);
        desc.setMaximumSize(new Dimension(220, 100));
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(10));
        card.add(desc);

        // Learn More
        JLabel learnMore = new JLabel("<html><u>" + linkText + "</u></html>");
        learnMore.setFont(new Font("Arial", Font.BOLD, 12));
        learnMore.setForeground(new Color(255, 105, 180));
        learnMore.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        learnMore.setAlignmentX(Component.CENTER_ALIGNMENT);
        learnMore.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        card.add(Box.createVerticalStrut(10));
        card.add(learnMore);
    
        return card;
    }
    
}
