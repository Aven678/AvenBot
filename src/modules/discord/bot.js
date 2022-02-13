const {Client, Intents} = require('discord.js')

class Bot {
    constructor(config) {
        this.config = config
        this.client = new Client({ intents: [Intents.FLAGS.GUILDS,"GUILD_MEMBERS"]})

        this.init()
    }

    init() {
        this.client.on('ready', () => {
            console.log(`${this.client.user.tag} is connected!`)
            this.client.user.setActivity('justaven.xyz', { type: "WATCHING" })
        })

        this.client.login(this.config.token).catch(e => console.log(e))
    }
}

exports.Bot = Bot