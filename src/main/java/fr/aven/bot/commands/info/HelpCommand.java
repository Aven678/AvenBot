package fr.aven.bot.commands.info;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class HelpCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        StringBuilder adminCommands = new StringBuilder();
        StringBuilder funCommands = new StringBuilder();
        StringBuilder infoCommands = new StringBuilder();
        StringBuilder modoCommands = new StringBuilder();
        StringBuilder musicCommands = new StringBuilder();
        StringBuilder utilCommands = new StringBuilder();

        for (ICommand command : Main.getCommandManager().getCommands())
        {
            switch (command.getType())
            {
                case ADMIN:
                    if (!adminCommands.toString().equalsIgnoreCase(""))
                        adminCommands.append(",");
                    adminCommands.append("`"+Constants.PREFIX+command.getInvoke()+"`");
                    break;
                case FUN:
                    if (!funCommands.toString().equalsIgnoreCase(""))
                        funCommands.append(",");
                    funCommands.append("`"+Constants.PREFIX+command.getInvoke()+"`");
                    break;
                case INFO:
                    if (!infoCommands.toString().equalsIgnoreCase(""))
                        infoCommands.append(",");
                    infoCommands.append("`"+Constants.PREFIX+command.getInvoke()+"`");
                    break;
                case INFO_SUB:
                    if (!infoCommands.toString().equalsIgnoreCase(""))
                        infoCommands.append(",");
                    infoCommands.append("`"+Constants.PREFIX+"info "+command.getInvoke()+"`");
                    break;
                case MODO:
                    if (!modoCommands.toString().equalsIgnoreCase(""))
                        modoCommands.append(",");
                    modoCommands.append("`"+Constants.PREFIX+command.getInvoke()+"`");
                    break;
                case MUSIC:
                    if (!musicCommands.toString().equalsIgnoreCase(""))
                        musicCommands.append(",");
                    musicCommands.append("`"+Constants.PREFIX+command.getInvoke()+"`");
                    break;
                case UTIL:
                    if (!utilCommands.toString().equalsIgnoreCase(""))
                        utilCommands.append(",");
                    utilCommands.append("`"+Constants.PREFIX+command.getInvoke()+"`");
                    break;
            }
        }

        EmbedBuilder builder =  new EmbedBuilder();
        builder.setAuthor("Commands list","https://avenbot.eu", event.getJDA().getSelfUser().getAvatarUrl());

        if (!adminCommands.toString().equalsIgnoreCase(""))
            builder.addField("Admin commands", adminCommands.toString(), false);

        if (!funCommands.toString().equalsIgnoreCase(""))
            builder.addField("Fun commands", funCommands.toString(), false);

        if (!infoCommands.toString().equalsIgnoreCase(""))
            builder.addField("Info commands", infoCommands.toString(), false);

        if (!modoCommands.toString().equalsIgnoreCase(""))
            builder.addField("Moderation commands", modoCommands.toString(), false);

        if (!musicCommands.toString().equalsIgnoreCase(""))
            builder.addField("Music commands", musicCommands.toString(), false);

        if (!utilCommands.toString().equalsIgnoreCase(""))
            builder.addField("Utils commands", utilCommands.toString(), false);

        builder.addField("For more informations about a command", "Do `-help` after a command : =help -help", false);
        builder.setFooter("AvenBot by Aven#1000", event.getJDA().getSelfUser().getAvatarUrl());

        event.replyEmbeds(builder.build());

    }

    @Override
    public Type getType() {
        return Type.INFO;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field(getDescription(), "Usage : `"+ Constants.PREFIX + getInvoke() + "`", false);
    }

    @Override
    public String getInvoke() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Sends the list of all commands.";
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {

    }

    @Override
    public Collection<net.dv8tion.jda.api.Permission> requiredDiscordPermission() {
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_WRITE, net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }
}
