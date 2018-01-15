import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FlashType {
	InputField inputField;
	Display display;
	Deck deck;
	Counter correct;
	Counter wrong;
	Counter remaining;
	JPanel counterGroup;
	JFileChooser chooser;
	JMenuBar menubar;
	JMenu kanjiProgress;
	JMenuItem saveMI;
	JMenuItem loadMI;
	
	public FlashType() {
		deck = new Deck();
		
		JFrame frame = new JFrame("FlashType");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		frame.getContentPane().setBackground(Color.BLACK);
		
		counterGroup = new JPanel();
		counterGroup.setLayout(new BoxLayout(counterGroup, BoxLayout.X_AXIS));
		counterGroup.setBackground(Color.BLACK);
		
		correct = new Counter("Correct: ", 0);
		correct.setForeground(Color.GREEN);
		counterGroup.add(Box.createHorizontalStrut(10));
		counterGroup.add(correct);
		wrong = new Counter("Wrong: ", 0);
		wrong.setForeground(Color.RED);
		counterGroup.add(Box.createHorizontalStrut(10));
		counterGroup.add(wrong);
		counterGroup.add(Box.createHorizontalStrut(10));
		remaining = new Counter("Remaining: ", deck.size());
		remaining.setForeground(Color.WHITE);
		counterGroup.add(remaining);
		
		counterGroup.setAlignmentX(Component.LEFT_ALIGNMENT);
		frame.add(counterGroup);
		
		display = new Display("");
		frame.add(display);
		inputField = new InputField();
		frame.add(inputField);
		inputField.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void keyTyped(KeyEvent arg0) {
					if(display.getText().length() > 0) {
						char key = arg0.getKeyChar();
						if(key == '\b') {
							if(inputField.getText().length() > 0) {
							  inputField.setText(inputField.getText().substring(0, inputField.getText().length() - 1));
							}
						}
						else if(key == '\n') {
							JOptionPane.showMessageDialog(null, deck.getCurrent().back, "Unknown", JOptionPane.ERROR_MESSAGE);
							deck.missed();
							wrong.inc();
							update();
						}
						else {
							inputField.setText(inputField.getText() + key);
							if(deck.matchesBack(inputField.getText())) {
								javax.swing.Timer timer = new javax.swing.Timer(300, new ActionListener() {
									@Override
									public void actionPerformed(ActionEvent arg0) {
										if(deck.next()) {
											remaining.dec();
											correct.inc();
											update();
										}
										else {
											System.exit(0);
										}
									}
								});
								timer.setRepeats(false);
								timer.start();
							}
						}
					}
				}
				
				public void update() {
					display.setText(deck.getCurrent().front);
					inputField.setText("");
				}
		});
		
		chooser = new JFileChooser();
		
		menubar = new JMenuBar();
		kanjiProgress = new JMenu("File");
		saveMI = new JMenuItem("Save");
		saveMI.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int retval = chooser.showOpenDialog(null);
				if(retval == JFileChooser.APPROVE_OPTION) {
				  deck.save(chooser.getSelectedFile().getAbsolutePath(), Integer.parseInt(correct.getText()), Integer.parseInt(wrong.getText()));
				}
			}
			
		});
		kanjiProgress.add(saveMI);
		
		loadMI = new JMenuItem("Load");
        loadMI.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                ArrayList<Integer> stats;
                int retval = chooser.showOpenDialog(null);
                if(retval == JFileChooser.APPROVE_OPTION) {
	                stats = deck.load(chooser.getSelectedFile().getAbsolutePath());
	                remaining.set(stats.get(0));
	                correct.set(stats.get(1));
	                wrong.set(stats.get(2));
	                display.setText(deck.getCurrent().front);
	                inputField.setText("");
                }
            }
        });
        kanjiProgress.add(loadMI);
        
		menubar.add(kanjiProgress);
		frame.setJMenuBar(menubar);
		
		frame.setVisible(true);
	}
	
	public static void main(String[] args) {
		FlashType ft = new FlashType();
	}
}
