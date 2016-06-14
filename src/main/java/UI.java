import com.samuel.controller.Controller;
import ij.ImagePlus;
import ij.io.Opener;
import ij.process.ImageProcessor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Samuel on 5/6/2016.
 */
public class UI {

    private static JFrame frame;
    private JPanel Main;
    private JPanel North;
    private JPanel South;
    private JPanel Center;
    private JPanel LeftPanel;
    private JPanel RightPanel;
    private JPanel Image1Panel;
    private JPanel Image1ButtonsPanel;
    private JPanel Image2Panel;
    private JPanel Image2ButtonsPanel;
    private JButton LoadImageOneButton;
    private JButton DisplayImageOneButton;
    private JButton LoadImageTwoButton;
    private JButton DisplayImageTwoButton;
  /*  private JButton imageOneButton;
    private JButton imageTwoButton;
    private JButton fusionButton;
    private JButton imbunatatireButton;
    private JComboBox fusionMethodSelect;*/

    public UI() {
        LoadImageOneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDicom(1);
                System.out.println("Load Image One");
            }
        });
        LoadImageTwoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDicom(2);
                System.out.println("Load Image Two");
            }
        });

        DisplayImageOneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.displayImage(1);
            }
        });
        DisplayImageTwoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.displayImage(2);
            }
        });
      /*  fusionButton.addActionListener(new ActionListener() {
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
        });*/
    }

    private void openDicom(int imageNr) {
        JFileChooser fileChooser = new JFileChooser("/");
//        fileChooser.setFileFilter(new FileNameExtensionFilter("DICOM", "dcm"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("JPG", "jpg"));
//        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG", "png"));

        int retVal = fileChooser.showOpenDialog(frame);

        if (retVal == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            ImagePlus image = Controller.loadImage(path, imageNr);
            if (image != null) {
                paintImage(image, imageNr);
                System.out.println("DICOM opened");
            } else {
                System.out.println("DICOM open ERROR");
            }
        }
    }

    private void paintImage(ImagePlus originalImage, Integer imageNumber) {
        Graphics graphics = null;
        if (imageNumber == 1) {
            graphics = Image1Panel.getGraphics();
        } else {
            graphics = Image2Panel.getGraphics();
        }
        ImagePlus duplicatedImage = originalImage.duplicate();
        ImageProcessor imageProcessor = duplicatedImage.getProcessor();
        imageProcessor.setInterpolate(true);
        ImageProcessor resizedImageProcessor = imageProcessor.resize(225, 200);
        ImagePlus small = new ImagePlus("small", resizedImageProcessor);
        graphics.drawImage(small.getBufferedImage(), 0, 0, null);
    }

    public static void main(String[] args) {
        frame = new JFrame("UI");
        frame.setContentPane(new UI().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
    }
}
