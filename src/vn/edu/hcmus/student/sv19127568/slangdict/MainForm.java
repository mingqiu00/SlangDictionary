package vn.edu.hcmus.student.sv19127568.slangdict;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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
    JTextArea txtMeaning;
    JTextField txtSearch;
    JComboBox cbSearch;
    JList<Slang> slangList;
    DefaultListModel<Slang> slangModel;
    JSplitPane splitPane;
    final JFrame frame;

    public MainForm(JFrame frame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
        mainPanel.setPreferredSize(new Dimension(500, 30));
        mainPanel.setMaximumSize(new Dimension(1000, 50));
        add(mainPanel);
        JPanel resPanel = new JPanel();
        resPanel.setLayout(new BoxLayout(resPanel, BoxLayout.X_AXIS));
        resPanel.setPreferredSize(new Dimension(500, 300));
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(resPanel);
        splitPane = new JSplitPane();
        lbSearch = new JLabel("Search");
        txtSearch = new JTextField();
        txtSearch.setColumns(20);
        txtSearch.setToolTipText("Search for a slang word");

        String[] options = {"By slang", "By definition"};
        cbSearch = new JComboBox(options);
        cbSearch.addActionListener(this);
        cbSearch.setActionCommand("search");
        slangList = new JList<Slang>();
        slangList.getSelectionModel().addListSelectionListener(e -> {
            Slang s = slangList.getSelectedValue();
            if (s != null) {
                txtMeaning.setText(s.getMeaning());
            }
        });
        slangModel = new DefaultListModel<>();
        txtMeaning = new JTextArea();
        txtMeaning.setEditable(false);
        Font font = txtMeaning.getFont();
        txtMeaning.setFont(font.deriveFont(font.getSize() + 2.0f));

        this.frame = frame;

        splitPane.setLeftComponent(new JScrollPane(slangList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        mainPanel.add(lbSearch);
        mainPanel.add(txtSearch);
        mainPanel.add(cbSearch);
        splitPane.setRightComponent(new JScrollPane(txtMeaning,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        resPanel.add(splitPane);
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

        menuItem = new JMenuItem("On this day slang");
        menuItem.setToolTipText("Random a slang word and its definition");
        menu.add(menuItem);

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
        mainFrame.setPreferredSize(new Dimension(600,500));
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add contents to the window
        MainForm mainForm = new MainForm(mainFrame);
        mainForm.setOpaque(true);
        mainFrame.add(mainForm);
        mainFrame.setContentPane(mainForm);
        mainFrame.setJMenuBar(mainForm.createMenuBar());

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
                Vector<Slang> slangs = new Vector<>();
                if (option.equals("By slang")) {
                   res = SlangDict.searchBySlang(input);
                } else if (option.equals("By definition")) {
                    res = SlangDict.searchByDefinition(input);
                }
                if (res == null || res.isEmpty()) {
                    showMessageDialog(this.frame, "There is no slang matching that!");
                } else {
                    for(Map.Entry<String, String> entry : res.entrySet()) {
                        String key = entry.getKey();
                        String value = entry.getValue();
                        slangs.add(new Slang(key, value));
                    }
                    SlangListThread sl = new SlangListThread(txtMeaning, slangList, slangModel, slangs);
                    sl.start();
                }
            }
        }
    }
}
