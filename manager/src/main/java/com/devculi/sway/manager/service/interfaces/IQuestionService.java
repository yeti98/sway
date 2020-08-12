package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.sharedmodel.request.UpsertQuestionRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Service
public interface IQuestionService {
    Question getQuestionByID(Long questionID);

    Question insertQuestion(UpsertQuestionRequest insertQuestionRequest);

    Question updateQuestion(Long questionId, UpsertQuestionRequest updateQuestionRequest);

    Long deleteQuestion(Long questionID);

    Collection<Question> importQuestionFormExcel(Long targetID, MultipartFile file);
}
