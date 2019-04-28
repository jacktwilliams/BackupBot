package probability;
import java.io.Serializable;
import java.util.HashSet;

import constants.BackupStatus;
import constants.FeatureTypes;
import storage.FileRecord;

public class Probability implements Serializable {
	private static final long serialVersionUID = 8555873221847634332L;
	private int totalFiles = 1; //avoid division by zero
	private int totalDesirable = 1; //start with 1/2 probability.
	private HashSet<FeatureCountTup> features = new HashSet<FeatureCountTup>();
	//private HashSet<FeatureCountTup> featurePurgatory = new HashSet<FeatureCountTup>(); TODO: we still don't need this right?
	
	public Probability() {
		this.priorKnowledgeFeatCounts();
	}
	
	public void keepFile(FileRecord f) {
		HashSet<Feature> fileFeatures = f.getFeatures();
		for (Feature feat : fileFeatures) {
			FeatureCountTup featCount = getFeatureCountFromSetOrAdd(feat);
			
			if (f.getBackupStatus() == BackupStatus.IGNORED) {
				featCount.incrementDesirableCount();
				++this.totalDesirable;
			}
			else {
				featCount.incrementTotal();
				featCount.incrementDesirableCount();
				++this.totalFiles;
				++this.totalDesirable;
				//TODO: make sure no files already KEPT are getting here bc we will increment again.
			}
		}
		
		f.setBackupStatus(BackupStatus.KEPT);
	}
	
	public void ignoreFile(FileRecord f) {
		HashSet<Feature> fileFeatures = f.getFeatures();
		for (Feature feat : fileFeatures) {
			FeatureCountTup featCount = getFeatureCountFromSetOrAdd(feat);
			
			if (f.getBackupStatus() == BackupStatus.KEPT) {
				featCount.decrementDesirableCount(); //total was already incremented. Take back the desirability count
				--this.totalDesirable;
			}
			else {
				featCount.incrementTotal();
				++this.totalFiles;
			}
		}
		
		f.setBackupStatus(BackupStatus.IGNORED);
	}
	
	public double getProbabilityOfDesirabilityForFile(FileRecord f) {
		HashSet<Feature> features = f.getFeatures();
		double probAccumulation = 1;
		for (Feature feat: features) {
			FeatureCountTup featCount = getFeatureCountFromSetOrAdd(feat);
			probAccumulation *= featCount.getProbabilityOfDesirabilityForFeature();
		}
		return probAccumulation * (this.totalDesirable / (double) this.totalFiles);
	}
	
	private FeatureCountTup getFeatureCountFromSetOrAdd(Feature feat) {
		FeatureCountTup tmp = new FeatureCountTup(feat);
		boolean didNotExist = this.features.add(tmp); //add if not already exists
		

		FeatureCountTup existing = null;
		if (!didNotExist) {
			//featureCount exists. find it.
			for (FeatureCountTup tup : this.features) {
				if (tup.equals(tmp)) { //if tup tracks same feature we are looking at
					existing = tup;
				}
			}
		}
		else {
			existing = tmp; //else we just added this feature
		}
		return existing;
	}
	
	private void priorKnowledgeFeatCounts() {
		String[] extensions = {"txt", "png", "mp3", "java", "lisp", "js", "docx", "jpg", "xml", "py", "csv", "pdf"};
		for (String ext : extensions) {
			features.add(new FeatureCountTup(new Feature(FeatureTypes.EXTENSION, ext), 2, 2));
		}
	}
}
