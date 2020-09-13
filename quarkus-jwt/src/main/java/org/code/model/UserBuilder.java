package org.code.model;

public class UserBuilder {
    private String id;
    private String roles;
    private String name;

    public UserBuilder setId(String id) {
        this.id = id;
        return this;
    }

    public UserBuilder setRoles(String roles) {
        this.roles = roles;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public User createUser() {
        return new User(id, roles, name);
    }
}