package autre;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SortieTrec {
	File fichier;
	FileWriter fW;

	public SortieTrec() {
		fichier = new File("resTP5.txt");
		try {
			fichier.createNewFile();
			fW = new FileWriter(fichier);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void ecrire(String str) {
		try {
			fW.write(str + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			fW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
