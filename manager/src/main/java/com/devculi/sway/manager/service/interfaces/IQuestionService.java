package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.sharedmodel.request.UpsertQuestionRequest;
import org.springframework.stereotype.Service;

@Service
public interface IQuestionService {
    Question getQuestionByID(Long questionID);

    Question insertQuestion(UpsertQuestionRequest insertQuestionRequest);

    Question updateQuestion(Long questionId, UpsertQuestionRequest updateQuestionRequest);

    Long deleteQuestion(Long questionID);
}
