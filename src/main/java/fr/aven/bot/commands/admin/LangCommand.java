package fr.aven.bot.commands.admin;

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

public class LangCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        if (args.size() == 0)
        {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        if (!args.get(0).equalsIgnoreCase("en"))
            if (!args.get(0).equalsIgnoreCase("fr"))
            {
                event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
                return;
            }

        Main.getDatabase().setLang(event.getGuild(), args.get(0));
        event.getChannel().sendMessage(Main.getLanguage().getTextFor("lang.success", event.getGuild())).queue();
    }

    @Override
    public Type getType() {
        return Type.ADMIN;
    }

    @Override
    public Permission getPermission() {
        return Permission.ADMIN;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field(getDescription(), "Usage : `"+ Constants.PREFIX + getInvoke() + "` <en/fr>", false);
    }

    @Override
    public String getInvoke() {
        return "lang";
    }

    @Override
    public String getDescription() {
        return "Change the language of the bot.";
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
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_WRITE);
    }
}
