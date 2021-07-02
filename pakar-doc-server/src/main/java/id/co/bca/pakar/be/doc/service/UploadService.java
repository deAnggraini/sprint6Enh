package id.co.bca.pakar.be.doc.service;

import id.co.bca.pakar.be.doc.dto.UploadFileDto;
import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
    UploadFileDto storeFile(MultipartFile file, String imageType, String username);
}
