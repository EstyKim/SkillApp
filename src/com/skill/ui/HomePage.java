package com.skill.ui;

import javax.swing.*;
import java.awt.*;

public class HomePage extends UIComponents {

    public HomePage() {
        super("Home Page", true, true, false); // Header: yes, Logout: yes, Back: no

        frame.add(createLabel("Ready to elevate your skills today?", 270, 140, 450, 40, 20, Font.BOLD));

        // Center image
        JLabel centerImage = new JLabel(scaleAndFitImage("images/students in tech.png", 280, 320));
        centerImage.setBounds(290, 180, 280, 320);
        frame.add(centerImage);

        // Navigation Buttons
        addNavButton("Browse Skills", 710, 200, new BrowseSkills(), "#FFB400");
        addNavButton("Join a Fun Group", 710, 270, new JoinGroup(), "#1E3CFF");
        addNavButton("Find a Peer", 710, 340, new FindaPeer(), "#00A032");
    }
}
