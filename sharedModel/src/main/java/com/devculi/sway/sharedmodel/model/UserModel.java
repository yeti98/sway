package com.devculi.sway.sharedmodel.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserModel {
    private Long id;
    private String name;
    private String username;
    private String avatar;
    private String description;
    private String status;
    private String type;

    public UserModel(Long id, String name, String username, String avatar, String description, String status, String type) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.avatar = avatar;
        this.description = description;
        this.status = status;
        this.type = type;
    }
}
