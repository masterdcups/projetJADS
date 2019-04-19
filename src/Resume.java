import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import autre.Phrase;
import tf_idf.TFLog;

public class Resume{

	File corpus;

	public Resume(File corpus) {
		this.corpus = corpus;
	}

	private float freqMot(String mot, List<String> mots) {
		int nb = 0;
		for (Iterator<String> it = mots.iterator(); it.hasNext();)
			if (it.next().equals(mot))
				nb++;
		return nb;
	}
	
	private float freqPhrase(String mot, List<String> phrases) {
		int nb = 0;
		for (Iterator<String> it = phrases.iterator(); it.hasNext();)
			if (it.next().contains(mot))
				nb++;
		return nb;
	}

	private float calculPoids(List<String> phrases, String s) {
		TFLog monTF = new TFLog();
		float temp1, temp2;
		float total = 0;
		List<String> mots = Arrays.asList(s.split(" "));
		for (Iterator<String> it = mots.iterator(); it.hasNext();) {
			String curr = it.next();
			temp1 = monTF.tf(freqMot(curr, mots));
			temp2 = monTF.idf(phrases.size(), (long)freqPhrase(curr, phrases));
			total += temp1*temp2; // poids du mot
		}
		return total;
	}

	public String AlgoGeneral(File f, int lonRes) {
		Set<Phrase> ensemble = new TreeSet<>();
		List<String> tweets = new ArrayList<>();
		StringWriter out = new StringWriter();
		try {
			BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String l;
			//int i = 0;
			while(((l = buff.readLine()) != null)) {
				tweets.add(l);
			}
			buff.close();
			out.close();
			
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		int i = 0;
		for(Iterator<String> it = tweets.iterator(); it.hasNext();) {
			String curr = it.next(); 
			i++;
			ensemble.add(new Phrase(curr, calculPoids(tweets, curr)));
		}

		String ret = "";
		Iterator<Phrase> it = ensemble.iterator();
		for (int l = 0; l < lonRes; l++) {
			Phrase p = it.next();
			
			/* Nbr de phrases */
			ret += p.getTexte()+"\n";

		}
		return ret;
	}

}