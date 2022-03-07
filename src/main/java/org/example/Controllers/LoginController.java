package org.example.Controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.CurrentUser;
import org.example.Database.User;

import javax.crypto.SecretKey;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.nio.charset.StandardCharsets;

import static org.example.App.CloseAndLoad;
import static org.example.App.FACTORY;

public class LoginController {


    public Text errorText;
    public TextField email;
    public TextField password;

    public void login() {
        // Get user by email
        EntityManager entityManager = FACTORY.createEntityManager();
        Query query = entityManager.createQuery("from User where email =:email ")
                .setParameter("email", email.getText());
        // If don't find user
        if (query.getResultList().size() <= 0)
            errorText.setVisible(true);
        else {
            User u = (User) query.getResultList().get(0);
            // verify JWT
            SecretKey key = Keys.hmacShaKeyFor("example_secret_key_for_pb_2021_secretKeyJWTtoken".getBytes(StandardCharsets.UTF_8));
            if (!Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(u.getJwt_token()).getBody().getSubject().equals(password.getText())) {
                errorText.setVisible(true);
                return;
            }
            CurrentUser.id = u.getId();
            CloseAndLoad((Stage) errorText.getScene().getWindow(), "products.fxml");
        }
    }

    public void registerScene() {
        CloseAndLoad((Stage) errorText.getScene().getWindow(), "register.fxml");
    }
}
