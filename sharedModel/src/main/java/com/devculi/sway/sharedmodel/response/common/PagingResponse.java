package com.devculi.sway.sharedmodel.response.common;

import java.util.ArrayList;
import java.util.Collection;

public class PagingResponse<T> {
  private int totalPage;
  private Collection<T> content;

  public PagingResponse(int totalPage, Collection<T> content) {
    this.totalPage = totalPage;
    this.content = content;
  }

  public static PagingResponse<Object> empty() {
    return new PagingResponse<>(0, new ArrayList<>());
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public Collection<T> getContent() {
    return content;
  }

  public void setContent(Collection<T> content) {
    this.content = content;
  }
}
