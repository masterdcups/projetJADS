import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class QuerySimple {

	String filename;
	String titleString;
	String indexPath;

	public QuerySimple(String indexPath) {
		this.indexPath = indexPath;
	}

	public ScoreDoc[] process(String querystr, int nb) throws IOException, ParseException {
		
		StandardAnalyzer analyzer = new StandardAnalyzer();
		
		Path path = new File(indexPath).toPath();
		Directory index = FSDirectory.open(path);

		Query q = new QueryParser("Text", analyzer).parse(querystr);

		int hitsPerPage = nb;
		IndexReader reader = DirectoryReader.open(index);

		IndexSearcher searcher = new IndexSearcher(reader);
		TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage);
		searcher.search(q, collector);
		ScoreDoc[] hits = collector.topDocs().scoreDocs;

		System.out.println("Found " + hits.length + " hits of " + collector.getTotalHits() + ".");
		
		for (int i = 0; i < hits.length; ++i) {
			int docId = hits[i].doc;
			Document d = searcher.doc(docId);
			System.out.println((i + 1) + ". " + d.get("Text"));
		}

		reader.close();
		return hits;
	}
}
