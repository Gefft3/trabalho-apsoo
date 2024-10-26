package trabalho.apsoo.pages;

import trabalho.apsoo.database.Firebase;
import trabalho.apsoo.model.User;

import javax.swing.*;
import java.awt.*;


public class LoginForm extends JPanel {

    public LoginForm(Firebase fb, User u, TextInput emailInput, PasswordInput passInput ,Button loginButton) {

        // Adicionando componentes ao painel de login
        add(Box.createRigidArea(new Dimension(100, 50))); // Espaço
        add(new Label("Log in", 40));
        add(Box.createRigidArea(new Dimension(100, 50))); // Espaço
        add(new Label("E-mail", 30));
        add(emailInput);
        add(Box.createRigidArea(new Dimension(100, 50))); // Espaço
        add(new Label("Senha", 30));
        add(passInput);
        add(Box.createRigidArea(new Dimension(100, 100))); // Espaço
        add(loginButton);
    }

}
