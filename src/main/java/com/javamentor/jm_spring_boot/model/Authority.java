package com.javamentor.jm_spring_boot.model;

import javax.persistence.*;
import java.util.Comparator;
import java.util.Objects;

@Entity
@Table(name = "authorities")
public class Authority implements Generic, Comparable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "authority", length = 15, unique = true, nullable = false, updatable = false)
    private String authority;

    public Authority() {
    }

    public Authority(String authority) {
        this.authority = authority;
    }

    public Authority(Long id, String authority) {
        this.id = id;
        this.authority = authority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return authority;
    }

    @Override
    public int compareTo(Object obj) {
        int result;
        if (obj instanceof Authority) {
            result = Objects.compare(authority, ((Authority) obj).getAuthority(), Comparator.comparing(String::toLowerCase));
        } else {
            result = Objects.compare(this, obj, Comparator.comparing(Objects::toString));
        }
        return result;
    }

}
