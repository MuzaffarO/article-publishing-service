package uz.nt.articlepublishingservice.service;

import org.springframework.web.multipart.MultipartFile;
import uz.nt.articlepublishingservice.dto.ResponseDto;

import java.io.IOException;

public interface ImageService {
    ResponseDto<Integer> fileUpload(Integer userId, MultipartFile file);

    ResponseDto<byte[]> getFileById(Integer fileId, String size) throws IOException;

}
