package fr.aven.bot.commands.modo;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MuteCommand extends ModoCommands {

    private Role mutedRole;
    private int counter = 0; //pr la bdd

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        TextChannel channel = event.getChannel();
        Message message = event.getMessage();
        Guild guild = event.getGuild();
        mutedRole = guild.getRolesByName("Muted", true).get(0);

        if (args.isEmpty()) {
            channel.sendMessage(Main.getDatabase().getTextFor("argsNotFound", event.getGuild())).queue();
            return;
        } else if (message.getMentionedUsers().isEmpty()) {
            channel.sendMessage(Main.getDatabase().getTextFor("mute.notMentionned", event.getGuild())).queue();

            return;
        }

        Member mutedMember = message.getMentionedMembers().get(0);

        if (message.toString().length() > 2) {

            String reason;

            StringBuilder reasonBuilder = new StringBuilder();
            for (int i = 1; i < args.size(); i++) {
                reasonBuilder.append(args.get(i));
            }
            reason = reasonBuilder.toString();
            Main.getDatabase().addMute(message.getMentionedUsers().get(0).getId(), event.getGuild().getId(), message.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), reason);
            guild.addRoleToMember(mutedMember, mutedRole).reason(reason).queue();

            MODOLOGGER.info(event.getAuthor().getName() + " muted " + message.getMentionedMembers().get(0).getEffectiveName() + " for: " + reason);

            channel.sendMessage(Main.getDatabase().getTextFor("mute.confirm", event.getGuild())).queue();

        }
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Mute a member\n", "Usage: `" + Constants.PREFIX + getInvoke() + " <@user> [reason]`", false);
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {}

    @Override
    public String getInvoke() {
        return "mute";
    }

    @Override
    public Type getType() {
        return super.getType();
    }

}
