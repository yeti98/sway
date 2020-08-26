package com.devculi.sway.business.shared.request;

import com.devculi.sway.business.shared.model.SwayClassModel;
import com.devculi.sway.dataaccess.entity.enums.TestType;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
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

    public String getSwayUsername() {
        return swayUsername;
    }

    public void setSwayUsername(String swayUsername) {
        this.swayUsername = swayUsername;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentContact() {
        return studentContact;
    }

    public void setStudentContact(String studentContact) {
        this.studentContact = studentContact;
    }

    public String getStudentNote() {
        return studentNote;
    }

    public void setStudentNote(String studentNote) {
        this.studentNote = studentNote;
    }

    public TestType getSubmitType() {
        return submitType;
    }

    public void setSubmitType(TestType submitType) {
        this.submitType = submitType;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public SwayClassModel getSwayClass() {
        return swayClass;
    }

    public void setSwayClass(SwayClassModel swayClass) {
        this.swayClass = swayClass;
    }

    public String getLecturerNote() {
        return lecturerNote;
    }

    public void setLecturerNote(String lecturerNote) {
        this.lecturerNote = lecturerNote;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
