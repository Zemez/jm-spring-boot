package com.javamentor.jm_spring_boot.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role implements Generic, Comparable, GrantedAuthority {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role", length = 15, unique = true, nullable = false, updatable = false)
    private String role;

    public Role() {
    }

    public Role(String role) {
        Assert.hasText(role, "A granted authority textual representation is required");
        this.role = role;
    }

    public Role(Long id, String role) {
        this(role);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Role) {
            return role.equals(((Role) obj).role);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return role.hashCode();
    }

    @Override
    public String toString() {
        return role;
    }

    @Override
    public int compareTo(Object obj) {
        int result;
        if (obj instanceof Role) {
            result = Objects.compare(role, ((Role) obj).getRole(), Comparator.comparing(String::toLowerCase));
        } else {
            result = Objects.compare(this, obj, Comparator.comparing(Objects::toString));
        }
        return result;
    }

}
