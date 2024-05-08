package com.googledocs.googledocsbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.*;

@Document(collection = "documents")
public class Doc {

  @Id
  private String id;
  private String uid;
  private Long createdAt;
  private String title = "Untitled Document";
  private List content = Collections.emptyList();

  public String getId() {
    return id;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public Long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Long createdAt) {
    this.createdAt = createdAt;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List getContent() {
    return content;
  }

  public void setContent(List content) {
    this.content = content;
  }
}
