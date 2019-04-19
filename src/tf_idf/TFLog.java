package tf_idf;

import org.apache.lucene.search.similarities.ClassicSimilarity;

public class TFLog extends ClassicSimilarity {

	public float idf(long docFreq, long docCount) {
		IDF CalculIDF = new IDF(docFreq, docCount);
		return CalculIDF.idf();
	}

	public float tf(float f) {
		return (float) (1 + Math.log(f));
	}

}
