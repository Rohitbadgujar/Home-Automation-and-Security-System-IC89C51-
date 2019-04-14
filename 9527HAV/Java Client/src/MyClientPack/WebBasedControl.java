/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyClientPack;

import JMyron.JMyron;
import JavaLib.LoadForm;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Timer;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author OORJATECH
 */
public class WebBasedControl extends javax.swing.JFrame {

    public byte[] myArrray;
    public BufferedImage biLabel;
    public Graphics2D g2dLabel;
    Timer t2;
    MyTimerTask5 tt;
    //public boolean running;
    BufferedImage biInput;
    public JMyron m; //a camera object
    public int cw, sw;
    public int ch, sh;
    public int frameRate; //fps
    DeviceDetails toServer, clientCommand;
    MyImage imageDetails;
    ImageIcon i1, i2, i3, i4, i5, i6;
    JLabel myBulb[] = new JLabel[8];
    JLabel myswitch[] = new JLabel[8];
    JComboBox myCombo[] = new JComboBox[8];
    MainMenu parent;
    int outData = 0;
    boolean running = false, clientCmd = false;
    Timer t, t1;
    int currentChannel;
    int adcValues[];
    MyTimerTask task;
    MyTimerTask1 task1;
    JProgressBar jp[];
    JCheckBox mycheckActv[];
    JCheckBox mycheckAuto[];
    Boolean actvSensor[];
    Boolean chckAutoMode[];
    JComboBox myThreshold[];
    public int currentMotion;
      public int pixels[][];
    public int currentMotionThreshold;
      public int intrusionID = 1;
    String intrusionTypes[];
    JLabel myVal[];
     Component[] components;
    boolean actPg1 = false, actPg2 = false, actPg3 = false, actPg4 = false, actPg5 = false, actPg6 = false, actPg7 = false, actPg8 = false;
    boolean autoPg1 = false, autoPg2 = false, autoPg3 = false, autoPg4 = false, autoPg5 = false, autoPg6 = false, autoPg7 = false, autoPg8 = false;
    boolean genFeeback = false, running1 = false;
    int temp[][];
    int waitCount;
      int tempdata=0;
    public static final int portNo=34;

    public WebBasedControl(MainMenu parent) {
        initComponents();
        System.out.println("In inint component");
        Dimension sd = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(sd.width / 2 - this.getWidth() / 2, sd.height / 2 - this.getHeight() / 2);
        this.parent = parent;
        intrusionTypes=new String[8];
        intrusionTypes[1]="Intrusion Detected";
        jp = new JProgressBar[8];
        jp[0] = pg1;
        jp[1] = pg2;
        jp[2] = pg3;
        jp[3] = pg4;
        jp[4] = pg5;
        jp[5] = pg6;
        jp[6] = pg7;
        jp[7] = pg8;
        initWebCam();
        initDevice();
        fillTh();
        initSensor();
        reset();
        initActvCeck();
        waitCount = 0;
      
//        temp = new int[240][320];
    }

    public void initWebCam() {
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
        jLabelFeed1.setIcon(new ImageIcon(biLabel));
        jLabelFeed1.repaint();

        running1 = false;
        t2 = new java.util.Timer();
        tt = new MyTimerTask5();
        t2.schedule(tt, 100, 50);
             pixels = new int[sh][sw];
        for (int y = 0; y < sh; y++) {
            for (int x = 0; x < sw; x++) {
                pixels[y][x] = -1;
            }
        }
    }

    public void updateImage() {
        imageDetails = new MyImage();
        // imageDetails.img= null;
        imageDetails.img = new Vector<Integer>();
        m.update();//update the camera view
        int[] img = m.image(); //get the normal image of the camera
        biInput.setRGB(0, 0, cw, ch, img, 0, cw);
        // scale and draw on biLabel
        g2dLabel.drawImage(biInput, 0, 0, sw, sh, null);
        jLabelFeed1.repaint();
        int col1, r1, g1, b1;
        int col2, r2, g2, b2;
        int sensitivity = jComboSensitivity.getSelectedIndex() + 1;

        //only if motion detect active....
        if (!jCheckDetectMotion.isSelected()) {
            jProgressMotion.setValue(0);
        } else {
            currentMotion = 0;
            for (int y = 0; y < sh; y++) {
                for (int x = 0; x < sw; x++) {
                    col1 = biLabel.getRGB(x, y);
                    col2 = pixels[y][x];
                    if (col2 != -1) {
                        r1 = (col1 >> 16) & 0xff;
                        g1 = (col1 >> 8) & 0xff;
                        b1 = (col1 >> 0) & 0xff;

                        r2 = (col2 >> 16) & 0xff;
                        g2 = (col2 >> 8) & 0xff;
                        b2 = (col2 >> 0) & 0xff;

                        // grayscale values...
                        r1 = (r1 + g1 + b1) / 3;
                        r2 = (r2 + g2 + b2) / 3;

                        if (Math.abs(r1 - r2) >= sensitivity) {
                            currentMotion++;
                        }
                    }
                    pixels[y][x] = biLabel.getRGB(x, y);
                }
            }
            // percentage calculation...
            currentMotion = (int) (((float) currentMotion / (sw * sh)) * 100);
            jProgressMotion.setValue(currentMotion);
            if (currentMotion >= currentMotionThreshold) {
                System.out.println("MOTION DETECTED");
                Calendar c = Calendar.getInstance();
                String fname1 = "D:\\ProjectData\\9125DB" + "\\ImgPack\\" + c.get(c.YEAR) + "_" + (c.get(c.MONTH) + 1) + "_" + c.get(c.DAY_OF_MONTH) + "_" + c.get(c.HOUR_OF_DAY) + "_" + c.get(c.MINUTE) + "_" + c.get(c.SECOND) + ".PNG";
                String fname2 = "D:\\ProjectData\\9125DB" + "\\ImgPack\\LATEST.PNG";
                try {
                    ImageIO.write(biLabel, "png", new File(fname1));
                    ImageIO.write(biLabel, "png", new File(fname2));
                    Thread.sleep(50);
                } catch (Exception e) {
                    System.out.println("Error Writing File: " + e);
                }
                processIntrusion();
            }
        }
        for (int x = 0; x < img.length; x++) {
            imageDetails.img.add(img[x]);
        }

        int count = 0;
        myArrray = new byte[sw * sh * 3];
        for (int i = 0; i < sh; i++) {
            for (int j = 0; j < sw; j++) {
                int col = biLabel.getRGB(j, i);
                int r = (col >> 16) & 0xff;
                myArrray[count] = (byte) r;
                count++;
                int g = (col >> 8) & 0xff;
                myArrray[count] = (byte) g;
                count++;
                int b = (col) & 0xff;
                myArrray[count] = (byte) b;
                count++;
            }
        }

    }

