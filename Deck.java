import java.util.*;
import java.lang.*;
import java.io.*;

public class Deck extends ArrayList<Card>{
	int current;
	public Deck() {
		super();
		//Collections.shuffle(this);
	}
	
	public void loadCards(String filename) {
		BufferedReader fr = null;
		try {
			fr = new BufferedReader(new FileReader(filename));
			String s;
			while((s = fr.readLine()) != null) {
				String[] arr = s.split("\t");
				add(new Card(s.split("\t")));
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				fr.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	public Card getCurrent() {
		return get(current);
	}
	
	public void setCurrent(int newVal) {
		current = newVal;
	}
	
	public boolean matchesBack(String s) {
		return getCurrent().back.equals(s);
	}
	
	public boolean next() {
		current++;
		return current != size();
	}

	public void missed() {
		add(remove(current));
	}
	
	public void save(String filename, int numCorrect, int numWrong) {
		BufferedWriter bw;
		FileWriter fw;
		
		try {
			fw = new FileWriter(filename);
			bw = new BufferedWriter(fw);
			bw.write(numCorrect + "\n");
			bw.write(numWrong + "\n");
			
			for(Card c : toArray(new Card[size()])) {
				bw.write(c.front + "\t" + c.back + "\n");
			}
			bw.close();
			fw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public ArrayList<Integer> load(String filename) {
        FileReader fr;
        BufferedReader br;
        String line;
        boolean isnew = false;
        int correct;
        int wrong;
        ArrayList<Integer> stats = null;

        try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
            clear();
            line = br.readLine();
            if(Character.isDigit(line.charAt(0))) {
              correct = Integer.parseInt(line);
              current = correct;
              wrong = Integer.parseInt(br.readLine());
            }
            else {
            	correct = 0;
            	current = 0;
            	wrong = 0;
            	add(new Card(line.split("\t")));
            	isnew = true;
            }
            while((line = br.readLine()) != null) {
                add(new Card(line.split("\t")));
            }

            stats = new ArrayList<Integer>();
            stats.add(size() - correct);
            stats.add(correct);
            stats.add(wrong);
            if(isnew) {
            	Collections.shuffle(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(0);
        }

        return stats;
    }

}
