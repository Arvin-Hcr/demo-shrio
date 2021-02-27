package com.hcr.shiro.model;

import java.io.Serializable;
import java.util.Objects;

public class Permission implements Serializable {
    private Long id;

    private String permissionName;

    private String permissionChineseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    public String getPermissionChineseName() {
        return permissionChineseName;
    }

    public void setPermissionChineseName(String permissionChineseName) {
        this.permissionChineseName = permissionChineseName == null ? null : permissionChineseName.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Permission)) {
            return false;
        }
        Permission that = (Permission) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getPermissionName(), that.getPermissionName()) &&
                Objects.equals(getPermissionChineseName(), that.getPermissionChineseName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPermissionName(), getPermissionChineseName());
    }

    @Override
    public String toString() {
        return "Permission{" +
                "id=" + id +
                ", permissionName='" + permissionName + '\'' +
                ", permissionChineseName='" + permissionChineseName + '\'' +
                '}';
    }
}