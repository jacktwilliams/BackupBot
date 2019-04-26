package probability;
import java.io.Serializable;

import constants.FeatureTypes;

public class Feature implements Serializable {
	private static final long serialVersionUID = -1623186715301806923L;
	public FeatureTypes type;
	public String feature; //name of extension, size of file etc.
	
	public Feature(FeatureTypes type, String feature) {
		this.type = type;
		this.feature = feature;
	}
	
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}
		Feature x = (Feature) o;
		if(this.toString().equals(x.toString())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return this.type.toString() + ": " + feature;
	}
}
