package com.example.phuongbusbooking.Controllers.Admin;

import com.example.phuongbusbooking.Models.busData;
import com.example.phuongbusbooking.Models.customersData;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
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


public class AdminDashboardController implements Initializable {
    @FXML
    private AreaChart<?, ?> dashboard_chart;
    @FXML
    private ComboBox<String> bookingTicket_busId;

    @FXML
    private DatePicker bookingTicket_date;

    @FXML
    private AnchorPane bookingTicket_form;

    @FXML
    private TextField bookingTicket_fullName;


    @FXML
    private ComboBox<String> bookingTicket_location;

    @FXML
    private TextField bookingTicket_phoneNumber;

    @FXML
    private Button bookingTicket_reset;

    @FXML
    private TextField bookingTicket_f_busId;

    @FXML
    private TextField bookingTicket_f_date;

    @FXML
    private TextField bookingTicket_f_fullName;

    @FXML
    private TextField bookingTicket_f_location;

    @FXML
    private Label bookingTicket_f_moneyTotal;

    @FXML
    private Button bookingTicket_f_pay;

    @FXML
    private TextField bookingTicket_f_phoneNumber;

    @FXML
    private Button bookingTicket_f_receipt;

    @FXML
    private TextField bookingTicket_f_ticketNumber;

    @FXML
    private Button bookingTicket_save;

    @FXML
    private ComboBox<String> bookingTicket_ticketNumber;

    @FXML
    private Button bus_add;

    @FXML
    private TextField bus_busId;

    @FXML
    private DatePicker bus_date;

    @FXML
    private Button bus_delete;

    @FXML
    private AnchorPane bus_form;

    @FXML
    private TextField bus_location;

    @FXML
    private TextField bus_price;

    @FXML
    private Button bus_reset;

    @FXML
    private TextField bus_search;

    @FXML
    private ComboBox<String> bus_status;
    @FXML
    private TableView<busData> bus_tableView;

    @FXML
    private TableColumn<busData, Integer> bus_t_busId;

    @FXML
    private TableColumn<busData, Date> bus_t_date;

    @FXML
    private TableColumn<busData, String> bus_t_location;

    @FXML
    private TableColumn<busData, Double> bus_t_price;

    @FXML
    private TableColumn<busData, String> bus_t_status;

    @FXML
    private Button bus_update;

    @FXML
    private TableColumn<customersData, String> customers_busid;

    @FXML
    private TableColumn<customersData, Date> customers_date;

    @FXML
    private AnchorPane customers_form;

    @FXML
    private TableColumn<customersData, String> customers_fullName;

    @FXML
    private TableColumn<customersData, String> customers_location;
    @FXML
    private TableColumn<customersData, Integer> customers_stt;
    @FXML
    private TableColumn<customersData, String> customers_phoneNumber;
    @FXML
    private TableColumn<customersData, Integer> customers_busId;
    @FXML
    private TableColumn<customersData, Integer> customers_Id;


    @FXML
    private TextField customers_search;


    @FXML
    private TableView<customersData> customers_table;

    @FXML
    private TableColumn<customersData, Integer> customers_ticketNum;

    @FXML
    private Label dashboard_busNum;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Label dashboard_todayIncome;

    @FXML
    private Label dashboard_totalIncome;
    @FXML
    private Button customers_bnt;
    @FXML
    private Button dashboard_bnt;
    @FXML
    private Button bus_bnt;
    @FXML
    private Button bookingTicket_bnt;
    @FXML
    private Button minimize;
    @FXML
    private Button close;
    @FXML
    private Label bookingticket_idCustomer;
    @FXML
    private DatePicker editDate;

    @FXML
    private TextField editFullname;

    @FXML
    private TextField editIdbus;

    @FXML
    private TextField editLocation;

    @FXML
    private TextField editPhonenumber;

    @FXML
    private TextField editTicket;

    @FXML
    private Button saveCustomers;
    @FXML
    private Button Edit_Cus;
    @FXML
    private Button Delete_Cus;

    @FXML
    private AnchorPane admin_dashboard;

    @FXML
    private Button receipt;

    @FXML
    private Label R_ID;

    @FXML
    private Label R_IdBus;

    @FXML
    private Label R_Location;

    @FXML
    private Label R_date;

    @FXML
    private Label R_fullName;

    @FXML
    private Label R_phoneNumber;

