/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyClientPack;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author OORJATECH
 */
public class UserRegistration extends javax.swing.JFrame {

    boolean checkStringFname = false, checkStringMname = false, checkStringLname = false, checkNumeric = false, checkEmail = false, checkNull = false, checkLength = false;
    Login Parent;
    HomeAutomationUser toServer = new HomeAutomationUser();

    public UserRegistration(Login Parent) {
        initComponents();
        this.Parent = Parent;
        Dimension sd = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(sd.width / 2 - this.getWidth() / 2, sd.height / 2 - this.getHeight() / 2);
    }

    boolean validateEmail(String email) {//This function will validate the email id
        //Set the email pattern string
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        //Match the given string with the pattern
        Matcher m = p.matcher(email);
        //check whether match is found
        boolean matchFound = m.matches();
        if (matchFound) {

            return true;
        } else {

            return false;
        }
    }

    boolean validateNumeric(String num) {   //To validate Mobile Number it should allow only numbers
        //Set the email pattern string
        Pattern p = Pattern.compile("[0-9]+");
        //Match the given string with the pattern
        Matcher m = p.matcher(num);
        //check whether match is found
        boolean matchFound = m.matches();
        if (matchFound) {
            return true;
        } else {
            return false;
        }
    }

    boolean validateString(String str) {  //To Validate Fname Mname Lname it should contain only aplhabets 
        //Set the email pattern string
        Pattern p = Pattern.compile("[a-zA-Z ]+");
        //Match the given string with the pattern
        Matcher m = p.matcher(str);
        //check whether match is found
        boolean matchFound = m.matches();
        if (matchFound) {
            return true;
        } else {
            return false;
        }
    }

    public void btnEnabled() {
        btnRegister.setEnabled(true);
    }

    public void btnDisabled() {
        btnRegister.setEnabled(false);
    }

