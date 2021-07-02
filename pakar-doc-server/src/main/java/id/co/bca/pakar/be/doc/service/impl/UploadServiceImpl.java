package id.co.bca.pakar.be.doc.service.impl;

import id.co.bca.pakar.be.doc.dao.ImageRepository;
import id.co.bca.pakar.be.doc.dao.ThemeImageRepository;
import id.co.bca.pakar.be.doc.dto.UploadFileDto;
import id.co.bca.pakar.be.doc.model.Images;
import id.co.bca.pakar.be.doc.model.ThemeImage;
import id.co.bca.pakar.be.doc.service.UploadService;
import id.co.bca.pakar.be.doc.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class UploadServiceImpl implements UploadService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @Value("${upload.path.theme}")
    private String pathTheme;

    @Autowired
    ThemeImageRepository themeImageRepository;

    @Autowired
    ImageRepository imageRepository;

    @Override
    public UploadFileDto storeFile(MultipartFile file, String imageType, String username) {

        UploadFileDto uploadFileDto = new UploadFileDto();
        Images images = new Images();
        ThemeImage themeImage = new ThemeImage();

        String fileLocation = "D:/Project/BCA/PAKAR/UploadFile/";
        logger.info("fileLocation >> " + fileLocation);
        Path storageLocation = Paths.get(fileLocation);
        logger.info("Storage Location >> " + storageLocation);

        if (!Files.exists(storageLocation)) {
            try {
                Files.createDirectories(storageLocation);
            } catch (IOException ioe) {
                logger.error(ioe.getMessage());
            }
        }

        File convFile = new File(storageLocation.toString() + "/" + file.getOriginalFilename());
        FileOutputStream fos = null;

        try {
            convFile.createNewFile();
            fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            logger.info("Successfully created file >> " + convFile.getName());
        } catch (FileNotFoundException fnfe) {
            logger.error(fnfe.getMessage());
        } catch (IOException ioe) {
            logger.error(ioe.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileDownloadUri = fileLocation.concat(fileName);
        logger.debug("Checking fileDownloadUri >>>" + fileDownloadUri);

        images.setImageName(fileName);
        images.setUri(fileDownloadUri);
        images = imageRepository.save(images);

        themeImage.setCreatedBy(username);
        themeImage.setCreatedDate(new Date());
        themeImage.setDeleted(Constant.DEFAULT_DELETED);
        themeImage.setImageType(imageType);

        themeImage = themeImageRepository.save(themeImage);

        uploadFileDto.setImage_name(images.getImageName());
        uploadFileDto.setImageType(themeImage.getImageType());
        uploadFileDto.setId(images.getId());
        uploadFileDto.setUri(images.getUri());
        return uploadFileDto;
    }
}
