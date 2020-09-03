package com.devculi.sway.business.shared.request;

import com.devculi.sway.sharedmodel.model.UserModel;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpsertPostRequest {

  private long id;
  private String coverPhoto;
  private String Menu;
  private String title;
  private String contents;
  private UserModel author;

  public UpsertPostRequest() {}

  public String getMenu() {
    return Menu;
  }

  public void setMenu(String menu) {
    Menu = menu;
  }

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
