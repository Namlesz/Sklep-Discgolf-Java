package org.example.Controllers;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.crypto.SecretKey;
import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import static org.example.App.CloseAndLoad;
import static org.example.App.FACTORY;

public class RegisterController {
    public TextField name;
    public TextField surname;
    public TextField house_num;
    public TextField street;
    public TextField town;
    public TextField email;
    public TextField door_num;
    public TextField password;
    public TextField _password;
    public DatePicker birthday;
    public Text errorText;

    public void backToLogin() {
        CloseAndLoad((Stage) errorText.getScene().getWindow(),"login.fxml");
    }

    public void register(ActionEvent actionEvent) throws ParseException {
        EntityManager entityManager = FACTORY.createEntityManager();

        Query q = entityManager.createQuery("from User where email =:email ")
                .setParameter("email", email.getText());
        if(q.getResultList().size()>0){
            errorText.setText("Email już został użyty");
            return;
        }
        else if (house_num.getText().compareTo("") == 0 || name.getText().compareTo("") == 0 || surname.getText().compareTo("") == 0 || door_num.getText().compareTo("") == 0 ||
                email.getText().compareTo("") == 0 || birthday.getValue() == null || town.getText().compareTo("") == 0 ||
                street.getText().compareTo("") == 0 || password.getText().compareTo("") == 0 || _password.getText().compareTo("") == 0)
        {
            errorText.setText("Proszę wypełnić wszystke obowiązkowe pola.");
            return;
        }
        else if(password.getText().compareTo(_password.getText())!=0){
            errorText.setText("Hasła nie są takie same.");
            return;
        }
        else if(!isNumeric(door_num.getText())){
            errorText.setText("Błędny numer mieszkania");
            door_num.setText("");
            return;
        }
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(birthday.getValue().toString());

        // Create JWT Token
        SecretKey key = Keys.hmacShaKeyFor("example_secret_key_for_pb_2021_secretKeyJWTtoken".getBytes(StandardCharsets.UTF_8));
        String jws = Jwts.builder().setSubject(password.getText()).signWith(key).compact();
        if (!Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jws).getBody().getSubject().equals(password.getText())){
            errorText.setText("Nie udało się utworzyć tokena.");
            return;
        }

        // Use procedure to add new user
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        StoredProcedureQuery query = entityManager
                .createStoredProcedureQuery("insert_new_klient")
                .registerStoredProcedureParameter(1,String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2,String.class,ParameterMode.IN)
                .registerStoredProcedureParameter(3, Date.class,ParameterMode.IN)
                .registerStoredProcedureParameter(4,String.class,ParameterMode.IN)
                .registerStoredProcedureParameter(5,String.class,ParameterMode.IN)
                .registerStoredProcedureParameter(6,String.class,ParameterMode.IN)
                .registerStoredProcedureParameter(7,String.class,ParameterMode.IN)
                .registerStoredProcedureParameter(8,Integer.class,ParameterMode.IN)
                .registerStoredProcedureParameter(9,String.class,ParameterMode.IN)
                .setParameter(1,name.getText())
                .setParameter(2,surname.getText())
                .setParameter(3,date)
                .setParameter(4,email.getText())
                .setParameter(5,town.getText())
                .setParameter(6,street.getText())
                .setParameter(7,house_num.getText())
                .setParameter(8,Integer.parseInt(door_num.getText()))
                .setParameter(9,jws);

        query.execute();
        transaction.commit();
        backToLogin();
    }

    private Pattern pattern = Pattern.compile("\\d+");
    public boolean isNumeric(String strNum) {
        if (strNum == null) return true;
        return pattern.matcher(strNum).matches();
    }
}
