package com.example.hooscarclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.ResourceBundle;

public class CarController
{
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label loginLabel;
    @FXML
    private TextField usernameLabel;
    @FXML
    private PasswordField passwordLabel;
    @FXML
    private TextField createPool;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ObservableList<String> pools;
    @FXML
    private Button[] buttons = new Button[6];

    private Socket socket = null;
    //write data to the server
    private DataOutputStream out = null;
    //pulls data from the server
    private BufferedReader in = null;
    private BufferedReader keys = null;

    private Profile current = null;
    @FXML
    private Label rideDriver;
    @FXML
    private Label rideDate; //still need to be able to retrieve date
    @FXML
    private Label rideTime;
    @FXML
    private Label rideStartLocation;
    @FXML
    private Label rideDestination;
    @FXML
    private Label ridePassengers;
    @FXML
    private TextField requestDriver;
    @FXML
    private TextField requestDate;
    @FXML
    private TextField requestTime;
    @FXML
    private TextField requestStartLocation;
    @FXML
    private TextField requestDestination;
    @FXML
    private TextField requestCarCap;

    private void initializeSockets()
    {
        try //try to make socket
        {
            //new socket, IP/port of server is constant
            socket = new Socket("172.25.174.86", 80);

            //sends data to server
            out = new DataOutputStream(socket.getOutputStream());
            //reads data from server
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //keys = new BufferedReader(new InputStreamReader(System.in));
        }
        catch(UnknownHostException e)
        {
            System.out.println("nothing to connect to");
            System.exit(0);
        }
        catch(IOException e)
        {
            System.out.println("fuck you");
            System.exit(0);
        }

    }
    private void closeSockets()
    {
        try
        {
            in.close();
            out.close();
            socket.close();
        }
        catch(IOException e)
        {
            System.out.println("fuck you in closing");
            System.exit(0);
        }
    }

    //EVENTLISTENER METHOD
    public void switchToProfile(ActionEvent event) throws IOException //TWO WAY SOCKET WORKS
    {
        initializeSockets();

        String user = "";
        String password = "";
        String result = "";
        try
        {
            //reads from textbox
            user = usernameLabel.getText();
            password = passwordLabel.getText();

            System.out.println("user and password obtained");
            System.out.println("login "+user+" "+password);

            //the info is sent to the server
            out.writeUTF("login "+user+" "+password);
            //get the result after the server processes
            result = in.readLine();
            System.out.println(result);
        }
        catch(IOException e)
        {
            System.out.println("fuck you bad line");
            System.exit(0);
        }

        if(result.equals("success"))
        {
            current = new Profile("", user, password);
            root = FXMLLoader.load(getClass().getResource("profile-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        //closeSockets();
    }


    public void switchToSignup(ActionEvent event) throws IOException {

            root = FXMLLoader.load(getClass().getResource("signup-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    /*ObservableList<String> list = FXCollections.observableArrayList(
            "Item 1", "Item 2", "Item 3", "Item 4");
    ListView<String> lv = new ListView<>(list);
        lv.setCellFactory(param -> new XCell()); */


    //EVENT LISTENER METHOD
    @FXML
    protected void switchToPool(ActionEvent event) throws IOException
    {
        // when a pool is clicked, move to a different scene
        // retrieve from server
        String createid = createPool.getText();
        System.out.println(createid);
        out.writeUTF("createPool " + createid);
        if (in.readLine().equals("success")) {

            root = FXMLLoader.load(getClass().getResource("pool-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }


    public void initializePoolList()
    {
        pools = FXCollections.observableArrayList(); // currently empty

        for (int i = 0; i < 5; i++)
            pools.add("No Pool");


        //retrieve pools for the member
        //"getAllPools username"
        //parse through
        //pools.set(i, parsed value)


       /*comboBox = new ComboBox<>();
       for(int i=0; i < 10; i++) {
           pools.add(""+i);
//            comboBox.getItems().add(""+i);
       }
       comboBox.setItems(pools);
       System.out.println(comboBox.toString()); */
        //fill pools using getAllPools
    }
    public void joinPool()
    {
        //enter ID
        //check if ID is valid (call to backend)
        //"selectPool poolID"
        // if valid add pool


        //comboBox.getItems().add("yum pices"); //add the id
    }


    public void createPool(ActionEvent event) throws IOException
    {
        String createid = createPool.getText();
        System.out.println(createid);
        out.writeUTF("createPool " + createid);
        System.out.println("written to server");
        String result = in.readLine();
        System.out.println(result);
        if (result.equals("success"))
        {

            root = FXMLLoader.load(getClass().getResource("pool-view.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void initializeButtons() {
        // request date, time + destination from
        // for each ride block (button), write the information we already know in them

        ArrayList<String> buttonText = new ArrayList<>();
        int i = 0;
        for (String s: buttonText) {
            buttons[i].setText(s);
            i++;
        }
    }

    //EVENTLISTENER METHOD
    public void switchToRide(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("ride-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        //set labels as requested by the server
        rideDriver.setText(""); // info from server
        rideDate.setText(""); // info from server
        rideTime.setText(""); // info from server
        rideStartLocation.setText(""); // info from server
        rideDestination.setText(""); // info from server
        ridePassengers.setText(""); // info from server
    }

    //EVENTLISTENER METHOD
    public void switchToRequest(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("request-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void switchToPoolAfterRequest(ActionEvent event) throws IOException
    {
        // when a pool is clicked, move to a different scene
        // retrieve from server
        // save all the new inputs
        //set labels as requested by the server

        root = FXMLLoader.load(getClass().getResource("pool-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        initializeButtons();

        requestDriver.getText(); // info to server
        requestDate.getText();
        requestTime.getText();
        requestStartLocation.getText();
        requestDestination.getText();
        requestCarCap.getText();
    }

}
