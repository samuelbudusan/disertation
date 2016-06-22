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
    private JPanel ResultsPanel;
    private JPanel ResultsButtonsPanel;
    private JButton LoadPerfectImage;
    private JButton RunQualityMetrics;
    private JCheckBox perfectImageLoaded;
    private JTextArea textResults;
    private JButton runQualityMetricsProcess;
    private JSpinner levelSpinner;
    private JSpinner sigmaSpinner;
    private JLabel levelLabel;
    private JLabel sigmaLabel;
    private JPanel FusionOptionsNorth;
    private JPanel FusionOptionsSouth;
    private JPanel LeftFuzOpt;
    private JPanel RightFuzOpt;
    private JMenuBar jMenuBar;
    private JMenu jMenu1;
    private JMenuItem jMenuItem1;
    private JMenuItem jMenuItem2;

    public UI() {
        levelSpinner.setVisible(false);
        sigmaSpinner.setVisible(false);
        levelLabel.setVisible(false);
        sigmaLabel.setVisible(false);
        SpinnerNumberModel modelLevel = new SpinnerNumberModel(3, 1, 9, 1);
        levelSpinner.setModel(modelLevel);
        SpinnerNumberModel modelSigma = new SpinnerNumberModel(3.0, 1.0, 15.0, 0.1);
        sigmaSpinner.setModel(modelSigma);

        /*jMenuBar = new JMenuBar();
        jMenu1 = new JMenu();
        jMenu1.setText("File");
        jMenuItem1 = new JMenuItem();

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Exit");
        jMenu1.add(jMenuItem1);
        frame.setJMenuBar(jMenuBar);*/

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
                int level = (Integer) levelSpinner.getValue();
                double sigma = (Double) sigmaSpinner.getValue();

                Controller.fuse(fusionMethod.getSelectedIndex(),level, sigma, graphics);
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

        LoadPerfectImage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean result = openPerfectImage();
                if (result) {
                    RunQualityMetrics.setEnabled(true);
                    perfectImageLoaded.setSelected(true);
                    System.out.println("Perfect image Loaded");
                } else {
                    System.out.println("Error while trying to load perfect image");
                }
            }
        });

        RunQualityMetrics.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.getQualityMetrics(textResults);
            }
        });

        runQualityMetricsProcess.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Controller.runQualityMetricsProcess();
            }
        });

        fusionMethod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Integer selectedIndex = fusionMethod.getSelectedIndex();
                switch (selectedIndex) {
                    case 3:
                        levelSpinner.setVisible(true);
                        sigmaSpinner.setVisible(true);
                        levelLabel.setVisible(true);
                        sigmaLabel.setVisible(true);
                        break;
                    case 4:
                        levelSpinner.setVisible(true);
                        sigmaSpinner.setVisible(true);
                        levelLabel.setVisible(true);
                        sigmaLabel.setVisible(true);
                        break;
                    case 5:
                        levelSpinner.setVisible(true);
                        sigmaSpinner.setVisible(false);
                        levelLabel.setVisible(true);
                        sigmaLabel.setVisible(false);
                        break;
                    default:
                        levelSpinner.setVisible(false);
                        sigmaSpinner.setVisible(false);
                        levelLabel.setVisible(false);
                        sigmaLabel.setVisible(false);
                }
                levelSpinner.setValue(3);
                sigmaSpinner.setValue(3.0);
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

    private boolean openPerfectImage() {
        boolean result = false;
        JFileChooser fileChooser = new JFileChooser("/");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JPEG", "jpg"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG", "png"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("DICOM", "dcm"));

        int retVal = fileChooser.showOpenDialog(frame);

        if (retVal == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            result = Controller.openPerfectImage(path);
        }
        return result;
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
