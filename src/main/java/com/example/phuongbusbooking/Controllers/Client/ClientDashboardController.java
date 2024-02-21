package com.example.phuongbusbooking.Controllers.Client;

import com.example.phuongbusbooking.Models.Database;
import com.example.phuongbusbooking.Models.busData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

public class ClientDashboardController implements Initializable {

    @FXML
    private Label name_User;

    @FXML
    private TextField C_F_fullname;
    @FXML
    private TextField C_F_phonenumber;
    @FXML
    private ComboBox<String> C_F_ticketNumber;
    @FXML
    private TextField C_F_FULLNAME;
    @FXML
    private TextField C_F_PHONENUMBER;
    @FXML
    private TextField C_F_Price;
    @FXML
    private TextField C_F_TICKETNUM;
    @FXML
    private TextField C_F_Location;
    @FXML
    private TextField C_F_IdBus;
    @FXML
    private Label C_F_IDCUS;
    @FXML
    private DatePicker C_F_Date;
    @FXML
    private Label C_F_TotalTicket;

    @FXML
    private TextField search_location;

    @FXML
    private TableView<busData> table_search_bus;
    @FXML
    private TableColumn<busData, Date> C_Date;

    @FXML
    private TableColumn<busData, String> C_Location;

    @FXML
    private TableColumn<busData, Double> C_Price;

    @FXML
    private TableColumn<busData, Integer> C_busId;


    @FXML
    private DatePicker search_date;

    @FXML
    private AnchorPane c_booking_dashboard;

    @FXML
    private Button c_bookingdashboard_bnt;

    @FXML
    private AnchorPane c_information_dasdboard;

    @FXML
    private Button c_information_dashboard_bnt;
    @FXML
    private Button Booking;
    @FXML
    private Button SearchBus;

    @FXML
    private Button close;

    @FXML
    private Button logout;

    @FXML
    private Button minimize;
    @FXML
    private AnchorPane dashboard_form;
    private static Connection connection = null;
    private static PreparedStatement perspectiveCamera = null;
    private static ResultSet resultSet = null;

