package org.example.Controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.App;
import org.example.Observable.OrderListObservable;
import org.example.Database.Product;
import org.example.Observable.ProductObservable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static org.example.App.FACTORY;

public class ProductsController {
    @FXML
    public TableView<ProductObservable> productsTable;
    public static ObservableList<OrderListObservable> orderList = FXCollections.observableArrayList();
    private ObservableList<ProductObservable> productList = FXCollections.observableArrayList();
    public Text cartInfo;

    @FXML
    public void initialize() {
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("from Product");
        List<Product> list = query.getResultList();
        list.forEach(product -> {
            SimpleIntegerProperty id = new SimpleIntegerProperty((int) product.getId());
            SimpleStringProperty code = new SimpleStringProperty(product.getCode());
            SimpleStringProperty name = new SimpleStringProperty(product.getName());
            SimpleStringProperty chain = new SimpleStringProperty(product.getChain());
            SimpleStringProperty ean = new SimpleStringProperty(product.getEan());
            SimpleIntegerProperty vat = new SimpleIntegerProperty(product.getVat());
            SimpleDoubleProperty rating = new SimpleDoubleProperty(product.getRating());
            SimpleDoubleProperty price = new SimpleDoubleProperty(product.getPrice());
            productList.add(new ProductObservable(id, code, name, chain, ean, vat, rating, price));
        });
        productsTable.setItems(productList);
        updateBasketText();
    }

    public void addProduct() {
        ProductObservable selectedItem = productsTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Nie zaznaczono żadnego produktu", ButtonType.APPLY);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        if (selectedItem == null)
            alert.showAndWait();
        else {
            int id_found = -1;
            for (int i = 0; i < orderList.size(); i++) {
                if (orderList.get(i).getId() == selectedItem.getId())
                    id_found = i;
            }
            if (id_found == -1) {
                SimpleIntegerProperty id = new SimpleIntegerProperty(selectedItem.getId());
                SimpleStringProperty name = new SimpleStringProperty(selectedItem.getName());
                SimpleDoubleProperty price = new SimpleDoubleProperty(selectedItem.getPrice());
                SimpleIntegerProperty count = new SimpleIntegerProperty(1);
                orderList.add(new OrderListObservable(id, name, price, count));
            } else {
                orderList.get(id_found).setCount(orderList.get(id_found).getCount() + 1);
            }
            updateBasketText();
        }
    }

    public void summary(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Brak produktów w koszyku.", ButtonType.APPLY);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);
        if (orderList.size() <= 0)
            alert.showAndWait();
        else
            App.CloseAndLoad((Stage) cartInfo.getScene().getWindow(), "orderBasket.fxml");
    }

    private void updateBasketText() {
        cartInfo.setText("Ilość produktów w koszyku: " + orderList.size());
    }

    public void orderHistory(ActionEvent actionEvent) {
        App.CloseAndLoad((Stage) cartInfo.getScene().getWindow(), "orderHistory.fxml");
    }

    public void tournamentList(ActionEvent actionEvent) {
        App.CloseAndLoad((Stage) cartInfo.getScene().getWindow(), "tournamentList.fxml");
    }

    public void myTournament(ActionEvent actionEvent) {
        App.CloseAndLoad((Stage) cartInfo.getScene().getWindow(), "myTournament.fxml");
    }
}
