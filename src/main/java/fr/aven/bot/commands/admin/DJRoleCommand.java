package fr.aven.bot.commands.admin;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class DJRoleCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (event.getMessage().getMentionedRoles().size() == 0)
        {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        Role mentionnedRole = event.getMessage().getMentionedRoles().get(0);
        Main.getDatabase().setDjRole(mentionnedRole);

        event.getChannel().sendMessage(Main.getDatabase().getTextFor("dj.success", event.getGuild())+ mentionnedRole.getAsMention()).queue();
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
        return new MessageEmbed.Field("Set the DJ Role.", "Usage: `" + Constants.PREFIX + getInvoke() + "`", false);
    }

    @Override
    public String getInvoke() {
        return "djrole";
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {

    }
}
