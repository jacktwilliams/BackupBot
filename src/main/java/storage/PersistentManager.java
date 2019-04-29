package storage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.PriorityQueue;

import constants.UserConfigConstants;
import probability.Probability;
import probability.QuestionableFile;

/*
 * Retrieve or save persistent data.  
 */
public class PersistentManager {
	public static final String recStoreLoc = UserConfigConstants.INSTALLDIR + "/persistent/recStore.dat";
	public static final String probStoreLoc = UserConfigConstants.INSTALLDIR + "/persistent/probStore.dat";
	public static final String questionableLoc = UserConfigConstants.INSTALLDIR + "/persistent/questionableStore.dat";

	@SuppressWarnings("unchecked")
	public static RecordStorage getFileRecords() throws ClassNotFoundException {
		if (UserConfigConstants.NOMEMORY) {
			System.out.println("NOMEMORY option turned on. Starting without file records.");
			return new RecordStorage();
		}
		File recStoreF = new File(recStoreLoc);
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
		File recStoreF = new File(recStoreLoc);
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
	
	public static void storeProbElem(Probability p) {
		File probStoreF = new File(probStoreLoc);
		try {
			FileOutputStream fout = new FileOutputStream(probStoreF);
			ObjectOutputStream storeOut = new ObjectOutputStream(fout);
			storeOut.writeObject(p);
			storeOut.close();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Probability getProbElem() {
		if (UserConfigConstants.NOMEMORY) {
			System.out.println("NOMEMORY option turned on. Returning fresh Bayes Net");
			return new Probability();
		}
		File probStoreF = new File(probStoreLoc);
		if (!probStoreF.exists()) {
			System.out.println("Can't find persistent probability element. Returning fresh Bayes Net");
			return new Probability();
		}
		
		FileInputStream fin;
		ObjectInputStream storeIn;
		Probability retVal;
		try {
			fin = new FileInputStream(probStoreF);
			storeIn = new ObjectInputStream(fin);
			retVal = (Probability) storeIn.readObject();
			storeIn.close();
			fin.close();
		} catch (Exception e) {
			e.printStackTrace(); //TODO developer logging.
			return new Probability();
		}
		
		return retVal;
	}
	
	public static void storeQuestionableFiles(PriorityQueue<QuestionableFile> p) {
		File questStoreF = new File(questionableLoc);
		try {
			FileOutputStream fout = new FileOutputStream(questStoreF);
			ObjectOutputStream storeOut = new ObjectOutputStream(fout);
			storeOut.writeObject(p);
			storeOut.close();
			fout.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static PriorityQueue<QuestionableFile> getQuestionableFiles() {
		if (UserConfigConstants.NOMEMORY) {
			return new PriorityQueue<QuestionableFile>();
		}
		File questStoreF = new File(questionableLoc);
		if (!questStoreF.exists()) {
			System.out.println("Can't find existing PriorityQueue<QuestionableFile> element.");
			return new PriorityQueue<QuestionableFile>();
		}
		
		FileInputStream fin;
		ObjectInputStream storeIn;
		PriorityQueue<QuestionableFile> retVal;
		try {
			fin = new FileInputStream(questStoreF);
			storeIn = new ObjectInputStream(fin);
			retVal = (PriorityQueue<QuestionableFile>) storeIn.readObject();
			storeIn.close();
			fin.close();
		} catch (Exception e) {
			e.printStackTrace(); //TODO developer logging.
			return new PriorityQueue<QuestionableFile>();
		}
		
		return retVal;
	}
}

