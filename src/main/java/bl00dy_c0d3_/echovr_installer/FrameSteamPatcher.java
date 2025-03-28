package bl00dy_c0d3_.echovr_installer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.URL;

import static bl00dy_c0d3_.echovr_installer.Helpers.checkForAdmin;
import static bl00dy_c0d3_.echovr_installer.Helpers.pathFolderChooser;

public class FrameSteamPatcher extends JDialog {
    Downloader downloader1 = null;
    Downloader downloader2 = null;
    Downloader downloader3 = null;
    Downloader downloader4 = null;
    FrameMain frameMain = null;
    int frameWidth = 700;
    int frameHeight = 394;
    String path = "C://EchoVR/ready-at-dawn-echo-arena";
    FrameSteamPatcher outFrame = this;



    //Constructor
    public FrameSteamPatcher(FrameMain frameMain){
        this.frameMain = frameMain;
        initComponents();
        this.setVisible(true);
        this.setModal(true);
    }


    public void dispose(){
        if (downloader1 != null){
            downloader1.cancelDownload();
        }
        if (downloader2 != null){
            downloader2.cancelDownload();
        }
        if (downloader3 != null){
            downloader3.cancelDownload();
        }
        if (downloader4 != null){
            downloader4.cancelDownload();
        }
        super.dispose();
    }

