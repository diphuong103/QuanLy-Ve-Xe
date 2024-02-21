package com.example.phuongbusbooking.Controllers;

import com.example.phuongbusbooking.Models.Database;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField accoutname;

    @FXML
    private Button close;

    @FXML
    private Button createAccount;

    @FXML
    private Hyperlink login;

    @FXML
    private AnchorPane main_form;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField password1;

    @FXML
    private TextField phonenumber;
    @FXML
    private Label erpass;

    @FXML
    private Label erphone;

    @FXML
    private StackPane stackPane;
    private double x = 0;
    private double y = 0;
    MouseEvent event;

    public void close()
    {
        System.exit(0);
    }

    @FXML
    void createAccount(ActionEvent event) throws IOException {
        // Kiểm tra số điện thoại hợp lệ
        if (phonenumber.getText().length() != 10) {
            erphone.setText("Số điện thoại không hợp lệ!");
            return;
        }
        if (!password.getText().equals(password1.getText())) {
            erpass.setText("Mật khẩu không trùng khớp!");
            return;
        }

        if (!phonenumber.getText().matches("\\d{10}")) {
            erphone.setText("Số điện thoại chỉ được nhập số!");
            return;
        }

        // Kiểm tra các trường thông tin có trống không
        if (accoutname.getText().isEmpty() || password.getText().isEmpty() || phonenumber.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi!");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền đầy đủ thông tin!");
            alert.showAndWait();
            return;
        }

        try (Connection connection = Database.getConnection()) {
            String checkPhoneQuery = "SELECT * FROM users WHERE phonenumber = ?";
            try (PreparedStatement checkPhoneStatement = connection.prepareStatement(checkPhoneQuery)) {
                checkPhoneStatement.setString(1, phonenumber.getText());
                ResultSet phoneResultSet = checkPhoneStatement.executeQuery();
                if (phoneResultSet.next()) {
                    erphone.setText("Số điện thoại đã được sử dụng!");
                    return;
                }
            }

            String checkUsernameQuery = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement checkUsernameStatement = connection.prepareStatement(checkUsernameQuery)) {
                checkUsernameStatement.setString(1, accoutname.getText());
                ResultSet usernameResultSet = checkUsernameStatement.executeQuery();
                if (usernameResultSet.next()) {
                    erpass.setText("Tên tài khoản đã được sử dụng!");
                    return;
                }
            }

            // Nếu số điện thoại và tên tài khoản chưa được sử dụng, tiến hành thêm tài khoản mới
            String insertQuery = "INSERT INTO users (username, password, phonenumber) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, accoutname.getText());

                // Mã hóa mật khẩu bằng MD5 trước khi lưu
                String hashedPassword = hashMD5(password.getText());
                preparedStatement.setString(2, hashedPassword);

                preparedStatement.setString(3, phonenumber.getText());

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Thông báo");
                    alert.setHeaderText(null);
                    alert.setContentText("Tạo tài khoản thành công!");
                    alert.showAndWait();

                    // Chuyển qua màn hình đăng nhập sau khi tạo tài khoản thành công
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/Login.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    currentStage.setScene(scene);
                    currentStage.show();
                }
            }
        } catch (SQLException e) {
            // Xử lý ngoại lệ khi gặp lỗi trong quá trình lưu vào cơ sở dữ liệu
            e.printStackTrace();
            // Xử lý lỗi và thông báo cho người dùng nếu có lỗi xảy ra
        }
    }



    @FXML
    void loginaccount(ActionEvent event) throws IOException {
        Stage currentStage = (Stage)login.getScene().getWindow();
        currentStage.hide();

        Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
        Scene scene = new Scene(root);
        currentStage.setScene(scene);
        currentStage.show();

    }

@FXML
    private void onMouse_Dragged(javafx.scene.input.MouseEvent event) {
        Stage stage = (Stage) main_form.getScene().getWindow();

        stage.setY(event.getScreenY() - y);
        stage.setX(event.getScreenX() - x);

    }

@FXML
    private void onMouse_Pressed(javafx.scene.input.MouseEvent event) {
        x = event.getScreenX();
        y = event.getScreenY();
    }

    private String hashMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());

            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
