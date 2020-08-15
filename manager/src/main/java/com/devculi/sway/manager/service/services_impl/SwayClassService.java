package com.devculi.sway.manager.service.services_impl;

import com.devculi.sway.business.shared.model.SwayClassModel;
import com.devculi.sway.business.shared.request.UpsertClassRequest;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.SwayClass;
import com.devculi.sway.dataaccess.entity.SwayUser;
import com.devculi.sway.dataaccess.repository.SwayClassRepository;
import com.devculi.sway.manager.service.interfaces.IClassService;
import com.devculi.sway.manager.service.interfaces.IUserService;
import com.devculi.sway.sharedmodel.exceptions.RecordNotFoundException;
import com.devculi.sway.sharedmodel.model.UserModel;
import com.devculi.sway.sharedmodel.response.common.PagingResponse;
import com.devculi.sway.utils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SwayClassService implements IClassService {
  @Autowired SwayClassRepository classRepository;
  @Autowired IUserService userService;
  @Autowired CourseService courseService;

  @Value("${site.admin.pagination.limit.class}")
  private Integer ClassPerPage;

  @Override
  public PagingResponse<SwayClassModel> getClassByPage(Integer page) {
    Pageable pageable = PageRequest.of(page, ClassPerPage, Sort.by("createdAt").descending());
    Page<SwayClass> all = classRepository.findAll(pageable);
    return new PagingResponse<>(
        all.getTotalPages(),
        all.getContent().stream().map(Entity2DTO::class2DTO).collect(Collectors.toList()));
  }

  @Override
  public SwayClass createClass() {
    SwayClass swayClass = new SwayClass();
    classRepository.save(swayClass);
    return swayClass;
  }

  @Override
  public SwayClass getClassById(Long id) {
    Optional<SwayClass> byId = classRepository.findById(id);
    return byId.orElseThrow(
        () -> new RecordNotFoundException(SwayClass.class, "id", id.toString()));
  }

  @Override
  public SwayClass updateClass(Long id, UpsertClassRequest upsertClassRequest) {
    SwayClass classById = getClassById(id);
    Set<String> nullPropertiesString = PropertyUtils.getNullProperties(upsertClassRequest);
    if (!nullPropertiesString.contains("classId")) {
      classById.setClassId(upsertClassRequest.getClassId());
    }
    if (!nullPropertiesString.contains("name")) {
      classById.setName(upsertClassRequest.getName());
    }
    if (!nullPropertiesString.contains("minScore")) {
      classById.setMinScore(upsertClassRequest.getMinScore());
    }
    if (!nullPropertiesString.contains("course")) {
      Long courseId = upsertClassRequest.getCourse().getId();
      classById.setCourse(courseService.getCourseById(courseId));
    }
    ArrayList<SwayUser> students = new ArrayList<>();
    List<Long> studentIdList =
        upsertClassRequest.getStudents().stream()
            .map(UserModel::getId)
            .collect(Collectors.toList());
    studentIdList.forEach(
        uID -> {
          SwayUser userById = userService.getUserById(uID);
          if (!students.contains(userById)) {
            students.add(userById);
          }
        });
    classById.setStudents(students);
    ArrayList<SwayUser> lecturers = new ArrayList<>();
    List<Long> lecturerIdList =
        upsertClassRequest.getLecturers().stream()
            .map(UserModel::getId)
            .collect(Collectors.toList());
    lecturerIdList.forEach(
        uID -> {
          SwayUser userById = userService.getUserById(uID);
          if (!lecturers.contains(userById)) {
            lecturers.add(userById);
          }
        });
    classRepository.save(classById);
    return classById;
  }

  @Override
  public Long deleteClassById(Long id) {
    SwayClass classById = getClassById(id);
    Long deletedId = classById.getId();
    classRepository.delete(classById);
    return deletedId;
  }

  @Override
  public List<SwayClass> getJoinedClasses() {
    try {
      List<SwayClass> classes = userService.getJoinedClasses();
      return classes;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }
}
