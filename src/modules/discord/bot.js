const { Client, Intents } = require('discord.js')
const config = require('../../config/config.json')

const client = new Client({ intents: [Intents.FLAGS.GUILDS,"GUILD_MEMBERS"]})

client.on('ready', () => {
    console.log(`${client.user.tag} is connected!`)
    client.user.setActivity('justaven.xyz', { type: "WATCHING" })
})

client.login(config.token).catch(e => console.log(e))

module.exports = client