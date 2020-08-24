package com.devculi.sway.sharedmodel.exceptions;

import com.devculi.sway.sharedmodel.common.ExceptionMessage;

public class ExistedRecordException extends BaseRuntimeException {

  public ExistedRecordException(Class clazz, String... params) {
    super("");
    getExceptionMessage().add(ExceptionMessage.getString("exception.common.duplicate", params));
  }
}
