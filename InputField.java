import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;

public class InputField extends JTextField {
	Font font;
	public InputField() {
		super();
		font = new Font("Ume Mincho", Font.BOLD, 32);
		setBackground(Color.black);
		setForeground(Color.white);
		setFont(font);
		setEditable(false);
	}

}
