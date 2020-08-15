package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.business.shared.model.SwayClassModel;
import com.devculi.sway.business.shared.request.UpsertClassRequest;
import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.stereotype.Service;

@Service
public interface IClassService {
  PagingResponse<SwayClassModel> getClassByPage(Integer page);

  SwayClass createClass();

  SwayClass getClassById(Long id);

  SwayClass updateClass(Long id, UpsertClassRequest upsertClassRequest);

  Long deleteClassById(Long id);
}
