package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.dataaccess.repository.QuestionRepository;
import com.devculi.sway.manager.service.interfaces.IQuestionService;
import com.devculi.sway.manager.service.interfaces.ISwayTestService;
import com.devculi.sway.manager.service.threadpool.MainExecutor;
import com.devculi.sway.sharedmodel.exceptions.RecordNotFoundException;
import com.devculi.sway.sharedmodel.request.UpsertQuestionRequest;
import com.devculi.sway.utils.PropertyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class QuestionService implements IQuestionService {
  @Autowired QuestionRepository questionRepository;
  @Autowired FileService fileService;
  @Autowired ISwayTestService testService;

  @Override
  public Question getQuestionByID(Long questionID) {
    Optional<Question> byId = questionRepository.findById(questionID);
    return byId.orElseThrow(
        () -> {
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

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Collection<Question> importQuestionFormExcel(Long targetID, MultipartFile file) {
    Map<Integer, Object> integerObjectMap = fileService.readExcel(file);
    Objects.requireNonNull(integerObjectMap);

    List<Question> newQuestionList = new ArrayList<>();
    for (Integer next : integerObjectMap.keySet()) {
      List<List> questions = (List<List>) integerObjectMap.get(next);
      questions.forEach(
          data -> {
            CompletableFuture<Question> future =
                CompletableFuture.supplyAsync(
                        () -> {
                          return mapListStringToQuestion(data);
                        },
                        MainExecutor.getInstance())
                    .thenApplyAsync(
                        question -> {
                          if (question != null) {
                            return questionRepository.save(question);
                          }
                          return null;
                        },
                        MainExecutor.getInstance());

            try {
              Question question = future.get();
              if (question != null) {
                newQuestionList.add(question);
              }
            } catch (InterruptedException | ExecutionException e) {
              e.printStackTrace();
            }
          });
    }
    return newQuestionList;
  }

  @Override
  public List<Question> searchBy(String keyword, boolean isIgnoreCase) {
    if (isIgnoreCase) {
      keyword = "%" + keyword.toLowerCase() + "%";
    } else {
      keyword = "%" + keyword + "%";
    }
    return questionRepository.findByQuestionIdLike(keyword);
  }

  private Question mapListStringToQuestion(List data) {
    Question question = new Question();
    int size = data.size();
    question.setQuestionId(data.get(0).toString());
    String content = String.valueOf(data.get(1));
    if (!StringUtils.hasText(content)) return null;
    question.setContent(content);
    question.setAnswer(String.valueOf(data.get(2)));
    List<String> choices = new ArrayList<>();
    for (int idx = 1; idx < 5; idx++) { // Upto 5 choices
      if (2 + idx >= size) break;
      String text = String.valueOf(data.get(2 + idx));
      if (StringUtils.hasText(text)) {
        choices.add(text);
      }
    }
    question.setChoices(String.join(Question.DETERMINER, choices));
    if (7 < size) {
      question.setExplanation(String.valueOf(data.get(7)));
    }
    return question;
  }

  public Question getQuestionById(Long id) {
    Optional<Question> byId = questionRepository.findById(id);
    if (byId.isPresent()) {
      return byId.get();
    }
    throw new RecordNotFoundException(Question.class, "id", String.valueOf(id));
  }
}
