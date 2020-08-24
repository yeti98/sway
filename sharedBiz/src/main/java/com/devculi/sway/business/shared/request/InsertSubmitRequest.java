package com.devculi.sway.business.shared.request;

import com.devculi.sway.business.shared.model.SwayClassModel;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import com.devculi.sway.sharedmodel.model.UserModel;

import java.time.LocalDateTime;

public class InsertSubmitRequest {
    private String swayUsername;
    private Long id;
    private String studentName;
    private String studentContact;
    private String studentNote;
    private TestType submitType;
    private boolean isChecked;
    private SwayClassModel swayClass;
    private String lecturerNote;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
