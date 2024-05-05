package com.googledocs.googledocsbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

  @Id
  private String id;
  private String name;
  private String email;
  private String profilePic;

  public String getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public void setProfilePic(String profilePic) {
    this.profilePic= profilePic;
  }

  public String getProfilePic() {
    return profilePic;
  }
}
