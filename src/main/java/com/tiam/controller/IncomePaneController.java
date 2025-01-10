package com.tiam.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.tiam.model.IncomeStreamData;
import com.tiam.service.Color;
import com.tiam.service.Database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class IncomePaneController extends StackPane implements Initializable {

    @FXML
    private AnchorPane empty_income_pane;

    @FXML
    private AnchorPane income_pane;

    @FXML
    private AnchorPane income_detail_pane;

    @FXML
    private VBox income_stream_container;

    @FXML
    private TableView<?> income_table;

    @FXML
    private AnchorPane msg_container;

    // database tools
    private Connection con;
    private PreparedStatement statement;
    private ResultSet resultSet;


    public IncomePaneController() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/income-pane.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int msg_container_height = 130;
        int msg_container_width = 470;
        empty_income_pane.heightProperty().addListener((obs, oldVal, newVal) -> {
            msg_container.layoutYProperty().set((newVal.doubleValue() - msg_container_height) / 2);
        });
        empty_income_pane.widthProperty().addListener((obs, oldVal, newVal) -> {
            msg_container.layoutXProperty().set((newVal.doubleValue() - msg_container_width) / 2);
        });

        fillIncomeStreamList();
    }

    /** --------------------------- Event handlers */

    @FXML
    public void addIncomeStream(ActionEvent actionEvent) throws IOException {
        Stage form = new Stage();
        Parent formRoot = FXMLLoader.load(getClass().getResource("/view/add-income-stream-form.fxml"));
        Scene formScene = new Scene(formRoot);
        form.setScene(formScene);
        form.setTitle("New income stream");
        form.setAlwaysOnTop(true);
        form.resizableProperty().set(false);
        form.initStyle(StageStyle.UTILITY);
        this.getParent().getParent().disableProperty().set(true);
        form.showAndWait();
        this.getParent().getParent().disableProperty().set(false);
    }

    @FXML
    public void addIncome(ActionEvent actionEvent) throws IOException{
        Stage form = new Stage();
        Parent formRoot = FXMLLoader.load(getClass().getResource("/view/add-income-form.fxml"));
        Scene formScene = new Scene(formRoot);
        form.setScene(formScene);
        form.setTitle("new income record");
        form.setAlwaysOnTop(true);
        form.resizableProperty().set(false);
        form.initStyle(StageStyle.UTILITY);
        this.getParent().getParent().disableProperty().set(true);
        form.showAndWait();
        this.getParent().getParent().disableProperty().set(false);
    }

    public void handleIncomeStreamSelect(MouseEvent mouseEvent) {
        for (Node card : income_stream_container.getChildren()) {
            card.getStyleClass().remove("selected");
            if (mouseEvent.getTarget().equals(card)) {
                card.getStyleClass().add("selected");
            } 
        }
    }


    /** --------------------------------------- utilities */

    private ObservableList<IncomeStreamData> fetchIncomStreams() {
        String query = "SELECT * FROM IncomeStream";
        con = Database.getConnection();
        ObservableList<IncomeStreamData> incomeStreamList = FXCollections.observableArrayList();
        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                IncomeStreamData data = new IncomeStreamData();
                data.setName(resultSet.getString("name"));
                data.setAmount(resultSet.getDouble("amount"));
                data.setColor(Color.getColorFromName(resultSet.getString("color_name")));
                incomeStreamList.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return incomeStreamList;
    }

    private void fillIncomeStreamList() {
        // get the list of income from the datbase
        ObservableList<IncomeStreamData> incomeStreamList = fetchIncomStreams();

        if (incomeStreamList.isEmpty()) {
            // if the list is empty show empty pane, otherwise add all income streams to ui
            empty_income_pane.setVisible(true);
            income_pane.setVisible(false);
        } else {
            // create an income card for each income stream and add it to income_stream_container
            for (IncomeStreamData data : incomeStreamList) {
                IncomeCardController incomeCard = new IncomeCardController(data);
                income_stream_container.getChildren().add(incomeCard);
                incomeCard.prefWidthProperty().bind(income_stream_container.prefWidthProperty());
                incomeCard.onMouseClickedProperty().set(event -> handleIncomeStreamSelect(event));
            }

            // show the income pane
            empty_income_pane.setVisible(false);
            income_pane.setVisible(true);
        }
    }

    public void testCards() {
        for (Color color : Color.colors) {
            IncomeStreamData incomeStream = new IncomeStreamData();
            incomeStream.setColor(color);
            incomeStream.setName("Job");
            IncomeCardController incomeCard = new IncomeCardController(incomeStream);
            income_stream_container.getChildren().add(incomeCard);
            incomeCard.prefWidthProperty().bind(income_stream_container.prefWidthProperty());
            incomeCard.onMouseClickedProperty().set(event -> handleIncomeStreamSelect(event));
        }
    }



}
