package uz.nt.articlepublishingservice.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
            summary = "Save user image in three formats",
            method = "POST",
            description = "Need to send image",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "File info",
                    content = @Content(mediaType = "multipart/form-data")),
            responses = {@ApiResponse(responseCode = "200", description = "OK")}
    )
    @PostMapping("upload")
    public ResponseDto<Integer> uploadFile(@RequestParam Integer userId, @RequestPart("file") MultipartFile file){
        return imageService.fileUpload(userId, file);
    }

    @Operation(
            summary = "Gets a image by image ID",
            method = "GET",
            description = "Need to send the image ID and size (LARGE, MEDIUM, SMALL) to receive this image",
            requestBody = @io.swagger.v3.oas.annotations.parameters.
                    RequestBody(description = "File info",
                    content = @Content(mediaType = "application/json")),
            responses = {
                    @ApiResponse(responseCode = "200", description = "OK"),
                    @ApiResponse(responseCode = "400", description = "Invalid id or size supplied"),
                    @ApiResponse(responseCode = "404", description = "File not found")
            }
    )
    @GetMapping("get-user-image")
    public ResponseDto<byte[]> getFileById(@RequestParam Integer userId,@RequestParam String size) throws IOException {
        return imageService.getFileById(userId, size);
    }
}
