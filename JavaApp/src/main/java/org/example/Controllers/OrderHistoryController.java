package org.example.Controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.App;
import org.example.CurrentUser;
import org.example.Database.Product;
import org.example.Database.ShoppingList;
import org.example.Observable.HistoryObservable;
import org.example.Database.Orders;
import org.example.Observable.ProductObservable;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.example.App.FACTORY;

public class OrderHistoryController {
    public TableView<HistoryObservable> HistoryTable;
    private ObservableList<HistoryObservable> historyList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("from Orders where user_id =:user_id ").setParameter("user_id", (int) CurrentUser.id);
        List<Orders> list = query.getResultList();
        list.forEach(orders -> {
            SimpleIntegerProperty id = new SimpleIntegerProperty((int) orders.getId());
            SimpleStringProperty date = new SimpleStringProperty(new SimpleDateFormat("dd-MM-YYYY").format(orders.getDate()));
            SimpleStringProperty state = new SimpleStringProperty(orders.getState());
            historyList.add(new HistoryObservable(id, date, state));
        });
        HistoryTable.setItems(historyList);
    }


    public void productsView() {
        App.CloseAndLoad((Stage) HistoryTable.getScene().getWindow(), "products.fxml");
    }

    public void showDetails() {
        HistoryObservable selectedItem = HistoryTable.getSelectionModel().getSelectedItem();
        EntityManager entityManager = FACTORY.createEntityManager();
        double wartosc  = (double) entityManager.createQuery("select sum(value) from ShoppingList where order_id =:order_id ").setParameter("order_id",selectedItem.getId()).getSingleResult();
        Query query = entityManager.createQuery("from ShoppingList where order_id =:order_id ").setParameter("order_id",selectedItem.getId());
        List<ShoppingList> list = query.getResultList();
        StringBuilder products = new StringBuilder("");
        products.append("Produkty: \n");
        for (ShoppingList element:list) {
           Product p = (Product) entityManager.createQuery("from Product where id =:id").setParameter("id", (long) element.getProduct_id()).getResultList().get(0);
           products.append("\t" + p.getName() + "\n");
        }
        products.append("\nWartość zamówienia: "+ wartosc);
        Alert info = new Alert(Alert.AlertType.INFORMATION, products.toString(),ButtonType.OK);
        info.setTitle("Szczegóły");
        info.setHeaderText(null);
        info.showAndWait();
    }
}
