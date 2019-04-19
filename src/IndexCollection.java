import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexCollection {

	String filename;
	String indexPath;
	IndexWriter writer;

	public IndexCollection(String filename, String indexPath) {
		this.filename = filename;
		this.indexPath = indexPath;
	}

	public void index() throws IOException {
		File f = new File(filename);
		if (!f.exists()) {
			System.err.println("Filename " + filename + " does not exist");
			return;
		}
		process();
	}

	private void indexDoc(String tweetID, String sentiment, String topicID, String country, String gender, String urls,
			String text, String user_ID, String user_Name, String date, String hashtags, String indication)
			throws IOException {
		Document doc = new Document();
		doc.add(new TextField("TweetID", tweetID, Field.Store.YES));
		doc.add(new TextField("Sentiment", sentiment, Field.Store.YES));
		doc.add(new TextField("TopicID", topicID, Field.Store.YES));
		doc.add(new TextField("Country", country, Field.Store.YES));
		doc.add(new TextField("Gender", gender, Field.Store.YES));
		doc.add(new TextField("URLs", urls, Field.Store.YES));
		doc.add(new TextField("Text", text, Field.Store.YES));
		doc.add(new TextField("User_ID", user_ID, Field.Store.YES));
		doc.add(new TextField("User_Name", user_Name, Field.Store.YES));
		doc.add(new TextField("Date", date, Field.Store.YES));
		doc.add(new TextField("Hashtags", hashtags, Field.Store.YES));
		doc.add(new TextField("Indication", indication, Field.Store.YES));
		writer.addDocument(doc);
	}

	public void process() throws IOException {

		Path path = new File(indexPath).toPath();

		Directory dir = FSDirectory.open(path);
		Analyzer analyzer = new StandardAnalyzer();
		IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
		boolean create = true;

		if (create)
			iwc.setOpenMode(OpenMode.CREATE);
		else
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);


		writer = new IndexWriter(dir, iwc);

		try {
			CSVParser csvFileParser = CSVFormat.DEFAULT.parse(new FileReader(new File(filename)));
			for (CSVRecord csvRecord : csvFileParser) {
				indexDoc(csvRecord.get(0), csvRecord.get(1), csvRecord.get(2), csvRecord.get(3), csvRecord.get(4),
						csvRecord.get(5), csvRecord.get(6), csvRecord.get(7), csvRecord.get(8), csvRecord.get(9),
						csvRecord.get(10), csvRecord.get(11));

			}
		} catch (IOException e) {
			Logger.getLogger(IndexCollection.class.getName()).log(Level.SEVERE, null, e);
		} finally {
			writer.close();
		}
	}
}
