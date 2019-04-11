

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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import autre.Phrase;
import tf.TFLog;

public class AlgoGeneral{

	File folder;
	List<File> listeFich = new LinkedList<>();
	List<String> motnuls = new LinkedList<>();

	public AlgoGeneral() {
		folder = new File("res/");
		for (int i = 0; i < folder.listFiles().length; i++)
			listeFich.add(folder.listFiles()[i]);
	}

	public static void main(String[] args) {
		//Algo general
		AlgoGeneral yo = new AlgoGeneral();
		for (Iterator<File> it = yo.listeFich.iterator(); it.hasNext();) {
			File f = it.next();
			String s = yo.AlgoGeneral(f, 3);
			System.out.println(s);
			// break;
			yo.WriteRes(f.getName().split(".data")[0], s);
		}
	}

	public void WriteRes(String name, String res) {
		try {
			FileWriter fw = new FileWriter(
					new File("autres/" + name + ".sys.algo"));
			fw.write(res, 0, res.length());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private float freqMot(String mot, List<String> mots) {
		int nb = 0;
		for (Iterator<String> it = mots.iterator(); it.hasNext();) {
			if (it.next().equals(mot)) {
				nb++;
			}
		}
		//System.out.println(nb);
		return nb;
	}
	
	private float freqPhrase(String mot, List<String> phrases) {
		int nb = 0;
		for (Iterator<String> it = phrases.iterator(); it.hasNext();) {
			if (it.next().contains(mot)) {
				nb++;
			}
		}
		//System.out.println(nb);
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
			//System.out.println(curr+" = "+total+" tf = "+temp1+" idf = "+temp2+" = "+(long)freqPhrase(curr, phrases));
		}
		return total;
	}
	
	public void majEnsemble(Set<Phrase> ensemble, String resume) {
		
	}

	public String AlgoGeneral(File f, int lonRes) {
		Set<Phrase> ensemble = new TreeSet<>();
		List<String> tweets = new ArrayList<>();
		StringWriter out = new StringWriter();
		try {
			BufferedReader buff = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
			String l;
			int i = 0;
			while(((l = buff.readLine()) != null) && i < 1000) {
				System.out.println(i++);
				tweets.add(l);
				System.out.println("Fichier : " + l);
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
			if(i%1000 == 0)
				System.out.println(i);
			//System.out.println(curr);
			ensemble.add(new Phrase(curr, calculPoids(tweets, curr)));
		}
		//List<String> phrase = Arrays.asList(texte.split("[\\.\\!\\?\\|]"));
		//int i = 0;
		
//		System.out.println(ensemble.size() + "/" + tweets.size());
//		for(Iterator<Phrase> it = ensemble.iterator(); it.hasNext();) {
//			System.out.println(it.next().getTexte());
//		}
		String ret = "";
		Iterator<Phrase> it = ensemble.iterator();
		for (int l = 0; l < lonRes; l++) {
			Phrase p = it.next();
			//System.out.println(p.getTexte().length()+" / "+p.getPoids());
			
			/* Nbr de phrases */
			ret += p.getTexte()+"\n";
			
			//System.out.println(s.length()+"/"+l+"/"+lonRes+"/"+s);
		}
		return ret;
	}

}
