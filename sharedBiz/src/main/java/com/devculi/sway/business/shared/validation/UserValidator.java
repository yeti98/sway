package com.devculi.sway.business.shared.validation;

import com.devculi.sway.business.shared.AbstractValidator;
import com.devculi.sway.business.shared.common.Message;
import com.devculi.sway.business.shared.exceptions.ValidationException;
import com.devculi.sway.sharedmodel.request.UpsertUserRequest;

public final class UserValidator extends AbstractValidator {

  // TODO: Hoàn thiện validate
  public void validateInsertUser(UpsertUserRequest insertUserRequest) {
    if (insertUserRequest == null) {
      getMessageDescription().add(Message.getString("validation.field.invalidFormat", "name"));
      throw new ValidationException(this.buildValidationMessage());
    }
  }

  public void validateUpdateUser(UpsertUserRequest updateUserRequest) {}
}
