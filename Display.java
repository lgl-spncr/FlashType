import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class Display extends JLabel {
	Font font;
	public Display(String text) {
		super(text);
		//font = new Font("Ume Mincho", Font.PLAIN, 128);
		font = new Font("Serif", Font.PLAIN, 128);
		setForeground(Color.white);
		setFont(font);
	}

}
