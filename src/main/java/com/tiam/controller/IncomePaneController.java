package com.tiam.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import com.tiam.model.IncomeRecordData;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
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
    private TableView<IncomeRecordData> income_table;

    @FXML
    private TableColumn<IncomeRecordData, String> dateRecorded_col;

    @FXML
    private TableColumn<IncomeRecordData, Double> amount_col;

    @FXML
    private AnchorPane msg_container;

    @FXML
    private Button editRecordBtn;

    @FXML
    private Button deleteRecordBtn;

    @FXML
    private Label streamTitle_label;

    // database tools
    private Connection con;
    private PreparedStatement statement;
    private ResultSet resultSet;

    private IncomeCardController selectedCard = null;

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
        empty_income_pane.heightProperty().addListener((_, _, newVal) -> {
            msg_container.layoutYProperty().set((newVal.doubleValue() - msg_container_height) / 2);
        });
        empty_income_pane.widthProperty().addListener((_, _, newVal) -> {
            msg_container.layoutXProperty().set((newVal.doubleValue() - msg_container_width) / 2);
        });

        fillIncomeStreamList();
    }

    /** --------------------------- Event handlers */

    @FXML
    public void addIncomeStream(ActionEvent actionEvent) throws IOException {
        Stage form = new Stage();
        Parent formRoot = FXMLLoader.load(getClass().getResource("/view/income-stream-insert-form.fxml"));
        Scene formScene = new Scene(formRoot);
        form.setScene(formScene);
        form.setTitle("New income stream");
        form.resizableProperty().set(false);
        form.initStyle(StageStyle.UTILITY);
        this.getParent().getParent().disableProperty().set(true);
        form.showAndWait();
        this.getParent().getParent().disableProperty().set(false);

        fillIncomeStreamList();
    }

    public void addIncomeRecord(ActionEvent actionEvent) throws IOException{
        if (selectedCard == null) {
            Alert dialog = new Alert(AlertType.WARNING);
            dialog.setContentText("Please first select an income stream to insert a new record");
            dialog.showAndWait();
        } else {
            Stage form = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/income-record-insert-form.fxml"));
            loader.load();
            ((IncomeRecordInsertController) loader.getController()).setSelectedIncomeId(selectedCard.getIncomeId());
            Parent formRoot = loader.getRoot();
            Scene formScene = new Scene(formRoot);
            form.setScene(formScene);
            form.setTitle("new income record");
            form.resizableProperty().set(false);
            form.initStyle(StageStyle.UTILITY);
            this.getParent().getParent().disableProperty().set(true);
            form.showAndWait();
            this.getParent().getParent().disableProperty().set(false);    

            fillIncomeRecordTable();
        }
    }

    public void showEditRecordBtn(MouseEvent mouseEvent) {
        if (income_table.getSelectionModel().getSelectedItem() != null) {
            editRecordBtn.setVisible(true);
            deleteRecordBtn.setVisible(true);
        } else {
            editRecordBtn.setVisible(false);
            deleteRecordBtn.setVisible(false);
        }
    }

    public void editIncomeRecord(ActionEvent actionEvent) throws IOException {
        IncomeRecordData selectedRecord = income_table.getSelectionModel().getSelectedItem();
        if (selectedRecord != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/income-record-update-form.fxml"));
            Scene scene = new Scene(loader.load());
            ((IncomeRecordUpdateController)loader.getController()).setSelectedIncomeRecord(selectedRecord);


            Stage updateForm = new Stage(StageStyle.UTILITY);
            updateForm.setTitle("Update Record");
            updateForm.setScene(scene);
            
            this.getParent().getParent().setDisable(true);
            updateForm.showAndWait();
            this.getParent().getParent().setDisable(false);

            fillIncomeRecordTable();
        }
    }
    
    public void handleIncomeStreamSelect(MouseEvent mouseEvent) {
        for (Node card : income_stream_container.getChildren()) {
            card.getStyleClass().remove("selected");
            if (mouseEvent.getTarget().equals(card)) {
                card.getStyleClass().add("selected");
                selectedCard = (IncomeCardController) card;
                fillIncomeRecordTable();
            } 
        }
    }

    public void deleteIncomeRecord(ActionEvent event) {
        Alert dialog = new Alert(AlertType.CONFIRMATION);
        dialog.setContentText("Are your sure you want to delete the selected record ?");
        dialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        IncomeRecordData selectedRecord = income_table.getSelectionModel().getSelectedItem();
        if (result.isPresent() && result.get() == ButtonType.YES) {
            String query = "DELETE FROM IncomeRecord WHERE id=%d".formatted(selectedRecord.getId());
            con = Database.getConnection();

            try {
                statement = con.prepareStatement(query);
                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } 

        fillIncomeRecordTable();
    }


    /** ---------------------------------------------------------------------- utilities */

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
                data.setColor(Color.getColorFromName(resultSet.getString("color_name")));
                data.setId(resultSet.getInt("id"));
                incomeStreamList.add(data);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return incomeStreamList;
    }

    private ObservableList<IncomeRecordData> fetchIncomRecords() {
        ObservableList<IncomeRecordData> incomeRecordList = FXCollections.observableArrayList();
        con = Database.getConnection();
        String query = "SELECT * FROM IncomeRecord WHERE stream_id=%d".formatted(selectedCard.getIncomeId());

        try {
            statement = con.prepareStatement(query);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                IncomeRecordData record = new IncomeRecordData();
                record.setAmount(resultSet.getDouble("amount"));
                record.setId(resultSet.getInt("id"));
                record.setDate(resultSet.getString("date_recorded"));
                incomeRecordList.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return incomeRecordList;
    }

    private void fillIncomeRecordTable() {
        streamTitle_label.setText(selectedCard.getIncomeStream().getName());
        ObservableList<IncomeRecordData> incomeRecordList = fetchIncomRecords();
        income_table.getItems().clear();
        
        if (!incomeRecordList.isEmpty()) {
            dateRecorded_col.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
            amount_col.setCellValueFactory(cellData -> cellData.getValue().amountProperty());
            income_table.setItems(incomeRecordList);
        }
    }

    private void fillIncomeStreamList() {
        ObservableList<IncomeStreamData> incomeStreamList = fetchIncomStreams();
        income_stream_container.getChildren().clear();
        income_table.getItems().clear();

        if (incomeStreamList.isEmpty()) {
            empty_income_pane.setVisible(true);
            income_pane.setVisible(false);
        } else {
            for (IncomeStreamData data : incomeStreamList) {
                IncomeCardController incomeCard = new IncomeCardController(data);
                incomeCard.setUpdateRunnable(this::fillIncomeStreamList);
                income_stream_container.getChildren().add(incomeCard);
                incomeCard.prefWidthProperty().bind(income_stream_container.prefWidthProperty());
                incomeCard.onMouseClickedProperty().set(event -> handleIncomeStreamSelect(event));
            }

            empty_income_pane.setVisible(false);
            income_pane.setVisible(true);
        }
    }
}
