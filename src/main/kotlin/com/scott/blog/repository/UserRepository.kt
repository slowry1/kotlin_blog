package com.scott.blog.repository

import com.scott.blog.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

// The UserRepository extends from the CrudRepository. It provides the type of the entity and its primary key.
// The CrudRepository implements basic CRUD operation, including count, delete, deleteById, save, saveAll, findById,
// and findAll. I have added custom ones bellow.
// Checkout @RepositoryRestResources
interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name : String) : Optional<User>   // look into Optional<User> and User? List<User> to get the first user with that name if doesnt exist will return empty list

    fun findByEmail(email : String) : List<User>
}