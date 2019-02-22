package tf;

import org.apache.lucene.search.similarities.ClassicSimilarity;

public class TFTotal extends ClassicSimilarity {

	public float idf(long docFreq, long docCount) {
		IDF CalculIDF = new IDF(docFreq, docCount);
		return CalculIDF.idf();
	}

	public float tf(float freq) {
		return freq;
	}

}
