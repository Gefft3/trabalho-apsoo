package trabalho.apsoo.pages;

import javax.swing.*;
import java.awt.*;

public class Label extends JLabel {

    public Label(String label, int size){
        setText(label);
        setForeground(Color.WHITE);
        setAlignmentX(Component.CENTER_ALIGNMENT);
        setFont(new Font("Roboto", Font.PLAIN, size));
    }
}
