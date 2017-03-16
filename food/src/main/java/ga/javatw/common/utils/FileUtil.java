package ga.javatw.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	public static String saveFile(MultipartFile file, String directory, InputStream in) throws IOException {


		checkDirectoryExist(directory);
		String newFilename = generateNewFile(file.getOriginalFilename());
		String saveFilePath = directory + "/" + newFilename;
		FileOutputStream out = new FileOutputStream(saveFilePath);

		IOUtils.copy(in, out);

		out.close();
		logger.debug("儲存路徑: " + saveFilePath);
		return newFilename;

	}



	private static void checkDirectoryExist(String directory) {
		File f = new File(directory);
		if (!f.exists()) {
			f.mkdirs();
		}

	}



	private static String generateNewFile(String filePath) {
		int point = filePath.lastIndexOf(".");
		String suffix = filePath.substring(point);
		String newFileName = UUID.randomUUID().toString();

		return newFileName + suffix;
	}
}
