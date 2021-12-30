package vn.edu.hcmus.student.sv19127568.slangdict;

import vn.edu.hcmus.student.sv19127568.slangdict.models.SlangDict;

/**
 * vn.edu.hcmus.student.sv19127568.slangdict
 * Created by Thu Nguyen
 * Date 12/23/2021 - 12:56 AM
 * Description: Main class
 */
public class Main {
    /**
     * Main function
     * @param args String[]
     */
    public static void main(String[] args) {
        SlangDict.init();
        javax.swing.SwingUtilities.invokeLater(MainForm::createAndShowGUI);
    }
}
