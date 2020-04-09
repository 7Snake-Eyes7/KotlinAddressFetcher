package com.vayana

import arrow.core.*

data class Fault(val errMsg:String, val ex: Option<Throwable> = None, val args: Map<String, Any> = emptyMap())

fun getSqlUserName(): Either<Fault, String> = System.getenv("sqlUserName")?.right() ?: Fault(userNameRequired).left()
fun getSqlPassword(): Either<Fault, String> = System.getenv("sqlPassword")?.right() ?: Fault(passwordRequired).left()
fun getSqlUrl(): Either<Fault, String> = System.getenv("url")?.right() ?: Fault(urlRequired).left()
fun getSqlDriver(): Either<Fault, String> = System.getenv("driver")?.right() ?: Fault(driverRequired).left()

//if not set up in the run time environment, the default connection properties ll be as follows

val user0: String = "root"
val password0: String = "root"
val url0: String ="jdbc:mysql://localhost:3306/userDetails?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true"
val driver0: String = "com.mysql.jdbc.Driver"
