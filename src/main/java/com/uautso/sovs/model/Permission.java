package com.uautso.sovs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "permissions")
@SQLDelete(sql = "UPDATE permissions SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
public class Permission extends BaseEntity implements Serializable {

    @Column(name = "permission_name", unique = true)
    private String name;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "group_name")
    private String group;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    @JsonIgnore
    @ManyToOne
    private PermissionGroup permissionGroup;

    public Permission(String name, String displayName, String group) {
        this.name = name;
        this.displayName = displayName;
        this.group = group;
    }

    @Override
    public String toString() {
        return "Permission{" +
                "name='" + name + '\'' +
                ", displayName='" + displayName + '\'' +
                ", group='" + group + '\'' +
                ", roles=" + roles +
                '}';
    }
}