    private void initComponents(){
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage(loadGUI("icon.png"));
        this.setTitle("Echo VR Installer v0.3c");
        this.setModal(true);


        Background back = new Background("echo_combat1.png");
        back.setLayout(null);
        this.setContentPane(back);

        //Note before installing Echo
        JOptionPane.showMessageDialog(this, "<html>This patch is only needed if you want to use a not Oculus capable Headset like the Valve Index!</html>", "Notification", JOptionPane.INFORMATION_MESSAGE);


        SpecialLabel labelPcDownloadPath = new SpecialLabel(path, 14);
        labelPcDownloadPath.setLocation(170,100);
        labelPcDownloadPath.setSize(490, 25);
        labelPcDownloadPath.setBackground(new Color(255, 255, 255, 200));
        labelPcDownloadPath.setForeground(Color.BLACK);
        back.add(labelPcDownloadPath);



        SpecialButton pcChooseOriginalPath = new SpecialButton("<html>Auto choose original<br>Oculus path</html>", "button_up_middle.png", "button_down_middle.png", "button_highlighted_middle.png", 14);
        pcChooseOriginalPath.setLocation(20, 40);
        pcChooseOriginalPath.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent event) {
                String newPath = checkForAdmin(outFrame);
                if (!newPath.matches("")) {
                    labelPcDownloadPath.setText(newPath + "Software\\Software\\ready-at-dawn-echo-arena");
                    outFrame.repaint();
                }
            }
        });
        back.add(pcChooseOriginalPath);

        SpecialLabel labelPcOculusPathExplaination = new SpecialLabel("Choose this to use the original Oculus path", 14);
        labelPcOculusPathExplaination.setLocation(252,40);
        back.add(labelPcOculusPathExplaination);





        SpecialButton pcChoosePath = new SpecialButton("Choose path", "button_up_small.png", "button_down_small.png", "button_highlighted_small.png", 14);
        pcChoosePath.setLocation(20, 100);
        pcChoosePath.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent event) {
                pathFolderChooser(labelPcDownloadPath, outFrame);
            }
        });
        back.add(pcChoosePath);



        SpecialLabel labelPcDownloadPathExplenation = new SpecialLabel("Specify the Path for the Echo Installation or leave it as it is.", 14);
        labelPcDownloadPathExplenation.setLocation(20,130);
        back.add(labelPcDownloadPathExplenation);


        SpecialLabel labelPatchProgress1 = new SpecialLabel(" 0%", 17);
        labelPatchProgress1.setHorizontalAlignment(SwingConstants.LEFT);  // Set text alignment to left
        labelPatchProgress1.setLocation(240,180);
        labelPatchProgress1.setSize(170, 32);
        labelPatchProgress1.setBackground(new Color(255, 255, 255, 200));
        labelPatchProgress1.setForeground(Color.BLACK);
        back.add(labelPatchProgress1);

        SpecialLabel labelPatchProgress2 = new SpecialLabel(" 0%", 17);
        labelPatchProgress2.setHorizontalAlignment(SwingConstants.LEFT);  // Set text alignment to left
        labelPatchProgress2.setLocation(240,212);
        labelPatchProgress2.setSize(170, 32);
        labelPatchProgress2.setBackground(new Color(255, 255, 255, 200));
        labelPatchProgress2.setForeground(Color.BLACK);
        back.add(labelPatchProgress2);

        SpecialLabel labelPatchProgress3 = new SpecialLabel(" 0%", 17);
        labelPatchProgress3.setHorizontalAlignment(SwingConstants.LEFT);  // Set text alignment to left
        labelPatchProgress3.setLocation(240,244);
        labelPatchProgress3.setSize(170, 32);
        labelPatchProgress3.setBackground(new Color(255, 255, 255, 200));
        labelPatchProgress3.setForeground(Color.BLACK);
        back.add(labelPatchProgress3);

        SpecialLabel labelPatchProgress4 = new SpecialLabel(" 0%", 17);
        labelPatchProgress4.setHorizontalAlignment(SwingConstants.LEFT);  // Set text alignment to left
        labelPatchProgress4.setLocation(240,276);
        labelPatchProgress4.setSize(170, 32);
        labelPatchProgress4.setBackground(new Color(255, 255, 255, 200));
        labelPatchProgress4.setForeground(Color.BLACK);
        back.add(labelPatchProgress4);


        SpecialButton pcStartDownload = new SpecialButton("Start Download", "button_up_middle.png", "button_down_middle.png", "button_highlighted_middle.png", 17);
        pcStartDownload.setLocation(20, 230);
        pcStartDownload.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent event) {
                File echoPath = new File(labelPcDownloadPath.getText() + "/bin/win10");

                if (!echoPath.exists() && !echoPath.isDirectory()) {
                    ErrorDialog error = new ErrorDialog();
                    error.errorDialog(outFrame, "Incorrect path to EchoVE", "Error: Choose the main directory of Echo. Like: C:\\echovr\\ready-at-dawn-echo-arena", 0);
                }
                else {
                    if (downloader1 != null){
                        downloader1.cancelDownload();
                        System.out.println("downloader1 Steam stopped");
                    }
                    if (downloader2 != null){
                        downloader2.cancelDownload();
                        System.out.println("downloader2 Steam stopped");
                    }
                    if (downloader3 != null){
                        downloader3.cancelDownload();
                        System.out.println("downloader3 Steam stopped");
                    }
                    if (downloader4 != null){
                        downloader4.cancelDownload();
                        System.out.println("downloader4 Steam stopped");
                    }
                    pcStartDownload.changeText("Restart Download");



                    JOptionPane.showMessageDialog(null, "The Download will start after pressing OK.", "Download started", JOptionPane.INFORMATION_MESSAGE);
                    downloader1 = new Downloader();
                    downloader1.startDownload("https://echo.marceldomain.de:6969/LibRevive64.dll", echoPath + "", "/LibRevive64.dll", labelPatchProgress1, outFrame, null, 1, true, -1);
                    downloader2 = new Downloader();
                    downloader2.startDownload("https://echo.marceldomain.de:6969/openvr_api64.dll", echoPath + "", "/openvr_api64.dll", labelPatchProgress2, outFrame, null, 1, true, -1);
                    downloader3 = new Downloader();
                    downloader3.startDownload("https://echo.marceldomain.de:6969/xinput1_3.dll", echoPath + "", "/xinput1_3.dll", labelPatchProgress3, outFrame, null, 1, true, -1);
                    downloader4 = new Downloader();
                    downloader4.startDownload("https://echo.marceldomain.de:6969/xinput9_1_0.dll", echoPath + "", "/xinput9_1_0.dll", labelPatchProgress4, outFrame, null, 3, true, -1);
                }
            }
        });
        back.add(pcStartDownload);

        //Alles fertig machen...
        this.pack();

        //Fenstergröße und Position setzen...
        this.setSize(frameWidth, frameHeight);
        int x = frameMain.getX() + (frameMain.getWidth() - this.getWidth()) / 2;
        int y = frameMain.getY() + (frameMain.getHeight() - this.getHeight()) / 2;
        this.setLocation(x, y);
    }

    //Lädt eine GUI-Grafik und gibt sie zurück:
    private java.awt.Image loadGUI(String imageName) {
        URL imageURL = getClass().getClassLoader().getResource(imageName);
        if (imageURL == null) return null;
        else return (new ImageIcon(imageURL, imageName)).getImage();
    }
}
