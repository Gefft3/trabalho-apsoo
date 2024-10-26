package trabalho.apsoo.pages;

import javax.swing.*;
import java.awt.*;

public class PasswordInput extends JPasswordField {
    public PasswordInput(int size) {
        setBackground(new Color(192, 187, 187));
        setMaximumSize(new Dimension(600, 50));
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setFont(new Font("Roboto", Font.PLAIN, size));
    }
}
