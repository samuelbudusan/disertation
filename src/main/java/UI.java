import com.samuel.controller.Controller;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Samuel on 5/6/2016.
 */
public class UI {

    private static JFrame frame;
    private JPanel panel1;
    private JButton imageOneButton;
    private JButton imageTwoButton;
    private JButton fusionButton;
    private JButton imbunatatireButton;
    private JComboBox fusionMethodSelect;

    public UI() {
        imageOneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDicom(1);
                System.out.println("Load Image One");
            }
        });
        imageTwoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDicom(2);
                System.out.println("Load Image Two");
            }
        });
        fusionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.fuse(fusionMethodSelect.getSelectedIndex());
                System.out.println("Fuse images");
            }
        });

        imbunatatireButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.improveQuality();
                System.out.println("Improve quality for merged image");
            }
        });
    }

    private boolean openDicom(int imageNr) {

        JFileChooser fileChooser = new JFileChooser("/");
//        fileChooser.setFileFilter(new FileNameExtensionFilter("DICOM", "dcm"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("JPG", "jpg"));
//        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG", "png"));

        int retVal = fileChooser.showOpenDialog(frame);
        boolean ok = false;

        if (retVal == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            System.out.println(path);
            if (Controller.loadImage(path, imageNr)) {
                // ok
                System.out.println("DICOM opened");
                ok = true;
            } else {
                // error
                System.out.println("DICOM open ERROR");
            }
        }
        return ok;
    }

    public static void main(String[] args) {
        frame = new JFrame("UI");
        frame.setContentPane(new UI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }
}
