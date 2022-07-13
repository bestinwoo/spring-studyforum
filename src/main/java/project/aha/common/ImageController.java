package project.aha.common;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
	@GetMapping("/image/{fileName}")
	public ResponseEntity<Resource> getImageByPath(@PathVariable String fileName) {
		String absolutePath = new File("").getAbsolutePath() + "\\";
		String path = absolutePath + "src/main/resources/images/" + fileName;
		Resource resource = new FileSystemResource(path);

		if (!resource.exists()) {
			return ResponseEntity.notFound().build();
		}

		HttpHeaders header = new HttpHeaders();
		Path filePath = null;
		try {
			filePath = Paths.get(path);
			header.add("Content-Type", Files.probeContentType(filePath));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}

		return new ResponseEntity<>(resource, header, HttpStatus.OK);
	}
}
