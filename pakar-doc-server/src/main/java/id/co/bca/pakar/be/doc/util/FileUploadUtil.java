package id.co.bca.pakar.be.doc.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUploadUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);
//    private static String UPLOADED_FOLDER = "/images/structures/";

    public static void saveFile(String uploadDir,
                                MultipartFile file) throws IOException {
        try {
            if (!Files.exists(Paths.get(uploadDir))) {
                logger.debug("create directory >> {}", Paths.get(uploadDir));
                Files.createDirectories(Paths.get(uploadDir));
            }
            // Get the file and save it somewhere
            logger.debug("read uploaded file");
            byte[] bytes = file.getBytes();
            Path path = Paths.get(uploadDir + file.getOriginalFilename());
            logger.debug("write to file >> {} ", path.toString());
            Files.write(path, bytes);
            logger.info("Successfully created file >> {}", path.getFileName().toString());
        } catch (IOException e) {
            throw new IOException("FAIL STORE FILE TO DIRECTORY", e);
        }
    }
}
