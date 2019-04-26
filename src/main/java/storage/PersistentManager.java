package storage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

/*
 * Retrieve or save persistent data.  
 */
public class PersistentManager {
	
	@SuppressWarnings("unchecked")
	public static RecordStorage getFileRecords() throws ClassNotFoundException {
		File recStoreF = new File("BackupBot/recordStore.dat");
		if (!recStoreF.exists()) {
			return new RecordStorage();
		}
		
		FileInputStream fin;
		ObjectInputStream storeIn;
		RecordStorage retVal;
		try {
			fin = new FileInputStream(recStoreF);
			storeIn = new ObjectInputStream(fin);
			retVal = (RecordStorage) storeIn.readObject();
			storeIn.close();
			fin.close();
		} catch (Exception e) {
			e.printStackTrace(); //TODO developer logging.
			return new RecordStorage();
		}
		
		return retVal;
	}
	
	public static void storeFileRecords(RecordStorage recStore) {
		File recStoreF = new File("BackupBot/recordStore.dat");
		try {
			FileOutputStream fout = new FileOutputStream(recStoreF);
			ObjectOutputStream storeOut = new ObjectOutputStream(fout);
			storeOut.writeObject(recStore);
			storeOut.close();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
