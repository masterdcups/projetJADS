package autre;

public class Phrase implements Comparable<Phrase>{

	private String texte;
	private float poids;
	
	public Phrase(String texte, float poids) {
		super();
		this.texte = texte;
		this.poids = poids;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public float getPoids() {
		return poids;
	}

	public void setPoids(float poids) {
		this.poids = poids;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(poids);
		result = prime * result + ((texte == null) ? 0 : texte.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Phrase other = (Phrase) obj;
		if (Float.floatToIntBits(poids) != Float.floatToIntBits(other.poids))
			return false;
		if (texte == null) {
			if (other.texte != null)
				return false;
		} else if (!texte.equals(other.texte))
			return false;
		return true;
	}

	@Override
	public int compareTo(Phrase o) {
		int comp = Float.compare(o.poids, poids);
		if(comp == 0) {
			return o.texte.compareTo(texte);
		}
		return comp;
	}	
	
}
