package com.arvin.shiro.model;

import java.io.Serializable;
import java.util.Objects;

public class Theacher implements Serializable {
    private Long id;

    private String username;

    private String password;

    private String salt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Theacher)) return false;
        Theacher theacher = (Theacher) o;
        return Objects.equals(getId(), theacher.getId()) &&
                Objects.equals(getUsername(), theacher.getUsername()) &&
                Objects.equals(getPassword(), theacher.getPassword()) &&
                Objects.equals(getSalt(), theacher.getSalt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getSalt());
    }

    @Override
    public String toString() {
        return "Theacher{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}