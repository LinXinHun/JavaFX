package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button myButton;

    @FXML
    private MenuButton myMenuButton;

    @FXML
    public MenuButton myMenuTableButton;

    @FXML
    private TextField myTextField;

    @FXML
    private TextField inParamField;

    @FXML
    private TextField outParamField;

    private int dbValue = 2;

    private int tableValue = 128;

    private String abdStr = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.printf("init success");
        // TODO (don't really need to do anything here).

    }

    public void modAction(ActionEvent event) {
        System.out.println("-----------------");

        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        String dateTimeString = df.format(date);
        myTextField.setText(dateTimeString);

        System.out.println(inParamField.getText());

        String param = inParamField.getText();

        if (param == null || param.equals("")) {
            outParamField.setText("别闹！");
            return;
        } else {
            String[] paramArrays = param.split("");
            boolean abcFlag = false;
            for (String par : paramArrays) {
                if (abdStr.contains(par)) {
                    abcFlag = true;
                    break;
                }
            }

            int dbValue = getDbValue();
            int tableValue = getTableValue();

            String outParam = "你在逗我？";
            try {

                Integer dbNum;
                Integer tableNum;

                String fiveNumber;

                // openId
                if (abcFlag) {
                    fiveNumber = getLastNumber(((Integer)param.hashCode()).toString(), 5);
                } else {

                    // userId
                    if (param.length() >= 10) {
                        fiveNumber = getFirstNumber(getLastNumber(param, 10), 5);
                    } else {
                        // businessId
                        fiveNumber = getLastNumber(param, 5);
                    }

                }
                System.out.println(fiveNumber);
                dbNum = Integer.valueOf(getFirstNumber(fiveNumber, 2)) % dbValue;
                tableNum = Integer.valueOf(getLastNumber(fiveNumber, 3)) % tableValue;
                outParam = "库:" + dbNum + "\t\t\t\t\t\t\t\t 表:" + tableNum;
            } catch (Exception e) {

            }

            outParamField.setText(outParam);
        }
    }

    public static String getLastNumber(String idStr, int length){
        int size = Math.min(idStr.length(),length);
        idStr = idStr.substring(idStr.length()-size);
        return String.format("%0" + length + "d",Long.valueOf(idStr));
    }

    public static String getFirstNumber(String idStr, int length){
        if(length > idStr.length()){
            return null;
        }
        idStr = idStr.substring(0,length);
        return String.format("%0" + length + "d",Long.valueOf(idStr));
    }

    public void dbAction(ActionEvent event) {
        System.out.println("---dbChange---");
        String text = ((MenuItem)event.getSource()).getText();
        myMenuButton.setText(text);

        if (text.contains("4")) {
            setDbValue(4);
        } else if (text.contains("8")) {
            setDbValue(8);
        } else {
            setDbValue(2);
        }

        String param = inParamField.getText();
        if (param != null && !param.equals("") && param.length() >= 5) {
            System.out.println("---modAction start---");
            modAction(event);
        }
    }

    public void tableAction(ActionEvent event) {
        System.out.println("---tableChange---");
        String text = ((MenuItem)event.getSource()).getText();
        myMenuTableButton.setText(text);

        if (text.contains("256")) {
            setTableValue(256);
        } else if (text.contains("512")) {
            setTableValue(512);
        } else if (text.contains("64")) {
            setTableValue(64);
        } else {
            setTableValue(128);
        }

        String param = inParamField.getText();
        if (param != null && !param.equals("") && param.length() >= 5) {
            System.out.println("---modAction start---");
            modAction(event);
        }
    }

    public void inParamKey(KeyEvent event) {
        System.out.println("---inParamChange---");
        String param = inParamField.getText();
        if (param != null && !param.equals("") && param.length() >= 5) {
            System.out.println("---modAction start---");
            modAction(new ActionEvent());
        }
    }

    public int getDbValue() {
        return dbValue;
    }

    public void setDbValue(int dbValue) {
        this.dbValue = dbValue;
    }

    public int getTableValue() {
        return tableValue;
    }

    public void setTableValue(int tableValue) {
        this.tableValue = tableValue;
    }

}
