package com.vayana

import com.vayana.Users.autoIncrement
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table

data class User(val id: Int? =null, val name:String, val age:Int, val address:String)

object Users: Table() {
    val id: Column<Int> = integer("id").autoIncrement()
    val name: Column<String> = varchar("name", 255)
    val age: Column<Int> = integer("age")
    val address: Column<String> = varchar("address", 255)

    override val primaryKey = PrimaryKey(id, name = "PK_USER_IDs")

    fun toUser(row: ResultRow): User =
        User(
            id = row[Users.id],
            name = row[Users.name],
            age = row[Users.age],
            address = row[Users.address]
        )
}
