package uz.nt.articlepublishingservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.nt.articlepublishingservice.dto.ResponseDto;
import uz.nt.articlepublishingservice.exceptions.FileConvertingException;
import uz.nt.articlepublishingservice.model.Image;
import uz.nt.articlepublishingservice.repository.ImageRepository;
import uz.nt.articlepublishingservice.service.ImageService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    public String filePath(String folder, String ext) {
        java.io.File file = new java.io.File("userImageFile" + "/" + folder);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = UUID.randomUUID().toString();

        return file.getPath() + "/" + fileName + ext;
    }


    @Override
    public ResponseDto<Integer> fileUpload(Integer userId, MultipartFile file) {
        Image entity = new Image();
        entity.setUserId(userId);
        entity.setExt(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        List<Future<String>> futures = null;
        try {
            futures = executorService.invokeAll(
                    Arrays.asList(
                            () -> saveLargeSize(file, entity.getExt()),
                            () -> saveMediumSize(file, entity.getExt()),
                            () -> saveSmallSize(file, entity.getExt())
                    )
            );
        } catch (InterruptedException e) {
            throw new FileConvertingException("File converting exception: " + e.getMessage());
        }

        futures.stream()
                .map(result -> {
                    try {
                        return result.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new FileConvertingException(e.getMessage());
                    }
                })
                .forEach(r -> {
                    if (r.substring(21, 26).equalsIgnoreCase("LARGE")) {
                        entity.setPathLarge(r);
                    } else if (r.substring(21, 27).equalsIgnoreCase("MEDIUM")) {
                        entity.setPathMedium(r);
                    } else if (r.substring(21, 26).equalsIgnoreCase("SMALL")) {
                        entity.setPathSmall(r);
                    }
                });

        executorService.shutdownNow();


        try {
            Optional<Image> upImage = imageRepository.findByUserId(userId);
            upImage.ifPresent(image -> entity.setId(image.getId()));
            Image savedImage = imageRepository.save(entity);
            return ResponseDto.<Integer>builder()
                    .data(savedImage.getId())
                    .message("OK")
                    .success(true)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<Integer>builder()
                    .code(2)  //AppStatusCodes.DATABASE_ERROR_CODE
                    .message("Database error" + ": " + e.getMessage()) //AppStatusMessages.DATABASE_ERROR
                    .build();
        }
    }

    @Override
    public ResponseDto<byte[]> getFileById(Integer userId, String size) throws IOException {
        if (userId == null || size == null) {
            return ResponseDto.<byte[]>builder()
                    .message("Null value") //AppStatusMessages.NULL_VALUE
                    .code(-2) //AppStatusCodes.VALIDATION_ERROR_CODE
                    .build();
        }
        String imagePath = "";

        Optional<Image> optional = imageRepository.findByUserId(userId);

        if (optional.isEmpty()) {
            return ResponseDto.<byte[]>builder()
                    .message("Not found") //AppStatusMessages.NOT_FOUND
                    .code(-1) //AppStatusCodes.NOT_FOUND_ERROR_CODE
                    .build();
        }

        if (size.equalsIgnoreCase("LARGE")) {
            imagePath = optional.get().getPathLarge();
        }
        if (size.equalsIgnoreCase("MEDIUM")) {
            imagePath = optional.get().getPathMedium();
        }
        if (size.equalsIgnoreCase("SMALL")) {
            imagePath = optional.get().getPathSmall();
        }

        byte[] file = new FileInputStream(imagePath).readAllBytes();

        return ResponseDto.<byte[]>builder()
                .message("OK") //AppStatusMessages.OK
                .code(0) //AppStatusMessages.OK
                .data(file)
                .success(true)
                .build();
    }


    private String saveLargeSize(MultipartFile file, String ext) {
        String filePathLarge;
        try {
            Files.copy(file.getInputStream(), Path.of(filePathLarge = filePath("images//large", ext)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePathLarge;
    }

    private String saveMediumSize(MultipartFile file, String ext) {
        String filePathMedium;
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file.getInputStream());
            BufferedImage bufferedImage1 = resizeImage(bufferedImage, bufferedImage.getWidth() / 2, bufferedImage.getHeight() / 2);
            ImageIO.write(bufferedImage1, ext.substring(1), new java.io.File(filePathMedium = filePath("images//medium", ext)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePathMedium;
    }

    private String saveSmallSize(MultipartFile file, String ext) {
        String filePathSmall;
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file.getInputStream());
            BufferedImage bufferedImage1 = resizeImage(bufferedImage, bufferedImage.getWidth() / 2 / 2, bufferedImage.getHeight() / 2 / 2);
            ImageIO.write(bufferedImage1, ext.substring(1), new java.io.File(filePathSmall = filePath("images//small", ext)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePathSmall;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        return resizedImage;
    }

}
