package vn.edu.hcmus.student.sv19127568.slangdict.dbconnection;
import vn.edu.hcmus.student.sv19127568.slangdict.utils.SpringUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static javax.swing.JOptionPane.*;

/**
 * PACKAGE_NAME
 * Created by Thu Nguyen
 * Date 11/30/2021 - 7:58 PM
 * Description: GUI JPanel to receive database connection data from user
 */
public class DatabaseConnectionForm extends JPanel implements ActionListener {
    JTextField txtServerName, txtPort, txtDBName, txtUsername;
    JPasswordField txtPassword;
    final JFrame frame;
    final static int GAP = 10;

    /**
     * set layout and add component panels
     * @param frame JFrame
     */
    public DatabaseConnectionForm(JFrame frame) {
        this.frame = frame;
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        JPanel mainPanel = new JPanel() {
            public Dimension getMaximumSize() {
                Dimension pref = getPreferredSize();
                return new Dimension(Integer.MAX_VALUE, pref.height);
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(createEntryFields());
        mainPanel.add(createButton());

        mainPanel.setOpaque(true);
        frame.setContentPane(mainPanel);
    }

    /**
     * create entry fields to receive user's input
     * @return panel JComponent
     */
    protected JComponent createEntryFields() {
        JPanel panel = new JPanel(new SpringLayout());

        String[] labelStrings = {"Server name: ", "Port: ", "Database name: ", "Username: ", "Password: "};

        JLabel[] labels = new JLabel[labelStrings.length];
        JComponent[] fields = new JComponent[labelStrings.length];
        int fieldNum = 0;

        //Create the text field and set it up.
        txtServerName  = new JTextField();
        txtServerName.setColumns(20);
        fields[fieldNum++] = txtServerName;
        //txtServerName.setText("localhost");

        txtPort = new JTextField();
        txtPort.setColumns(20);
        fields[fieldNum++] = txtPort;
        //txtPort.setText("1433");

        txtDBName = new JTextField();
        txtDBName.setColumns(20);
        fields[fieldNum++] = txtDBName;
        //txtDBName.setText("DB_QLSV");

        txtUsername = new JTextField();
        txtUsername.setColumns(20);
        fields[fieldNum++] = txtUsername;
        //txtUsername.setText("sa");

        txtPassword = new JPasswordField();
        txtPassword.setColumns(20);
        fields[fieldNum++] = txtPassword;
        //txtPassword.setText("1234");

        //Associate label/field pairs, add everything,
        //and lay it out.
        for (int i = 0; i < labelStrings.length; i++) {
            labels[i] = new JLabel(labelStrings[i], JLabel.TRAILING);
            labels[i].setLabelFor(fields[i]);
            panel.add(labels[i]);
            panel.add(fields[i]);
        }
        SpringUtil.makeCompactGrid(panel,
                labelStrings.length, 2,
                GAP, GAP, //init x,y
                GAP, GAP);//xpad, ypad
        return panel;
    }

    /**
     * create button Connect
     * @return panel JComponent
     */
    protected JComponent createButton() {
        JPanel panel = new JPanel(new FlowLayout());
        JButton btnConnect = new JButton("Connect");
        btnConnect.addActionListener(this);
        btnConnect.setActionCommand("connect");
        btnConnect.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(btnConnect);
        return panel;
    }

    /**
     * handle action event when user presses button Connect
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("connect".equals(e.getActionCommand())) {
            DatabaseConnectionInfo.serverName = txtServerName.getText();
            DatabaseConnectionInfo.port = txtPort.getText();
            DatabaseConnectionInfo.dbName = txtDBName.getText();
            DatabaseConnectionInfo.username = txtUsername.getText();
            DatabaseConnectionInfo.password = txtPassword.getText();
            if (DatabaseConnectionInfo.serverName.length() != 0 && DatabaseConnectionInfo.port.length() != 0
                    && DatabaseConnectionInfo.dbName.length() != 0 && DatabaseConnectionInfo.username.length() != 0
                    && DatabaseConnectionInfo.password.length() != 0) {
                String url = "jdbc:sqlserver://" + DatabaseConnectionInfo.serverName + ":" + DatabaseConnectionInfo.port
                        + ";databaseName=" + DatabaseConnectionInfo.dbName;
                try {
                    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                    Connection con = DriverManager.getConnection(url, DatabaseConnectionInfo.username, DatabaseConnectionInfo.password);
                    if (con != null) {
                        showMessageDialog(this.frame, "Connection succcess!");
                        this.frame.setVisible(false);
                        SwingUtilities.invokeLater(() -> {
                            //StudentManagementForm.createAndShowGUI();
                        });
                        con.close();
                    }
                    else
                        showMessageDialog(this.frame, "Connection error!", "Error", ERROR_MESSAGE);
                }
                // Handle any errors that may have occurred.
                catch (Exception err) {
                    showMessageDialog(this.frame, "Make sure all fields are correct to connect!", "Error", ERROR_MESSAGE);
                    err.printStackTrace();
                }
            }
            else {
                showMessageDialog(this.frame, "Please fill out all fields to connect!", "Warning", WARNING_MESSAGE);
            }
        }
    }

    public static void createAndShowGUI() {
        //Create and set up the window.
        JFrame dbFrame = new JFrame("Database connection");
        dbFrame.setPreferredSize(new Dimension(375,300));
        dbFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add contents to the window.
        DatabaseConnectionForm dbForm = new DatabaseConnectionForm(dbFrame);
        dbFrame.add(dbForm);

        //Display the window.
        dbFrame.pack();
        dbFrame.setLocationRelativeTo(null);
        dbFrame.setVisible(true);
    }
}