     public void sendSMS() {
        String msg="";
        Calendar c = Calendar.getInstance();
        msg = intrusionTypes[intrusionID] + " AT " + c.get(c.YEAR) + "-" + (c.get(c.MONTH) + 1) + "-" + c.get(c.DATE) + " " + c.get(c.HOUR_OF_DAY) + ":" + c.get(c.MINUTE);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(System.getProperty("user.dir") + "\\autoSMS\\in.txt"));
            bw.write("9545644144" + "$$" + msg);
            bw.close();
            System.out.println("SMS Sending!");
        } catch (Exception e) {
            System.out.println("Error Sending SMS:" + e);
        }
    }

    public void processIntrusion() {
      
        // write code to process output alarm if required....
        if (jCheckAlarm.isSelected()) {
                outData |= 128;
            } else if(!jCheckAlarm.isSelected()) {
                outData&= 127;
            }
        parent.writeData(portNo);
        parent.writeData(outData);
        if (jCheckSMS.isSelected()) {
            sendSMS();
        }
    }

    public void initActvCeck() {
        mycheckActv = new JCheckBox[8];
        mycheckActv[0] = ac1;
        mycheckActv[1] = ac2;
        mycheckActv[2] = ac3;
        mycheckActv[3] = ac4;
        mycheckActv[4] = ac5;
        mycheckActv[5] = ac6;
        mycheckActv[6] = ac7;
        mycheckActv[7] = ac8;

        actvSensor = new Boolean[8];
        actvSensor[0] = actPg1;
        actvSensor[1] = actPg2;
        actvSensor[2] = actPg3;
        actvSensor[3] = actPg4;
        actvSensor[4] = actPg5;
        actvSensor[5] = actPg6;
        actvSensor[6] = actPg7;
        actvSensor[7] = actPg8;

        mycheckAuto = new JCheckBox[8];
        mycheckAuto[0] = chk1;
        mycheckAuto[1] = chk2;
        mycheckAuto[2] = chk3;
        mycheckAuto[3] = chk4;
        mycheckAuto[4] = chk5;
        mycheckAuto[5] = chk6;
        mycheckAuto[6] = chk7;
        mycheckAuto[7] = chk8;

        chckAutoMode = new Boolean[8];

        chckAutoMode[0] = autoPg1;
        chckAutoMode[1] = autoPg2;
        chckAutoMode[2] = autoPg3;
        chckAutoMode[3] = autoPg4;
        chckAutoMode[4] = autoPg5;
        chckAutoMode[5] = autoPg6;
        chckAutoMode[6] = autoPg7;
        chckAutoMode[7] = autoPg8;


        myThreshold = new JComboBox[8];
        myThreshold[0] = th1;
        myThreshold[1] = th2;
        myThreshold[2] = th3;
        myThreshold[3] = th4;
        myThreshold[4] = th5;
        myThreshold[5] = th6;
        myThreshold[6] = th7;
        myThreshold[7] = th8;

        myVal = new JLabel[8];
        myVal[0] = val1;
        myVal[1] = val2;
        myVal[2] = val3;
        myVal[3] = val4;
        myVal[4] = val5;
        myVal[5] = val6;
        myVal[6] = val7;
        myVal[7] = val8;
        for (int i = 0; i < 8; i++) {
            actvSensor[i] = false;
            chckAutoMode[i] = false;
        }
    }

    public void takeImageData() {
        imageDetails = new MyImage();

    }

    public void checkActvState(int index) {
        if (mycheckActv[index].isSelected()) {
            actvSensor[index] = true;
        } else {
            actvSensor[index] = false;
        }
    }

    public void checkAutoState(int index) {
        if (mycheckAuto[index].isSelected()) {
            chckAutoMode[index] = true;
        } else {
            chckAutoMode[index] = false;
        }
    }

    public void startSensor(int chnl) {
        if (genFeeback) {
            takeDevicedata();
            if (checkEnableVideoFeedback.isSelected()) {
                updataJavaData(toServer, myArrray, 1);
            } else {
                updataJavaData(toServer, myArrray, 0);
            }
        }
        if (actvSensor[chnl]) {
            adcValues[chnl] = parent.inData;
            jp[chnl].setValue(adcValues[chnl]);
            myVal[chnl].setText("" + adcValues[chnl]);
            if (chckAutoMode[chnl]) {
                myswitch[chnl].setEnabled(false);
                
                if (adcValues[chnl] > (Integer.parseInt(myThreshold[chnl].getSelectedItem().toString()))) {
                    myBulb[chnl].setIcon(i1);
                    myswitch[chnl].setIcon(i4);
                    writeDataOn(chnl);
                } else {
                    myBulb[chnl].setIcon(i2);
                    myswitch[chnl].setIcon(i3);
                    writeDataOff(chnl);
                }
            } else {
                myswitch[chnl].setEnabled(true);
            }
        } else {
            jp[chnl].setValue(0);
            myswitch[chnl].setEnabled(true);
        }
    }

    public void writeDataOn(int deviceNo) {
        if (deviceNo == 0) {
            outData |= 1;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else if (deviceNo == 1) {
            outData |= 2;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else if (deviceNo == 2) {
            outData |= 4;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else if (deviceNo == 3) {
            outData |= 8;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else if (deviceNo == 4) {
            outData |= 16;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else if (deviceNo == 5) {
            outData |= 32;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else if (deviceNo == 6) {
            outData |= 64;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else if (deviceNo == 7) {
            outData |= 128;
            parent.writeData(portNo);
            parent.writeData(outData);
        }
    }

    public void writeDataOff(int deviceNo) {
        if (deviceNo == 0) {
            outData &= ~1;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else if (deviceNo == 1) {
            outData &= ~2;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else if (deviceNo == 2) {
            outData &= ~4;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else if (deviceNo == 3) {
            outData &= ~8;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else if (deviceNo == 4) {
            outData &= ~16;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else if (deviceNo == 5) {
            outData &= ~32;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else if (deviceNo == 6) {
            outData &= ~64;
            parent.writeData(portNo);
            parent.writeData(outData);
        } else if (deviceNo == 7) {
            outData &= ~128;
            parent.writeData(portNo);
            parent.writeData(outData);
        }
    }

    public void initDevice() {
        i1 = new ImageIcon(Settings.imagePath + "\\on.jpg");
        i2 = new ImageIcon(Settings.imagePath + "\\off.jpg");
        i3 = new ImageIcon(Settings.imagePath + "\\btnOn.jpg");
        i4 = new ImageIcon(Settings.imagePath + "\\btnOff.jpg");
        i5 = new ImageIcon(Settings.imagePath + "\\reset_off.jpg");
        i6 = new ImageIcon(Settings.imagePath + "\\reset_on.jpg");
        blb1.setIcon(i2);
        swtch1.setIcon(i3);
        myBulb[0] = blb1;
        myBulb[1] = blb2;
        myBulb[2] = blb3;
        myBulb[3] = blb4;
        myBulb[4] = blb5;
        myBulb[5] = blb6;
        myBulb[6] = blb7;
        myBulb[7] = blb8;

        myswitch[0] = swtch1;
        myswitch[1] = swtch2;
        myswitch[2] = swtch3;
        myswitch[3] = swtch4;
        myswitch[4] = swtch5;
        myswitch[5] = swtch6;
        myswitch[6] = swtch7;
        myswitch[7] = swtch8;

        myCombo[0] = th1;
        myCombo[1] = th2;
        myCombo[2] = th3;
        myCombo[3] = th4;
        myCombo[4] = th8;
        myCombo[5] = th5;
        myCombo[6] = th6;
        myCombo[7] = th7;
    }

    public void chkAuto() {
        if (mycheckAuto[0].isSelected() || mycheckAuto[1].isSelected() || mycheckAuto[2].isSelected() || mycheckAuto[3].isSelected() || mycheckAuto[4].isSelected() || mycheckAuto[5].isSelected() || mycheckAuto[6].isSelected() || mycheckAuto[7].isSelected()) {
            btnAllOn.setEnabled(false);
            btnReset.setEnabled(false);
        } else {
            btnAllOn.setEnabled(true);
            btnReset.setEnabled(true);
        }
    }

    public void initSensor() {
        currentChannel = 0;
        jp = new JProgressBar[8];
        adcValues = new int[8];
        task = new MyTimerTask();
        t = new Timer();
        t.schedule(task, 100, 50);
        task1 = new MyTimerTask1();
        t1 = new Timer();
        t1.schedule(task1, 100, 200);
        jp[0] = pg1;
        jp[1] = pg2;
        jp[2] = pg3;
        jp[3] = pg4;
        jp[4] = pg5;
        jp[5] = pg6;
        jp[6] = pg7;
        jp[7] = pg8;
        for (int i = 0; i < 8; i++) {
            jp[i].setValue(0);
            myCombo[i].setSelectedItem(128);
        }
    }

    class MyTimerTask5 extends TimerTask {

        public void run() {
            if (!running1) {
                return;
            }
            updateImage();
        }
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            if (!running) {
                return;
            }
            if (parent.dataReady) {
                 waitCount++;
        //   if (waitCount == 3) {
             //   waitCount=0;
                 parent.dataReady = false;
                startSensor(currentChannel);
               
          //  }
                currentChannel++;
                if (currentChannel == 8) {
                    currentChannel = 0;
                }
            }
        //    }
//            waitCount++;
//            if (waitCount == 2) {
//                startSensor(currentChannel);
//                currentChannel++;
//                if (currentChannel == 8) {
//                    currentChannel = 0;
//                }
//                waitCount = 0;
//            }
            parent.writeData(73);
            parent.writeData(currentChannel);
            chkAuto();
            try {
                Thread.sleep(25);
            } catch (InterruptedException ex) {
                //Logger.getLogger(TestSensorFrm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    class MyTimerTask1 extends TimerTask {

        @Override
        public void run() {
            if (clientCmd) {
                getClientCommand();
            }
        }
    }

    public void getClientCommand() {
        int temp = 1;
        try {
            clientCommand = getClientCmdJava(parent.username);
        } catch (Exception e) {
        }
        if (clientCommand != null) {

            for (int i = 0; i < 8; i++) {
                if ((temp & clientCommand.deviceStatus) == 0) {
                    offBulb(i);
                    writeDataOff(i);
                } else {
                    onBulb(i);
                    writeDataOn(i);
                }
                temp *= 2;
            }

            for (int i = 0; i < 8; i++) {
                myThreshold[i].setSelectedItem(clientCommand.th.get(i));
            }
        }
    }

    public void onBulb(int i) {
        myBulb[i].setIcon(i1);
        myswitch[i].setIcon(i4);
    }

    public void offBulb(int i) {
        myBulb[i].setIcon(i2);
        myswitch[i].setIcon(i3);
    }

    public void reset() {
        for (int i = 0; i < 8; i++) {
            myBulb[i].setIcon(i2);

            myswitch[i].setIcon(i3);
        }
        outData &= 0;
        parent.writeData(portNo);
        parent.writeData(outData);
    }

    public void fillTh() {
        for (int i = 0; i < 8; i++) {
            for (int j = 1; j <= 255; j++) {
                myCombo[i].addItem(j);
            }
        }
    }

    String objectToString(Object obj) {
        byte[] b = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            b = bos.toByteArray();
        } catch (Exception e) {
            System.out.println("NOT SERIALIZABLE: " + e);
        }
        return Base64.encode(b);
    }

    Object stringToObject(String inp) {
        byte b[] = Base64.decode(inp);
        Object ret = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(b);
            ObjectInput in = new ObjectInputStream(bis);
            ret = (Object) in.readObject();
            bis.close();
            in.close();
        } catch (Exception e) {
            System.out.println("NOT DE-SERIALIZABLE: " + e);
        }
        return ret;
    }

    public void takeDevicedata() {
        toServer = new DeviceDetails();
        toServer.th = new ArrayList<Integer>();
        toServer.auto = new ArrayList<String>();
        toServer.th.add(Integer.parseInt(myThreshold[0].getSelectedItem().toString()));
        toServer.th.add(Integer.parseInt(myThreshold[1].getSelectedItem().toString()));
        toServer.th.add(Integer.parseInt(myThreshold[2].getSelectedItem().toString()));
        toServer.th.add(Integer.parseInt(myThreshold[3].getSelectedItem().toString()));
        toServer.th.add(Integer.parseInt(myThreshold[4].getSelectedItem().toString()));
        toServer.th.add(Integer.parseInt(myThreshold[5].getSelectedItem().toString()));
        toServer.th.add(Integer.parseInt(myThreshold[6].getSelectedItem().toString()));
        toServer.th.add(Integer.parseInt(myThreshold[7].getSelectedItem().toString()));
        toServer.sensor = new ArrayList<Integer>();
        toServer.sensor.add(pg1.getValue());
        toServer.sensor.add(pg2.getValue());
        toServer.sensor.add(pg3.getValue());
        toServer.sensor.add(pg4.getValue());
        toServer.sensor.add(pg5.getValue());
        toServer.sensor.add(pg6.getValue());
        toServer.sensor.add(pg7.getValue());
        toServer.sensor.add(pg8.getValue());
        toServer.deviceStatus = outData;
        toServer.userId = parent.username;
        if (chkallowControl.isSelected()) {
            toServer.allControl = "Yes";
        } else {
            toServer.allControl = "No";
        }
        if (genFeeback) {
            toServer.feedback = "Yes";
        } else {
            toServer.feedback = "No";
        }
        for (int i = 0; i < 8; i++) {
            if (chckAutoMode[i]) {
                toServer.auto.add("Yes");
            } else {
                toServer.auto.add("No");
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        chkallowControl = new javax.swing.JCheckBox();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        chckgenFb = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        blb1 = new javax.swing.JLabel();
        swtch1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        blb2 = new javax.swing.JLabel();
        swtch2 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        blb3 = new javax.swing.JLabel();
        swtch3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        blb4 = new javax.swing.JLabel();
        swtch4 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        blb5 = new javax.swing.JLabel();
        swtch5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        blb6 = new javax.swing.JLabel();
        swtch6 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        blb7 = new javax.swing.JLabel();
        swtch7 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        blb8 = new javax.swing.JLabel();
        swtch8 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        ac1 = new javax.swing.JCheckBox();
        pg1 = new javax.swing.JProgressBar();
        chk1 = new javax.swing.JCheckBox();
        th1 = new javax.swing.JComboBox();
        val1 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        ac2 = new javax.swing.JCheckBox();
        pg2 = new javax.swing.JProgressBar();
        chk2 = new javax.swing.JCheckBox();
        th2 = new javax.swing.JComboBox();
        val2 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        ac3 = new javax.swing.JCheckBox();
        pg3 = new javax.swing.JProgressBar();
        chk3 = new javax.swing.JCheckBox();
        th3 = new javax.swing.JComboBox();
        val3 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        ac4 = new javax.swing.JCheckBox();
        pg4 = new javax.swing.JProgressBar();
        chk4 = new javax.swing.JCheckBox();
        th4 = new javax.swing.JComboBox();
        val4 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        ac5 = new javax.swing.JCheckBox();
        pg5 = new javax.swing.JProgressBar();
        chk5 = new javax.swing.JCheckBox();
        th5 = new javax.swing.JComboBox();
        val5 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        ac6 = new javax.swing.JCheckBox();
        pg6 = new javax.swing.JProgressBar();
        chk6 = new javax.swing.JCheckBox();
        th6 = new javax.swing.JComboBox();
        val6 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        ac7 = new javax.swing.JCheckBox();
        pg7 = new javax.swing.JProgressBar();
        chk7 = new javax.swing.JCheckBox();
        th7 = new javax.swing.JComboBox();
        val7 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        ac8 = new javax.swing.JCheckBox();
        pg8 = new javax.swing.JProgressBar();
        chk8 = new javax.swing.JCheckBox();
        th8 = new javax.swing.JComboBox();
        val8 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jLabelFeed1 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();

        new JavaLib.LoadForm();
        jProgressMotion = new javax.swing.JProgressBar();
        jComboThreshold = new javax.swing.JComboBox();

        new JavaLib.LoadForm();
        jLabel20 = new javax.swing.JLabel();

        new JavaLib.LoadForm();
        jComboSensitivity = new javax.swing.JComboBox();
        StartBtn1 = new javax.swing.JButton();
        StopBtn1 = new javax.swing.JButton();
        jPanel24 = new javax.swing.JPanel();
        btnAllOn = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jCheckAlarm = new javax.swing.JCheckBox();
        jCheckSMS = new javax.swing.JCheckBox();
        checkEnableVideoFeedback = new javax.swing.JCheckBox();

        new JavaLib.LoadForm();
        jCheckDetectMotion = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setFont(new java.awt.Font("Calibri", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("WEB BASED CONTROL");

        chkallowControl.setBackground(new java.awt.Color(0, 0, 0));
        chkallowControl.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chkallowControl.setForeground(new java.awt.Color(0, 204, 204));
        chkallowControl.setText("ALLOW CONTROL");
        chkallowControl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chkallowControl.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkallowControlStateChanged(evt);
            }
        });
        chkallowControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkallowControlActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(0, 204, 51));
        jButton3.setFont(new java.awt.Font("Calibri", 0, 10)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("DEACTIVATE");
        jButton3.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(204, 0, 0));
        jButton1.setFont(new java.awt.Font("Calibri", 0, 10)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("ACTIVATE");
        jButton1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        chckgenFb.setBackground(new java.awt.Color(0, 0, 0));
        chckgenFb.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chckgenFb.setForeground(new java.awt.Color(0, 204, 204));
        chckgenFb.setText("GENERATE FEEDBACK");
        chckgenFb.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        chckgenFb.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chckgenFbStateChanged(evt);
            }
        });
        chckgenFb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chckgenFbActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 51), 4));

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel2.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("DEVICE 1");

        blb1.setBackground(new java.awt.Color(0, 0, 0));
        blb1.setOpaque(true);

        swtch1.setBackground(new java.awt.Color(0, 0, 0));
        swtch1.setOpaque(true);
        swtch1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                swtch1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(blb1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                            .addComponent(swtch1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(blb1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(swtch1, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("DEVICE 2");

        blb2.setBackground(new java.awt.Color(0, 0, 0));
        blb2.setOpaque(true);

        swtch2.setBackground(new java.awt.Color(0, 0, 0));
        swtch2.setOpaque(true);
        swtch2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                swtch2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(blb2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(swtch2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(blb2, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(swtch2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));
        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("DEVICE 3");

        blb3.setBackground(new java.awt.Color(0, 0, 0));
        blb3.setOpaque(true);

        swtch3.setBackground(new java.awt.Color(0, 0, 0));
        swtch3.setOpaque(true);
        swtch3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                swtch3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(blb3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(swtch3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(blb3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(swtch3, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));
        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("DEVICE 4");

        blb4.setBackground(new java.awt.Color(0, 0, 0));
        blb4.setOpaque(true);

        swtch4.setBackground(new java.awt.Color(0, 0, 0));
        swtch4.setOpaque(true);
        swtch4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                swtch4MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(blb4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(swtch4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(blb4, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(swtch4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(0, 0, 0));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel6.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("DEVICE 5");

        blb5.setBackground(new java.awt.Color(0, 0, 0));
        blb5.setOpaque(true);

        swtch5.setBackground(new java.awt.Color(0, 0, 0));
        swtch5.setOpaque(true);
        swtch5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                swtch5MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(blb5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(swtch5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(blb5, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(swtch5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(0, 0, 0));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel7.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("DEVICE 6");

        blb6.setBackground(new java.awt.Color(0, 0, 0));
        blb6.setOpaque(true);

        swtch6.setBackground(new java.awt.Color(0, 0, 0));
        swtch6.setOpaque(true);
        swtch6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                swtch6MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(blb6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(swtch6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(blb6, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(swtch6, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(0, 0, 0));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("DEVICE 7");

        blb7.setBackground(new java.awt.Color(0, 0, 0));
        blb7.setOpaque(true);

        swtch7.setBackground(new java.awt.Color(0, 0, 0));
        swtch7.setOpaque(true);
        swtch7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                swtch7MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(blb7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(swtch7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(blb7, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(swtch7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(0, 0, 0));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel9.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("DEVICE 8");

        blb8.setBackground(new java.awt.Color(0, 0, 0));
        blb8.setOpaque(true);

        swtch8.setBackground(new java.awt.Color(0, 0, 0));
        swtch8.setOpaque(true);
        swtch8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                swtch8MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(blb8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(swtch8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(blb8, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(swtch8, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel10, jPanel4, jPanel5, jPanel6, jPanel7, jPanel8, jPanel9});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel10, jPanel3, jPanel4, jPanel5, jPanel6, jPanel7, jPanel8, jPanel9});

        jPanel16.setBackground(new java.awt.Color(0, 0, 0));
        jPanel16.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 51), 4));

        jPanel11.setBackground(new java.awt.Color(0, 0, 0));

        jLabel10.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Sensor 1");

        ac1.setBackground(new java.awt.Color(0, 0, 0));
        ac1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ac1StateChanged(evt);
            }
        });

        pg1.setMaximum(255);

        chk1.setBackground(new java.awt.Color(0, 0, 0));
        chk1.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        chk1.setForeground(new java.awt.Color(255, 255, 255));
        chk1.setText("Auto");
        chk1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 51)));
        chk1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chk1MouseClicked(evt);
            }
        });
        chk1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chk1StateChanged(evt);
            }
        });

        val1.setBackground(new java.awt.Color(0, 0, 0));
        val1.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        val1.setForeground(new java.awt.Color(0, 162, 232));
        val1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        val1.setText("0");
        val1.setOpaque(true);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(ac1)
                .addGap(18, 18, 18)
                .addComponent(pg1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(val1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chk1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(th1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ac1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pg1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chk1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(th1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(val1))))
        );

        jPanel13.setBackground(new java.awt.Color(0, 0, 0));

        jLabel11.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Sensor 2");

        ac2.setBackground(new java.awt.Color(0, 0, 0));
        ac2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ac2StateChanged(evt);
            }
        });

        pg2.setMaximum(255);

        chk2.setBackground(new java.awt.Color(0, 0, 0));
        chk2.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        chk2.setForeground(new java.awt.Color(255, 255, 255));
        chk2.setText("Auto");
        chk2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chk2MouseClicked(evt);
            }
        });
        chk2.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chk2StateChanged(evt);
            }
        });

        val2.setBackground(new java.awt.Color(0, 0, 0));
        val2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        val2.setForeground(new java.awt.Color(0, 162, 232));
        val2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        val2.setText("0");
        val2.setOpaque(true);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(ac2)
                .addGap(18, 18, 18)
                .addComponent(pg2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(val2, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chk2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(th2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ac2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pg2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chk2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(th2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(val2))))
        );

        jPanel14.setBackground(new java.awt.Color(0, 0, 0));

        jLabel12.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Sensor 3");

        ac3.setBackground(new java.awt.Color(0, 0, 0));
        ac3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ac3StateChanged(evt);
            }
        });

        pg3.setMaximum(255);

        chk3.setBackground(new java.awt.Color(0, 0, 0));
        chk3.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        chk3.setForeground(new java.awt.Color(255, 255, 255));
        chk3.setText("Auto");
        chk3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chk3MouseClicked(evt);
            }
        });
        chk3.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chk3StateChanged(evt);
            }
        });

        val3.setBackground(new java.awt.Color(0, 0, 0));
        val3.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        val3.setForeground(new java.awt.Color(0, 162, 232));
        val3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        val3.setText("0");
        val3.setOpaque(true);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(ac3)
                .addGap(18, 18, 18)
                .addComponent(pg3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(val3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chk3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(th3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ac3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pg3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chk3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(th3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(val3)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBackground(new java.awt.Color(0, 0, 0));

        jLabel13.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Sensor 4");

        ac4.setBackground(new java.awt.Color(0, 0, 0));
        ac4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ac4StateChanged(evt);
            }
        });

        pg4.setMaximum(255);

        chk4.setBackground(new java.awt.Color(0, 0, 0));
        chk4.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        chk4.setForeground(new java.awt.Color(255, 255, 255));
        chk4.setText("Auto");
        chk4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chk4MouseClicked(evt);
            }
        });
        chk4.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chk4StateChanged(evt);
            }
        });

        val4.setBackground(new java.awt.Color(0, 0, 0));
        val4.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        val4.setForeground(new java.awt.Color(0, 162, 232));
        val4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        val4.setText("0");
        val4.setOpaque(true);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGap(18, 18, 18)
                .addComponent(ac4)
                .addGap(18, 18, 18)
                .addComponent(pg4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(val4, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chk4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(th4, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ac4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pg4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chk4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(th4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(val4)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.setBackground(new java.awt.Color(0, 0, 0));

        jLabel14.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Sensor 5");

        ac5.setBackground(new java.awt.Color(0, 0, 0));
        ac5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ac5StateChanged(evt);
            }
        });

        pg5.setMaximum(255);

        chk5.setBackground(new java.awt.Color(0, 0, 0));
        chk5.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        chk5.setForeground(new java.awt.Color(255, 255, 255));
        chk5.setText("Auto");
        chk5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chk5MouseClicked(evt);
            }
        });
        chk5.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chk5StateChanged(evt);
            }
        });

        val5.setBackground(new java.awt.Color(0, 0, 0));
        val5.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        val5.setForeground(new java.awt.Color(0, 162, 232));
        val5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        val5.setText("0");
        val5.setOpaque(true);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(ac5)
                .addGap(18, 18, 18)
                .addComponent(pg5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(val5, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chk5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(th5, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ac5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pg5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chk5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(th5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(val5))))
        );

        jPanel19.setBackground(new java.awt.Color(0, 0, 0));

        jLabel15.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Sensor 6");

        ac6.setBackground(new java.awt.Color(0, 0, 0));
        ac6.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ac6StateChanged(evt);
            }
        });

        pg6.setMaximum(255);

        chk6.setBackground(new java.awt.Color(0, 0, 0));
        chk6.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        chk6.setForeground(new java.awt.Color(255, 255, 255));
        chk6.setText("Auto");
        chk6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chk6MouseClicked(evt);
            }
        });
        chk6.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chk6StateChanged(evt);
            }
        });

        val6.setBackground(new java.awt.Color(0, 0, 0));
        val6.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        val6.setForeground(new java.awt.Color(0, 162, 232));
        val6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        val6.setText("0");
        val6.setOpaque(true);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(ac6)
                .addGap(18, 18, 18)
                .addComponent(pg6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(val6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chk6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(th6, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ac6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pg6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chk6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(th6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(val6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel20.setBackground(new java.awt.Color(0, 0, 0));

        jLabel16.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Sensor 7");

        ac7.setBackground(new java.awt.Color(0, 0, 0));
        ac7.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ac7StateChanged(evt);
            }
        });

        pg7.setMaximum(255);

        chk7.setBackground(new java.awt.Color(0, 0, 0));
        chk7.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        chk7.setForeground(new java.awt.Color(255, 255, 255));
        chk7.setText("Auto");
        chk7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chk7MouseClicked(evt);
            }
        });
        chk7.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chk7StateChanged(evt);
            }
        });

        val7.setBackground(new java.awt.Color(0, 0, 0));
        val7.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        val7.setForeground(new java.awt.Color(0, 162, 232));
        val7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        val7.setText("0");
        val7.setOpaque(true);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(ac7)
                .addGap(18, 18, 18)
                .addComponent(pg7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(val7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chk7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(th7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ac7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(pg7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(chk7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(th7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(val7)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel21.setBackground(new java.awt.Color(0, 0, 0));

        jLabel17.setFont(new java.awt.Font("Calibri", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Sensor 8");

        ac8.setBackground(new java.awt.Color(0, 0, 0));
        ac8.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ac8StateChanged(evt);
            }
        });

        pg8.setMaximum(255);

        chk8.setBackground(new java.awt.Color(0, 0, 0));
        chk8.setFont(new java.awt.Font("Calibri", 0, 16)); // NOI18N
        chk8.setForeground(new java.awt.Color(255, 255, 255));
        chk8.setText("Auto");
        chk8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chk8MouseClicked(evt);
            }
        });
        chk8.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chk8StateChanged(evt);
            }
        });

        val8.setBackground(new java.awt.Color(0, 0, 0));
        val8.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        val8.setForeground(new java.awt.Color(0, 162, 232));
        val8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        val8.setText("0");
        val8.setOpaque(true);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(ac8)
                .addGap(18, 18, 18)
                .addComponent(pg8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(val8, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chk8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(th8, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel21Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ac8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pg8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(chk8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(th8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(val8)))))
        );

        jPanel22.setBackground(new java.awt.Color(70, 68, 68));
        jPanel22.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        jLabelFeed1.setBackground(new java.awt.Color(70, 68, 68));
        jLabelFeed1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelFeed1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyImagePack/Back320x240.PNG"))); // NOI18N
        jLabelFeed1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jLabelFeed1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelFeed1MouseClicked(evt);
            }
        });

        jPanel17.setBackground(new java.awt.Color(102, 102, 102));
        jPanel17.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel18.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 204));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("THRESHOLD %");
        jLabel18.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel19.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 204));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("MOTION %");
        jLabel19.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jProgressMotion.setStringPainted(true);

        jComboThreshold.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        jComboThreshold.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "100" }));
        jComboThreshold.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboThresholdItemStateChanged(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Tempus Sans ITC", 1, 11)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 204));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("SENSITIVITY");
        jLabel20.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jComboSensitivity.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        jComboSensitivity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "100" }));
        jComboSensitivity.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboSensitivityItemStateChanged(evt);
            }
        });

        StartBtn1.setBackground(new java.awt.Color(0, 153, 153));
        StartBtn1.setFont(new java.awt.Font("Narkisim", 1, 18)); // NOI18N
        StartBtn1.setForeground(new java.awt.Color(255, 255, 255));
        StartBtn1.setText("START");
        StartBtn1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        StartBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartBtn1ActionPerformed(evt);
            }
        });

        StopBtn1.setBackground(new java.awt.Color(0, 153, 153));
        StopBtn1.setFont(new java.awt.Font("Narkisim", 1, 18)); // NOI18N
        StopBtn1.setForeground(new java.awt.Color(255, 255, 255));
        StopBtn1.setText("STOP");
        StopBtn1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        StopBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopBtn1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(StartBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(StopBtn1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel17Layout.createSequentialGroup()
                                .addComponent(jComboThreshold, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboSensitivity, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jProgressMotion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 4, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(StartBtn1)
                    .addComponent(StopBtn1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jComboThreshold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(jComboSensitivity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jProgressMotion, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelFeed1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelFeed1, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel21, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel16Layout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel16Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jPanel11, jPanel13, jPanel14, jPanel15, jPanel18, jPanel19, jPanel20, jPanel21});

        jPanel24.setBackground(new java.awt.Color(0, 0, 0));
        jPanel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 204, 51), 2));

        btnAllOn.setBackground(new java.awt.Color(0, 153, 153));
        btnAllOn.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnAllOn.setForeground(new java.awt.Color(255, 255, 255));
        btnAllOn.setText("ALL ON");
        btnAllOn.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnAllOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAllOnActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(0, 153, 153));
        btnReset.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("RESET");
        btnReset.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 0, 0));
        jButton2.setFont(new java.awt.Font("Calibri", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("BACK");
        jButton2.setActionCommand("DEACTIVATE");
        jButton2.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jCheckAlarm.setBackground(new java.awt.Color(0, 0, 0));
        jCheckAlarm.setFont(new java.awt.Font("Tempus Sans ITC", 1, 15)); // NOI18N
        jCheckAlarm.setForeground(new java.awt.Color(0, 204, 204));
        jCheckAlarm.setText("ALARM");
        jCheckAlarm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckAlarmActionPerformed(evt);
            }
        });

        jCheckSMS.setBackground(new java.awt.Color(0, 0, 0));
        jCheckSMS.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        jCheckSMS.setForeground(new java.awt.Color(0, 204, 204));
        jCheckSMS.setText("SMS ALERTS");
        jCheckSMS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckSMSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCheckAlarm, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckSMS, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAllOn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnAllOn, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckAlarm, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jCheckSMS, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addContainerGap())
        );

        checkEnableVideoFeedback.setBackground(new java.awt.Color(0, 0, 0));
        checkEnableVideoFeedback.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        checkEnableVideoFeedback.setForeground(new java.awt.Color(0, 204, 204));
        checkEnableVideoFeedback.setText("VIDEO FEEDBACK");
        checkEnableVideoFeedback.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        checkEnableVideoFeedback.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                checkEnableVideoFeedbackStateChanged(evt);
            }
        });
        checkEnableVideoFeedback.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkEnableVideoFeedbackActionPerformed(evt);
            }
        });

        jCheckDetectMotion.setBackground(new java.awt.Color(0, 0, 0));
        jCheckDetectMotion.setFont(new java.awt.Font("Tempus Sans ITC", 1, 12)); // NOI18N
        jCheckDetectMotion.setForeground(new java.awt.Color(0, 204, 204));
        jCheckDetectMotion.setText("DETECT MOTION");
        jCheckDetectMotion.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckDetectMotion.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jCheckDetectMotion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckDetectMotionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkallowControl, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chckgenFb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(checkEnableVideoFeedback)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckDetectMotion, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(chkallowControl)
                    .addComponent(chckgenFb)
                    .addComponent(checkEnableVideoFeedback)
                    .addComponent(jCheckDetectMotion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(136, 136, 136))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {chckgenFb, chkallowControl});

        new LoadForm();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 742, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void swtch1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_swtch1MouseClicked
        if (swtch1.getIcon() == i3) {
            onBulb(0);
            writeDataOn(0);
        } else {
            offBulb(0);
            writeDataOff(0);
        }
    }//GEN-LAST:event_swtch1MouseClicked

    private void swtch2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_swtch2MouseClicked
        if (swtch2.getIcon() == i3) {
            onBulb(1);
            writeDataOn(1);
        } else {
            offBulb(1);
            writeDataOff(1);
        }
    }//GEN-LAST:event_swtch2MouseClicked

    private void swtch3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_swtch3MouseClicked
        if (swtch3.getIcon() == i3) {
            onBulb(2);
            writeDataOn(2);
        } else {
            offBulb(2);
            writeDataOff(2);
        }
    }//GEN-LAST:event_swtch3MouseClicked

    private void swtch4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_swtch4MouseClicked
        if (swtch4.getIcon() == i3) {
            onBulb(3);
            writeDataOn(3);
        } else {
            offBulb(3);
            writeDataOff(3);
        }
    }//GEN-LAST:event_swtch4MouseClicked

    private void swtch5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_swtch5MouseClicked
        if (swtch5.getIcon() == i3) {
            onBulb(4);
            writeDataOn(4);
        } else {
            offBulb(4);
            writeDataOff(4);
        }
    }//GEN-LAST:event_swtch5MouseClicked

    private void swtch6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_swtch6MouseClicked
        if (swtch6.getIcon() == i3) {
            onBulb(5);
            writeDataOn(5);
        } else {
            offBulb(5);
            writeDataOff(5);
        }
    }//GEN-LAST:event_swtch6MouseClicked

    private void swtch7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_swtch7MouseClicked
        if (swtch7.getIcon() == i3) {
            onBulb(6);
            writeDataOn(6);
        } else {
            offBulb(6);
            writeDataOff(6);
        }
    }//GEN-LAST:event_swtch7MouseClicked

    private void swtch8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_swtch8MouseClicked
        if (swtch8.getIcon() == i3) {
            onBulb(7);
            writeDataOn(7);
        } else {
            offBulb(7);
            writeDataOff(7);
        }
    }//GEN-LAST:event_swtch8MouseClicked

    private void chckgenFbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chckgenFbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chckgenFbActionPerformed

    private void chkallowControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkallowControlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkallowControlActionPerformed

    private void btnAllOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAllOnActionPerformed
        for (int i = 0; i < 8; i++) {
            myswitch[i].setIcon(i4);
            myBulb[i].setIcon(i1);
        }
        outData |= 255;
        parent.writeData(portNo);
        parent.writeData(outData);
    }//GEN-LAST:event_btnAllOnActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        running = false;
        running1 = false;
        m.stop();
        this.setVisible(false);
        parent.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void ac1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ac1StateChanged
        checkActvState(0);
    }//GEN-LAST:event_ac1StateChanged

    private void ac2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ac2StateChanged
        checkActvState(1);
    }//GEN-LAST:event_ac2StateChanged

    private void ac3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ac3StateChanged
        checkActvState(2);
    }//GEN-LAST:event_ac3StateChanged

    private void ac4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ac4StateChanged
        checkActvState(3);
    }//GEN-LAST:event_ac4StateChanged

    private void ac5StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ac5StateChanged
        checkActvState(4);
    }//GEN-LAST:event_ac5StateChanged

    private void ac6StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ac6StateChanged
        checkActvState(5);
    }//GEN-LAST:event_ac6StateChanged

    private void ac7StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ac7StateChanged
        checkActvState(6);
    }//GEN-LAST:event_ac7StateChanged

    private void ac8StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ac8StateChanged
        checkActvState(7);
    }//GEN-LAST:event_ac8StateChanged

    private void chk1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chk1StateChanged
