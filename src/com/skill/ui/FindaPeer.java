package com.skill.ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import org.bson.Document;  // This is critical
import java.util.List;  // Add this import
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.skill.db.MongoConnection;
public class FindaPeer extends UIComponents {

	private JComboBox<String> skillDropdown; // Store dropdown references
    private JComboBox<String> availabilityDropdown;
    
    private JPanel selectedTutorCard = null;
    
    public FindaPeer() {
        super("Find A Peer", true, true, true); // header, logout, back
        
        addDropdowns();
        addActionButtons();
       
    }

    private void addDropdowns() {
        skillDropdown = createStyledComboBox(new String[]{
            "Skill Area", "Emotional Intelligence", "Time Management", "Public Speaking"
        }, 80);
        frame.add(skillDropdown);

        // Add the level dropdown (optional, if you need it later)
        JComboBox<String> levelDropdown = createStyledComboBox(new String[]{
            "Level", "Intern", "Assistant", "Pro Trainee"
        }, 260);
        frame.add(levelDropdown);

        availabilityDropdown = createStyledComboBox(new String[]{
            "Availability", "Twice a week", "Weekends", "Evenings"
        }, 440);
        frame.add(availabilityDropdown);
    }

    private JComboBox<String> createStyledComboBox(String[] options, int x) {
        JComboBox<String> box = new JComboBox<>(options);
        box.setBounds(x, 200, 150, 35);
        box.setBackground(new Color(5, 2, 70));
        box.setForeground(Color.WHITE);
        box.setFont(new Font("SansSerif", Font.PLAIN, 13));
        styleComboBox(box); // Now lives in UIComponents!
        return box;
    }

    private void addActionButtons() {
        JButton searchButton = createStyledButton("Search", 620, 200, 100, 35, "#008080", "#007070");
        searchButton.addActionListener(e -> refreshTutors());
        frame.add(searchButton);

        JButton connectButton = createStyledButton("Connect", 740, 200, 100, 35, "#0066CC", "#0050AA");
        connectButton.addActionListener(e -> handleConnect());
        frame.add(connectButton);
    }

    private void refreshTutors() {
        // Clear existing tutor cards
        Component[] components = frame.getComponents();
        for (Component comp : components) {
            if (comp instanceof JPanel && comp.getY() >= 360) {
                frame.remove(comp);
            }
        }

        // Reload tutors
        addTutorSection();
        frame.revalidate();
        frame.repaint();
    }

    private void addTutorSection() {
        selectedTutorCard = null;
        JLabel title = createLabel("Connect to a Tutor", 0, 300, 900, 40, 20, Font.BOLD);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(title);

        // Get selected filters from dropdowns
        String skill = skillDropdown.getSelectedItem().toString();
        String availability = availabilityDropdown.getSelectedItem().toString();

        // Fetch tutors from MongoDB
        List<Document> tutors = MongoConnection.findTutors(skill, availability);

        if (tutors.isEmpty()) {
            JOptionPane.showMessageDialog(frame, 
                "No tutors available with these criteria.", 
                "No Tutors Found", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int x = 80;
        for (Document tutor : tutors) {
            String description = String.format(
                "%s\n%s\nAvailable: %s",
                tutor.getString("skill"),
                tutor.getString("level"),
                tutor.getString("availability")
            );
            JPanel card = createTutorCard(tutor.getString("name"), description);
            card.setBounds(x, 360, 220, 160);
            
            // Add selection functionality
            card.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Deselect previous card
                    if (selectedTutorCard != null) {
                        selectedTutorCard.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
                    }
                    
                    // Select new card
                    selectedTutorCard = card;
                    card.setBorder(new LineBorder(Color.GREEN, 3, true));
                }
            });
            
            frame.add(card);
            x += 260;
        }
    }

    private JPanel createTutorCard(String name, String description) {
    	JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.decode("#050246"));
        // Set initial border (will be changed when selected)
        card.setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));

        JLabel icon = new JLabel(new ImageIcon("images/person-18-16.png"));
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(icon);

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(nameLabel);

        JTextArea desc = new JTextArea(description);
        desc.setFont(new Font("SansSerif", Font.PLAIN, 12));
        desc.setEditable(false);
        desc.setOpaque(false);
        desc.setWrapStyleWord(true);
        desc.setLineWrap(true);
        desc.setForeground(Color.WHITE);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(desc);

        return card;
    }

    private void handleConnect() {
        if (selectedTutorCard == null) {
            showWarning("Please select a tutor first!", "No Selection");
            return;
        }

        String tutorName = extractTutorName(selectedTutorCard);
        
        if (confirmSendRequest(tutorName)) {
            showSuccess("Request sent to " + tutorName + "!");
            navigateToHome();
        }
    }

    // Helper methods
    private String extractTutorName(JPanel card) {
        for (Component comp : card.getComponents()) {
            if (comp instanceof JLabel && ((JLabel)comp).getIcon() == null) {
                return ((JLabel)comp).getText();
            }
        }
        return "the selected tutor";
    }

    private boolean confirmSendRequest(String tutorName) {
        int response = JOptionPane.showConfirmDialog(
            frame,
            "Send request to " + tutorName + "?",
            "Confirm Request",
            JOptionPane.YES_NO_OPTION
        );
        return response == JOptionPane.YES_OPTION;
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(
            frame,
            message,
            "Success",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showWarning(String message, String title) {
        JOptionPane.showMessageDialog(
            frame,
            message,
            title,
            JOptionPane.WARNING_MESSAGE
        );
    }

    private void navigateToHome() {
        frame.dispose();
        new HomePage().showPage();
    }
    
}

