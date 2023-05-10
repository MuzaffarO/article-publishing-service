package uz.nt.articlepublishingservice.service;

import uz.nt.articlepublishingservice.dto.CommentDto;
import uz.nt.articlepublishingservice.dto.ResponseDto;

import java.util.List;

public interface CommentService {
    ResponseDto<List<CommentDto>> viewAll();
    ResponseDto<CommentDto> addComment(CommentDto commentDto);
    ResponseDto<CommentDto> removeComment(Integer id);
}
