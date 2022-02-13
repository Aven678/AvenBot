const fs = require('fs')
const { REST } = require('@discordjs/rest')
const { Routes } = require('discord-api-types/v9')

class CommandManager {
    constructor(config, client, database) {
        this.config = config
        this.client = client
        this.database = database
    }

    deployCommands(commands) {
        let rest = new REST({ version: '9'}).setToken(this.config.token);

        rest.put(Routes.applicationCommands(this.config.clientId), {body: commands})
            .then(() => console.log('Commands registered.'))
            .catch(console.error);
    }
}

exports.CommandManager = CommandManager