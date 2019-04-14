package MyClientPack;

import JMyron.JMyron;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.TimerTask;
import javax.swing.ImageIcon;

class MyTimerTask5 extends TimerTask {

    FrmWebcamTest parent;

    public MyTimerTask5(FrmWebcamTest parent) {
        this.parent = parent;
    }

    public void run() {
        if (!parent.running) {
            return;
        }
        parent.updateImage();
    }
}

public class FrmWebcamTest extends javax.swing.JFrame {

    MainMenu parent;
    public BufferedImage biLabel;
    public Graphics2D g2dLabel;
    java.util.Timer t1;
    MyTimerTask5 tt;
    public boolean running;
    BufferedImage biInput;
    public JMyron m; //a camera object
    public int cw, sw;
    public int ch, sh;
    public int frameRate; //fps

    public FrmWebcamTest(MainMenu parent) {
        this.parent = parent;
        initComponents();
        Dimension sd = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(sd.width / 2 - this.getWidth() / 2, sd.height / 2 - this.getHeight() / 2);

        cw = 400;
        ch = 300;
        sw = 320;
        sh = 240;

        System.out.println("Initializing Webcam, w:" + cw + ", h:" + ch);
        m = new JMyron();//make a new instance of the object
        m.start(cw, ch);//start a capture at 320x240
        m.findGlobs(0);//disable the intelligence to speed up frame rate
        cw = m.getForcedWidth();
        ch = m.getForcedHeight();
        System.out.println("Forced Dimensions, w:" + cw + ", h:" + ch);

        m.stop();

        // Reinitializing with required dimensions
        System.out.println("Re-Initializing Webcam, w:" + cw + ", h:" + ch);
        m = new JMyron();//make a new instance of the object
        m.start(cw, ch);//start a capture at 320x240
        m.findGlobs(0);//disable the intelligence to speed up frame rate
        cw = m.getForcedWidth();
        ch = m.getForcedHeight();
        // input equal to cw , ch
        biInput = new BufferedImage(cw, ch, BufferedImage.TYPE_INT_ARGB);

        // label required 320x240
        biLabel = new BufferedImage(sw, sh, BufferedImage.TYPE_INT_RGB);
        g2dLabel = (Graphics2D) biLabel.createGraphics();
        jLabelFeed.setIcon(new ImageIcon(biLabel));
        jLabelFeed.repaint();

        running = false;
        t1 = new java.util.Timer();
        tt = new MyTimerTask5(this);
        t1.schedule(tt, 100, 100);
    }

    public void updateImage() {
        m.update();//update the camera view
        int[] img = m.image(); //get the normal image of the camera
        biInput.setRGB(0, 0, cw, ch, img, 0, cw);

        // scale and draw on biLabel
        g2dLabel.drawImage(biInput, 0, 0, sw, sh, null);
        jLabelFeed.repaint();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        StartBtn = new javax.swing.JButton();
        StopBtn = new javax.swing.JButton();
        jLabelFeed = new javax.swing.JLabel();
        BackBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 51), 8));

        StartBtn.setBackground(new java.awt.Color(0, 153, 153));
        StartBtn.setFont(new java.awt.Font("Narkisim", 1, 18)); // NOI18N
        StartBtn.setForeground(new java.awt.Color(255, 255, 255));
        StartBtn.setText("START");
        StartBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        StartBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartBtnActionPerformed(evt);
            }
        });

        StopBtn.setBackground(new java.awt.Color(0, 153, 153));
        StopBtn.setFont(new java.awt.Font("Narkisim", 1, 18)); // NOI18N
        StopBtn.setForeground(new java.awt.Color(255, 255, 255));
        StopBtn.setText("STOP");
        StopBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        StopBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopBtnActionPerformed(evt);
            }
        });

        jLabelFeed.setBackground(new java.awt.Color(70, 68, 68));
        jLabelFeed.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelFeed.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImagePack/Back320x240.PNG"))); // NOI18N
        jLabelFeed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelFeedMouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel4Layout = new org.jdesktop.layout.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel4Layout.createSequentialGroup()
                        .add(StartBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 102, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(211, 211, 211)
                        .add(StopBtn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 102, Short.MAX_VALUE))
                    .add(jLabelFeed, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4Layout.linkSize(new java.awt.Component[] {StartBtn, StopBtn}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabelFeed, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 266, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel4Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(StartBtn)
                    .add(StopBtn))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BackBtn.setBackground(new java.awt.Color(204, 0, 0));
        BackBtn.setFont(new java.awt.Font("Narkisim", 1, 18)); // NOI18N
        BackBtn.setForeground(new java.awt.Color(255, 255, 255));
        BackBtn.setText("Back");
        BackBtn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BackBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BackBtnActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Narkisim", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("WEBCAM  TEST");
        jLabel1.setOpaque(true);
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1MouseClicked(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jPanel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(BackBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 83, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 45, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(jPanel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(BackBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 26, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabelFeedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelFeedMouseClicked
// TODO add your handling code here:
        m.settings();
    }//GEN-LAST:event_jLabelFeedMouseClicked

    private void StopBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopBtnActionPerformed
// TODO add your handling code here:
        running = false;
    }//GEN-LAST:event_StopBtnActionPerformed

    private void StartBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartBtnActionPerformed
// TODO add your handling code here:
        running = true;
    }//GEN-LAST:event_StartBtnActionPerformed

    private void BackBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BackBtnActionPerformed
// TODO add your handling code here:
        running = false;
        m.stop();
        setVisible(false);
        parent.setVisible(true);
    }//GEN-LAST:event_BackBtnActionPerformed

    private void jLabel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1MouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jLabel1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BackBtn;
    private javax.swing.JButton StartBtn;
    private javax.swing.JButton StopBtn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelFeed;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    // End of variables declaration//GEN-END:variables
}
