package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.manager.service.interfaces.IQuestionService;
import com.devculi.sway.sharedmodel.request.UpsertQuestionRequest;
import org.springframework.stereotype.Service;

@Service
public class QuestionService implements IQuestionService {
    @Override
    public Question getQuestionByID(Long questionID) {
        return null;
    }

    @Override
    public Question insertQuestion(UpsertQuestionRequest insertQuestionRequest) {
        return null;
    }

    @Override
    public Question updateQuestion(Long questionId, UpsertQuestionRequest updateQuestionRequest) {
        return null;
    }

    @Override
    public Question deleteQuestion(Long questionID) {
        return null;
    }
}
