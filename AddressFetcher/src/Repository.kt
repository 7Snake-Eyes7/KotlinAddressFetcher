package com.vayana

import arrow.core.Option
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

// database connection properties
val user:String? = System.getenv("sqlUserName")?: throw Exception(userNameRequired)
val password:String? = System.getenv("sqlPassword")?: throw Exception(passwordRequired)
val url:String? = System.getenv("url")?: throw Exception(urlRequired)
val driver:String? = System.getenv("driver")?: throw Exception(driverRequired)


data class User(val id: Int? =null, val name:String, val age:Int, val address:String)

object Users: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", 255)
    val age: Column<Int> = integer("age")
    val address: Column<String> = varchar("address", 255)

    override val primaryKey = PrimaryKey(id, name = "PK_USER_IDs")

    fun toUser(row: ResultRow): Option<User?> =
        Option(User(
            id = row[Users.id],
            name = row[Users.name],
            age = row[Users.age],
            address = row[Users.address]
        ))

}


