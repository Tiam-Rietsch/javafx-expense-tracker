package com.tiam.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import com.tiam.model.IncomeStreamData;
import com.tiam.service.Database;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class IncomeCardController extends AnchorPane {

    @FXML
    private Label amount_label;

    @FXML
    private Rectangle income_rectangle;

    @FXML
    private Label title_label;

    @FXML
    private Group editIncomeStream_btn;

    @FXML
    private Group deleteIncomeStream_btn;

    private IncomeStreamData incomeStream;
    private Runnable updateIncomeStreamList;

    private Connection con;
    private PreparedStatement statement;

    public IncomeCardController(IncomeStreamData incomeStream) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/income-card.fxml"));
            loader.setRoot(this);
            loader.setController(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.incomeStream = incomeStream;
        income_rectangle.setFill(Paint.valueOf(incomeStream.getColor().getHex()));
        title_label.setText(incomeStream.getName());

    }

    public int getIncomeId() {
        return incomeStream.getId();
    }

    public IncomeStreamData getIncomeStream() {
        return incomeStream;
    }

    public void setUpdateRunnable(Runnable runnable) {
        updateIncomeStreamList = runnable;
    }

    // ----------------------------------------------------- Event handler
    public void editIncomeStream(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/income-stream-update-form.fxml"));
        Scene scene = new Scene(loader.load());
        ((IncomeStreamUpdateController)loader.getController()).setIncomeStream(incomeStream);

        Stage updateForm = new Stage(StageStyle.UTILITY);
        updateForm.setTitle("update income stream");
        updateForm.setScene(scene);
        this.getParent().getScene().getRoot().setDisable(true);
        updateForm.showAndWait();
        this.getParent().getScene().getRoot().setDisable(false);

        updateIncomeStreamList.run();
    }

    public void deleteIncomeStream(ActionEvent event) {
        Alert dialog = new Alert(AlertType.CONFIRMATION);
        dialog.setContentText("Are your sure you want to delete the selected record ?");
        dialog.getButtonTypes().setAll(ButtonType.YES, ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.YES) {
            String query = "DELETE FROM IncomeStream WHERE id=%d".formatted(incomeStream.getId());
            con = Database.getConnection();

            try {
                statement = con.prepareStatement(query);
                statement.execute();

                updateIncomeStreamList.run();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }


    public void showActionButtons(MouseEvent event) {
        editIncomeStream_btn.setVisible(true);
        deleteIncomeStream_btn.setVisible(true);
    }

    public void hideActionButtons(MouseEvent event) {
        editIncomeStream_btn.setVisible(false);
        deleteIncomeStream_btn.setVisible(false);
    }


}
