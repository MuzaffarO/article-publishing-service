package uz.nt.articlepublishingservice.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.service.ImageService;

import java.io.IOException;

@RestController
@RequestMapping("image")
@RequiredArgsConstructor
public class ImageResources {

    private final ImageService imageService;

    @PostMapping("upload")
    public ResponseDto<Integer> uploadFile(@RequestParam Integer userId, @RequestPart("file") MultipartFile file){
        return imageService.fileUpload(userId, file);
    }

    @GetMapping("get-user-image")
    public ResponseDto<byte[]> getFileById(@RequestParam Integer userId,@RequestParam String size) throws IOException {
        return imageService.getFileById(userId, size);
    }
}
