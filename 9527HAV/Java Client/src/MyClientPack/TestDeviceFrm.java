/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyClientPack;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JToggleButton;

/**
 *
 * @author OORJATECH
 */
public class TestDeviceFrm extends javax.swing.JFrame {

    MainMenu parent;
    JLabel[] myBulb;
    JToggleButton[] mySwitch;
    int outData = 0;
    ImageIcon i1,i2;
    public static final int portNo=34;

    public TestDeviceFrm(MainMenu parent) {
        initComponents();
        this.parent = parent;
        Dimension sd = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(sd.width / 2 - this.getWidth() / 2, sd.height / 2 - this.getHeight() / 2);
        myBulb = new JLabel[8];
        mySwitch = new JToggleButton[8];
      //i1=new ImageIcon("F:\\shree projects\\HOME AUTOMATION\\HOME AUTOMATION CLIENT\\Welcome\\src\\MyImagePack\\On.png");
      //i2=new ImageIcon("F:\\shree projects\\HOME AUTOMATION\\HOME AUTOMATION CLIENT\\Welcome\\src\\MyImagePack\\off.png");
      
        parent.writeData(portNo);
        parent.writeData(0);
        myBulb[0] = blb1;
        myBulb[1] = blb2;
        myBulb[2] = blb3;
        myBulb[3] = blb4;
        myBulb[4] = blb5;
        myBulb[5] = blb6;
        myBulb[6] = blb7;
        myBulb[7] = blb8;

        mySwitch[0] = switch1;
        mySwitch[1] = switch2;
        mySwitch[2] = switch3;
        mySwitch[3] = switch4;
        mySwitch[4] = switch5;
        mySwitch[5] = switch6;
        mySwitch[6] = switch7;
        mySwitch[7] = switch8;

        for (int i = 0; i < 8; i++) {
           myBulb[i].setBackground(Color.GREEN);
           myBulb[i].setIcon(i1);
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        blb1 = new javax.swing.JLabel();
        switch1 = new javax.swing.JToggleButton();
        jLabel4 = new javax.swing.JLabel();
        blb2 = new javax.swing.JLabel();
        switch2 = new javax.swing.JToggleButton();
        jLabel6 = new javax.swing.JLabel();
        blb3 = new javax.swing.JLabel();
        switch3 = new javax.swing.JToggleButton();
        jLabel8 = new javax.swing.JLabel();
        blb4 = new javax.swing.JLabel();
        switch4 = new javax.swing.JToggleButton();
        jLabel10 = new javax.swing.JLabel();
        blb5 = new javax.swing.JLabel();
        switch5 = new javax.swing.JToggleButton();
        jLabel12 = new javax.swing.JLabel();
        blb6 = new javax.swing.JLabel();
        switch6 = new javax.swing.JToggleButton();
        jLabel14 = new javax.swing.JLabel();
        blb7 = new javax.swing.JLabel();
        switch7 = new javax.swing.JToggleButton();
        jLabel16 = new javax.swing.JLabel();
        blb8 = new javax.swing.JLabel();
        switch8 = new javax.swing.JToggleButton();
        jPanel4 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Calibri", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("TEST DEVICE");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jLabel1.setOpaque(true);

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 51), 14));

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText(" Device 1 Test");

        blb1.setBackground(new java.awt.Color(0, 102, 51));
        blb1.setOpaque(true);

        switch1.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        switch1.setText("ON");
        switch1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        switch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switch1ActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(0, 0, 0));
        jLabel4.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText(" Device 2 Test");

        blb2.setBackground(new java.awt.Color(0, 102, 51));
        blb2.setOpaque(true);

        switch2.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        switch2.setText("ON");
        switch2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        switch2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switch2ActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(0, 0, 0));
        jLabel6.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Device 3 Test");

        blb3.setBackground(new java.awt.Color(0, 102, 51));
        blb3.setOpaque(true);

        switch3.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        switch3.setText("ON");
        switch3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        switch3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switch3ActionPerformed(evt);
            }
        });

        jLabel8.setBackground(new java.awt.Color(0, 0, 0));
        jLabel8.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("Device 4 Test");

        blb4.setBackground(new java.awt.Color(0, 102, 51));
        blb4.setOpaque(true);

        switch4.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        switch4.setText("ON");
        switch4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        switch4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switch4ActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("Device 5 Test");

        blb5.setBackground(new java.awt.Color(0, 102, 51));
        blb5.setOpaque(true);

        switch5.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        switch5.setText("ON");
        switch5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        switch5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switch5ActionPerformed(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel12.setText("Device 6 Test");

        blb6.setBackground(new java.awt.Color(0, 102, 51));
        blb6.setOpaque(true);

        switch6.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        switch6.setText("ON");
        switch6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        switch6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switch6ActionPerformed(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel14.setText("Device 7 Test");

        blb7.setBackground(new java.awt.Color(0, 102, 51));
        blb7.setOpaque(true);

        switch7.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        switch7.setText("ON");
        switch7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        switch7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switch7ActionPerformed(evt);
            }
        });

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setFont(new java.awt.Font("Calibri", 0, 22)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel16.setText("Device 8 Test");

        blb8.setBackground(new java.awt.Color(0, 102, 51));
        blb8.setOpaque(true);

        switch8.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        switch8.setText("ON");
        switch8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        switch8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switch8ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(blb3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(switch3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(blb1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(blb2, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(switch2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(switch1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(blb4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(switch4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                            .addComponent(blb5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(switch5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(blb6, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(switch6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(blb7, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(switch7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(blb8, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(switch8, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(27, 27, 27))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel2)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel4)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel6)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel8)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel10)
                        .addGap(26, 26, 26)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(blb1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(switch1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(blb2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(switch2))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(blb3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(switch3))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(blb4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(switch4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(blb5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(switch5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(blb6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(switch6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(blb7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(switch7))))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(blb8, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(switch8))
                    .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jButton2.setBackground(new java.awt.Color(0, 153, 153));
        jButton2.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("RESET");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 153, 153));
        jButton3.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("ALL ON");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(43, 43, 43))
        );

        jButton1.setBackground(new java.awt.Color(204, 0, 0));
        jButton1.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("BACK");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 598, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.setVisible(false);
        parent.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        for (int i = 0; i < 8; i++) {
            mySwitch[i].setText("OFF");
            myBulb[i].setBackground(Color.RED);
            myBulb[i].setForeground(Color.RED);
        }
         outData |= 255;
        parent.writeData(portNo);
        parent.writeData(outData);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed

        for (int i = 0; i < 8; i++) {
            mySwitch[i].setText("ON");
            myBulb[i].setBackground(Color.GREEN);
            myBulb[i].setForeground(Color.GREEN);
        }
         outData &= 0;
        parent.writeData(portNo);
        parent.writeData(0);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void switch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switch1ActionPerformed
        if (blb1.getBackground().equals(Color.GREEN)) {
           blb1.setBackground(Color.RED);
            blb1.setForeground(Color.RED);
            switch1.setText("OFF");
              outData |= 1;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else {
            blb1.setBackground(Color.GREEN);
            blb1.setForeground(Color.GREEN);
            outData &=~ 1;
            switch1.setText("ON");
            parent.writeData(portNo);
            parent.writeData(outData);
        }
    }//GEN-LAST:event_switch1ActionPerformed

    private void switch2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switch2ActionPerformed
        if (blb2.getBackground().equals(Color.GREEN)) {
            blb2.setBackground(Color.RED);
            blb2.setForeground(Color.RED);
            switch2.setText("OFF");
             outData |= 2;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else {
            blb2.setBackground(Color.GREEN);
            blb2.setForeground(Color.GREEN);
           outData &=~ 2;
            switch2.setText("ON");
            parent.writeData(portNo);
            parent.writeData(outData);
        }
    }//GEN-LAST:event_switch2ActionPerformed

    private void switch3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switch3ActionPerformed
        if (blb3.getBackground().equals(Color.GREEN)) {
            blb3.setBackground(Color.RED);
            blb3.setForeground(Color.RED);
            switch3.setText("OFF");
            outData |= 4;
            parent.writeData(34);
            parent.writeData(outData);
        } else {
            blb3.setBackground(Color.GREEN);
            blb3.setForeground(Color.GREEN);
           outData &=~ 4;
            switch3.setText("ON");
            parent.writeData(portNo);
            parent.writeData(outData);
        }
    }//GEN-LAST:event_switch3ActionPerformed

    private void switch5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switch5ActionPerformed
        if (blb5.getBackground().equals(Color.GREEN)) {
            blb5.setBackground(Color.RED);
            blb5.setForeground(Color.RED);
            switch5.setText("OFF");
       outData |= 16;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else {
            blb5.setBackground(Color.GREEN);
            blb5.setForeground(Color.GREEN);
                outData &=~ 16;
            switch5.setText("ON");
            parent.writeData(portNo);
            parent.writeData(outData);
        }
    }//GEN-LAST:event_switch5ActionPerformed

    private void switch4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switch4ActionPerformed
        if (blb4.getBackground().equals(Color.GREEN)) {
            blb4.setBackground(Color.RED);
            blb4.setForeground(Color.RED);
            switch4.setText("OFF");
           outData |= 8;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else {
            blb4.setBackground(Color.GREEN);
            blb4.setForeground(Color.GREEN);
                outData &=~ 8;
            switch4.setText("ON");
            parent.writeData(portNo);
            parent.writeData(outData);
        }
    }//GEN-LAST:event_switch4ActionPerformed

    private void switch6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switch6ActionPerformed
        if (blb6.getBackground().equals(Color.GREEN)) {
            blb6.setBackground(Color.RED);
            blb6.setForeground(Color.RED);
            switch6.setText("OFF");
           outData |= 32;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else {
            blb6.setBackground(Color.GREEN);
            blb6.setForeground(Color.GREEN);
                outData &=~ 32;
            switch6.setText("ON");
            parent.writeData(portNo);
            parent.writeData(outData);
        }
    }//GEN-LAST:event_switch6ActionPerformed

    private void switch7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switch7ActionPerformed
        if (blb7.getBackground().equals(Color.GREEN)) {
            blb7.setBackground(Color.RED);
            blb7.setForeground(Color.RED);
            switch7.setText("OFF");
          outData |= 64;
            parent.writeData(34);
            parent.writeData(outData);
        } else {
            blb7.setBackground(Color.GREEN);
            blb7.setForeground(Color.GREEN);
              outData &=~ 64;
            switch7.setText("ON");
            parent.writeData(portNo);
            parent.writeData(outData);
        }
    }//GEN-LAST:event_switch7ActionPerformed

    private void switch8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switch8ActionPerformed
        if (blb8.getBackground().equals(Color.GREEN)) {
            blb8.setBackground(Color.RED);
            blb8.setForeground(Color.RED);
            switch8.setText("OFF");
            outData |= 128;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else {
            blb8.setBackground(Color.GREEN);
            blb8.setForeground(Color.GREEN);
               outData &=~ 128;
            switch8.setText("ON");
            parent.writeData(portNo);
            parent.writeData(outData);
        }
    }//GEN-LAST:event_switch8ActionPerformed

    /**
     * @param args the command line arguments
     */
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel blb1;
    private javax.swing.JLabel blb2;
    private javax.swing.JLabel blb3;
    private javax.swing.JLabel blb4;
    private javax.swing.JLabel blb5;
    private javax.swing.JLabel blb6;
    private javax.swing.JLabel blb7;
    private javax.swing.JLabel blb8;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JToggleButton switch1;
    private javax.swing.JToggleButton switch2;
    private javax.swing.JToggleButton switch3;
    private javax.swing.JToggleButton switch4;
    private javax.swing.JToggleButton switch5;
    private javax.swing.JToggleButton switch6;
    private javax.swing.JToggleButton switch7;
    private javax.swing.JToggleButton switch8;
    // End of variables declaration//GEN-END:variables
}
