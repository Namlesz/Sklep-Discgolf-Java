package org.example.Controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.example.App;
import org.example.CurrentUser;
import org.example.Database.Tournaments;
import org.example.Database.TournamentsList;
import org.example.Observable.TournamentListObservable;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.example.App.FACTORY;

public class TournamentListController {
    public TableView<TournamentListObservable> tournamentListTable;
    private ObservableList<TournamentListObservable> tourList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("from Tournaments");
        List<Tournaments> list = query.getResultList();
        list.forEach(element -> {
            SimpleIntegerProperty id = new SimpleIntegerProperty(element.getId());
            SimpleStringProperty date = new SimpleStringProperty(new SimpleDateFormat("dd-MM-YYYY").format(element.getDate()));
            SimpleStringProperty name = new SimpleStringProperty(element.getName());
            tourList.add(new TournamentListObservable(id, date, name));
        });
        tournamentListTable.setItems(tourList);
    }

    public void signUp() {
        TournamentListObservable selectedItem = tournamentListTable.getSelectionModel().getSelectedItem();
        EntityManager entityManager = FACTORY.createEntityManager();
        Query q1 = entityManager.createQuery("from TournamentsList where user_id =:user_id ").setParameter("user_id", (int) CurrentUser.id);
        List<TournamentsList> list = q1.getResultList();
        Boolean find = false;
        for (TournamentsList el : list) {
            if (el.getTour_id() == selectedItem.getId())
                find = true;
        }
        if (!find) {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            StoredProcedureQuery q2 = entityManager
                    .createStoredProcedureQuery("INSER_NEW_USER_TO_TOURNAMENT")
                    .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Integer.class, ParameterMode.IN)
                    .setParameter(1, (int) CurrentUser.id)
                    .setParameter(2, selectedItem.getId());
            q2.execute();
            transaction.commit();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Już zostałeś zapisany na ten konkurs", ButtonType.APPLY);
            alert.setTitle("Informacja");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    public void setProductsView() {
        App.CloseAndLoad((Stage) tournamentListTable.getScene().getWindow(), "products.fxml");
    }
}
