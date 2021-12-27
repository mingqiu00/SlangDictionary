package vn.edu.hcmus.student.sv19127568.slangdict;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * vn.edu.hcmus.student.sv19127568.slangdict
 * Created by Thu Nguyen
 * Date 12/23/2021 - 12:56 AM
 * Description: Main Form
 */
public class MainForm extends JPanel implements ActionListener {
    JLabel lbSearch;
    JTextField txtSearch;
    JComboBox cbSearch;
    final JFrame frame;

    public MainForm(JFrame frame) {
        JPanel mainPanel = new JPanel(new FlowLayout());
        add(mainPanel, BorderLayout.CENTER);
        lbSearch = new JLabel("Search");
        txtSearch = new JTextField();
        txtSearch.setColumns(20);
        txtSearch.setToolTipText("Search for a slang word");
        String[] options = {"By slang", "By definition", "Random search"};
        cbSearch = new JComboBox(options);
        cbSearch.addActionListener(this);
        cbSearch.setActionCommand("search");
        this.frame = frame;
        mainPanel.add(lbSearch);
        mainPanel.add(txtSearch);
        mainPanel.add(cbSearch);
        mainPanel.setOpaque(true);
        frame.setContentPane(mainPanel);
    }

    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu;
        JMenuItem menuItem;

        menuBar = new JMenuBar();

        menu = new JMenu("Menu");
        menu.setToolTipText("The main menu");
        menuBar.add(menu);

        menuItem = new JMenuItem("History");
        menuItem.setToolTipText("View search history");
        menu.add(menuItem);

        menuItem = new JMenuItem("Reset");
        menuItem.setToolTipText("Reset slang words list");
        menu.add(menuItem);

        menu = new JMenu("Action");
        menu.setToolTipText("Perform action on a particular slang word");
        menuBar.add(menu);

        menuItem = new JMenuItem("Add");
        menuItem.setToolTipText("Add a new slang word");
        menu.add(menuItem);

        menuItem = new JMenuItem("Edit");
        menuItem.setToolTipText("Edit an existing slang word");
        menu.add(menuItem);

        menuItem = new JMenuItem("Delete");
        menuItem.setToolTipText("Delete an existing slang word");
        menu.add(menuItem);

        menu = new JMenu("Fun");
        menu.setToolTipText("Fun things to do with slang words");
        menuBar.add(menu);

        menuItem = new JMenuItem("Random slang");
        menuItem.setToolTipText("Random a slang word and choose the right definition");
        menu.add(menuItem);

        menuItem = new JMenuItem("Random definition");
        menuItem.setToolTipText("Random a definition and choose the right slang word");
        menu.add(menuItem);

        return menuBar;
    }

    public static void createAndShowGUI() {
        // Create and set up the window
        JFrame mainFrame = new JFrame("Slang dictionary");
        mainFrame.setPreferredSize(new Dimension(500,400));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add contents to the window
        MainForm mainForm = new MainForm(mainFrame);
        mainFrame.setJMenuBar(mainForm.createMenuBar());
        mainFrame.add(mainForm);

        // Display the window
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if("search".equals(e.getActionCommand())) {
            String input = txtSearch.getText();
            if (input.length() == 0) {
                showMessageDialog(this.frame, "Please input a string to search!", "Error", ERROR_MESSAGE);
            } else {
                JComboBox cb = (JComboBox) e.getSource();
                String option = (String)cb.getSelectedItem();
                HashMap<String, String> res = null;
                if (option.equals("By slang")) {
                   res = SlangDict.searchBySlang(input);
                   if (res.isEmpty()) {
                       showMessageDialog(this.frame, "There is no slang matching that!");
                   } else {
                       for(Map.Entry<String, String> entry : res.entrySet()) {
                           String key = entry.getKey();
                           String value = entry.getValue();
                           System.out.println(key+":"+value);
                       }
                   }
                } else if (option.equals("By definition")) {
                    res = SlangDict.searchByDefinition(input);
                    if (res.isEmpty()) {
                        showMessageDialog(this.frame, "There is no slang containing that!");
                    } else {
                        for(Map.Entry<String, String> entry : res.entrySet()) {
                            String key = entry.getKey();
                            String value = entry.getValue();
                            System.out.println(key+":"+value);
                        }
                    }
                } else if (option.equals("Random search")) {

                }
            }
        }
    }
}
