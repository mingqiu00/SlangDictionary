package vn.edu.hcmus.student.sv19127568.slangdict;

import vn.edu.hcmus.student.sv19127568.slangdict.models.Slang;
import vn.edu.hcmus.student.sv19127568.slangdict.models.SlangDict;
import vn.edu.hcmus.student.sv19127568.slangdict.utils.SpringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
    JTextField txtSearch, txtSlang, txtDef;
    String slang, def;
    JComboBox cbSearch;
    JList<Slang> slangList;
    JList<String> historyList;
    DefaultListModel<Slang> slangModel;
    DefaultListModel<String> historyModel;
    JSplitPane splitPane;
    JDialog historyDialog, slangDetailsDialog;
    final JFrame frame;
    final static int GAP = 10;

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
            if (!e.getValueIsAdjusting()) {
                Slang s = slangList.getSelectedValue();
                if (s != null) {
                    SlangDict.history.add(s.getSlang());
                    txtMeaning.setText(s.getMeaning());
                }
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
        mainPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        mainPanel.add(txtSearch);
        mainPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        mainPanel.add(cbSearch);
        splitPane.setRightComponent(new JScrollPane(txtMeaning,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        resPanel.add(splitPane);
    }

    /**
     * create menu bar
     * @return menu bar JMenuBar
     */
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
        menuItem.setActionCommand("history");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Reset");
        menuItem.setToolTipText("Reset slang words list");
        menuItem.setActionCommand("reset");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menu = new JMenu("Action");
        menu.setToolTipText("Perform action on a particular slang word");
        menuBar.add(menu);

        menuItem = new JMenuItem("Add");
        menuItem.setToolTipText("Add a new slang word");
        menuItem.setActionCommand("add");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Edit");
        menuItem.setToolTipText("Edit an existing slang word");
        menuItem.setActionCommand("edit");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Delete");
        menuItem.setToolTipText("Delete an existing slang word");
        menuItem.setActionCommand("delete");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menu = new JMenu("Fun");
        menu.setToolTipText("Fun things to do with slang words");
        menuBar.add(menu);

        menuItem = new JMenuItem("On this day slang");
        menuItem.setToolTipText("Random a slang word and its definition");
        menuItem.setActionCommand("random_otd");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Random slang");
        menuItem.setToolTipText("Random a slang word and choose the right definition");
        menuItem.setActionCommand("random_sl");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Random definition");
        menuItem.setToolTipText("Random a definition and choose the right slang word");
        menuItem.setActionCommand("random_def");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        return menuBar;
    }

    private void createHistoryDialog(Vector<String> history) {
        historyDialog = new JDialog(this.frame, "History", true);
        historyDialog.setLayout(new BorderLayout());
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        historyList = new JList<>(history);
        JScrollPane scrollPane = new JScrollPane(historyList);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane);

        historyDialog.add(panel);
        historyDialog.setSize(new Dimension(300, 400));
        historyDialog.setLocationRelativeTo(null);
        historyDialog.setVisible(true);
    }

    private void createSlangDetailsDialog(String action, String slang_, String def_) {
        slangDetailsDialog = new JDialog(this.frame, "Slang details", true);
        slangDetailsDialog.setLayout(new BorderLayout());
        JPanel panel = new JPanel(new SpringLayout());

        String[] labelStrings = {"Slang: ", "Definition: "};
        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];
        int fieldNum = 0;

        txtSlang = new JTextField();
        txtSlang.setColumns(20);
        fields[fieldNum++] = txtSlang;
        if (action.equals("edit")) {
            txtSlang.setEditable(false);
        }
        txtSlang.setText(slang_);

        txtDef = new JTextField();
        txtDef.setColumns(20);
        fields[fieldNum++] = txtDef;
        txtDef.setText(def_);

        for (int i = 0; i < labelStrings.length; i++) {
            labels[i] = new JLabel(labelStrings[i], JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            panel.add(labels[i]);
            panel.add(fields[i]);
        }
        SpringUtil.makeCompactGrid(panel, labelStrings.length, 2,
                GAP, GAP, // init x, y
                GAP, GAP); // xpad, ypad
        slangDetailsDialog.add(panel, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton btnOK, btnCancel;
        btnOK = new JButton("OK");
        btnOK.addActionListener(e -> {
            slang = txtSlang.getText();
            def = txtDef.getText();
            if (slang.length() != 0 && def.length() != 0) {
                if (action.equals("add")) {
                    String meaning = SlangDict.isExisted(slang);
                    if (meaning.equals("")) {
                        SlangDict.add(slang, def);
                        showMessageDialog(slangDetailsDialog, "Add successfully!");
                        slangDetailsDialog.setVisible(false);
                    } else {
                        Object[] options = {"Overwrite", "Duplicate"};
                        int selection = JOptionPane.showOptionDialog(slangDetailsDialog, "Existing slang in dictionary?",
                                "Existing slang options", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, null);
                        switch (selection) {
                            // overwrite slang with new definition
                            case 0:
                                SlangDict.edit(slang, def);
                                slangDetailsDialog.setVisible(false);
                                break;
                            // duplicate to a new slang
                            case 1:
                                SlangDict.edit(slang, meaning + "| " + def);
                                slangDetailsDialog.setVisible(false);
                                break;
                        }
                    }
                } else if (action.equals("edit")) {
                    SlangDict.edit(slang, txtDef.getText());
                    showMessageDialog(slangDetailsDialog, "Edit successfully!");
                    slangDetailsDialog.setVisible(false);
                }
            } else {
                showMessageDialog(slangDetailsDialog, "Missing required data fields!", "Error", ERROR_MESSAGE);
            }
        });
        btnOK.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPanel.add(btnOK);

        btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(e -> slangDetailsDialog.setVisible(false));
        btnPanel.add(btnCancel);
        slangDetailsDialog.add(btnPanel, BorderLayout.PAGE_END);

        slangDetailsDialog.setSize(new Dimension(450, 200));
        slangDetailsDialog.setLocationRelativeTo(null);
        slangDetailsDialog.setVisible(true);
    }

    /**
     * create and show main GUI
     */
    public static void createAndShowGUI() {
        // Create and set up the window
        JFrame mainFrame = new JFrame("Slang dictionary");
        mainFrame.setPreferredSize(new Dimension(600,500));
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                SlangDict.saveDict();
                SlangDict.saveHis();
                System.exit(0);
            }
        });

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
        if ("history".equals(e.getActionCommand())) {
            javax.swing.SwingUtilities.invokeLater(() -> createHistoryDialog(SlangDict.history));
        }
        if ("reset".equals(e.getActionCommand())) {
            SlangDict.reset();
            showMessageDialog(this.frame, "Reset successfully!");
        }
        if ("add".equals(e.getActionCommand())) {
            javax.swing.SwingUtilities.invokeLater(() -> createSlangDetailsDialog("add", "", ""));
        }
        if ("edit".equals(e.getActionCommand())) {
            int idx = slangList.getSelectedIndex();
            if (idx == -1) {
                showMessageDialog(this.frame, "Please select a slang to edit!");
            } else {
                Slang s = slangList.getSelectedValue();
                javax.swing.SwingUtilities.invokeLater(() -> createSlangDetailsDialog("edit", s.getSlang(), s.getMeaning()));
            }
        }
        if ("delete".equals(e.getActionCommand())) {
            int idx = slangList.getSelectedIndex();
            if (idx == -1) {
                showMessageDialog(this.frame, "Please select a slang to delete!");
            } else {
                int selection = JOptionPane.showConfirmDialog(this.frame, "Are you sure?",
                        "Delete a slang", JOptionPane.YES_NO_OPTION);
                switch (selection) {
                    // YES
                    case 0:
                        try {
                            Slang s = slangList.getSelectedValue();
                            slangModel.remove(idx);
                            showMessageDialog(frame, "Delete successfully!");
                            SlangDict.delete(s.getSlang(), s.getMeaning());
                        } catch (Exception err) {
                            // ignored
                        } finally {
                            break;
                        }
                    // NO
                    case 1:
                        break;
                }
            }
        }
        if ("random_otd".equals(e.getActionCommand())) {
            Slang s = SlangDict.randomOTD();
            JOptionPane.showMessageDialog(this.frame, s.getSlang() + ": " + s.getMeaning(),
                    "On this day slang word", JOptionPane.PLAIN_MESSAGE);
        }
        if ("random_sl".equals(e.getActionCommand())) {

        }
        if ("random_def".equals(e.getActionCommand())) {

        }
    }
}
