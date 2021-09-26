package fr.aven.bot.modules.database

import org.ktorm.database.Database

class DatabaseManager(url: String, user: String, pwd: String)
{
    init {
        val db = Database.connect(url, "com.mysql.jdbc.Driver", user, pwd)
    }


}