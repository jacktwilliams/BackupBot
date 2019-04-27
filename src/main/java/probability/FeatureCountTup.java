package probability;

import java.io.Serializable;

import constants.FeatureTypes;

public class FeatureCountTup implements Serializable {
	private static final long serialVersionUID = -8668919896296667620L;
	private Feature feature;
	private int countDesirable = 1;
	private int totalCount = 1;
	
	public FeatureCountTup(Feature f) {
		this.feature = f;
		
		/*
		 * Common extensions are defined to start with P=1 in Probability.priorKnowledgeFeatCounts()
		 * Other extensions: let's start with 1 / 4. This way the extension can get to P=.5 with two manual adds.
		 * Sizes will start with 1 / 1. This way they don't have as much effect on the probability of desirability unless
		 * 	the user requests "IGNORE" on those files.
		 */
		if (f.getFeatType() == FeatureTypes.EXTENSION) {
			this.countDesirable = 1;
			this.totalCount = 4;
		}
	}
	
	public FeatureCountTup(Feature f, int countD, int total) {
		this.feature = f;
		this.countDesirable = countD;
		this.totalCount = total;
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
	
	public int hashCode() {
		return this.feature.toString().hashCode();
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
