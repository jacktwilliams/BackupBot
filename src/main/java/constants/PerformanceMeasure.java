package constants;

public class PerformanceMeasure {
	/*
	 * Our performance measure ensures we don't back up files larger than 1/10 of the backup media.
	 * One chunk is 1/1000000 (one millionth) of the storage.
	 * By using 1/10 of the chunks you will have a -100000 penalty, meaning it can't be desirable to back up the file.
	 */
	public static int desirable = 100000;
	public static int unDesirable = -80000;
	public static int oneChunkConsumed = -1;
}
