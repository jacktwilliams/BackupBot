package probability;

import java.io.Serializable;

public class FeatureCountTup implements Serializable {
	private static final long serialVersionUID = -8668919896296667620L;
	private Feature feature;
	private int countDesirable = 0;
	private int totalCount = 0;
	
	public FeatureCountTup(Feature f) {
		this.feature = f;
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
		FeatureCountTup x = (FeatureCountTup) o;
		if(this.feature.toString().equals(x.feature.toString())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public String toString() {
		return feature.toString() + " - " + String.valueOf(countDesirable) + " / " + String.valueOf(totalCount);
	}
	
	public void incrementDesirableCount() {
		++this.countDesirable;
	}
	
	public void decrementDesirableCount() {
		--this.countDesirable;
	}
	
	public void incrementTotal() {
		++this.totalCount;
	}
	
	public double getProbabilityOfDesirabilityForFeature() {
		return countDesirable / (double) totalCount;
	}
}
