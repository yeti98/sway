package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.dataaccess.repository.QuestionRepository;
import com.devculi.sway.manager.service.interfaces.IQuestionService;
import com.devculi.sway.sharedmodel.exceptions.RecordNotFoundException;
import com.devculi.sway.sharedmodel.request.UpsertQuestionRequest;
import com.devculi.sway.utils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class QuestionService implements IQuestionService {
  @Autowired QuestionRepository questionRepository;

  @Override
  public Question getQuestionByID(Long questionID) {
    Optional<Question> byId = questionRepository.findById(questionID);
    return byId.orElseThrow(() -> {
      return new RecordNotFoundException(Question.class, "id", questionID.toString());
    });
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Question insertQuestion(UpsertQuestionRequest insertQuestionRequest) {
    Question question = new Question();
    question.setAnswer(insertQuestionRequest.getAnswer());
    question.setChoices(String.join(Question.DETERMINER, insertQuestionRequest.getChoices()));
    question.setContent(insertQuestionRequest.getContent());
    question.setExplanation(insertQuestionRequest.getExplanation());
    question.setActive(insertQuestionRequest.isActive());
    question.setQuestionId(insertQuestionRequest.getQuestionId());
    questionRepository.save(question);
    return question;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Question updateQuestion(Long questionId, UpsertQuestionRequest updateQuestionRequest) {
    Question question = getQuestionById(questionId);
    String[] nullPropertiesString = PropertyUtils.getNullPropertiesString(updateQuestionRequest);
    BeanUtils.copyProperties(updateQuestionRequest, question, nullPropertiesString);
    question.setChoices(String.join(Question.DETERMINER, updateQuestionRequest.getChoices()));
    questionRepository.save(question);
    return question;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Long deleteQuestion(Long questionID) {
    Question question = getQuestionById(questionID);
    questionRepository.delete(question);
    return question.getId();
  }

  public Question getQuestionById(Long id) {
    Optional<Question> byId = questionRepository.findById(id);
    if (byId.isPresent()) {
      return byId.get();
    }
    throw new RecordNotFoundException(Question.class, "id", String.valueOf(id));
  }
}
