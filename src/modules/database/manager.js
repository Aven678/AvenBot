const mongoose = require('mongoose')

let GuildModel = mongoose.model('guilds', {
    _id: String,
    lang: String,
    warnConfig: {
        limit: Number,
        type: String
    },
    roles: {
        autoRole: String,
        dj: String,
        mute: String
    },
    activity: {
        ban: String,
        join: String,
        leave: String,
        channel: String
    }
})

class Database {


    constructor(config) {
        this.config = config
    }

    init() {
        mongoose.connect(`mongodb://${this.config.db.host}:${this.config.db.port}/${this.config.db.name}`).then(() => console.log('Connected to the database!'))
    }

    guildConfig(guildID) {
        GuildModel.findOne().where('_id').gte(guildID).exec(async document => {
            if (document == null) {
                await this.createGuildConfig(guildID)
            }

            return GuildConfig(guildID, document)
        })
    }
    createGuildConfig(guildID) {
        let _doc = new GuildModel({
            _id: guildID,
            lang: 'en',
            warnConfig: {
                limit: 5,
                type: 'kick'
            },
            roles: {
                autoRole: null,
                dj: null,
                mute: null
            },
            activity: {
                ban: null,
                join: null,
                leave: null,
                channel: null
            }
        })

        _doc.save()
    }
}

class GuildConfig {
    constructor(id, document)
    {
        this.id = id
        this.document = document
        this.lang = document.get('lang')
        this.warnConfig = document.get('warns')
        this.roles = document.get('roles')
        this.activity = document.get('activity')
    }
}

exports.Database = Database