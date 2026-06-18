package com.buynow.entity;

import com.buynow.enums.RoleType;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleType roleName;

    @OneToMany(mappedBy = "role")
    private List<User> users;
}