    @FXML
    void close(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void logout(ActionEvent event) {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Thông báo xác nhận");
            alert.setHeaderText(null);
            alert.setContentText("Bạn có chắc chắn muốn đăng xuất?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(getClass().getResource("/FXML/Login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } else return;

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage = (Stage) dashboard_form.getScene().getWindow();
        stage.setIconified(true);
    }


    @FXML
    void switchForm(ActionEvent event) {
        if (event.getSource() == c_bookingdashboard_bnt) {
            c_booking_dashboard.setVisible(true);
            c_information_dasdboard.setVisible(false);

            c_bookingdashboard_bnt.setStyle("-fx-background-color: linear-gradient(to bottom right, #4ca3e0,#d14781)");
            c_information_dashboard_bnt.setStyle("-fx-background-color: transparent");

            C_B_View();
            bus_t_Searchdate();
            bus_t_search();


        } else if (event.getSource() == c_information_dashboard_bnt) {
            c_booking_dashboard.setVisible(false);
            c_information_dasdboard.setVisible(true);

            c_bookingdashboard_bnt.setStyle("-fx-background-color: transparent");
            c_information_dashboard_bnt.setStyle("-fx-background-color: linear-gradient(to bottom right, #4ca3e0,#d14781)");

            C_ticketNum();
        }
    }




    public ObservableList<busData> C_B_View() {
        ObservableList<busData> busDataList = FXCollections.observableArrayList();

        C_busId.setCellValueFactory(new PropertyValueFactory<>("busId"));
        C_Location.setCellValueFactory(new PropertyValueFactory<>("location"));
        C_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        C_Date.setCellValueFactory(new PropertyValueFactory<>("day_go"));

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:D:/Sqlite/MyDatabase/QLVXBUS.db");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Bus")) {

            while (resultSet.next()) {
                busData busData = new busData();
                busData.setBusId(resultSet.getInt("busId"));
                busData.setLocation(resultSet.getString("location"));
                busData.setPrice(Double.parseDouble(resultSet.getString("price")));
                busData.setDay_go(Date.valueOf(resultSet.getString("day_go")));
                busDataList.add(busData);
            }

            table_search_bus.setItems(busDataList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return busDataList; // trả về danh sách
    }

//    public void Search_Bus(ActionEvent event) {
//        table_search_bus.setItems(C_B_View());
//        bus_t_search();
//        bus_t_Searchdate();
//    }

    public void bus_t_search() {
        FilteredList<busData> filteredList = new FilteredList<>(table_search_bus.getItems(), e -> true);
        search_location.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(bus -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Hiển thị tất cả nếu ô tìm kiếm trống
                }

                String searchText = newValue.toLowerCase();

                // Điều kiện tìm kiếm cho mỗi trường dữ liệu (ví dụ: busId, location, status, price)
                if (String.valueOf(bus.getBusId()).toLowerCase().contains(searchText)
                        || bus.getLocation().toLowerCase().contains(searchText)
                        || String.valueOf(bus.getPrice()).toLowerCase().contains(searchText)) {
                    return true; // Trả về true nếu dữ liệu khớp với từ khóa tìm kiếm
                }
                return false; // Trả về false nếu không khớp
            });
        });

        // Đặt lại dữ liệu hiển thị trong TableView để hiển thị kết quả tìm kiếm
        table_search_bus.setItems(filteredList);
    }

    public void bus_t_Searchdate() {
        FilteredList<busData> filteredList = new FilteredList<>(table_search_bus.getItems(), e -> true);

        search_date.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(bus -> {
                if (newValue == null) {
                    return true; // Hiển thị tất cả nếu không có ngày được chọn
                }

                // Chỉ lọc dữ liệu theo ngày được chọn
                return bus.getDay_go().equals(Date.valueOf(newValue));
            });
        });

        // Đặt lại dữ liệu hiển thị trong TableView để hiển thị kết quả tìm kiếm
        table_search_bus.setItems(filteredList);
    }

    public void C_BookingTicket(ActionEvent event) {
        if (event.getSource() == Booking) {
            c_booking_dashboard.setVisible(false);
            c_information_dasdboard.setVisible(true);

            c_bookingdashboard_bnt.setStyle("-fx-background-color: transparent");
            c_information_dashboard_bnt.setStyle("-fx-background-color: linear-gradient(to bottom right, #4ca3e0,#d14781)");

            C_ticketNum();
        }
    }

    public void avaiableCSelectBusData() { // chọn thông tin trong bảng
        try {
            busData selectedBus = table_search_bus.getSelectionModel().getSelectedItem();

            if (selectedBus != null) {
                C_F_IdBus.setText(String.valueOf(selectedBus.getBusId()));
                C_F_Location.setText(selectedBus.getLocation());
                C_F_Price.setText(String.valueOf(selectedBus.getPrice()));
                C_F_Date.setValue(selectedBus.getDay_go().toLocalDate());

            } else {
                // Hiển thị thông báo hoặc xử lý khi không có hàng nào được chọn
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Lỗi");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng chọn một hàng để xem chi tiết.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý các ngoại lệ xảy ra khi truy cập dữ liệu từ đối tượng selectedBus
            // Đồng thời bạn cũng có thể hiển thị thông báo hoặc xử lý theo cách khác tùy vào ngữ cảnh cụ thể
        }
    }

    public void C_ticketNum() {
        try {
            List<String> listTicket = new ArrayList<>();
            for (int i = 1; i <= 40; i++) {
                listTicket.add(String.valueOf(i));
            }

            ObservableList<String> listT = FXCollections.observableArrayList(listTicket);
            C_F_ticketNumber.setItems(listT);
        } catch (Exception e) {
            e.printStackTrace(); // In ra thông tin về lỗi nếu có
            // Hoặc xử lý lỗi ở đây theo nhu cầu của bạn
        }
    }

    public void enter_information() {
        Random rd = new Random();
        int random = rd.nextInt(10000);

        String fullName = C_F_fullname.getText();
        String phoneNumber = C_F_phonenumber.getText();

        String ticket = (String) C_F_ticketNumber.getSelectionModel().getSelectedItem();
        String busid = (String) C_F_IdBus.getText();
        String location = (String) C_F_Location.getText();

        Alert alert;

        if (fullName.isEmpty() || phoneNumber.isEmpty() || ticket.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo lỗi!");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền vào hết các ô trống");
            alert.showAndWait();
        } else {
            try {
                String statusQuery = "SELECT status, price FROM Bus WHERE busId = ? AND location = ?";
                double totalPriceValue = 0.0;
                String busStatus = "";

                connection = Database.getConnection();
                PreparedStatement statusStatement = connection.prepareStatement(statusQuery);
                statusStatement.setString(1, busid);
                statusStatement.setString(2, location);

                ResultSet statusResult = statusStatement.executeQuery();

                if (statusResult.next()) {
                    double priceData = statusResult.getDouble("price");
                    totalPriceValue = Double.parseDouble(ticket) * priceData;

                    // Set the total price value in the moneyTotal field
                    C_F_TotalTicket.setText(String.valueOf(totalPriceValue));

                    // Set values for other fields
                    C_F_FULLNAME.setText(fullName);
                    C_F_PHONENUMBER.setText(phoneNumber);
                    C_F_TICKETNUM.setText(ticket);
                    C_F_IDCUS.setText(String.valueOf(random));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
                // Handle SQL exception as required
            } finally {
                // Close connections, statements, etc. if needed
            }
        }
    }

    public void C_complete() {
        String idCustomer = C_F_IDCUS.getText();
        String fullName = C_F_FULLNAME.getText();
        String ticket = C_F_TICKETNUM.getText();
        String phoneNumber = C_F_PHONENUMBER.getText();
        String busId = C_F_IdBus.getText();
        String location = C_F_Location.getText();
        String day_go = String.valueOf(C_F_Date.getValue());
        String income = C_F_TotalTicket.getText();

        try {
            connection = Database.getConnection();

            if (idCustomer.isEmpty() || fullName.isEmpty() || ticket.isEmpty() || phoneNumber.isEmpty()
                    || busId.isEmpty() || location.isEmpty() || day_go.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Thông báo lỗi!");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng điền hết thông tin");
                alert.showAndWait();
            } else {
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Thông báo xác nhận!");
                confirmAlert.setHeaderText(null);
                confirmAlert.setContentText("Bạn có chắc chắn muốn xác nhận");

                Optional<ButtonType> result = confirmAlert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    String fetchCurrentSttQuery = "SELECT MAX(Stt) AS max_stt FROM Customers";
                    PreparedStatement fetchStatement = connection.prepareStatement(fetchCurrentSttQuery);
                    ResultSet resultSet = fetchStatement.executeQuery();
                    int currentStt = 0;

                    if (resultSet.next()) {
                        currentStt = resultSet.getInt("max_stt");
                    }

                    // Tăng giá trị hiện tại của Stt lên 1 để sử dụng cho bản ghi mới
                    int newStt = currentStt + 1;

                    String payData = "INSERT INTO Customers(Stt, Id, ticket, fullName, phoneNumber, busID, location, day_go, income) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(payData);

                    preparedStatement.setInt(1, newStt);
                    preparedStatement.setString(2, idCustomer);
                    preparedStatement.setString(3, ticket);
                    preparedStatement.setString(4, fullName);
                    preparedStatement.setString(5, phoneNumber);
                    preparedStatement.setString(6, busId);
                    preparedStatement.setString(7, location);
                    preparedStatement.setString(8, day_go);
                    preparedStatement.setString(9, income);

                    preparedStatement.executeUpdate();
                    C_B_View();

                    // Display success notification
                    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                    successAlert.setTitle("Thông báo thành công");
                    successAlert.setHeaderText(null);
                    successAlert.setContentText("Đặt vé thành công!");
                    successAlert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void C_receipt(ActionEvent event) {
        try {
            // Lấy giá trị từ các đối tượng TextField và Label
            String idCustomer = C_F_IDCUS.getText();
            String fullName = C_F_FULLNAME.getText();
            String phoneNumber = C_F_PHONENUMBER.getText();
            String date = String.valueOf(C_F_Date.getValue());
            String busId = C_F_IdBus.getText();
            String location = C_F_Location.getText();
            String ticketNumber = C_F_TICKETNUM.getText();
            String moneyTotal = C_F_TotalTicket.getText();

            // Kiểm tra xem các giá trị có tồn tại không
            if (idCustomer.isEmpty() || fullName.isEmpty() || phoneNumber.isEmpty() || date.isEmpty() ||
                    busId.isEmpty() || location.isEmpty() || ticketNumber.isEmpty() || moneyTotal.isEmpty()) {
                showAlert("Không thể lưu thông tin do một số giá trị không tồn tại.", Alert.AlertType.ERROR);
                return;
            }

            // Tạo chuỗi nội dung
            String content = "Thông tin đặt vé:\n" +
                    "Mã khách hàng: " + idCustomer + "\n" +
                    "Họ và tên: " + fullName + "\n" +
                    "Số điện thoại: " + phoneNumber + "\n" +
                    "Ngày đi: " + date + "\n" +
                    "Mã xe: " + busId + "\n" +
                    "Vị trí: " + location + "\n" +
                    "Số vé: " + ticketNumber + "\n" +
                    "Tổng số tiền: " + moneyTotal + "\n"+
                    "---------------------------------------------------------------------" + "\n" +
                    "-" +                 "Có thể thanh toán qua:" +                    "-" + "\n" +
                    "-" +                                                               "-" + "\n" +
                    "-" +                    "MB: PHUONGBUSBOOKING"+                    "-" + "\n" +
                    "-" +                "Số tài khoản: 533464748664" +                 "-" + "\n" +
                    "-" +                                                               "-" + "\n" +
                    "-" +                                                               "-" + "\n" +
                    "-" +  "Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi <3. " +         "-" + "\n" +
                    "-" +  "Sự hài lòng của bạn là mối quan tâm số một của chúng tôi!"+ "-" + "\n" +
                    "-" +                                                               "-" + "\n" +
                    "-" +                                                               "-" + "\n"+
                    "---------------------------------------------------------------------";

            String fileName = "D:/" + fullName + "_" + location + "_" + date + ".txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write(content);
                showAlert("Đã lưu thông tin biên lai vào tệp"+ "("+ fileName + ") trong máy của bạn", Alert.AlertType.INFORMATION);
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Lỗi khi lưu thông tin vào tệp.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        C_B_View();
        bus_t_Searchdate();
        bus_t_search();
        C_ticketNum();

    }
    private double x = 0;
    private double y = 0;


    public void onMouse_Dragged(MouseEvent mouseEvent) {
        Stage stage = (Stage) dashboard_form.getScene().getWindow();

        stage.setY(mouseEvent.getScreenY() - y);
        stage.setX(mouseEvent.getScreenX() - x);
    }

    public void onMouse_Pressed(MouseEvent mouseEvent) {
        x = mouseEvent.getScreenX();
        y = mouseEvent.getScreenY();
    }
}



