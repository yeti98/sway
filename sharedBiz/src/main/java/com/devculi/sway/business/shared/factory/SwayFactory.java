package com.devculi.sway.business.shared.factory;

import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.business.shared.validation.AuthValidator;
import com.devculi.sway.business.shared.validation.QuestionValidator;
import com.devculi.sway.business.shared.validation.UserValidator;

public final class SwayFactory {

  public static UserValidator getUserValidation() {
    return new UserValidator();
  }

  public static QuestionValidator getQuestionValidator() {
    return new QuestionValidator();
  }

  public static AuthValidator getAuthValidator() {
    return new AuthValidator();
  }

  public static Entity2DTO getModelMapper() {
    return new Entity2DTO();
  }
}
