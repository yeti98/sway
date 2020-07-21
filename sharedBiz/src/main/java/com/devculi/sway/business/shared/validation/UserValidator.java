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

  public void validateUpdateUser(UpsertUserRequest updateUserRequest) {
  }

  //	public static void validateAuthorization(AuthorizationRequestDTO request) throws
  // ValidationException{
  //		if(request == null)
  //			throw new ValidationException("Email/Mobile and Password input is required.");
  //
  //		if(StringUtils.isNullOrEmpty(request.getAccountName()))
  //			throw new ValidationException("Email/Mobile is required.");
  //
  //		if(StringUtils.isNullOrEmpty(request.getPassword()))
  //			throw new ValidationException("Password is required.");
  //	}
  //
  //	public static void validateRefreshToken(AuthorizationRefreshTokenDTO request) throws
  // ValidationException{
  //		if(request == null)
  //			throw new ValidationException("Email/Mobile and Refresh Token input is required.");
  //
  //		if(StringUtils.isNullOrEmpty(request.getUuid()))
  //			throw new ValidationException("UUID is required.");
  //
  //		if(StringUtils.isNullOrEmpty(request.getRefreshToken()))
  //			throw new ValidationException("Refresh Token is required.");
  //	}
}
