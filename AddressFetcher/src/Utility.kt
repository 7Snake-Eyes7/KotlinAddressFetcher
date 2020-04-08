package com.vayana

import arrow.core.Either
import arrow.core.left
import arrow.core.right

// database connection properties

//typealias Fault = String

//val user: Either<Fault, String> = System.getenv("sqlUserName").right()?: urlRequired.left()
//val password: Either<Fault, String> = System.getenv("sqlPassword").right()?: passwordRequired.left()
//val url: Either<Fault, String> = System.getenv("url").right() ?: urlRequired.left()
//val driver: Either<Fault, String> = System.getenv("driver").right() ?: driverRequired.left()

val user:String = System.getenv("sqlUserName")?: throw Exception(userNameRequired)
val password:String = System.getenv("sqlPassword")?: throw Exception(passwordRequired)
val url:String = System.getenv("url")?: throw Exception(urlRequired)
val driver:String = System.getenv("driver")?: throw Exception(driverRequired)

//if not set up in the run time environment, the default connection properties ll be as follows

val user0: String = "root"
val password0: String = "root"
val url0: String ="jdbc:mysql://localhost:3306/userDetails?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true"
val driver0: String = "com.mysql.jdbc.Driver"
