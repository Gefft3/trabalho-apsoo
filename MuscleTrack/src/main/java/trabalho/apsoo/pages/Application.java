package trabalho.apsoo.pages;

import trabalho.apsoo.database.Firebase;
import trabalho.apsoo.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Application extends JFrame {

    User user;
    Firebase firebase;

    public Application() {

        this.user = new User();
        this.firebase = new Firebase(System.getenv("FIREBASE_API_KEY"), System.getenv("AUTH_BASE_URL"),
                System.getenv("FIREBASE_BASE_URL"), System.getenv("AUTH_REFRESH_URL"));

        // Configurações da janela
        setTitle("Muscle Track");
        setSize(1500, 1500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel da janela

        JPanel windowPanel = new JPanel();
        windowPanel.setLayout(new BorderLayout());
        windowPanel.setBackground(new Color(17, 17, 17));

        // Painel do título
        JPanel titlePanel = new JPanel();

        titlePanel.setBackground(new Color(27, 30, 42)); // Cor semelhante à barra superior
        JLabel titleLabel = new JLabel("Muscle Track");
        titleLabel.setForeground(Color.WHITE); // Texto branco
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 16 * 3)); // Fonte maior e negrito
        titlePanel.add(titleLabel);

        JPanel mainContent = new JPanel();
        mainContent.setBackground(new Color(17, 17, 17));
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));

        TextInput emailInput = new TextInput(30);
        PasswordInput passInput = new PasswordInput(30);

        ActionListener loginListener = actionEvent -> {
            char[] pass = passInput.getPassword();
            StringBuilder sb;
            sb = new StringBuilder();
            for(char c: pass) sb.append(c);

            user.setPassword(sb.toString());
            user.setEmail(emailInput.getText());

            if(firebase.authenticateUser(user)){
                System.out.println("Usuário logado");
                carregarHome(mainContent);
            }else{
                System.out.println("Não logado");
            }

        };

        Button loginButton = new Button("Log in", loginListener);

        LoginForm loginPanel = new LoginForm(this.firebase, this.user, emailInput, passInput, loginButton);
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(new Color(15, 15, 45)); // Cor de fundo da caixa de login
        loginPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 50, 50)); // Margem interna


        mainContent.add(Box.createRigidArea(new Dimension(100, 100)));
        mainContent.add(loginPanel);
        windowPanel.add(titlePanel, BorderLayout.NORTH);
        windowPanel.add(mainContent, BorderLayout.CENTER);

        add(windowPanel);
    }

    private void carregarHome(JPanel mainContent){
        mainContent.remove(1);
        Label lb = new Label("FUNCIONA!!!", 40);
        mainContent.add(lb);
        mainContent.updateUI();
    }


}
