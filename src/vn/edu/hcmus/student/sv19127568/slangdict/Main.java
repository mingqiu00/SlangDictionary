package vn.edu.hcmus.student.sv19127568.slangdict;

import vn.edu.hcmus.student.sv19127568.slangdict.dbconnection.DatabaseConnectionForm;

/**
 * vn.edu.hcmus.student.sv19127568.slangdict
 * Created by Thu Nguyen
 * Date 12/21/2021 - 1:43 AM
 * Description: Main class
 */
public class Main {
    /**
     * Main function
     * @param args String[]
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(DatabaseConnectionForm::createAndShowGUI);
    }
}