//        checkAutoState(0);
    }//GEN-LAST:event_chk1StateChanged

    private void chk2StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chk2StateChanged
        //      checkAutoState(1);
    }//GEN-LAST:event_chk2StateChanged

    private void chk3StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chk3StateChanged
        //    checkAutoState(2);
    }//GEN-LAST:event_chk3StateChanged

    private void chk4StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chk4StateChanged
        //  checkAutoState(3);
    }//GEN-LAST:event_chk4StateChanged

    private void chk5StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chk5StateChanged
        //checkAutoState(4);
    }//GEN-LAST:event_chk5StateChanged

    private void chk6StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chk6StateChanged
        //checkAutoState(5);
    }//GEN-LAST:event_chk6StateChanged

    private void chk7StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chk7StateChanged
        //checkAutoState(6);
    }//GEN-LAST:event_chk7StateChanged

    private void chk8StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chk8StateChanged
        //checkAutoState(7);
    }//GEN-LAST:event_chk8StateChanged

    private void chckgenFbStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chckgenFbStateChanged
        if (chckgenFb.isSelected()) {
            genFeeback = true;
        } else {
            genFeeback = false;
        }
    }//GEN-LAST:event_chckgenFbStateChanged

    private void chkallowControlStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkallowControlStateChanged
        if (chkallowControl.isSelected()) {
            clientCmd = true;
        } else {
            clientCmd = false;
        }
    }//GEN-LAST:event_chkallowControlStateChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        running = false;
        for (int i = 0; i < 8; i++) {
            jp[i].setValue(0);
            myVal[i].setText("" + 0);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        running = true;
    }//GEN-LAST:event_jButton1ActionPerformed

    private void StartBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartBtn1ActionPerformed
