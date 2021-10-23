package fr.aven.bot;

/*import fr.aven.bot.commands.fun.CatCommand;
import fr.aven.bot.commands.fun.DogCommand;
import fr.aven.bot.commands.util.search.SearchCommand;
import fr.aven.bot.commands.util.search.subcommands.CountryCommand;
import fr.aven.bot.commands.util.search.subcommands.ImdbCommand;*/
//import fr.aven.bot.commands.info.InfoCommand;
import fr.aven.bot.commands.admin.*;
import fr.aven.bot.commands.admin.announce.AnnounceCommand;
import fr.aven.bot.commands.fun.*;
import fr.aven.bot.commands.info.HelpCommand;
import fr.aven.bot.commands.info.subcommands.BotCommand;
import fr.aven.bot.commands.info.subcommands.ServerCommand;
import fr.aven.bot.commands.info.subcommands.UserCommand;
import fr.aven.bot.commands.modo.*;
import fr.aven.bot.commands.music.*;
/*import fr.aven.bot.commands.util.LmgtfyCommand;
import fr.aven.bot.commands.util.search.subcommands.SearchSubCommands;*/
import fr.aven.bot.commands.util.*;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();
    private final Map<String, ICommand> commandsAlias = new HashMap<>();
    private final Map<String, ICommand> commandsWithEvent = new HashMap<>();
    private final Logger COMMANDLOGGER = LoggerFactory.getLogger(this.getClass());

    CommandManager() {

        COMMANDLOGGER.info("Loading commands");

        //ADMIN COMMANDS
        addCommands(new AnnounceCommand(), new AutoRoleCommand(), new ClearConfigCommand(), new DJRoleCommand(), new LangCommand(), new Prefix(), new RolesCommand());

        //MUSIC COMMANDS
        addCommands(new BassCommand(), new JoinCommand(), new LyricsCommand(), new PlayCommand(), new PauseCommand(), new QueueCommand(), new SeekCommand(), new SkipCommand(), new ShuffleCommand(), new StopCommand(), new VolumeCommand());
        //MODO COMMANDS
        addCommands(new BanCommand(), new ClearCommand(), new KickCommand(), new ModlogsCommand(), new MuteCommand(), new UnbanCommand(), new UnmuteCommand(), new WarnCommand(), new WarnsCommand());
        //INFO COMMANDS
        addCommands(new HelpCommand(), new BotCommand(), new ServerCommand(), new UserCommand());
        //UTIL COMMANDS
        addCommands(new AFKCommand(), new InviteCommand(), new PatchnoteCommand());
        //addCommands(new LmgtfyCommand(), new HelpCommand(this), new SearchCommand());
        //FUN COMMANDS
        addCommands(new Base64Command(), new BetrayalCommand(), new BingoCommand(), new BobCommand(), new CatCommand(), new CfunCommand(), new ConfusedStonks(), new DogCommand(), new FakebanCommand(), new FakewarnCommand(), new FishingtonCommand(), new IssouCommand(), new NotStonksCommand(), new NsfwPictureCommand(), new OMDBCommand(), new PokerCommand(), new RollCommand(), new SayCommand(), new SearchCommand(), new StonksCommand(), new YoutubeTogetherCommand());
    }

    private void addCommand(ICommand command) {
        if (!commands.containsKey(command.getInvoke())) {
            if (command.haveEvent()) {
                commandsWithEvent.put(command.getInvoke(), command);
            }
            if (!command.getAlias().isEmpty()) command.getAlias().forEach(alias -> {
                commandsAlias.put(alias, command);
            });

            commands.put(command.getInvoke(), command);
        }
    }

    private void addCommands(ICommand... commands) {
        for (ICommand command : commands) {
            addCommand(command);
        }
    }

    public Collection<ICommand> getCommands() {
        return commands.values();
    }

    public ICommand getCommand(@NotNull String name) {
        return commands.containsKey(name) ? commands.get(name) : commandsAlias.get(name);
    }

    public void handleCommand(CommandEvent event) {
        //final String prefix = "&";
        /*String prefix = Constants.PREFIX;
        if (!prefixDefaultUsed)
            prefix = Main.getDatabase().getGuildPrefix(event.getGuild());//Constants.PREFIXES.get(event.getGuild().getIdLong());

        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(prefix), "").split(" +");
        final String invoke = split[0].toLowerCase();

        System.out.println(commandsAlias.size());*/

        if (!commands.containsKey(event.getInvoke()))
        {
            if (!commandsAlias.containsKey(event.getInvoke()))
                return;
        }

        if (!Main.getDatabase().checkPermission(event.getGuild(), event.getAuthor(), getCommand(event.getInvoke()).getPermission(), event.getChannel())) {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("You don't have the permission to execute this command.").setFooter("Command executed by " + event.getAuthor().getAsTag()).build()).queue();
            return;
        }

        //final List<String> args = Arrays.asList(event.split).subList(1, event.split.length);

        if (event.getArgs().size() != 0 && event.getArgs().get(0).equals("-help")) {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField(getCommand(event.getInvoke()).getHelp()).build()).queue();
        } else {
            if (event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
                if (!event.getGuild().getSelfMember().hasPermission(getCommand(event.getInvoke()).requiredDiscordPermission()))
                    if (!event.getGuild().getSelfMember().hasPermission(Permission.ADMINISTRATOR)) {
                        event.getChannel().sendMessage(Main.getLanguage().getTextFor("missingPermissions", event.getGuild())).queue();
                        return;
                    }
            }

            getCommand(event.getInvoke()).handle(event.getArgs(), event);
            COMMANDLOGGER.info("[Command] "+event.getAuthor().getAsTag()+" ("+event.getAuthor().getId()+") - "+event.getGuild().getName()+" ("+event.getGuild().getId()+") => "+(event.isMessageEvent() ? event.message().getContentRaw() : event.getSlashCommandEvent().getCommandString()+" (SlashCommand)"));
            //if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) event.getMessage().delete().queue();
        }
    }

    public Collection<ICommand> getCommandByType(ICommand.Type type) {
        Collection<ICommand> commands = new ArrayList<>();

        for (ICommand command : getCommands()) {
            if (command.getType().equals(type)) {
                commands.add(command);
            }
        }

        return commands;
    }

    public Collection<ICommand> getCommandsWithEvent() {
        return commandsWithEvent.values();
    }
}