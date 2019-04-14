
package MyClientPack;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;

/**
 *
 * @author  Ravee
 */
public class MessageBox extends javax.swing.JDialog {
    public boolean ok;
    
    /** Creates new form MessageBox */
    public MessageBox(java.awt.Frame parent, String message) {
        super(parent, true);
        initComponents();
        registerFont();
             
        jLabelMain.setText(message);
        Dimension sd  = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(sd.width / 2 - this.getWidth()/ 2, sd.height / 2 - this.getHeight()/ 2);
        ok = false;
    }
     public void registerFont()
    {
                try {
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            File f = new File(System.getProperty("user.dir") + "\\src\\MyShipPack\\Army.ttf");
            File f1 = new File(System.getProperty("user.dir") + "\\src\\MyShipPack\\GearsOfPeace_0.ttf");
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, f));
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, f1));
            jLabelMain.setFont(new Font("Army", Font.PLAIN, 48));
            jButton1.setFont(new Font("Gears Of Peace",Font.PLAIN,36));
            for (int i = 0; i < ge.getAvailableFontFamilyNames().length; i++) {
                System.err.println(i + " :" + ge.getAvailableFontFamilyNames()[i]);
            }
        } catch (Exception e) {
            System.out.println("" + e);
        }

    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabelMain = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 51), 5));

        jLabelMain.setBackground(new java.awt.Color(153, 153, 153));
        jLabelMain.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelMain.setForeground(new java.awt.Color(235, 235, 225));
        jLabelMain.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelMain.setText("jLabel1");
        jLabelMain.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton1.setText("OK");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                        .add(0, 565, Short.MAX_VALUE)
                        .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 87, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jLabelMain, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabelMain)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 33, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
        ok = true;
        setVisible(false);
        
    }//GEN-LAST:event_jButton1ActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabelMain;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
}
