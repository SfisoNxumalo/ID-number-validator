package com.sfisomawila.luhnalogorithm;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private ImageView imgHomeImg;

    @FXML
    private Label lblCountry;

    @FXML
    private Label lblDob;

    @FXML
    private Label lblDob1;

    @FXML
    private ImageView imgPhoto;

    @FXML
    private Label lblError;

    @FXML
    private Label lblIdentity;

    @FXML
    private Label lblSex;

    @FXML
    private Label lblStatus;

    @FXML
    private StackPane stcIdCard;

    @FXML
    private TextField txtID;

    @FXML
    private CheckBox chkId;

    HashMap<Integer, String> genderMap = new HashMap<>();
    HashMap<Integer, String> CitizenshipMap = new HashMap<>();

    int imageNo = 1;

    Image imgError = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("Images/invalid.png")));
    Image imgMain = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("Images/main.png")));


    StringBuilder OldID = new StringBuilder();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        genderMap.put(1, "F");
        genderMap.put(2, "M");

        CitizenshipMap.put(0, "Citizen");
        CitizenshipMap.put(1, "Permanent Resident");
        CitizenshipMap.put(2, "Refugee");


        mClearDetails();
    }

    @FXML
    void mKeyReleased(KeyEvent event) {

        OldID.append(event.getText());

        if(!OldID.toString().matches("\\d+"))
        {
            txtID.setStyle("-fx-border-color: red; -fx-border-radius: 5px;");
            mClearDetails();

            if(OldID.length() != 0){
                OldID.deleteCharAt(OldID.length() - 1);
            }

            return;
        }
        else
        {
            txtID.setStyle("-fx-border-color: none; -fx-border-radius: 0px;");
        }

        if(event.getCode() == KeyCode.BACK_SPACE)
        {
            if(OldID.length() != 0){
                OldID.deleteCharAt(OldID.length() - 1);
            }

            if(txtID.getText().length() == 0) {
                OldID.delete(0, OldID.length());
            }

            processIDCheck();

            return;
        }

        if(!txtID.getText().isEmpty())
        {
            processIDCheck();
        }
    }

    public void mCheckIDNo(KeyEvent event) {
        if((txtID.getText().length()+1) > 13)
        {
            txtID.setText("");
        }

        if(txtID.getText().length() == 0) {
            OldID.delete(0, OldID.length());
        }
    }

    public void mHideId(ActionEvent actionEvent) {
        idFilter();
    }

    private void mGetDob()
    {
        LocalDate todayDate = LocalDate.now();
        String id = OldID.substring(0, 6);

        int year, month, day;

        year = Integer.parseInt(id.substring(0, 2));
        month = Integer.parseInt(id.substring(2, 4));
        day = Integer.parseInt(id.substring(4, 6));

        int todayYear = todayDate.getYear();

        int age = Integer.parseInt(String.valueOf(todayYear - year).substring(2, 4));

        int yearBorn = todayYear - age;
        String strD = String.valueOf(day);

        if(strD.length() == 1)
        {
            strD = "0" + day;
        }

        if(month <= 12 && day <= 31)
        {
            lblDob.setText(strD + " " + String.valueOf(Month.of(month)).substring(0, 3) + " " +  yearBorn);
        }
    }

//    Starts the verification process when all checks pass
    private void processIDCheck()
    {
        try
        {
            if(chkId.isSelected())
            {
                if(!txtID.getText().isBlank())
                {
                    txtID.setText(IdNumberFilter.mFilterID(txtID.getText()));
                    txtID.positionCaret(txtID.getLength());
                }
            }

            if((txtID.getText().length()) == 13)
            {
                if(mCheckTheIDCharacter(OldID.toString()))
                {
                    if(IDNumberValidator.isValidIDNumber(OldID.toString()))
                    {
                        txtID.setStyle("-fx-border-color: none; -fx-border-radius: 0;");

                        char genderDigit = OldID.toString().charAt(6);
                        char Citizenship = OldID.toString().charAt(10);

                        mShowID(genderDigit, Citizenship);
                    }
                    else
                    {
                        mShowAlert("This is not a Valid South African ID");
                    }
                }
                else
                {
                    mShowAlert("The SA Id number only contains numbers");
                }
            }
            else
            {
                mClearDetails();
                txtID.setStyle("-fx-border-color: red; -fx-border-radius: 0;");
            }
        }
        catch(Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }

    private boolean mCheckTheIDCharacter(String ID)
    {
        for (char ch : ID.toCharArray())
        {
            System.out.println(ch);
            if(!Character.isDigit(ch))
            {
                return false;
            }
        }

        return true;
    }

//    Clear all details
    private void mClearDetails(){
        lblCountry.setText("....");
        lblDob.setText("....");
        lblIdentity.setText("....");
        lblSex.setText("....");
        lblStatus.setText("....");

        stcIdCard.setVisible(false);
        imgHomeImg.setVisible(true);
        lblError.setText("");
        imgHomeImg.setImage(imgMain);
    }

    private void mShowAlert(String Content){
        try
        {
//            Alert alert = new Alert(Alert.AlertType.WARNING);
//            alert.setHeaderText(header);
//            alert.setContentText(Content);
//            alert.show();

            mClearDetails();

            imgHomeImg.setImage(imgError);
            lblError.setText(Content);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

//    Generate the ID and the required details
    private void mShowID(char genderDigit, char Citizenship){

        if(Byte.parseByte(String.valueOf(genderDigit)) <= 4)
        {
            lblSex.setText(genderMap.get(1));
            imageNo = new Random().nextInt(1, 16);
            Image imgFemale = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("Images/female" + imageNo + ".png")));
            imgPhoto.setImage(imgFemale);
        }
        else
        {
            lblSex.setText(genderMap.get(2));
            imageNo = new Random().nextInt(1, 14);
            Image imgMale = new Image(Objects.requireNonNull(this.getClass().getResourceAsStream("Images/male" + imageNo + ".png")));
            imgPhoto.setImage(imgMale);
        }

        if(Byte.parseByte(String.valueOf(Citizenship)) == 0){
            lblStatus.setText(CitizenshipMap.get(0));
            lblCountry.setText("South Africa");
        }
        else{
            lblStatus.setText(CitizenshipMap.get(1));
        }


        lblIdentity.setText(OldID.toString());
        mGetDob();
        stcIdCard.setVisible(true);
        imgHomeImg.setVisible(false);
    }

//    Filters & hides some characters from the ID number
    private void idFilter(){
        if(chkId.isSelected())
        {
            if(!txtID.getText().isBlank())
            {
                txtID.setText(IdNumberFilter.mFilterID(txtID.getText()));
            }
        }
        else{
            txtID.setText(String.valueOf(OldID));
        }
    }
}