package constants;

public class PerformanceMeasure {
	/*
	 * Our performance measure ensures we don't back up files larger than 1/10 of the backup media.
	 * One chunk is 1/1000000 (one millionth) of the storage.
	 * By using 1/10 of the chunks you will have a -100000 penalty, meaning it can't be desirable to back up the file.
	 * 
	 * The unDesirable figure is then chosen based on the fact that a file with P(desirable) = .33 will be chosen for backup,
	 * 	while lower probabilities will not. This is to create an agent that values safety over complete accuracy. 
	 */
	public static int desirable = 100000;
	public static int unDesirable = -49999;
	public static int oneChunkConsumed = -1;
}
