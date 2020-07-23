package fr.aven.bot.commands.info;

import fr.aven.bot.Constants;
import fr.aven.bot.commands.info.subcommands.BotCommand;
import fr.aven.bot.commands.info.subcommands.InfoSubCommands;
import fr.aven.bot.commands.info.subcommands.ServerCommand;
import fr.aven.bot.commands.info.subcommands.UserCommand;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InfoCommand extends InfoCommands {

    private Message _msg;
    private User _target;
    private Guild _guild;
    private List<InfoSubCommands> commands = new ArrayList<>();
    private List<String> invokes = new ArrayList<>();
    private List<MessageEmbed.Field> helps = new ArrayList<>();

    public InfoCommand() {
        if (BotCommand.class != null && UserCommand.class != null && ServerCommand.class != null) {
            commands.add(new UserCommand());
            commands.add(new ServerCommand());
            commands.add(new BotCommand());
        } else {
            return;
        }
    }

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        Message message = event.getMessage();
        Guild guild = event.getGuild();
        _guild = guild;
        _msg = message;

        for (ICommand command : commands) {
            invokes.add(command.getInvoke());
            helps.add(command.getHelp());
        }

        if (args.size() == 0)
        {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        System.out.println(args);

        if (invokes.contains(args.get(0))) {
            if (args.get(0).equals(invokes.get(0))) { //test if "info user <args>"
                StringBuilder namearg = new StringBuilder();
                for (int i = 1; i < args.size(); i++) {
                    namearg.append(args.get(i));
                    namearg.append(" ");
                }
                List<String> name = Arrays.asList(namearg.toString().split(" "));

                commands.get(0).handle(name, event);
            } else if (args.get(0).equals(invokes.get(1))) { //test if "info server"
                commands.get(1).handle(Arrays.asList(new String()), event);
            } else if (args.get(0).equals(invokes.get(2))) { //test if "info bot"
                commands.get(2).handle(Arrays.asList(new String()), event);
            } else if (args.get(1) != null && args.get(1).equals("-help")) {
                if (args.get(0).equals(invokes.get(0))) {
                    event.getChannel().sendMessage(new EmbedBuilder().addField(helps.get(0)).build()).queue();
                } else if (args.get(0).equals(invokes.get(1))) {
                    event.getChannel().sendMessage(new EmbedBuilder().addField(helps.get(1)).build()).queue();
                } else if (args.get(0).equals(invokes.get(2))) {
                    event.getChannel().sendMessage(new EmbedBuilder().addField(helps.get(2)).build()).queue();
                }
            }
        }
    }

    @Override
    public boolean haveEvent() {
        return true;
    }

    @Override
    public void onEvent(GenericEvent event) {
        for (InfoSubCommands command : commands) {
            command.onEvent(event);
        }
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Get informations about a user, a server or the bot", "Usage: `" + Constants.PREFIX + getInvoke() + " <bot/user/server> <@user/id>`", false);
    }

    @Override
    public String getInvoke() {
        return "info";
    }

    @Override
    public Type getType() {
        return super.getType();
    }
}
