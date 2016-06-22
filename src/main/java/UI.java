import com.samuel.controller.Controller;
import com.samuel.enums.ImagesEnum;
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
    private JComboBox fusionMethod;
    private JLabel methodLabel;
    private JButton fuseButton;
    private JPanel SouthLeftPanel;
    private JPanel SouthRightPanel;
    private JPanel FusedImagePanel;
    private JPanel FusedImageButtonsPanel;
    private JButton SaveFusedImage;
    private JButton DisplayFusedImage;
    private JButton EnhanceResultButton;

    public UI() {
        LoadImageOneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDicom(ImagesEnum.IMAGE_ONE);
                System.out.println("Load Image One");
            }
        });

        LoadImageTwoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDicom(ImagesEnum.IMAGE_TWO);
                System.out.println("Load Image Two");
            }
        });

        DisplayImageOneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.showImage(ImagesEnum.IMAGE_ONE.getImageNr());
                System.out.println("Display Image One");
            }
        });

        DisplayImageTwoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.showImage(ImagesEnum.IMAGE_TWO.getImageNr());
                System.out.println("Display Image Two");
            }
        });

        fuseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Graphics graphics = FusedImagePanel.getGraphics();
                Controller.fuse(fusionMethod.getSelectedIndex(), graphics);
                System.out.println("Fuse images");
            }
        });

        DisplayFusedImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.showImage(ImagesEnum.FUSED_IMAGE.getImageNr());
                System.out.println("Display fused image");
            }
        });

        SaveFusedImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveImage();
            }
        });

        EnhanceResultButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.enhanceResult();
            }
        });
    }

    private void openDicom(ImagesEnum imagesEnum) {
        JFileChooser fileChooser = new JFileChooser("/");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JPG", "jpg"));

        int retVal = fileChooser.showOpenDialog(frame);

        if (retVal == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();

            if (imagesEnum.equals(ImagesEnum.IMAGE_ONE)) {
                Graphics graphics = Image1Panel.getGraphics();
                Controller.loadImage(path, ImagesEnum.IMAGE_ONE, graphics);
            } else {
                if (imagesEnum.equals(ImagesEnum.IMAGE_TWO)) {
                    Graphics graphics = Image2Panel.getGraphics();
                    Controller.loadImage(path, ImagesEnum.IMAGE_TWO, graphics);
                }
            }
        }
    }

    private void saveImage() {
        JFileChooser fileChooser = new JFileChooser("/");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setApproveButtonText("Save");

        int retVal = fileChooser.showOpenDialog(frame);

        if (retVal == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            Controller.saveImage(path);
        }
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
