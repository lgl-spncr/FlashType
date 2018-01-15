import javax.swing.JLabel;

public class Counter extends JLabel {
	int count;
	String label;
	public Counter(String l, int startCount) {
		super(l + Integer.toString(startCount));
		label = l;
		count = startCount;
	}
	
	public void update() {
		setText(label + Integer.toString(count));
	}
	
	public void set(int newVal) {
        count = newVal;
        update();
    }
	
	public void inc() {
		count++;
		update();
	}
	
	public void dec() {
		count--;
		update();
	}
}
