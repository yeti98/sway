package com.devculi.sway.manager.service.interfaces;

import com.devculi.sway.business.shared.model.SwayClassModel;
import com.devculi.sway.business.shared.request.UpsertClassRequest;
import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IClassService {
  PagingResponse<SwayClassModel> getClassByPage(Integer page);

  SwayClass createClass();

  SwayClass getClassById(Long id);

  SwayClass getClassBySlug(String slug);

  SwayClass updateClass(Long id, UpsertClassRequest upsertClassRequest);

  Long deleteClassById(Long id);

  List<SwayClass> getJoinedClasses();

  List<SwayClass> searchBy(String keyword, boolean isIgnoreCase);

  boolean isRegistered(SwayClass swayClass);
}
