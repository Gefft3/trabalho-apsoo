package trabalho.apsoo.pages;

import javax.swing.*;
import java.awt.*;


public class LoginPage extends JFrame {

    public LoginPage() {
        // Configurações da janela
        setTitle("Muscle Track");
        setSize(1500, 1500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Painel principal para alinhar tudo ao centro
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(17,17,17)); // Cor de fundo da tela inteira

        // Painel do título
        JPanel titlePanel = new JPanel();

        titlePanel.setBackground(new Color(27, 30, 42)); // Cor semelhante à barra superior
        JLabel titleLabel = new JLabel("Muscle Track");
        titleLabel.setForeground(Color.WHITE); // Texto branco
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 48*3)); // Fonte maior e negrito
        titlePanel.add(titleLabel);


        // Painel de login
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(new Color(15, 15, 45)); // Cor de fundo da caixa de login
        loginPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margem interna

        // Labels e campos de texto
        JLabel loginLabel = new JLabel("Logar-se");
        loginLabel.setForeground(Color.WHITE); // Texto branco
        loginLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizado
        loginLabel.setFont(new Font("Roboto", Font.BOLD, 18*4));

        JTextField usernameField = new JTextField(15);
        usernameField.setBackground(new Color(192,187,187));
        usernameField.setMaximumSize(new Dimension(750, 75));
        usernameField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setBackground(new Color(192,187,187));
        passwordField.setMaximumSize(new Dimension(750, 75));
        passwordField.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão de login
        JButton loginButton = new JButton("Logar");
        loginButton.setBackground(new Color(10,195,10));
        loginButton.setForeground(Color.WHITE); // Texto branco no botão
        loginButton.setFont(new Font("Roboto", Font.BOLD, 18*3));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setFocusable(false);
        loginButton.setPreferredSize(new Dimension(375, 75)); // Tamanho do botão

        // Adicionando componentes ao painel de login
        loginPanel.add(Box.createRigidArea(new Dimension(100, 100))); // Espaço
        loginPanel.add(loginLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(100, 100))); // Espaço
        loginPanel.add(usernameField);
        loginPanel.add(Box.createRigidArea(new Dimension(100, 100))); // Espaço
        loginPanel.add(passwordField);
        loginPanel.add(Box.createRigidArea(new Dimension(100, 100))); // Espaço
        loginPanel.add(loginButton);

        // Adicionando o título e o painel de login ao painel principal
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // Adiciona o painel principal à janela
        add(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginPage().setVisible(true);
        });
    }
}
