package org.example.Controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.App;
import org.example.CurrentUser;
import org.example.Database.Tournaments;
import org.example.Database.TournamentsList;
import org.example.Observable.TournamentListObservable;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.example.App.FACTORY;

public class MyTournamentController {
    public TableView<TournamentListObservable> tournamentListTable;
    private ObservableList<TournamentListObservable> tourList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("from TournamentsList where user_id =:user_id ").setParameter("user_id", (int) CurrentUser.id);
        List<TournamentsList> list = query.getResultList();
        list.forEach(element -> {
            SimpleIntegerProperty id = new SimpleIntegerProperty(element.getId());
            SimpleStringProperty date = new SimpleStringProperty(new SimpleDateFormat("dd-MM-YYYY").format(element.getTour_date()));
            SimpleStringProperty name = new SimpleStringProperty(element.getTour_name());
            tourList.add(new TournamentListObservable(id, date, name));
        });
        tournamentListTable.setItems(tourList);
    }

    public void setProductsView() {
        App.CloseAndLoad((Stage) tournamentListTable.getScene().getWindow(), "products.fxml");
    }
}
