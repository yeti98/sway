package com.devculi.sway.business.shared.utils;

import com.devculi.sway.business.shared.model.*;
import com.devculi.sway.dataaccess.entity.*;
import com.devculi.sway.sharedmodel.model.UserModel;
import org.modelmapper.ModelMapper;

import java.util.Arrays;

public final class Entity2DTO {
  private static final ModelMapper modelMapper = new ModelMapper();

  public static UserModel user2DTO(SwayUser user) {
    return modelMapper.map(user, UserModel.class);
  }

  public static SwayClassModel class2DTO(SwayClass swayClass) {
    return modelMapper.map(swayClass, SwayClassModel.class);
  }

  public static QuestionModel question2DTO(Question question) {
    return modelMapper.map(question, QuestionModel.class);
  }

  public static QuestionModel2 toQuestionModel2(Question qm) {
    QuestionModel2 qm2 = new QuestionModel2();
    qm2.setActive(qm.isActive());
    qm2.setAnswer(qm.getAnswer());
    qm2.setContent(qm.getContent());
    qm2.setExplanation(qm.getExplanation());
    qm2.setId(qm.getId());
    qm2.setQuestionId(qm.getQuestionId());
    qm2.setChoices(Arrays.asList(qm.getChoices().split(Question.DETERMINER)));
    qm2.setSelected("");
    return qm2;
  }

  public static PostModel post2DTO(Post post) {
    return modelMapper.map(post, PostModel.class);
  }

  public static SwayTestModel swayTest2DTO(SwayTest swayTest) {
    return modelMapper.map(swayTest, SwayTestModel.class);
  }

  public static LessonModel lesson2DTO(Lesson lesson) {
    return modelMapper.map(lesson, LessonModel.class);
  }

  public static CourseModel course2DTO(Course course) {
    return modelMapper.map(course, CourseModel.class);
  }
}
