package uz.nt.articlepublishingservice.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uz.nt.articlepublishingservice.dto.ErrorDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;


import static java.util.stream.Collectors.toList;
import static uz.nt.articlepublishingservice.service.additional.AppStatusCodes.VALIDATION_ERROR_CODE;
import static uz.nt.articlepublishingservice.service.additional.AppStatusMessages.VALIDATION_ERROR;

@RestControllerAdvice
public class ExceptionHandlerResource {

    @ExceptionHandler
    public ResponseEntity<ResponseDto<Void>> validationError(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest()
                        .body(ResponseDto.<Void>builder()
                                .code(VALIDATION_ERROR_CODE)
                                .message(VALIDATION_ERROR)
                                .errors(e.getBindingResult().getFieldErrors()
                                        .stream()
                                        .map(f -> new ErrorDto(f.getField(), f.getDefaultMessage()))
                                        .collect(toList()))
                                .build());
    }

}