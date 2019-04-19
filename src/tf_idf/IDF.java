package tf_idf;

public class IDF {
	long docFreq;
	long docCount;

	public IDF(long docFreq, long docCount) {
		this.docFreq = docFreq;
		this.docCount = docCount;
	}

	/* On décommente l'IDF que l'on veut utiliser */

	/* idf total */
//	float idf() {
//		return (float) Math.log(docFreq);
//	}

	/* idf sum */
	// float idf () {
		// double temp = Math.log(docCount/docFreq);
		 //System.out.print("math = "+ temp);
		 
		 //return (float) temp;
	 //}

	/* idf sum-smooth */
	float idf () {
		return (float) Math.log(docFreq + 0.5/docCount + 1);
	}
	
	/* idf BIR */
	// float idf () {
	// return (float) Math.log(docFreq/docCount - docFreq);
	// }

	/* idf BIR-smooth*/
	// float idf () {
	// return (float) Math.log(docFreq + 0.5/docCount - docFreq + 0.5);
	// }
}
