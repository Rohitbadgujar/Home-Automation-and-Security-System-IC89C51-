/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package MyServerPack;

import MyHomeAutomationUserPack.DeviceDetails;
import MyHomeAutomationUserPack.HomeAutomationUser;
import MyHomeAutomationUserPack.MyImage;

import java.io.*;
import java.sql.*;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(serviceName = "HomeAutomationService")
public class HomeAutomationService {

    String connection = "jdbc:mysql://localhost/9527DB";
    String user = "root";
    String password = "pass";
    Connection con;
    DeviceDetails toAndroidClient;
    DeviceDetails storeClientCmd;

    public void initDatabse() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(connection, user, password);
        } catch (Exception e) {
            //System.out.println("Error opening database : " + e);
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
            //System.out.println("NOT SERIALIZABLE: " + e);
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
            //System.out.println("NOT DE-SERIALIZABLE: " + e);
        }
        return ret;
    }

    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    @WebMethod(operationName = "PassObjectRefrence")
    @Oneway
    public void PassObjectRefrence(@WebParam(name = "obj") HomeAutomationUser obj) {
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "StoreData")
    public boolean StoreData(@WebParam(name = "fromClient") HomeAutomationUser fromClient) {
        initDatabse();
        try {
            con = DriverManager.getConnection(connection, user, password);
            String sql = "INSERT INTO user values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, fromClient.UserName);
            statement.setString(2, fromClient.Fname);
            statement.setString(3, fromClient.Mname);
            statement.setString(4, fromClient.Lname);
            statement.setString(5, fromClient.Gender);
            statement.setString(6, fromClient.mbNumber);
            statement.setString(7, fromClient.EmailId);
            statement.setString(8, fromClient.Address);
            statement.setString(9, fromClient.Password);
            int status = statement.executeUpdate();
            String qry = "insert into deviceDetails values(null,'" + fromClient.UserName + "',0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,'No','No','No','No','No','No','No','No','No','No')";
            //System.out.println("Query: " + qry);
            Statement stmt = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_INSENSITIVE);
            stmt.executeUpdate(qry);
            String sql1 = "insert into imageDetails values('" + fromClient.UserName + "',320,240,0,0)";
            Statement stmt1 = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_INSENSITIVE);
            stmt1.executeUpdate(sql1);
            con.close();
            if (status == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            //System.out.println("Error  to store on database");
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "SearchUserPresent")
    public boolean SearchUserPresent(@WebParam(name = "fromClient") HomeAutomationUser fromClient) {
        initDatabse();
        try {
            String qry = "select uid from user where uid ='" + fromClient.UserName + "'";
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(qry);
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            //System.out.println("Error in reading reccords " + e);
        }
        return false;
    }

    @WebMethod(operationName = "AuthenticateUser1")
    public HomeAutomationUser AuthenticateUser1(@WebParam(name = "fromclient") HomeAutomationUser fromclient) {
        initDatabse();
        HomeAutomationUser toClient = new HomeAutomationUser();
        try {
            String qry = "select * from user where uid ='" + fromclient.UserName + "' AND password='" + fromclient.Password + "'";
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(qry);
            if (rs.next()) {
                toClient.Fname = rs.getString(2);
                toClient.mbNumber = "1";
                toClient.UserName = rs.getString(1);
                return (toClient);
            }
        } catch (Exception e) {
            //System.out.println("Error in reading from database " + e);
        }
        return null;
    }

    @WebMethod(operationName = "AuthenticateAndroidUser")
    public String AuthenticateAndroidUser(@WebParam(name = "fromClient") String fromClient) {
        HomeAutomationUser obj;
        obj = (HomeAutomationUser) stringToObject(fromClient);
        initDatabse();
        try {
            //System.out.println("UserName: " + obj.UserName);
            //System.out.println("UserName: " + obj.Password);
            String qry = "select * from user where uid ='" + obj.UserName + "' AND password='" + obj.Password + "'";
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(qry);
            if (rs.next()) {
                obj.Fname = rs.getString(2);
                //System.out.println("Fname : " + obj.Fname);
                //System.out.println("Inside if");
                return (objectToString(obj));
            }
        } catch (Exception e) {
            System.out.println("Error in reading from database " + e);
        }
        return "notFound";
    }

    @WebMethod(operationName = "fetchdataAndroid")
    public String fetchdataAndroid(@WebParam(name = "uid") String uname) {
        initDatabse();
        int notEmpty = 0;
        toAndroidClient = new DeviceDetails();
        try {
            String qry = "select * from devicedetails where userid='" + uname + "'";
            //System.out.println("2");
            Statement stmt = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_INSENSITIVE);
            //  System.out.println("Query=" + qry);
            ResultSet rs = stmt.executeQuery(qry);
            if (rs.next()) {
                notEmpty = 1;
                toAndroidClient.userId = rs.getString(2);
                for (int i = 0; i < 8; i++) {
                    toAndroidClient.sensor.add(rs.getInt(i + 3));
                }
                int j = 11;
                for (int i = 0; i < 8; i++) {
                    toAndroidClient.th.add(rs.getInt(j));
                    j++;
                }
                toAndroidClient.deviceStatus = rs.getInt(19);
                toAndroidClient.feedback = rs.getString(20);
                toAndroidClient.allControl = rs.getString(21);
                for (int i = 0; i < 8; i++) {
                    toAndroidClient.auto.add(rs.getString(i + 22));
                }
            }
            con.close();
            return (objectToString(toAndroidClient) + "###" + notEmpty);
        } catch (Exception e) {
            System.out.println("Error in reading datafor android: " + e);
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "pass")
    public String pass(@WebParam(name = "parameter") DeviceDetails parameter) {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "passobjref")
    @Oneway
    public void passobjref(@WebParam(name = "parameter1") HomeAutomationUser parameter1) {
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "addClientCommands")
    public String addClientCommands(@WebParam(name = "clientCommands") String clientCommands) {
        storeClientCmd = new DeviceDetails();
        storeClientCmd = (DeviceDetails) stringToObject(clientCommands);
      //  System.out.println("Commands: "+storeClientCmd.userId);
        initDatabse();
        try {
            String qry = "insert into clientCommand values(null,'" + storeClientCmd.userId + "','" + storeClientCmd.th.get(0) + "','" + storeClientCmd.th.get(1) + "','" + storeClientCmd.th.get(2);
            qry += "','" + storeClientCmd.th.get(3) + "','" + storeClientCmd.th.get(4) + "','" + storeClientCmd.th.get(5) + "','" + storeClientCmd.th.get(6);
            qry += "','" + storeClientCmd.th.get(7) + "','" + storeClientCmd.deviceStatus + "')";
        //    System.out.println("Query for Client Commands " + qry);
            Statement stmt = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_INSENSITIVE);
            stmt.executeUpdate(qry);
        } catch (Exception e) {
            System.out.println("Error in inserting clientCommands");
            e.printStackTrace();
        }
        return " ";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getClientCmdJava")
    public DeviceDetails getClientCmdJava(@WebParam(name = "uid") String uid) {
        initDatabse();
        DeviceDetails clientCmdToJava = null;
        try {
            String qry = "select * from clientCommand where userid ='" + uid + "'";
            //   System.out.println("Query " + qry);
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet rs = stmt.executeQuery(qry);
            if (rs.next()) {
                clientCmdToJava = new DeviceDetails();
                clientCmdToJava.deviceStatus = rs.getInt(11);
          //      System.out.println("Client Commands in get:"+clientCmdToJava.deviceStatus);
                for (int i = 0; i < 8; i++) {
                    clientCmdToJava.th.add(rs.getInt(i + 3));
                }
                String sql = "delete from clientcommand";
                Statement stmt1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
                stmt1.executeUpdate(sql);
            }
            return (clientCmdToJava);
        } catch (Exception e) {
            System.out.println("Error in get client command for java");
        }
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "updataJavaData")
    @Oneway
    public void updataJavaData(@WebParam(name = "deviceData") DeviceDetails deviceData, @WebParam(name = "imageArray") byte[] imageArray, @WebParam(name = "state") int state) {
        initDatabse();

        if (state == 0) {
            imageArray = new byte[]{};
        }
        try {
            String qry = "update deviceDetails set s1='" + deviceData.sensor.get(0) + "',s2='" + deviceData.sensor.get(1) + "',s3='" + deviceData.sensor.get(2) + "',s4='" + deviceData.sensor.get(3) + "',";
            qry += "s5='" + deviceData.sensor.get(4) + "',s6='" + deviceData.sensor.get(5) + "',s7='" + deviceData.sensor.get(6);
            qry += "',s8='" + deviceData.sensor.get(7) + "',th1='" + deviceData.th.get(0) + "',th2='" + deviceData.th.get(1) + "',th3='" + deviceData.th.get(2);
            qry += "',th4='" + deviceData.th.get(3) + "',th5='" + deviceData.th.get(4) + "',th6='" + deviceData.th.get(5) + "',th7='" + deviceData.th.get(6);
            qry += "',th8='" + deviceData.th.get(7) + "',devStatus='" + deviceData.deviceStatus + "',fbAllowed='" + deviceData.feedback + "',ControlAllowed='" + deviceData.allControl;
            qry += "',auto1='" + deviceData.auto.get(0) + "',auto2='" + deviceData.auto.get(1) + "',auto3='" + deviceData.auto.get(2) + "',auto4='" + deviceData.auto.get(3);
            qry += "',auto5='" + deviceData.auto.get(4) + "',auto6='" + deviceData.auto.get(5) + "',auto7='" + deviceData.auto.get(6) + "',auto8='" + deviceData.auto.get(7);
            qry += "' where userId='" + deviceData.userId + "'";
            Statement stmt = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_INSENSITIVE);

            stmt.executeUpdate(qry);
            String ssql = "update imageDetails set state = " + state + ", image=? where userId='" + deviceData.userId + "'";
            PreparedStatement pre;
            ByteArrayInputStream bis = new ByteArrayInputStream(imageArray);
            pre = con.prepareStatement(ssql);
            pre.setBinaryStream(1, bis, (int) (imageArray.length));
            pre.executeUpdate();
            con.close();
           // System.out.println("Query " + ssql);
        } catch (Exception e) {
            System.out.println("Error in updating data: " + e);
            e.printStackTrace();
        }
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "passrefofMyImage")
    public MyImage passrefofMyImage() {
        //TODO write your implementation code here:
        return null;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "videoStreamForAndroid")
    public String videoStreamForAndroid(@WebParam(name = "uid") String uid) {
        initDatabse();
        byte[] temp = null;
        try {
            String qry = "select * from imageDetails where userid='" + uid + "'";
            //      System.out.println("Query " + qry);
            Statement stmt = con.createStatement(ResultSet.CONCUR_UPDATABLE, ResultSet.TYPE_SCROLL_INSENSITIVE);
            ResultSet rs = stmt.executeQuery(qry);
            if (rs.next()) {

                Blob b = rs.getBlob(4);
                temp = b.getBytes(1, (int) b.length());
              //  System.out.println("Got Video: " + temp.length);
                return (objectToString(temp) + "###" + rs.getInt(5));
                // return (objectToString(temp));
            }

        } catch (Exception e) {
            System.out.println("Error in video streaming" + e);
            e.printStackTrace();
        }
        // return (objectToString(temp));
        return (objectToString(temp) + "###" + 0);
    }
}
