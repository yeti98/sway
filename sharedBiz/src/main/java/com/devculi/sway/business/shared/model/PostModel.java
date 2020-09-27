package com.devculi.sway.business.shared.model;

import com.devculi.sway.sharedmodel.model.UserModel;
import com.devculi.sway.utils.GsonUtils;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostModel {

    private long id;
    private String menu;
    private String coverPhoto;
    private String title;
    private String slug;
    private String contents;
    private UserModel author;
    private LocalDateTime createdAt;
    private String createDay;
    private LocalDateTime updatedAt;
    private String updateDay;

    public PostModel() {}

    public String getMenu() { return menu; }

    public void setMenu(String menu) { this.menu = menu; }

    public String getSlug() { return slug; }

    public void setSlug(String slug) { this.slug = slug; }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreateDay() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(createdAt);
    }

    public void setCreateDay(String createDay) {
        this.createDay = createDay;
    }

    public String getUpdateDay() {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(updatedAt);
    }

    public void setUpdateDay(String updateDay) {
        this.updateDay = updateDay;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getJsonString() { return GsonUtils.toJson(this); }

    public String getReadableMenu() {
        switch (this.menu) {
            case "English":
                return "Bài viết Tiếng Anh";
            case "Japanese":
                return "Bài viết Tiếng Nhật";
            case "Chinese":
                return "Bài viết Tiếng Trung";
            case "Korean":
                return "Bài viết Tiếng Hàn";
            case "Recruitment":
                return "Cơ hội việc làm";
            default:
        }
        return "Chưa phân loại";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }
}
