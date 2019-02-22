package tf;

import org.apache.lucene.search.similarities.ClassicSimilarity;

public class TFFrac extends ClassicSimilarity {

	int k;

	public TFFrac(int k) {
		this.k = k;
	}

	public float idf(long docFreq, long docCount) {
		IDF CalculIDF = new IDF(docFreq, docCount);
		return CalculIDF.idf();
	}

	public float tf(float freq) {

		return freq / (freq + k);
	}

}