// TODO add your handling code here:
        running1 = true;
    }//GEN-LAST:event_StartBtn1ActionPerformed

    private void StopBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopBtn1ActionPerformed
// TODO add your handling code here:
        running1 = false;
    }//GEN-LAST:event_StopBtn1ActionPerformed

    private void jLabelFeed1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelFeed1MouseClicked
// TODO add your handling code here:
        m.settings();
    }//GEN-LAST:event_jLabelFeed1MouseClicked

    private void checkEnableVideoFeedbackStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_checkEnableVideoFeedbackStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_checkEnableVideoFeedbackStateChanged

    private void checkEnableVideoFeedbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkEnableVideoFeedbackActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkEnableVideoFeedbackActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        reset();
    }//GEN-LAST:event_btnResetActionPerformed

    private void chk1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chk1MouseClicked
        if (actvSensor[0]) {
            checkAutoState(0);
        } else {
            if (chk1.isSelected()) {
                chk1.setSelected(false);
            }
            new MessageBox(this, "Sensor In-Active").setVisible(true);
        }
    }//GEN-LAST:event_chk1MouseClicked

    private void chk2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chk2MouseClicked
        if (actvSensor[1]) {
            checkAutoState(1);
        } else {
            if (chk2.isSelected()) {
                chk2.setSelected(false);
            }
            new MessageBox(this, "Sensor In-Active").setVisible(true);
        }
    }//GEN-LAST:event_chk2MouseClicked

    private void chk3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chk3MouseClicked
        if (actvSensor[2]) {
            checkAutoState(2);
        } else {
            if (chk3.isSelected()) {
                chk3.setSelected(false);
            }
            new MessageBox(this, "Sensor In-Active").setVisible(true);
        }
    }//GEN-LAST:event_chk3MouseClicked

    private void chk4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chk4MouseClicked
        if (actvSensor[3]) {
            checkAutoState(3);
        } else {
            if (chk4.isSelected()) {
                chk4.setSelected(false);
            }
            new MessageBox(this, "Sensor In-Active").setVisible(true);
        }
    }//GEN-LAST:event_chk4MouseClicked

    private void chk5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chk5MouseClicked
        if (actvSensor[4]) {
            checkAutoState(4);
        } else {
            if (chk5.isSelected()) {
                chk5.setSelected(false);
            }
            new MessageBox(this, "Sensor In-Active").setVisible(true);
        }
    }//GEN-LAST:event_chk5MouseClicked

    private void chk6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chk6MouseClicked
        if (actvSensor[5]) {
            checkAutoState(5);
        } else {
            if (chk6.isSelected()) {
                chk6.setSelected(false);
            }
            new MessageBox(this, "Sensor In-Active").setVisible(true);
        }
    }//GEN-LAST:event_chk6MouseClicked

    private void chk7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chk7MouseClicked
        if (actvSensor[6]) {
            checkAutoState(6);
        } else {
            if (chk7.isSelected()) {
                chk7.setSelected(false);
            }
            new MessageBox(this, "Sensor In-Active").setVisible(true);
        }
    }//GEN-LAST:event_chk7MouseClicked

    private void chk8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chk8MouseClicked
        if (actvSensor[7]) {
            checkAutoState(7);
        } else {
            if (chk8.isSelected()) {
                chk8.setSelected(false);
            }
            new MessageBox(this, "Sensor In-Active").setVisible(true);
        }
    }//GEN-LAST:event_chk8MouseClicked

    private void jCheckDetectMotionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckDetectMotionActionPerformed
        jLabel12.setBackground(Color.BLACK);
        jLabel13.setBackground(Color.BLACK);
