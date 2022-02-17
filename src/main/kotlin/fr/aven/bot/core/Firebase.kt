package fr.aven.bot.core

import com.google.api.core.ApiFuture
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.firestore.QuerySnapshot
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.cloud.FirestoreClient
import com.google.gson.JsonObject
import net.dv8tion.jda.api.entities.Guild
import java.io.FileInputStream

data class GuildConfig(val _id: String, val lang: String?, val warnConfig: Map<String,*>, val roles: Map<String,Int>, val activities: Map<String, *>)

class Firebase(configuration: Config)
{
    private var serviceAccount = FileInputStream("firebase.json")
    private val options: FirebaseOptions = FirebaseOptions.builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .setDatabaseUrl(configuration.firebaseUrl)
        .build()

    private val app = FirebaseApp.initializeApp(options)
    private val database = FirestoreClient.getFirestore()

    private val guildCollection = database.collection("guilds")

    fun getGuildConfig(guild: Guild): GuildConfig? {
        val query = guildCollection.whereEqualTo("_id", guild.id).get().get()
        val document = query.documents.firstOrNull() ?: return null
        return GuildConfig(guild.id,
            document.getString("lang"),
            document.get("warnConfig") as Map<String, *>,
            document.get("roles") as Map<String, Int>,
            document.get("activities") as Map<String, *>
        )
    }

}