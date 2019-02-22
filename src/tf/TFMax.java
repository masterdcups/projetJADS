package tf;

import org.apache.lucene.search.similarities.ClassicSimilarity;

public class TFMax extends ClassicSimilarity {

	private float f_max;

	public TFMax(float f_max) {
		super();
		this.f_max = f_max;
	}
	
	public float idf(long docFreq, long docCount) {
		IDF CalculIDF = new IDF(docFreq, docCount);
		return CalculIDF.idf();
	}

	public float tf(float f) {
		return f / f_max;
	}

}
