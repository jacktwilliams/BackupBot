import java.io.File;
import java.nio.file.Path;

public class FileRecord {
	private BackupStatus status = BackupStatus.UNDECIDED;
	private Path path;
	private boolean isDir;
	
	public FileRecord(String p) {
		path = new File(p).toPath();
		isDir = checkDirStatus();
	}
	
	private boolean checkDirStatus() {
		return path.toUri().toString().endsWith("/");
	}
	
	public String toString() {
		return path.toUri().toString();
	}
	
	public int hashCode() {
		return this.toString().hashCode();
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
		FileRecord x = (FileRecord) o;
		if(this.toString().equals(x.toString())) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean getDirStatus() {
		return isDir;
	}
}
