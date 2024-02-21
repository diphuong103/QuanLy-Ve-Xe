package com.example.phuongbusbooking.Controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Button close;

    @FXML
    private Button login;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private Hyperlink register;

    @FXML
    private TextField username;

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void createaccount(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) register.getScene().getWindow();
        currentStage.hide();

        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Register.fxml"));
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.show();
    }

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public void login() throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        Alert alert;

        try {
            connection = com.example.phuongbusbooking.Models.Database.getConnection();

            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi!");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng điền vào các ô thông tin còn trống!");
                alert.showAndWait();
            } else {
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username.getText());
                preparedStatement.setString(2, password.getText());
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String username = resultSet.getString("username");
                    // Lấy tên người dùng từ cột 'username' trong kết quả truy vấn

                    if (username.equals("admin")) { // Kiểm tra nếu tên người dùng là 'admin'
                        // Hiển thị thông báo thành công
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Đăng nhập thành công!");
                        alert.setHeaderText(null);
                        alert.setContentText("Chào mừng Admin!");
                        alert.showAndWait();

                        Stage currentStage = (Stage) login.getScene().getWindow();
                        currentStage.hide();

                        // Chuyển đến AdminDashboard.fxml
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Admin/AdminDashboard.fxml")));
                        Scene scene = new Scene(root);
                        currentStage.setScene(scene);
                        currentStage.show();
                    } else {
                        // Hiển thị thông báo cho người dùng khác (không phải admin)
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Đăng nhập thành công!");
                        alert.setHeaderText(null);
                        alert.setContentText("Chào mừng " + username + "!");
                        alert.showAndWait();

                        Stage currentStage = (Stage) login.getScene().getWindow();
                        currentStage.hide();

                        // Chuyển đến ClientDashboard.fxml hoặc trang nào đó cho người dùng thường
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/FXML/Client/ClientDashboard.fxml")));
                        Scene scene = new Scene(root);
                        currentStage.setScene(scene);
                        currentStage.show();
                    }
                } else {
                    // Hiển thị thông báo lỗi nếu không tìm thấy người dùng trong cơ sở dữ liệu
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi!");
                    alert.setHeaderText(null);
                    alert.setContentText("Tên người dùng hoặc mật khẩu sai!");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}

