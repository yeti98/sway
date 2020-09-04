package com.devculi.sway.business.shared.utils;

import com.devculi.sway.business.shared.model.*;
import com.devculi.sway.dataaccess.entity.*;
import com.devculi.sway.sharedmodel.model.UserModel;
import org.modelmapper.ModelMapper;

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
