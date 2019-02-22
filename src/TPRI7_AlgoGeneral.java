

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import autre.Phrase;
import tf.TFLog;

public class TPRI7_AlgoGeneral{

	File folder;
	List<File> listeFich = new LinkedList<>();
	List<String> motnuls = new LinkedList<>();

	public TPRI7_AlgoGeneral() {
		folder = new File("critiques/projects/test-summarization/topics");
		for (int i = 0; i < folder.listFiles().length; i++)
			listeFich.add(folder.listFiles()[i]);
	}

	public static void main(String[] args) {
		//Algo general
		TPRI7_AlgoGeneral yo = new TPRI7_AlgoGeneral();
		for (Iterator<File> it = yo.listeFich.iterator(); it.hasNext();) {
			File f = it.next();
			String s = yo.AlgoGeneral(yo.loadFile(f), 3);
			System.out.println(s);
			// break;
			yo.WriteRes(f.getName().split(".data")[0], s);
		}
	}

	public void WriteRes(String name, String res) {
		try {
			FileWriter fw = new FileWriter(
					new File("critiques/projects/test-summarization/system/" + name + ".sys.algo"));
			fw.write(res, 0, res.length());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String loadFile(File f) {
		StringWriter out = new StringWriter();
		try {
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
			int b;
			while ((b = in.read()) != -1)
				out.write(b);
			out.flush();
			out.close();
			in.close();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
		return out.toString();
	}

	private float freqMot(String mot, List<String> mots) {
		int nb = 0;
		for (Iterator<String> it = mots.iterator(); it.hasNext();) {
			if (it.next().equals(mot)) {
				nb++;
			}
		}
		// System.out.println(nb);
		return nb;
	}
	
	private float freqPhrase(String mot, List<String> phrases) {
		int nb = 0;
		for (Iterator<String> it = phrases.iterator(); it.hasNext();) {
			if (it.next().contains(mot)) {
				nb++;
			}
		}
		// System.out.println(nb);
		return nb;
	}

	private float calculPoids(List<String> phrases, String s) {
		TFLog monTF = new TFLog();
		float total = 0;
		List<String> mots = Arrays.asList(s.split(" "));
		for (Iterator<String> it = mots.iterator(); it.hasNext();) {
			String curr = it.next();
			total += monTF.tf(freqMot(curr, mots))*monTF.idf(phrases.size(), (long)freqPhrase(curr, phrases)); // poids du mot
			// System.out.println(curr+" = "+total);
		}
		return total;
	}
	
	public void majEnsemble(Set<Phrase> ensemble, String resume) {
		
	}

	public String AlgoGeneral(String texte, int lonRes) {
		Set<Phrase> ensemble = new TreeSet<>();
		List<String> phrase = Arrays.asList(texte.split("[\\.\\!\\?\\|]"));
		int i = 0;
		for (Iterator<String> it = phrase.iterator(); it.hasNext();) {
			i++;
			String curr = it.next();
			// System.out.println("Fichier : " + curr);
			ensemble.add(new Phrase(curr, calculPoids(phrase, curr)));
		}
		System.out.println(ensemble.size() + "/" + i);
		String ret = "";
		for (int l = 0; l < lonRes; l++) {
			Iterator<Phrase> it = ensemble.iterator();
			Phrase p = it.next();
			// System.out.println(p.getTexte().length()+" / "+p.getPoids());
			
			/* Nbr de phrases */
			ret += p.getTexte();
			l++;
			
			// System.out.println(s.length()+"/"+l+"/"+lonRes+"/"+s);
		}
		return ret;
	}

}