    public boolean checkLength(String num) {
        if (num.length() > 10) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblFname = new javax.swing.JLabel();
        txtFname = new javax.swing.JTextField();
        lblMname = new javax.swing.JLabel();
        lblLname = new javax.swing.JLabel();
        txtMname = new javax.swing.JTextField();
        txtLname = new javax.swing.JTextField();
        lblNumber = new javax.swing.JLabel();
        txtNmber = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAddress = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblUname = new javax.swing.JLabel();
        txtUname = new javax.swing.JTextField();
        lblPwd = new javax.swing.JLabel();
        txtPwd = new javax.swing.JPasswordField();
        jButton2 = new javax.swing.JButton();
        btnRegister = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Calibri", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REGISTRATION WINDOW");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jLabel1.setOpaque(true);

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 51), 14));

        lblFname.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        lblFname.setForeground(new java.awt.Color(255, 255, 255));
        lblFname.setText("FIRST NAME");

        txtFname.setBackground(new java.awt.Color(23, 21, 21));
        txtFname.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtFname.setForeground(new java.awt.Color(204, 204, 204));
        txtFname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFnameKeyReleased(evt);
            }
        });

        lblMname.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        lblMname.setForeground(new java.awt.Color(255, 255, 255));
        lblMname.setText("MIDDLE NAME");

        lblLname.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        lblLname.setForeground(new java.awt.Color(255, 255, 255));
        lblLname.setText("LAST NAME");

        txtMname.setBackground(new java.awt.Color(23, 21, 21));
        txtMname.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtMname.setForeground(new java.awt.Color(204, 204, 204));
        txtMname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMnameKeyReleased(evt);
            }
        });

        txtLname.setBackground(new java.awt.Color(23, 21, 21));
        txtLname.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtLname.setForeground(new java.awt.Color(204, 204, 204));
        txtLname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtLnameKeyReleased(evt);
            }
        });

        lblNumber.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        lblNumber.setForeground(new java.awt.Color(255, 255, 255));
        lblNumber.setText("MOBILE NUMBER");

        txtNmber.setBackground(new java.awt.Color(23, 21, 21));
        txtNmber.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtNmber.setForeground(new java.awt.Color(204, 204, 204));
        txtNmber.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNmberFocusLost(evt);
            }
        });
        txtNmber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNmberKeyReleased(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("SEX");

        jRadioButton1.setBackground(new java.awt.Color(0, 0, 0));
        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jRadioButton1.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton1.setText("MALE");

        jRadioButton2.setBackground(new java.awt.Color(0, 0, 0));
        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jRadioButton2.setForeground(new java.awt.Color(255, 255, 255));
        jRadioButton2.setText("FEMALE");

        txtAddress.setBackground(new java.awt.Color(23, 21, 21));
        txtAddress.setColumns(20);
        txtAddress.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtAddress.setForeground(new java.awt.Color(204, 204, 204));
        txtAddress.setRows(5);
        jScrollPane1.setViewportView(txtAddress);

        jLabel8.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("ADDRESS");

        lblEmail.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        lblEmail.setForeground(new java.awt.Color(255, 255, 255));
        lblEmail.setText("EMAIL ID");

        txtEmail.setBackground(new java.awt.Color(23, 21, 21));
        txtEmail.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(204, 204, 204));
        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtEmailKeyReleased(evt);
            }
        });

        lblUname.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        lblUname.setForeground(new java.awt.Color(255, 255, 255));
        lblUname.setText("USER NAME");

        txtUname.setBackground(new java.awt.Color(23, 21, 21));
        txtUname.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtUname.setForeground(new java.awt.Color(204, 204, 204));
        txtUname.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUnameKeyReleased(evt);
            }
        });

        lblPwd.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        lblPwd.setForeground(new java.awt.Color(255, 255, 255));
        lblPwd.setText("PASSWORD");

        txtPwd.setBackground(new java.awt.Color(23, 21, 21));
        txtPwd.setFont(new java.awt.Font("Calibri", 0, 18)); // NOI18N
        txtPwd.setForeground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblFname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblMname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNumber, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblUname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblPwd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFname)
                    .addComponent(txtMname)
                    .addComponent(txtLname)
                    .addComponent(txtNmber)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
                    .addComponent(txtEmail)
                    .addComponent(txtUname)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jRadioButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(txtPwd))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblMname)
                        .addGap(18, 18, 18)
                        .addComponent(lblLname)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblNumber)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtMname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtLname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtNmber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtUname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUname))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPwd))
                .addGap(62, 62, 62))
        );

        jButton2.setBackground(new java.awt.Color(204, 0, 0));
        jButton2.setFont(new java.awt.Font("Calibri", 1, 22)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("BACK");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        btnRegister.setBackground(new java.awt.Color(0, 153, 153));
        btnRegister.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        btnRegister.setForeground(new java.awt.Color(255, 255, 255));
        btnRegister.setText("SignUp");
        btnRegister.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
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
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(233, 233, 233)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegister)
                    .addComponent(jButton2)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.setVisible(false);
        Parent.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txtFnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFnameKeyReleased
        checkStringFname = validateString(txtFname.getText());
        if (!checkStringFname) {
            btnDisabled();//To Disable Register Button
            lblFname.setText("FIRST NAME(ONLY CHARACTERS)");

        } else {
            btnEnabled();//To Enable Register Button
            lblFname.setText("FIRST NAME");
        }
    }//GEN-LAST:event_txtFnameKeyReleased

    private void txtMnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMnameKeyReleased
        checkStringMname = validateString(txtMname.getText());
        if (!checkStringMname) {
            btnDisabled();//To Disable Register Button
            lblMname.setText("MIDDLE NAME(ONLY CHARACTERS)");

        } else {
            btnEnabled();//To Enable Register Button
            lblMname.setText("MIDDLE NAME");
        }
    }//GEN-LAST:event_txtMnameKeyReleased

    private void txtLnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtLnameKeyReleased
        checkStringLname = validateString(txtLname.getText());
        if (!checkStringLname) {
            btnDisabled();//To Disable Register Button
            lblLname.setText("LAST NAME(ONLY CHARACTERS)");

        } else {
            btnEnabled();//To Enable Register Button
            lblLname.setText("LAST NAME");
        }
    }//GEN-LAST:event_txtLnameKeyReleased

    private void txtNmberKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNmberKeyReleased
        checkNumeric = validateNumeric(txtNmber.getText());
        System.out.println("CheckNumeric" + checkNumeric);
        if (!checkNumeric) {
            System.out.println("Inside if");
            lblNumber.setText("MOBILE NUMBER(ONLY NUMBER)");
            btnDisabled();//To Disable Register Button


        } else {
            System.out.println("Inside else");
            btnEnabled();//To Enable Register Button
            lblNumber.setText("MOBILE NUMBER");
        }
    }//GEN-LAST:event_txtNmberKeyReleased

    private void txtEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtEmailKeyReleased
        checkEmail = validateEmail(txtEmail.getText());
        if (checkEmail) {
            btnEnabled();
            lblEmail.setText("EMAIL ID (VALID)");
        } else {
            lblEmail.setText("EMAIL ID (INVALID)");
            btnDisabled();
        }
    }//GEN-LAST:event_txtEmailKeyReleased

    public void getDetails() {
        if (jRadioButton1.isSelected()) {
            toServer.gender = "M";
        } else if (jRadioButton2.isSelected()) {
            toServer.gender = "F";
        }
        toServer.fname = txtFname.getText();
        toServer.mname = txtMname.getText();
        toServer.lname = txtLname.getText();
        toServer.mbNumber = txtNmber.getText();
        toServer.address = txtAddress.getText();
        toServer.emailId = txtEmail.getText();
        toServer.userName = txtUname.getText();
        toServer.password = new String(txtPwd.getPassword());
    }

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed


        if (checkNull == true) {
            JOptionPane.showMessageDialog(this, "All the fields are Mandatory");
        } else {
            if (checkEmail == true && checkNumeric == true && checkStringFname == true
                    && checkStringMname == true && checkStringLname == true && checkNull != true) {

                btnEnabled();//To Enable Register Button
                getDetails();
                boolean isRecordInserted = storeData(toServer);
                if (isRecordInserted) {
                    JOptionPane.showMessageDialog(this, "Registration Successful");
                    this.setVisible(false);
                    Parent.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Registration Failed");
                }
            } else {
                JOptionPane.showMessageDialog(this, "All Fileds are Mandatory");
            }
        }
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void txtNmberFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNmberFocusLost




        checkLength = checkLength(txtNmber.getText());
        if (checkLength) {
            btnDisabled();//To Disable Register Button
            lblNumber.setText("MOBILE NUMBER(INVALID NUMBER)");
        } else {
            btnEnabled();//To Disable Register Button
            lblNumber.setText("MOBILE NUMBER");
        }
    }//GEN-LAST:event_txtNmberFocusLost

    private void txtUnameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUnameKeyReleased
       toServer.userName=txtUname.getText();
        boolean isUserPresent=searchUserPresent(toServer);
         if (isUserPresent) {
                lblUname.setText("USER NAME (UNAVAILABLE)");
                btnDisabled();

            } else {
                lblUname.setText("USER NAME (AVAILABLE)");
                btnEnabled();
            }
    }//GEN-LAST:event_txtUnameKeyReleased

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRegister;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFname;
    private javax.swing.JLabel lblLname;
    private javax.swing.JLabel lblMname;
    private javax.swing.JLabel lblNumber;
    private javax.swing.JLabel lblPwd;
    private javax.swing.JLabel lblUname;
    private javax.swing.JTextArea txtAddress;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFname;
    private javax.swing.JTextField txtLname;
    private javax.swing.JTextField txtMname;
    private javax.swing.JTextField txtNmber;
    private javax.swing.JPasswordField txtPwd;
    private javax.swing.JTextField txtUname;
    // End of variables declaration//GEN-END:variables

    private static void passObjectRefrence(MyClientPack.HomeAutomationUser obj) {
        MyClientPack.HomeAutomationService_Service service = new MyClientPack.HomeAutomationService_Service();
        MyClientPack.HomeAutomationService port = service.getHomeAutomationServicePort();
        port.passObjectRefrence(obj);
    }

    private static boolean storeData(MyClientPack.HomeAutomationUser fromClient) {
        MyClientPack.HomeAutomationService_Service service = new MyClientPack.HomeAutomationService_Service();
        MyClientPack.HomeAutomationService port = service.getHomeAutomationServicePort();
        return port.storeData(fromClient);
    }

    private static boolean searchUserPresent(MyClientPack.HomeAutomationUser fromClient) {
        MyClientPack.HomeAutomationService_Service service = new MyClientPack.HomeAutomationService_Service();
        MyClientPack.HomeAutomationService port = service.getHomeAutomationServicePort();
        return port.searchUserPresent(fromClient);
    }
}
