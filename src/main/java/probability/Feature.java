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
	
	public Feature(FeatureTypes type, int size) {
		this.type = type;
		
		if (size < 10) {
			this.feature = "10KB";
		}
		else if (size < 100) {
			this.feature = "100KB";
		}
		else if (size < 1000) {
			this.feature = "1MB";
		}
		else if (size < 10000) {
			this.feature = "10MB";  
		}
		else if (size < 100000) {
			this.feature = "100MB";
		}
		else if (size < 1000000) {
			this.feature = "1GB";
		}
		else {
			this.feature = ">1GB";
		}
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
	
	public int hashCode() {
		return this.feature.hashCode();
	}
	
	public String toString() {
		return this.type.toString() + ": " + feature;
	}
	
	public FeatureTypes getFeatType() {
		return this.type;
	}
}
