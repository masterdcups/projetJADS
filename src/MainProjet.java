import java.util.logging.Level;
import java.util.logging.Logger;

public class MainProjet {

	String nameFileXML;
	String nameFolderIndex;

	public MainProjet() {
		nameFileXML = "index/iotCSV.csv";
		nameFolderIndex = "index/";
	}

	public static void main(String[] args) {
		MainProjet main = new MainProjet();
		// main.index();
		main.query("Text:IoT");
	}

	public void index() {
		IndexCollection tweets = new IndexCollection(nameFileXML, nameFolderIndex);
		try {
			tweets.index();
		} catch (Exception ex) {
			Logger.getLogger(MainProjet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private void query(String query) {
		QuerySimple qs = new QuerySimple(nameFolderIndex);
		try {
			qs.process(query);
		} catch (Exception ex) {
			Logger.getLogger(MainProjet.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
