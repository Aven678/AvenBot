package fr.aven.bot;

/*import fr.aven.bot.commands.fun.CatCommand;
import fr.aven.bot.commands.fun.DogCommand;
import fr.aven.bot.commands.util.search.SearchCommand;
import fr.aven.bot.commands.util.search.subcommands.CountryCommand;
import fr.aven.bot.commands.util.search.subcommands.ImdbCommand;*/
//import fr.aven.bot.commands.info.InfoCommand;
import fr.aven.bot.commands.admin.ClearConfigCommand;
import fr.aven.bot.commands.admin.DJRoleCommand;
import fr.aven.bot.commands.admin.Prefix;
import fr.aven.bot.commands.info.HelpCommand;
import fr.aven.bot.commands.info.InfoCommand;
import fr.aven.bot.commands.info.InfoCommands;
import fr.aven.bot.commands.modo.BanCommand;
import fr.aven.bot.commands.modo.KickCommand;
import fr.aven.bot.commands.modo.MuteCommand;
import fr.aven.bot.commands.modo.UnmuteCommand;
import fr.aven.bot.commands.music.*;
/*import fr.aven.bot.commands.util.HelpCommand;
import fr.aven.bot.commands.util.LmgtfyCommand;
import fr.aven.bot.commands.util.search.subcommands.SearchSubCommands;*/
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
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
        addCommands(new DJRoleCommand(), new Prefix(), new ClearConfigCommand());

        //MUSIC COMMANDS
        addCommands(new JoinCommand(), new LyricsCommand(), new StopCommand(), new PlayCommand(), new PauseCommand(), new QueueCommand(), new ShuffleCommand(), new SkipCommand(), new VolumeCommand());
        //MODO COMMANDS
        addCommands(new BanCommand(), new KickCommand(), new MuteCommand(), new UnmuteCommand());
        //INFO COMMANDS
        addCommands(new InfoCommand(), new HelpCommand());
        //UTIL COMMANDS
        //addCommands(new LmgtfyCommand(), new HelpCommand(this), new SearchCommand());
        //FUN COMMANDS
        //addCommands(new CatCommand(), new DogCommand());
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

    void handleCommand(GuildMessageReceivedEvent event, boolean prefixDefaultUsed) {
        //final String prefix = "&";
        String prefix = Constants.PREFIX;
        if (!prefixDefaultUsed)
            prefix = Main.getDatabase().getGuildPrefix(event.getGuild());//Constants.PREFIXES.get(event.getGuild().getIdLong());

        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(prefix), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            if (!Main.getDatabase().checkPermission(event.getGuild(), event.getAuthor(), commands.get(invoke).getPermission()))
            {
                event.getChannel().sendMessage(new EmbedBuilder().setColor(Color.RED).setTitle("Error").setDescription("You don't have the permission to execute this command.").setFooter("Command executed by "+event.getAuthor().getAsTag()).build()).queue();
                return;
            }

            final List<String> args = Arrays.asList(split).subList(1, split.length);

            if (args.size() != 0 && args.get(0).equals("-help")) {
                event.getChannel().sendMessage(new EmbedBuilder().addField(commands.get(invoke).getHelp()).build()).queue();
            } else {
                event.getChannel().sendTyping().queue();
                commands.get(invoke).handle(args, event);
            }
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