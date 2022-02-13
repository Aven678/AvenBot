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

data class GuildConfig(val _id: String, val lang: String?, val warnConfig: Map<*,*>, val roles: Map<*,*>, val activities: Map<*,*>)

class Firebase
{
    companion object {
        private var serviceAccount = FileInputStream("firebase.json")
        private val options: FirebaseOptions = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .setDatabaseUrl("https://aven-aac58-default-rtdb.europe-west1.firebasedatabase.app")
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

        /**
        fun test() {
            val docRef = database.collection("users").document("alovelace");
            var data = mutableMapOf<String, Any>()
            data.put("first", "Ada");
            data.put("last", "Lovelace");
            data.put("born", 1815);
            val result = docRef.set(data);
            System.out.println("Update time : " + result.get().getUpdateTime());
        }*/
    }

}