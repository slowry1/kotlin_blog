package com.scott.blog

import org.springframework.data.jpa.repository.JpaRepository

// The UserRepository extends from the CrudRepository. It provides the type of the entity and its primary key.
// The CrudRepository implements basic CRUD operation, including count, delete, deleteById, save, saveAll, findById,
// and findAll. I have added custom ones bellow.
// Checkout @RepositoryRestResources
interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name : String) : User   // look into Optional<User> and User? List<User> to get the first user with that name if doesnt exist will return empy list

    fun findByEmail(email : String) : User
}