//        if (jCheckDetectMotion.isSelected()) {
//            for (int i = 0; i < components.length; i++) {
//                components[i].setEnabled(false);
//            }
//        } else {
//            for (int i = 0; i < components.length; i++) {
//                components[i].setEnabled(true);
//            }
//        }
    }//GEN-LAST:event_jCheckDetectMotionActionPerformed

    private void jComboThresholdItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboThresholdItemStateChanged
// TODO add your handling code here:
        currentMotionThreshold = jComboThreshold.getSelectedIndex() + 1;
    }//GEN-LAST:event_jComboThresholdItemStateChanged

    private void jComboSensitivityItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboSensitivityItemStateChanged
// TODO add your handling code here:
    }//GEN-LAST:event_jComboSensitivityItemStateChanged

    private void jCheckAlarmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckAlarmActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckAlarmActionPerformed

    private void jCheckSMSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckSMSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckSMSActionPerformed
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton StartBtn1;
    private javax.swing.JButton StopBtn1;
    private javax.swing.JCheckBox ac1;
    private javax.swing.JCheckBox ac2;
    private javax.swing.JCheckBox ac3;
    private javax.swing.JCheckBox ac4;
    private javax.swing.JCheckBox ac5;
    private javax.swing.JCheckBox ac6;
    private javax.swing.JCheckBox ac7;
    private javax.swing.JCheckBox ac8;
    private javax.swing.JLabel blb1;
    private javax.swing.JLabel blb2;
    private javax.swing.JLabel blb3;
    private javax.swing.JLabel blb4;
    private javax.swing.JLabel blb5;
    private javax.swing.JLabel blb6;
    private javax.swing.JLabel blb7;
    private javax.swing.JLabel blb8;
    private javax.swing.JButton btnAllOn;
    private javax.swing.JButton btnReset;
    private javax.swing.JCheckBox chckgenFb;
    private javax.swing.JCheckBox checkEnableVideoFeedback;
    private javax.swing.JCheckBox chk1;
    private javax.swing.JCheckBox chk2;
    private javax.swing.JCheckBox chk3;
    private javax.swing.JCheckBox chk4;
    private javax.swing.JCheckBox chk5;
    private javax.swing.JCheckBox chk6;
    private javax.swing.JCheckBox chk7;
    private javax.swing.JCheckBox chk8;
    private javax.swing.JCheckBox chkallowControl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckAlarm;
    private javax.swing.JCheckBox jCheckDetectMotion;
    private javax.swing.JCheckBox jCheckSMS;
    private javax.swing.JComboBox jComboSensitivity;
    private javax.swing.JComboBox jComboThreshold;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelFeed1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JProgressBar jProgressMotion;
    private javax.swing.JProgressBar pg1;
    private javax.swing.JProgressBar pg2;
    private javax.swing.JProgressBar pg3;
    private javax.swing.JProgressBar pg4;
    private javax.swing.JProgressBar pg5;
    private javax.swing.JProgressBar pg6;
    private javax.swing.JProgressBar pg7;
    private javax.swing.JProgressBar pg8;
    private javax.swing.JLabel swtch1;
    private javax.swing.JLabel swtch2;
    private javax.swing.JLabel swtch3;
    private javax.swing.JLabel swtch4;
    private javax.swing.JLabel swtch5;
    private javax.swing.JLabel swtch6;
    private javax.swing.JLabel swtch7;
    private javax.swing.JLabel swtch8;
    private javax.swing.JComboBox th1;
    private javax.swing.JComboBox th2;
    private javax.swing.JComboBox th3;
    private javax.swing.JComboBox th4;
    private javax.swing.JComboBox th5;
    private javax.swing.JComboBox th6;
    private javax.swing.JComboBox th7;
    private javax.swing.JComboBox th8;
    private javax.swing.JLabel val1;
    private javax.swing.JLabel val2;
    private javax.swing.JLabel val3;
    private javax.swing.JLabel val4;
    private javax.swing.JLabel val5;
    private javax.swing.JLabel val6;
    private javax.swing.JLabel val7;
    private javax.swing.JLabel val8;
    // End of variables declaration//GEN-END:variables

    private static void passObjectRefrence(MyClientPack.HomeAutomationUser obj) {
        MyClientPack.HomeAutomationService_Service service = new MyClientPack.HomeAutomationService_Service();
        MyClientPack.HomeAutomationService port = service.getHomeAutomationServicePort();
        port.passObjectRefrence(obj);
    }

    private static String pass(MyClientPack.DeviceDetails parameter) {
        MyClientPack.HomeAutomationService_Service service = new MyClientPack.HomeAutomationService_Service();
        MyClientPack.HomeAutomationService port = service.getHomeAutomationServicePort();
        return port.pass(parameter);
    }

    private static DeviceDetails getClientCmdJava(java.lang.String uid) {
        MyClientPack.HomeAutomationService_Service service = new MyClientPack.HomeAutomationService_Service();
        MyClientPack.HomeAutomationService port = service.getHomeAutomationServicePort();
        return port.getClientCmdJava(uid);
    }

    private static MyImage passrefofMyImage() {
        MyClientPack.HomeAutomationService_Service service = new MyClientPack.HomeAutomationService_Service();
        MyClientPack.HomeAutomationService port = service.getHomeAutomationServicePort();
        return port.passrefofMyImage();
    }

    private static void updataJavaData(MyClientPack.DeviceDetails deviceData, byte[] imageArray, int state) {
        MyClientPack.HomeAutomationService_Service service = new MyClientPack.HomeAutomationService_Service();
        MyClientPack.HomeAutomationService port = service.getHomeAutomationServicePort();
        port.updataJavaData(deviceData, imageArray, state);
    }
}