    @FXML
    private Label R_price;

    @FXML
    private Label R_ticketNum;


    private double x = 0;
    private double y = 0;

    public void onMouse_Pressed(MouseEvent mouseEvent) {
        x = mouseEvent.getScreenX();
        y = mouseEvent.getScreenY();
    }

    public void onMouse_Dragged(MouseEvent mouseEvent) {
        Stage stage = (Stage) dashboard_form.getScene().getWindow();

        stage.setY(mouseEvent.getScreenY() - y);
        stage.setX(mouseEvent.getScreenX() - x);
    }


    public void close() {
        System.exit(0);
    }

    @FXML
    private Button logout;
    private static Connection connection = null;
    private static PreparedStatement perspectiveCamera = null;
    private static ResultSet resultSet = null;
    private Statement statement = null;

    public AdminDashboardController() {
    }

    public void minimize() {
        Stage stage = (Stage) dashboard_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_bnt) {
            dashboard_form.setVisible(true);
            bus_form.setVisible(false);
            bookingTicket_form.setVisible(false);
            customers_form.setVisible(false);

            dashboard_bnt.setStyle("-fx-background-color: linear-gradient(to bottom right, #4ca3e0,#d14781)");
            bus_bnt.setStyle("-fx-background-color: transparent");
            bookingTicket_bnt.setStyle("-fx-background-color: transparent");
            customers_bnt.setStyle("-fx-background-color: transparent");

            dashboardBusAv();
            dashboardTodayIncome();
            dashboardTotalIncome();
            dashboardChart();

        } else if (event.getSource() == bus_bnt) {
            dashboard_form.setVisible(false);
            bus_form.setVisible(true);
            bookingTicket_form.setVisible(false);
            customers_form.setVisible(false);

            dashboard_bnt.setStyle("-fx-background-color: transparent");
            bus_bnt.setStyle("-fx-background-color: linear-gradient(to bottom right, #4ca3e0,#d14781)");
            bookingTicket_bnt.setStyle("-fx-background-color: transparent");
            customers_bnt.setStyle("-fx-background-color: transparent");

            tableB_View();
            bus_t_search();

        } else if (event.getSource() == bookingTicket_bnt) {
            dashboard_form.setVisible(false);
            bus_form.setVisible(false);
            bookingTicket_form.setVisible(true);
            customers_form.setVisible(false);

            dashboard_bnt.setStyle("-fx-background-color: transparent");
            bus_bnt.setStyle("-fx-background-color: transparent");
            bookingTicket_bnt.setStyle("-fx-background-color: linear-gradient(to bottom right, #4ca3e0,#d14781)");
            customers_bnt.setStyle("-fx-background-color: transparent");

            busIDList();
            busLocationList();
            ticketNum();


        } else if (event.getSource() == customers_bnt) {
            dashboard_form.setVisible(false);
            bus_form.setVisible(false);
            bookingTicket_form.setVisible(false);
            customers_form.setVisible(true);

            dashboard_bnt.setStyle("-fx-background-color: transparent");
            bus_bnt.setStyle("-fx-background-color: transparent");
            bookingTicket_bnt.setStyle("-fx-background-color: transparent");
            customers_bnt.setStyle("-fx-background-color: linear-gradient(to bottom right, #4ca3e0,#d14781)");

            tableC_View();
            cus_t_search();
        }
    }

    public void logout(ActionEvent event) {
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

    //-----------------------bus-form---------------------------------------------------
    private String[] statusList = {"Có sẵn", "Không có sẵn"};

    public void comboBoxStatus() {
        List<String> listS = new ArrayList<>();

        for (String data : statusList) {
            listS.add(data);
        }
        ObservableList listStatus = FXCollections.observableArrayList(listS);
        bus_status.setItems(listStatus);
    }

    public void avaiableBSelectBusData() { //// chọn thông tin trong bảng
        try {
            busData selectedBus = bus_tableView.getSelectionModel().getSelectedItem();

            if (selectedBus != null) {
                bus_busId.setText(String.valueOf(selectedBus.getBusId()));
                bus_location.setText(selectedBus.getLocation());
                bus_status.setValue(selectedBus.getStatus());
                bus_price.setText(String.valueOf(selectedBus.getPrice()));
                // Điền giá trị của datePicker, ví dụ:
                bus_date.setValue(selectedBus.getDay_go().toLocalDate());
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

    public ObservableList<busData> tableB_View() {
        ObservableList<com.example.phuongbusbooking.Models.busData> busDataList = FXCollections.observableArrayList();

        bus_t_busId.setCellValueFactory(new PropertyValueFactory<>("busId"));
        bus_t_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        bus_t_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        bus_t_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        bus_t_date.setCellValueFactory(new PropertyValueFactory<>("day_go"));

        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:D:/Sqlite/MyDatabase/QLVXBUS.db");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Bus")) {

            while (resultSet.next()) {
                com.example.phuongbusbooking.Models.busData busData = new com.example.phuongbusbooking.Models.busData();
                busData.setBusId(resultSet.getInt("busId"));
                busData.setLocation(resultSet.getString("location"));
                busData.setStatus(resultSet.getString("status"));
                busData.setPrice(Double.parseDouble(resultSet.getString("price")));
                busData.setDay_go(Date.valueOf(resultSet.getString("day_go")));
                busDataList.add(busData);
            }

            bus_tableView.setItems(busDataList);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return busDataList;
    }

//    public ObservableList<com.example.phuongbusbooking.Models.busData> getBusData() {
//        return tableB_View();
    //   }


    public void BusAdd() {
        String addData = "INSERT INTO Bus (busId, location, status, price, day_go) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = com.example.phuongbusbooking.Models.Database.getConnection();
             PreparedStatement checkStatement = connection.prepareStatement("SELECT busId FROM Bus WHERE busId = ?");
             PreparedStatement addStatement = connection.prepareStatement(addData)) {

            String location = bus_location.getText();

            if (bus_busId.getText().isEmpty() || location.isEmpty() || bus_status.getSelectionModel().getSelectedItem() == null || bus_price.getText().isEmpty() || bus_date.getValue() == null) {
                showAlert("Vui lòng điền tất cả các trường", Alert.AlertType.ERROR);
                return;
            }

//            if (!bus_location.getText().matches("[a-zA-Z]+")) {
//                Alert alert = new Alert(Alert.AlertType.ERROR);
//                alert.setTitle("Lỗi");
//                alert.setHeaderText(null);
//                alert.setContentText("Vị trí chỉ được nhập bằng chữ!");
//                alert.showAndWait();
//                return;
//            }

            // Kiểm tra giá chỉ chứa số
            if (!bus_price.getText().matches("\\d+")) {
                showAlert("Giá chỉ được nhập bằng số!", Alert.AlertType.ERROR);
                return;
            }

            // Kiểm tra Bus ID đã tồn tại
            checkStatement.setString(1, bus_busId.getText());
            ResultSet resultSet = checkStatement.executeQuery();
            if (resultSet.next()) {
                showAlert("Bus ID: " + bus_busId.getText() + " đã tồn tại!", Alert.AlertType.ERROR);
            } else {
                // Thêm dữ liệu mới
                addStatement.setString(1, bus_busId.getText());
                addStatement.setString(2, location);
                addStatement.setString(3, (String) bus_status.getSelectionModel().getSelectedItem());
                addStatement.setString(4, bus_price.getText());
                addStatement.setString(5, String.valueOf(bus_date.getValue()));

                int rowsAffected = addStatement.executeUpdate();

                if (rowsAffected > 0) {
                    // Hiển thị thông báo khi thêm dữ liệu thành công
                    showAlert("Thêm thành công!", Alert.AlertType.INFORMATION);

                    // Cập nhật TableView sau khi thêm dữ liệu mới
                    BusReset();
                    tableB_View();
                    tableC_View();
                }
            }
        } catch (SQLException e) {
            // Xử lý lỗi SQLException
            showAlert("Lỗi SQL: " + e.getMessage(), Alert.AlertType.ERROR);
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


    public void BusReset() {
        try {
            bus_busId.setText("");
            bus_location.setText("");
            bus_status.getSelectionModel().clearSelection();
            bus_price.setText("");
            bus_t_date.setText("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML

    private void BusUpdate(ActionEvent event) {
        try {
            int busId = Integer.parseInt(bus_busId.getText());
            String location = bus_location.getText();
            String status = bus_status.getValue();
            double price = Double.parseDouble(bus_price.getText());
            LocalDate localDate = bus_date.getValue();
            Date date = Date.valueOf(localDate);

            busData selectedBus = bus_tableView.getSelectionModel().getSelectedItem();

            if (selectedBus != null) {

//                if (!location.matches("[a-zA-Z]+")) {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Lỗi");
//                    alert.setHeaderText(null);
//                    alert.setContentText("Vị trí chỉ được nhập bằng chữ!");
//                    alert.showAndWait();
//                    return;
//                }

                // Kiểm tra giá chỉ chứa số
                if (!Double.toString(price).matches("\\d+")) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Lỗi");
                    alert.setHeaderText(null);
                    alert.setContentText("Giá chỉ được nhập bằng số!");
                    alert.showAndWait();
                    return;
                }

                selectedBus.setBusId(busId);
                selectedBus.setLocation(location);
                selectedBus.setStatus(status);
                selectedBus.setPrice(price);
                selectedBus.setDay_go(date);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Cập nhật thành công!");
                alert.showAndWait();

                bus_tableView.refresh();
            } else {
                // Xử lý khi không có hàng nào được chọn trong TableView
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Cảnh báo");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng chọn một hàng từ bảng trước khi cập nhật!");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            // Xử lý khi dữ liệu nhập vào không hợp lệ (ví dụ: không phải số)
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Lỗi");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng nhập dữ liệu hợp lệ!");
            alert.showAndWait();
        }
    }


    @FXML
    private void BusDelete(ActionEvent event) {
        String sql = "DELETE FROM Bus WHERE busId = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        busData selectedBus = bus_tableView.getSelectionModel().getSelectedItem();

        try {
            if (selectedBus != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc chắn muốn xóa không?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    connection = com.example.phuongbusbooking.Models.Database.getConnection();
                    statement = connection.prepareStatement(sql);
                    statement.setInt(1, selectedBus.getBusId());
                    int rowsAffected = statement.executeUpdate();
                    if (rowsAffected > 0) {
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Thông báo Xóa thành công");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("Vé đã được xóa thành cồn");
                        successAlert.showAndWait();

                        tableB_View(); // Refresh the table after deletion
                        tableC_View();
                    }
                }
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Lỗi");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Vui lòng chọn vé cần xóa");
                errorAlert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void bus_t_search() {
        FilteredList<com.example.phuongbusbooking.Models.busData> filteredList = new FilteredList<>(bus_tableView.getItems(), e -> true);

        bus_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(bus -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchText = newValue.toLowerCase();


                if (String.valueOf(bus.getBusId()).toLowerCase().contains(searchText)
                        || bus.getLocation().toLowerCase().contains(searchText)
                        || bus.getStatus().toLowerCase().contains(searchText)
                        || String.valueOf(bus.getPrice()).toLowerCase().contains(searchText)) {
                    return true;
                }
                return false;
            });
        });


        bus_tableView.setItems(filteredList);
    }


// ------------------BookingTicketForm------------------------------------------------------------------------------------------------------//

    public void busIDList() {
        String busD = "SELECT DISTINCT busId FROM Bus";

        try {
            connection = com.example.phuongbusbooking.Models.Database.getConnection();

            if (connection != null) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(busD);
                     ResultSet resultSet = preparedStatement.executeQuery()) {

                    ObservableList<String> listB = FXCollections.observableArrayList();

                    while (resultSet.next()) {
                        listB.add(resultSet.getString("busId"));
                    }
                    bookingTicket_busId.setItems(listB);

                } catch (SQLException sqlException) {
                    // Xử lý lỗi SQL cụ thể
                    sqlException.printStackTrace();
                    System.out.println("Thông báo ngoại lệ SQL: " + sqlException.getMessage());
                } finally {
                    try {
                        if (connection != null && !connection.isClosed()) {
                            connection.close();
                        }
                    } catch (SQLException closeException) {
                        // Xử lý lỗi đóng kết nối
                        closeException.printStackTrace();
                        System.out.println("Kết nối Đóng thông báo ngoại lệ: " + closeException.getMessage());
                    }
                }
            } else {
                System.out.println("Không thể thiết lập kết nối cơ sở dữ liệu.");
            }

        } catch (Exception e) {
            // Xử lý ngoại lệ chung
            e.printStackTrace();
            System.out.println("Thông báo ngoại lệ: " + e.getMessage());
        }
    }

    public void busLocationList() {
        String locationL = "SELECT DISTINCT location FROM Bus";

        connection = com.example.phuongbusbooking.Models.Database.getConnection();

        try {
            perspectiveCamera = connection.prepareStatement(locationL);
            resultSet = perspectiveCamera.executeQuery();

            ObservableList listL = FXCollections.observableArrayList();

            while (resultSet.next()) {
                listL.add(resultSet.getString("location"));
            }
            bookingTicket_location.setItems(listL);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void ticketNum() {
        try {
            List<String> listTicket = new ArrayList<>();
            for (int i = 1; i <= 40; i++) {
                listTicket.add(String.valueOf(i));
            }

            ObservableList<String> listT = FXCollections.observableArrayList(listTicket);
            bookingTicket_ticketNumber.setItems(listT);
        } catch (Exception e) {
            e.printStackTrace(); // In ra thông tin về lỗi nếu có
            // Hoặc xử lý lỗi ở đây theo nhu cầu của bạn
        }
    }


    public void bookingTicketSelect() {
        Random rd = new Random();
        int random = rd.nextInt(10000);

        String fullName = bookingTicket_fullName.getText();
        String phoneNumber = bookingTicket_phoneNumber.getText();
        String day_go = String.valueOf(bookingTicket_date.getValue());

        String busId = (String) bookingTicket_busId.getSelectionModel().getSelectedItem();
        String location = (String) bookingTicket_location.getSelectionModel().getSelectedItem();
        String ticket = (String) bookingTicket_ticketNumber.getSelectionModel().getSelectedItem();

        Alert alert;

//        if (!(bookingTicket_fullName.getText().matches("^[a-zA-Z ]+$"))) {
//            showAlert("Tên chỉ được nhập bằng chữ!", Alert.AlertType.ERROR);
//            return;
//        }


        if (!phoneNumber.matches("\\d{10}")) {
            showAlert("Số điện thoại phải có đúng 10 số và chỉ được nhập bằng số!", Alert.AlertType.ERROR);
            return;
        }

        if (fullName.isEmpty() || phoneNumber.isEmpty() || day_go.isEmpty() || location.isEmpty() || ticket.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Thông báo lỗi!");
            alert.setHeaderText(null);
            alert.setContentText("Vui lòng điền vào hết các ô trống");
            alert.showAndWait();
        } else {
            String statusQuery = "SELECT status, price FROM Bus WHERE busId = ? AND location = ?";
            double totalPriceValue = 0.0;
            String busStatus = "";

            try {
                connection = com.example.phuongbusbooking.Models.Database.getConnection();
                PreparedStatement statusStatement = connection.prepareStatement(statusQuery);
                statusStatement.setString(1, busId);
                statusStatement.setString(2, location);

                ResultSet statusResult = statusStatement.executeQuery();

                if (statusResult.next()) {
                    busStatus = statusResult.getString("status");

                    if (busStatus.equals("Không có sẵn")) {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Thông báo lỗi!");
                        alert.setHeaderText(null);
                        alert.setContentText("Xe không có sẵn tại vị trí này");
                        alert.showAndWait();
                        return;
                    }

                    double priceData = statusResult.getDouble("price");
                    totalPriceValue = Double.parseDouble(ticket) * priceData;

                    // Set the total price value in the moneyTotal field
                    bookingTicket_f_moneyTotal.setText(String.valueOf(totalPriceValue));

                    // Set values for other fields
                    bookingTicket_f_fullName.setText(fullName);
                    bookingTicket_f_phoneNumber.setText(phoneNumber);
                    bookingTicket_f_date.setText(day_go);
                    bookingTicket_f_busId.setText(busId);
                    bookingTicket_f_location.setText(location);
                    bookingTicket_f_ticketNumber.setText(ticket);
                    bookingticket_idCustomer.setText(String.valueOf(random));
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Thông báo lỗi!");
                    alert.setHeaderText(null);
                    alert.setContentText("Không tìm thấy thông tin cho mã xe và vị trí này");
                    alert.showAndWait();
                    return;
                }
            } catch (Exception e) {
                // Handle exceptions if any
            }
        }

        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo thông tin!");
        alert.setHeaderText(null);
        alert.setContentText("Đã chọn thành công");
        alert.showAndWait();

        bookingTicketreset();
    }


    public void bookingTicketreset() {
        bookingTicket_fullName.setText("");
        bookingTicket_phoneNumber.setText("");
        bookingTicket_date.setValue(null);

    }


    public void bookingTicketPay() {
        String idCustomer = bookingticket_idCustomer.getText();
        String fullName = bookingTicket_f_fullName.getText();
        String ticket = bookingTicket_f_ticketNumber.getText();
        String phoneNumber = bookingTicket_f_phoneNumber.getText();
        String busId = bookingTicket_f_busId.getText();
        String location = bookingTicket_f_location.getText();
        String day_go = bookingTicket_f_date.getText();
        String income = bookingTicket_f_moneyTotal.getText();

        try {
            connection = com.example.phuongbusbooking.Models.Database.getConnection();

            if (idCustomer.isEmpty() || fullName.isEmpty() || ticket.isEmpty() || phoneNumber.isEmpty()
                    || busId.isEmpty() || location.isEmpty() || day_go.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Thông báo lỗi!");
                alert.setHeaderText(null);
                alert.setContentText("Vui lòng điền hết thông tin");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Thông báo xác nhận!");
                alert.setHeaderText(null);
                alert.setContentText("Bạn có chắc chắn muốn xác nhận");
                alert.showAndWait();

                String fetchCurrentSttQuery = "SELECT MAX(Stt) AS max_stt FROM Customers";
                PreparedStatement fetchStatement = connection.prepareStatement(fetchCurrentSttQuery);
                ResultSet resultSet = fetchStatement.executeQuery();
                int currentStt = 0;

                if (resultSet.next()) {
                    currentStt = resultSet.getInt("max_stt");
                }

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
                tableC_View();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //----------------------------Customer form---------------------------------------------------------
    public void tableC_View() {

        customers_stt.setCellValueFactory(new PropertyValueFactory<>("Stt"));
        customers_Id.setCellValueFactory(new PropertyValueFactory<>("Id"));
        customers_fullName.setCellValueFactory(new PropertyValueFactory<>("FullName"));
        customers_ticketNum.setCellValueFactory(new PropertyValueFactory<>("Ticket"));
        customers_phoneNumber.setCellValueFactory(new PropertyValueFactory<>("PhoneNumber"));
        customers_busId.setCellValueFactory(new PropertyValueFactory<>("busId"));
        customers_location.setCellValueFactory(new PropertyValueFactory<>("location"));
        customers_date.setCellValueFactory(new PropertyValueFactory<>("Day_go"));


        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:D:/Sqlite/MyDatabase/QLVXBUS.db");
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Customers")) {

            ObservableList<customersData> customersDataList = FXCollections.observableArrayList();
            while (resultSet.next()) {
                customersData customerData = new customersData();
                customerData.setStt(resultSet.getInt("Stt"));
                customerData.setId(resultSet.getString("Id"));
                customerData.setFullName(resultSet.getString("fullName"));
                customerData.setTicket(resultSet.getInt("ticket"));
                customerData.setPhoneNumber(resultSet.getString("phoneNumber"));
                customerData.setBusId(resultSet.getInt("busID"));
                customerData.setLocation(resultSet.getString("location"));
                customerData.setDay_go(Date.valueOf(resultSet.getString("day_go")));
                customerData.setIncome(resultSet.getDouble("income"));

                customersDataList.add(customerData);
            }

            customers_table.setItems(customersDataList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cus_t_search() {
        FilteredList<customersData> filteredList = new FilteredList<>(customers_table.getItems(), e -> true);

        customers_search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(customer -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true; // Hiển thị tất cả nếu ô tìm kiếm trống
                }

                String searchText = newValue.toLowerCase();

                // Điều kiện tìm kiếm cho mỗi trường dữ liệu (ví dụ: FullName, Location, Ticket, PhoneNumber, Stt, Day_go, ID)
                return customer.getFullName().toLowerCase().contains(searchText)
                        || customer.getLocation().toLowerCase().contains(searchText)
                        || String.valueOf(customer.getTicket()).toLowerCase().contains(searchText)
                        || String.valueOf(customer.getBusId()).toLowerCase().contains(searchText)
                        || customer.getPhoneNumber().toLowerCase().contains(searchText)
                        || String.valueOf(customer.getStt()).toLowerCase().contains(searchText)
                        || String.valueOf(customer.getDay_go()).toLowerCase().contains(searchText)
                        || String.valueOf(customer.getId()).toLowerCase().contains(searchText);
            });


            customers_table.setItems(filteredList);
        });
    }

    //

    private int countBAV;
    private double countTodayIncome;
    private double countTotalIncome;

    public void dashboardBusAv() {
        String sql = "SELECT COUNT(*) AS total FROM Bus WHERE status = 'Có sẵn'";

        try (Connection connection = com.example.phuongbusbooking.Models.Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                countBAV = resultSet.getInt("total");
            }
            dashboard_busNum.setText(String.valueOf(countBAV));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dashboardTodayIncome() {
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());

        String dayIncomeQuery = "SELECT * FROM Customers WHERE DATE(day_go) = CURRENT_DATE";
        String incomeTodayQuery = "SELECT SUM(income) AS total_income FROM Customers WHERE DATE(day_go) = CURRENT_DATE";

        Connection connection = com.example.phuongbusbooking.Models.Database.getConnection();

        try (PreparedStatement dayIncomeStatement = connection.prepareStatement(dayIncomeQuery);
             PreparedStatement incomeTodayStatement = connection.prepareStatement(incomeTodayQuery)) {

            ResultSet dayIncomeResultSet = dayIncomeStatement.executeQuery();

            if (dayIncomeResultSet.next()) {

                ResultSet incomeTodayResultSet = incomeTodayStatement.executeQuery();

                if (incomeTodayResultSet.next()) {
                    double countTodayIncome = incomeTodayResultSet.getDouble("total_income");
                    dashboard_todayIncome.setText(String.valueOf(countTodayIncome));
                } else {
                    dashboard_todayIncome.setText("0.0");
                }
            } else {
                dashboard_todayIncome.setText("0.0");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dashboardTotalIncome() {
        String totalIncomeQuery = "SELECT SUM(income) AS total FROM Customers";
        connection = com.example.phuongbusbooking.Models.Database.getConnection();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(totalIncomeQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double totalIncome = resultSet.getDouble("total");
                String formattedTotalIncome = String.valueOf(totalIncome);
                dashboard_totalIncome.setText(formattedTotalIncome);
            } else {
                dashboard_totalIncome.setText("0.0");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
        }
    }

    public void dashboardChart() {
        dashboard_chart.getData().clear();

        String sql = "SELECT day_go, SUM(income) FROM Customers WHERE day_go IS NOT NULL GROUP BY day_go ORDER BY day_go ASC LIMIT 9";
        connection = com.example.phuongbusbooking.Models.Database.getConnection();

        XYChart.Series chart = new XYChart.Series();
        try {
            perspectiveCamera = connection.prepareStatement(sql);
            resultSet = perspectiveCamera.executeQuery();

            while (resultSet.next()) {
                chart.getData().add(new XYChart.Data(resultSet.getString(1), resultSet.getInt(2)));
            }
            dashboard_chart.getData().add(chart);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void Bookingbus_receipt(ActionEvent event) throws IOException {
        // Lấy giá trị từ các đối tượng Label bằng cách sử dụng getText()
        String idCustomer = bookingticket_idCustomer.getText();
        String fullName = bookingTicket_f_fullName.getText();
        String phoneNumber = bookingTicket_f_phoneNumber.getText();
        String date = bookingTicket_f_date.getText();
        String busId = bookingTicket_f_busId.getText();
        String location = bookingTicket_f_location.getText();
        String ticketNumber = bookingTicket_f_ticketNumber.getText();
        String moneyTotal = bookingTicket_f_moneyTotal.getText();

        // Kiểm tra xem các giá trị có tồn tại không
        if (idCustomer == null || fullName == null || phoneNumber == null || date == null ||
                busId == null || location == null || ticketNumber == null || moneyTotal == null) {
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

        // Lưu thông tin vào tệp
        String fileName = "D:/" + fullName + "_" + location + "_" + date + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(content);
            showAlert("Đã lưu thông tin vào tệp " + fileName, Alert.AlertType.INFORMATION);
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Lỗi khi lưu thông tin vào tệp.", Alert.AlertType.ERROR);
        }
        }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            tableB_View();
            tableC_View();
            comboBoxStatus();
            dashboardBusAv();
            dashboardTodayIncome();
            dashboardTotalIncome();
            dashboardChart();

            busIDList();
            busLocationList();
            ticketNum();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}






