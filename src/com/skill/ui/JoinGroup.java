package com.skill.ui;

import javax.swing.*;
import java.awt.*;

public class JoinGroup extends UIComponents {

    public JoinGroup() {
        super("Join A Fun Group!", true, true, true);
        addBackHomeButton();

        frame.add(createGroupPanel("Java Meme Mondays", "#00FF99",
                "Share your favorite programmer memes every Monday. We vote the best one!", 120, 170));
        frame.add(createGroupPanel("Study Music and Chill", "#FFFF00",
                "Lo-fi beats, deep focus playlists, and good vibes while we code.", 480, 170));
        frame.add(createGroupPanel("Virtual Coffee Breaks", "#FF3366",
                "Hop in during the afternoon slump and chat about your day.", 120, 340));
        frame.add(createGroupPanel("Gamified Quizzes", "#FF9900",
                "Compete with peers in fun, timed quizzes on coding and soft skills.", 480, 340));
    }

    private JPanel createGroupPanel(String title, String colorHex, String description, int x, int y) {
        JPanel panel = new JPanel(null);
        panel.setBounds(x, y, 300, 150);
        panel.setBackground(new Color(10, 10, 80));

        //we can also use JTextArea for this one.
        JLabel titleLabel = createLabel(title, 15, 10, 270, 20, 14, Font.BOLD);
        titleLabel.setForeground(Color.decode(colorHex));
        panel.add(titleLabel);

        JLabel descLabel = new JLabel("<html>" + description + "</html>");
        descLabel.setForeground(Color.WHITE);
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        descLabel.setBounds(15, 35, 270, 40);
        panel.add(descLabel);

        JButton joinBtn = createStyledButton("Join Group", 90, 100, 120, 30, "#FFFFFF", "#DDDDDD");
        joinBtn.setForeground(Color.BLACK);
        panel.add(joinBtn);

        return panel;
    }
   
}
