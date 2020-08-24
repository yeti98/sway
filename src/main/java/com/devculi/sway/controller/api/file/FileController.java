package com.devculi.sway.controller.api.file;

import com.devculi.sway.annotations.RequireRoleLecturer;
import com.devculi.sway.business.shared.common.Constant;
import com.devculi.sway.business.shared.model.QuestionModel;
import com.devculi.sway.business.shared.utils.Entity2DTO;
import com.devculi.sway.dataaccess.entity.Question;
import com.devculi.sway.manager.service.interfaces.IQuestionService;
import com.devculi.sway.manager.service.interfaces.ISwayTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
@RequireRoleLecturer
public class FileController {
  @Autowired IQuestionService questionService;
  @Autowired ISwayTestService swayTestService;

  @PostMapping("/upload")
  @Transactional(rollbackFor = Exception.class)
  public List<QuestionModel> uploadFile(
      @RequestParam("file") MultipartFile file,
      @RequestParam(name = "operation") Integer fileOperation,
      @RequestParam("target") Long targetID) {
    if (fileOperation.equals(Constant.FILE_OPERATION.IMPORT_QUESTION)) {
      List<Question> questions =
          (List<Question>) questionService.importQuestionFormExcel(targetID, file);
      swayTestService.insertQuestions(targetID, questions);
      return questions.stream().map(Entity2DTO::question2DTO).collect(Collectors.toList());
    }
    return null;
  }
}
