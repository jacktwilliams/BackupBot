package constants;

public enum BackupStatus {
	KEPT, //files we decide to backup.
	IGNORED, //ignored is for files that we have been told not to back up
	UNDECIDED, //undecided is for files that we have decided not to back up at this time
}
