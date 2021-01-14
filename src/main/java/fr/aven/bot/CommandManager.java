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
import fr.aven.bot.commands.info.InfoCommand;
import fr.aven.bot.commands.modo.*;
import fr.aven.bot.commands.music.*;
/*import fr.aven.bot.commands.util.LmgtfyCommand;
import fr.aven.bot.commands.util.search.subcommands.SearchSubCommands;*/
import fr.aven.bot.commands.util.InviteCommand;
import fr.aven.bot.util.ICommand;
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
    private final Map<String, ICommand> commandsWithEvent = new HashMap<>();
    private Logger COMMANDLOGGER = LoggerFactory.getLogger(this.getClass());

    CommandManager() {

        COMMANDLOGGER.info("Loading commands");

        //ADMIN COMMANDS
        addCommands(new AnnounceCommand(), new ClearConfigCommand(), new DJRoleCommand(), new LangCommand(), new Prefix(), new WarnConfig());

        //MUSIC COMMANDS
        addCommands(new BassCommand(), new JoinCommand(), new LyricsCommand(), new PlayCommand(), new PauseCommand(), new QueueCommand(), new SkipCommand(), new ShuffleCommand(), new StopCommand(), new VolumeCommand());
        //MODO COMMANDS
        addCommands(new BanCommand(), new KickCommand(), new ModlogsCommand(), new MuteCommand(), new UnbanCommand(), new UnmuteCommand(), new WarnCommand(), new WarnsCommand());
        //INFO COMMANDS
        addCommands(new HelpCommand(), new InfoCommand());
        //UTIL COMMANDS
        addCommands(new InviteCommand());
        //addCommands(new LmgtfyCommand(), new HelpCommand(this), new SearchCommand());
        //FUN COMMANDS
        addCommands(new CatCommand(), new CfunCommand(), new DogCommand(), new LmgtfyCommand(), new NsfwPictureCommand(), new SayCommand(), new StonksCommand());
    }

    private void addCommand(ICommand command) {
        if (!commands.containsKey(command.getInvoke())) {
            if (command.haveEvent()) {
                commandsWithEvent.put(command.getInvoke(), command);
            }
            commands.put(command.getInvoke(), command);

            Main.getDatabase().checkCmd(command);
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
        return commands.get(name);
    }

    public void handleCommand(GuildMessageReceivedEvent event, boolean prefixDefaultUsed) {
        //final String prefix = "&";
        String prefix = Constants.PREFIX;
        if (!prefixDefaultUsed)
            prefix = Main.getDatabase().getGuildPrefix(event.getGuild());//Constants.PREFIXES.get(event.getGuild().getIdLong());

        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(prefix), "").split(" +");
        final String invoke = split[0].toLowerCase();

        if (!commands.containsKey(invoke)) return;

        if (!Main.getDatabase().checkPermission(event.getGuild(), event.getAuthor(), getCommand(invoke).getPermission(), event.getChannel())) {
            event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("You don't have the permission to execute this command.").setFooter("Command executed by " + event.getAuthor().getAsTag()).build()).queue();
            return;
        }

        final List<String> args = Arrays.asList(split).subList(1, split.length);

        if (args.size() != 0 && args.get(0).equals("-help")) {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getCommand(invoke).getHelp()).build()).queue();
        } else {
            if (event.getGuild().getSelfMember().hasPermission(event.getChannel(), Permission.MESSAGE_WRITE)) {
                if (!event.getGuild().getSelfMember().hasPermission(getCommand(invoke).requiredDiscordPermission()))
                    if (!event.getGuild().getSelfMember().hasPermission(Permission.ADMINISTRATOR)) {
                        event.getChannel().sendMessage(Main.getDatabase().getTextFor("missingPermissions", event.getGuild())).queue();
                        return;
                    }
            }

            getCommand(invoke).handle(args, event);
            if (event.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_MANAGE)) event.getMessage().delete().queue();
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