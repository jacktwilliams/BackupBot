import constants.FeatureTypes;

public class Feature {
	public FeatureTypes type;
	public String feature; //name of extension, size of file etc.
	
	public Feature(FeatureTypes type, String feature) {
		this.type = type;
		this.feature = feature;
	}
}
