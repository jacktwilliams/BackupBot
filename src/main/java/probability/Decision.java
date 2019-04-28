package probability;

import java.util.HashSet;
import java.util.PriorityQueue;

import constants.PerformanceMeasure;
import drivers.BackupManagerImpl;
import storage.FileRecord;

public class Decision {
	
	public static PriorityQueue<BackupAction> getOrderedBackupList(HashSet<FileRecord> undecided, BackupManagerImpl bman) {
		PriorityQueue<BackupAction> backupChoices = new PriorityQueue<BackupAction>();
		Probability prob = bman.getProbElem();
		final int chunk = BackupManagerImpl.STORAGESIZE / 10000000;
		for (FileRecord rec : undecided) {
			double pDesirable = prob.getProbabilityOfDesirabilityForFile(rec);
			bman.addQuestionableFile(new QuestionableFile(rec, pDesirable));
			double pNotDesirable = 1 - pDesirable;
			int chunksConsumed = rec.getSize() / chunk; //TODO: optimization
			
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
