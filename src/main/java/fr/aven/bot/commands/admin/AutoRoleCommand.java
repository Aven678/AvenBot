package fr.aven.bot.commands.admin;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class AutoRoleCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event)
    {
        if (args.isEmpty())
        {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        if (args.get(0).equalsIgnoreCase("off"))
        {
            Main.getDatabase().setAutoRole(event.getGuild(), null);
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().setDescription("✅ The autorole has been disabled").build()).queue();
            return;
        }

        if (event.message().getMentionedRoles().isEmpty())
        {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        Role role = event.message().getMentionedRoles().get(0);
        Main.getDatabase().setAutoRole(event.getGuild(), role.getId());
        event.getChannel().sendMessageEmbeds(new EmbedBuilder().setDescription("✅ The autorole has been set to "+role.getName()).build()).queue();
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
        return new MessageEmbed.Field(getDescription(), "Usage: `"+ Constants.PREFIX + getInvoke() +" <@Role/off>`", false);
    }

    @Override
    public String getInvoke() {
        return "autorole";
    }

    @Override
    public String getDescription() {
        return "Set the autorole";
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
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }
}
