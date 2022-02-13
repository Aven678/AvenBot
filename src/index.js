const {Bot} = require('./modules/discord/bot')
const {Database} = require('./modules/database/manager')
const {CommandManager} = require('./modules/commands/CommandManager')

const config = require('./config/config.json')

const manager = new CommandManager(config, new Bot(config), new Database(config));