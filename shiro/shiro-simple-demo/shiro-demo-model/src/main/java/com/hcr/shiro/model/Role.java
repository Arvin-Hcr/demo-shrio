package com.hcr.shiro.model;

import java.io.Serializable;
import java.util.Objects;

public class Role implements Serializable {
    private Long id;

    private String roleName;

    private String roleChineseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleChineseName() {
        return roleChineseName;
    }

    public void setRoleChineseName(String roleChineseName) {
        this.roleChineseName = roleChineseName == null ? null : roleChineseName.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;
        Role role = (Role) o;
        return Objects.equals(getId(), role.getId()) &&
                Objects.equals(getRoleName(), role.getRoleName()) &&
                Objects.equals(getRoleChineseName(), role.getRoleChineseName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRoleName(), getRoleChineseName());
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", roleChineseName='" + roleChineseName + '\'' +
                '}';
    }
}