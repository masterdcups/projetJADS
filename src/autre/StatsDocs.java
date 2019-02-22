package autre;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class StatsDocs {
	private File f;

	public StatsDocs(File f) {
		this.f = f;
	}
	
	public String[] decoupeDoc() throws IOException {
		String[] lignes = new String[this.nombreDeLigne()]; 
 		FileReader in = new FileReader(f);
		BufferedReader buf = new BufferedReader(in);
		int i=0;
		while(i < this.nombreDeLigne()) {
			lignes[i] = buf.readLine();
			i++;
		}
		buf.close();
		return lignes;		
	}

	public int nombreDeLigne() throws IOException {
		int nbLignes = 0;
		FileReader in = new FileReader(f);
		BufferedReader buf = new BufferedReader(in);
		while ((buf.readLine()) != null)
			nbLignes++;
		buf.close();
		return nbLignes;
	}
	
	public int nombreDeMots() throws IOException {
		int nbMots = 0;
		String ligne;
		FileReader in = new FileReader(f);
		BufferedReader buf = new BufferedReader(in);
		while ((ligne = buf.readLine()) != null) {
			String[] mots = ligne.split(" ");
			nbMots += mots.length;
		}
		buf.close();
		return nbMots;
	}

}
