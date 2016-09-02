import java.util.Iterator;
import java.util.List;

public class FoldersStructure {
	String sourseFolderPath = null;
	String targetFolderPath = null;

	public void finalBuilder() {
		// возвращет список папок в исходной папке
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
				String fileDate = getFileInfoDateCreation(fileNameSourceFullPath);
				// в заданной папке,создает структуру папок основываясь на дате создания файла
				String finalPath = folderStructurForFileCreation(targetFolderPath, fileDate);
				// копирует файл в папку которая была созданана основании даты создания файла
				String fileNameFullTargetPath = copyFileToTargetFolder(finalPath, fileNameSourceFullPath);
				// сравнивает исходный файл и созданный, если равны удаляет оригинал
				if (compareSourceAndTargetFile(fileNameSourceFullPath, fileNameFullTargetPath)) {
					deleteSourceFile(fileNameSourceFullPath);
				}
			}
			//после того как все файлы из папки скопированны и удалены, удаляет саму папку
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

	private String folderStructurForFileCreation(String targetFolderPath, String fileDate) {
		return null;

	}

	private String getFileInfoDateCreation(String object) {
		return null;

	}

	private List<String> getListFilesAtFolder(String lowFolderPath) {
		return null;
	}

	private String diveDownLowLevelFolderStructure(String sourseFolderPath) {
		return null;
	}

}
