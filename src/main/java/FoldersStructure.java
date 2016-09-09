import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

public class FoldersStructure {
	String sourseFolderPath = null;
	String targetFolderPath = null;
	String lastSubFolderPath = null;

	public void finalBuilder() {
		// возвращет список папок в исходной папке "sourseFolderPath"
		List<String> listTopLevelFolders = getListFolderTopLevel(sourseFolderPath);
		Iterator<String> listFolders = listTopLevelFolders.iterator();
		// проходит по всем папкам в исходной папке
		while (listFolders.hasNext()) {
			// заходит в папку, проверяет если там есть еще папки, заходит в
			// первую и так далее пока,
			// не дойдет до папки в которой не будет вложенных папок
			// возвращает имя папки короая не содержит других папок
			String lowFolderPath = diveDownLowLevelFolderStructure(listFolders.next());
			// возвращает лист путей к файлам с именами файлов, содеращимся в
			// папке
			List<String> listFilesFullPath = getListFilesAtFolder(lowFolderPath);
			Iterator<String> listPhoto = listFilesFullPath.iterator();
			// проходит по всем файлам в папке
			while (listPhoto.hasNext()) {
				// возваращет путь к файлу с именем файла
				String fileNameSourceFullPath = listPhoto.next();
				// возвращает информацию о файле, дату создания
				HashMap<String, String> fileDate = getFileInfoDateCreation(fileNameSourceFullPath);
				// в заданной папке,создает структуру папок основываясь на дате
				// создания файла
				String finalPath = folderStructurForFileCreation(targetFolderPath, fileDate);
				// копирует файл в папку которая была созданана основании даты
				// создания файла
				String fileNameFullTargetPath = copyFileToTargetFolder(finalPath, fileNameSourceFullPath);
				// сравнивает исходный файл и созданный, если равны удаляет
				// оригинал
				if (compareSourceAndTargetFile(fileNameSourceFullPath, fileNameFullTargetPath)) {
					deleteSourceFile(fileNameSourceFullPath);
				}
			}
			// после того как все файлы из папки скопированны и удалены, удаляет
			// саму папку
			deleteEmptyFolder(lowFolderPath);
		}
	}

	private void deleteSourceFile(String fileNameSourceFullPath) {

	}

	private boolean compareSourceAndTargetFile(String fileNameSourceFullPath, String fileNameFullTargetPath) {
		return false;
	}

	private void deleteEmptyFolder(String lowFolderPath) {

	}

	private List<String> getListFolderTopLevel(String sourseFolderPath2) {
		return null;
	}

	private String copyFileToTargetFolder(String finalPath, String fileNameSourceFullPath) {
		return null;

	}

	private String folderStructurForFileCreation(String targetFolderPath, HashMap<String, String> fileDate) {
		// Creation Folder YEAR
		File file = new File(targetFolderPath + fileDate.get("Year"));
		if (file.mkdir()) {
			System.out.println("Folder " + fileDate.get("Year") + " has been created.");
		} else {
			System.out.println("Folder " + fileDate.get("Year") + " was created previosly.");
		}

		// Creation Folder MONTH
		file = new File(targetFolderPath + fileDate.get("Year") + "/" + fileDate.get("Month"));
		if (file.mkdir()) {
			System.out.println("Folder " + fileDate.get("Month") + " has been created.");
		} else {
			System.out.println("Folder " + fileDate.get("Month") + " was created previosly.");
		}

		// Creation Folder DAY
		file = new File(
				targetFolderPath + fileDate.get("Year") + "/" + fileDate.get("Month") + "/" + fileDate.get("Day"));
		if (file.mkdir()) {
			System.out.println("Folder " + fileDate.get("Day") + " has been created.");
		} else {
			System.out.println("Folder " + fileDate.get("Day") + " was created previosly.");
		}
		String finalFolderPath = targetFolderPath + fileDate.get("Year") + "/" + fileDate.get("Month") + "/"
				+ fileDate.get("Day") + "/";

		return finalFolderPath;

	}

	private HashMap<String, String> getFileInfoDateCreation(String object) {

		File file = new File(object);
		HashMap<String, String> date = new HashMap<String, String>();
		Metadata metadata = null;
		try {
			metadata = ImageMetadataReader.readMetadata(file);
		} catch (ImageProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (Directory directory : metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				if (tag.getTagName().contains("Date/Time Original")) {
					date.put("Year", ((tag.getDescription()).substring(0, 4)));
					date.put("Month", ((tag.getDescription()).substring(5, 7)));
					date.put("Day", ((tag.getDescription()).substring(8, 10)));
				}
			}
			if (directory.hasErrors()) {
				for (String error : directory.getErrors()) {
					System.err.format("ERROR: %s", error);
				}

			}
		}
		return date;
	}

	private List<String> getListFilesAtFolder(String lowFolderPath) {
		File directory = new File(lowFolderPath);
		List<String> listFiles = new ArrayList<String>();
		String[] files = directory.list();
		for (int i = 0; i < files.length; i++) {
			listFiles.add(lowFolderPath + "/" + files[i]);
		}
		return listFiles;
	}

	private String diveDownLowLevelFolderStructure(String sourseFolderPath) {
		File file = new File(sourseFolderPath);
		String[] names = file.list();
		String folder = "folders is absent";

		for (String name : names) {
			if (new File(file.getPath() + "/" + name).isDirectory()) {
				folder = file.getPath() + "/" + name;
				break;
			}
		}

		if (!folder.equals("folders is absent")) {
			diveDownLowLevelFolderStructure(folder);
		} else {
			lastSubFolderPath = file.getPath();
		}
		return lastSubFolderPath;
	}

}
