import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class MainProjet {

	String nameFile;
	String nameFolderIndex;
	File corpus;
	File corpusTxt;
	
	public MainProjet() {
		nameFile = "index/iotCSV.csv";
		nameFolderIndex = "index/";
		corpus = new File(nameFile);
		corpusTxt = new File("corpus.txt");
	}

	public static void main(String[] args) {
		MainProjet main = new MainProjet();
		// main.index();
		ScoreDoc[] q = main.query("Text:IoT");
		main.sdToTxt(q, main.corpus);
		/*Resume res = new Resume(main.corpusTxt);
		String s = res.AlgoGeneral(res.loadFile(main.corpusTxt), 3);
		res.WriteRes(res.corpus.getName().split(".data")[0], s);*/
	}

	public void sdToTxt(ScoreDoc[] s, File f) {
		try {
			f.createNewFile();
			Path path = f.toPath();
			Directory index = FSDirectory.open(path);

			FileWriter fw = new FileWriter(f);
			IndexReader reader = DirectoryReader.open(index);

			IndexSearcher searcher = new IndexSearcher(reader);
			for (int i = 0; i < s.length; i++) {
				int docId = s[i].doc;
				Document d = searcher.doc(docId);
				fw.write(d.get("Text"));
				fw.write("\n");
			}
			fw.close();
		} catch (Exception e) {
		}

	}

	public void index() {
		IndexCollection tweets = new IndexCollection(nameFile, nameFolderIndex);
		try {
			tweets.index();
		} catch (Exception ex) {
			Logger.getLogger(MainProjet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private ScoreDoc[] query(String query) {
		QuerySimple qs = new QuerySimple(nameFolderIndex);
		try {
			ScoreDoc[] q = qs.process(query);
			return q;
		} catch (Exception ex) {
			Logger.getLogger(MainProjet.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
	}
}
