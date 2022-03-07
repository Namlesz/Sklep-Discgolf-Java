package org.example.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import org.example.*;
import org.example.Database.Orders;
import org.example.Database.ShoppingList;
import org.example.Observable.OrderListObservable;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.example.App.FACTORY;

public class OrderBasketController {
    public TableView<OrderListObservable> basketTable;
    public Text summaryText;
    public TableColumn countCol;

    @FXML
    public void initialize() {
        // Change price text
        updatePriceText();
        // Set observable list
        basketTable.setItems(ProductsController.orderList);
        countCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    }

    public void buy() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Brak produktów w koszyku.", ButtonType.APPLY);
        alert.setTitle("Informacja");
        alert.setHeaderText(null);

        if (ProductsController.orderList.size() <= 0) {
            alert.showAndWait();
        } else {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Czy chcesz skompletować zamówienie?", ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Koszyk");
            confirm.setHeaderText(null);
            confirm.showAndWait();

            if (confirm.getResult() == ButtonType.YES) {
                try {
                    proceedCheckout();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else
                return;
        }
        ProductsController.orderList.clear();
        App.CloseAndLoad((Stage) summaryText.getScene().getWindow(), "products.fxml");
    }

    public void delete(ActionEvent actionEvent) {
        OrderListObservable selectedItem = basketTable.getSelectionModel().getSelectedItem();
        ProductsController.orderList.remove(selectedItem);
        updatePriceText();
    }

    public void back(ActionEvent actionEvent) {
        App.CloseAndLoad((Stage) summaryText.getScene().getWindow(), "products.fxml");
    }

    private double calculateSumOrder() {
        double sum = 0;
        for (OrderListObservable product : ProductsController.orderList) {
            sum += product.getPrice() * product.getCount();
        }
        return sum;
    }

    private void proceedCheckout() throws ParseException {
        // New order
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(java.time.LocalDate.now().toString());
        Orders order = new Orders(date, (int) CurrentUser.id, "przyjete");

        EntityManager entityManager = FACTORY.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(order);
        transaction.commit();

        // Found inserted id
        Query query = entityManager.createQuery("from Orders");
        ArrayList<Orders> orderList = (ArrayList<Orders>) query.getResultList();
        int max = 0;
        for (Orders o : orderList) {
            int idx = (int) o.getId();
            if (max < idx)
                max = idx;
        }

        // Add order positions
        ArrayList<ShoppingList> list = new ArrayList<>();
        for (OrderListObservable orderListObservable : ProductsController.orderList) {
            list.add(new ShoppingList(orderListObservable.getCount(), orderListObservable.getCount() * orderListObservable.getPrice(), max, orderListObservable.getId()));
        }

        transaction.begin();
        list.forEach(product -> entityManager.merge(product));
        transaction.commit();
    }

    private int getIndex() {
        return basketTable.getSelectionModel().getSelectedIndex();
    }

    public void changeCount(TableColumn.CellEditEvent cellEditEvent) {
        ProductsController.orderList.get(getIndex()).setCount((int) cellEditEvent.getNewValue());
        summaryText.setText("Cena całkowita: " + calculateSumOrder());
    }

    private void updatePriceText() {
        DecimalFormat df = new DecimalFormat("#.##");
        summaryText.setText("Cena całkowita: " + df.format(calculateSumOrder()) + " ZŁ");
    }

}
