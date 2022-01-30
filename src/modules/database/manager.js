import mongoose from 'mongoose'

module.exports = (config) => {
    mongoose.connect(`mongodb://${config.db.host}:${config.db.port}/${config.db.name}`).then(() => console.log('Connected to the database!'))

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
    function guildConfig(guildID): GuildConfig {
        GuildModel.findOne().where('_id').gte(guildID).exec(async document => {
            if (document == null) {
                await createGuildConfig(guildID)
            }

            return GuildConfig(guildID, document)
        })
    }
    function createGuildConfig(guildID) {
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