package probability;

import java.util.HashSet;
import java.util.PriorityQueue;

import constants.PerformanceMeasure;
import storage.FileRecord;

public class Decision {
	
	public static PriorityQueue<BackupAction> getOrderedBackupList(HashSet<FileRecord> undecided, Probability prob) {
		PriorityQueue<BackupAction> backupChoices = new PriorityQueue<BackupAction>();
		final int mediaSize = 30016144;
		final int chunk = mediaSize / 10000000;
		for (FileRecord rec : undecided) {
			double pDesirable = prob.getProbabilityOfDesirabilityForFile(rec);
			double pNotDesirable = 1 - pDesirable;
			int chunksConsumed = rec.getSize() / chunk;
			
			int expectedUtilityKEEP = (int) ((pDesirable * PerformanceMeasure.desirable) + 
										 (pNotDesirable * PerformanceMeasure.unDesirable) + 
										 (chunksConsumed * PerformanceMeasure.oneChunkConsumed));
			
			//note that utility of skipping this file is 0.
			System.out.println("Expected utility of " + rec + ": " + expectedUtilityKEEP);
			if (expectedUtilityKEEP > 0) {
				backupChoices.add(new BackupAction(rec, expectedUtilityKEEP));
			}
		}
		return backupChoices;
	}
}
