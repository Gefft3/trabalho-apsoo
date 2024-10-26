package trabalho.apsoo.pages;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Button extends JButton {
    public Button(String text, ActionListener listener){
        setText(text);
        setBackground(new Color(10, 195, 10));
        setForeground(Color.WHITE); // Texto branco no botão
        setFont(new Font("Roboto", Font.BOLD, 12 * 3));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setFocusable(false);
        addActionListener(listener);

//        loginButton.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent actionEvent) {
//                char[] pass = passwordField.getPassword();
//                StringBuilder passString = new StringBuilder();
//                for (char c : pass) passString.append(c);
//
//                u.setEmail(emailField.getText());
//                u.setPassword(passString.toString());
//
//                if(fb.authenticateUser(u)){
//                    System.out.println("Usuário logado");
//                }else{
//                    System.out.println("Usuário não logado");
//                }
//                dispatchEvent(new ActionEvent(loginButton, 0, "logged"));
//            }
//        });
    }
}
