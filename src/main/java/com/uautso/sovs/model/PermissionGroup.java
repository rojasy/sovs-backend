package com.uautso.sovs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "permission_groups")
@SQLDelete(sql = "UPDATE permission_groups SET deleted = true WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted = false")
public class PermissionGroup extends BaseEntity {

    @Column(name = "group_name")
    private String groupName;

    @OneToMany(mappedBy = "permissionGroup")
    private List<Permission> permissions = new ArrayList<>();

    @Override
    public String toString() {
        return "PermissionGroup{" +
                "groupName='" + groupName + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
