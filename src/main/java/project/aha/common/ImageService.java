package project.aha.common;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

	public String saveImage(MultipartFile multipartFile, Long id, String type) throws IOException {
		String originName = multipartFile.getOriginalFilename().toLowerCase();
		if (!originName.endsWith(".png") && !originName.endsWith(".jpg")) {
			throw new IOException("png 또는 jpg 파일만 업로드 가능합니다.");
		}
		String absolutePath = new File("").getAbsolutePath() + "\\";
		String path = "src/main/resources/images/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		String fileName = type + "-" + id + ".png";
		String imagePath = path + "/" + fileName;

		file = new File(absolutePath + imagePath);
		multipartFile.transferTo(file);
		return fileName;
	}
}
