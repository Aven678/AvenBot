package fr.aven.bot.commands.modo;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.entity.Warn;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class WarnsCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        if (event.message().getMentionedUsers().isEmpty())
        {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        if (args.get(0).equalsIgnoreCase("clear"))
        {
            Main.getDatabase().clearWarns(event.getGuild(), event.message().getMentionedUsers().get(0));
            event.message().addReaction("✅").queue();
            return;
        }

        User _target = event.message().getMentionedUsers().get(0);

        List<Warn> warns = Main.getDatabase().listWarns(_target.getId(), event.getGuild().getId());

        EmbedBuilder builder = new EmbedBuilder();
        int numCase = 0;

        for (Warn warn : warns)
        {
            numCase++;;
            User mod = event.getJDA().getUserById(warn.getModeratorID());
            builder.addField("Case "+numCase+": Warn", "Moderator: "+mod.getName()+"#"+mod.getDiscriminator() +"\nReason: "+warn.getReason()+"\nDate: "+warn.getDateTime(), false);
        }

        builder.setAuthor("Warns : "+_target.getName()+"#"+_target.getDiscriminator(), "https://justaven.xyz", _target.getAvatarUrl());
        builder.setFooter("AvenBot by Aven#1000");

        event.getChannel().sendMessageEmbeds(builder.build()).queue();
    }

    @Override
    public Type getType() {
        return Type.MODO;
    }

    @Override
    public Permission getPermission() {
        return Permission.MODO;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("List all warns for a member.", "Usage: `"+ Constants.PREFIX + getInvoke() + " [clear] <@user>`", false);
    }

    @Override
    public String getInvoke() {
        return "warns";
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
