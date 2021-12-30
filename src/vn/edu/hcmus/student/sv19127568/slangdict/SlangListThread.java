package vn.edu.hcmus.student.sv19127568.slangdict;

import javax.swing.*;
import java.util.Vector;

/**
 * vn.edu.hcmus.student.sv19127568.slangdict
 * Created by Thu Nguyen
 * Date 12/31/2021 - 12:42 AM
 * Description: SlangListThread class
 */
class SlangListThread extends Thread {
    JTextArea txtarea;
    JList<Slang> list;
    DefaultListModel<Slang> model;
    Vector<Slang> slangs;
    public SlangListThread(JTextArea txtarea, JList<Slang> list, DefaultListModel<Slang> model, Vector<Slang> slangs) {
        this.txtarea = txtarea;
        this.list = list;
        this.model = model;
        this.slangs = slangs;
    }

    public void run() {
        try {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    if (model != null) {
                        txtarea.setText("");
                        model.clear();
                        for (Slang slang : slangs) {
                            model.addElement(slang);
                        }
                        list.setModel(model);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
