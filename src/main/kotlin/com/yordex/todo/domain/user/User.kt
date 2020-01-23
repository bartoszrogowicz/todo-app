package com.yordex.todo.domain.user

import javax.persistence.*
import javax.validation.constraints.Size

@Entity
@Table(name = "user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0,

        @Size(max = 50)
        var login: String ,

        @Size(min = 8, max = 50)
        @Column(name = "password_hash")
        var password: String,

        @Size(max = 50)
        @Column(name = "first_name")
        var firstName: String,

        @Size(max = 50)
        @Column(name = "last_name")
        var lastName: String,

        @ManyToMany
        @JoinTable(
                name = "user_permission",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "permission_name", referencedColumnName = "name")]
        )
        var permissions: MutableSet<Permission> = mutableSetOf()
)

@Entity
@Table(name = "permission")
data class Permission(
        @Id
        @Size(max = 50)
        var name: String
)
