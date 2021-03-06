package fr.aven.bot.commands.modo;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MuteCommand extends ModoCommands {

    private Role mutedRole;
    private int counter = 0; //pr la bdd

    @Override
    public void handle(List<String> args, CommandEvent event) {

        TextChannel channel = event.getChannel();
        Message message = event.message();
        Guild guild = event.getGuild();

        if (!guild.getSelfMember().hasPermission(net.dv8tion.jda.api.Permission.MANAGE_ROLES))
        {
            channel.sendMessageEmbeds(new EmbedBuilder()
                    .setTitle(Main.getLanguage().getTextFor("error", event.getGuild()))
                    .setDescription(Main.getLanguage().getTextFor("hasNotPermission", event.getGuild()))
                    .setColor(Color.RED)
                    .setFooter("Command executed by "+event.getAuthor().getAsTag())
                    .build()
            ).queue();
            return;
        }

        if (guild.getRoleById(Main.getDatabase().getMuteRole(guild)) == null) {
            createRoleMute(event.getGuild());
        }

        mutedRole = guild.getRoleById(Main.getDatabase().getMuteRole(guild));

        if (args.isEmpty()) {
            channel.sendMessage(Main.getLanguage().getTextFor("argsNotFound", event.getGuild())).queue();
            return;
        } else if (message.getMentionedUsers().isEmpty()) {
            channel.sendMessage(Main.getLanguage().getTextFor("mute.notMentionned", event.getGuild())).queue();

            return;
        }

        Member mutedMember = message.getMentionedMembers().get(0);

        if (mutedMember.getId().equalsIgnoreCase(message.getAuthor().getId()))
        {
            channel.sendMessageEmbeds(new EmbedBuilder().setDescription("You can't mute yourself!").setColor(Color.RED).build()).queue();
            return;
        }

        if (message.toString().length() > 2) {

            String reason;

            StringBuilder reasonBuilder = new StringBuilder();
            for (int i = 1; i < args.size(); i++) {
                if (!reasonBuilder.toString().equalsIgnoreCase("")) reasonBuilder.append("\n");
                reasonBuilder.append(args.get(i));
            }
            reason = reasonBuilder.toString();
            Main.getDatabase().addMute(message.getMentionedUsers().get(0).getId(), event.getGuild().getId(), message.getAuthor().getId(), message.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME), reason);
            guild.addRoleToMember(mutedMember, mutedRole).reason(reason).queue();

            MODOLOGGER.info(event.getAuthor().getName() + " muted " + message.getMentionedMembers().get(0).getEffectiveName() + " for: " + reason);

            channel.sendMessage(Main.getLanguage().getTextFor("mute.confirm", event.getGuild()) + " : "+message.getMentionedMembers().get(0).getUser().getAsTag()).queue();

        }
    }

    public void createRoleMute(Guild guild)
    {
        guild.createRole()
                .setColor(Color.GRAY)
                .setName("Muted")
                .setMentionable(false)
                .setPermissions(net.dv8tion.jda.api.Permission.EMPTY_PERMISSIONS) //put off all perms
                .setPermissions(net.dv8tion.jda.api.Permission.MESSAGE_READ, net.dv8tion.jda.api.Permission.VIEW_CHANNEL)
                .queue(mutedRole -> {

                            for (TextChannel channel : guild.getTextChannels()) {

                                channel.createPermissionOverride(mutedRole).setDeny(
                                        net.dv8tion.jda.api.Permission.MESSAGE_WRITE,
                                        net.dv8tion.jda.api.Permission.MESSAGE_ADD_REACTION,
                                        net.dv8tion.jda.api.Permission.ADMINISTRATOR
                                ).reason("Establishing Muted Role")
                                        .queue();
                            }

                            Main.getDatabase().setMuteRole(mutedRole);
                            Main.logger.info("Muted Role created on server: " + guild.getName());
                        }
                );
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field(getDescription(), "Usage: `" + Constants.PREFIX + getInvoke() + " <@user> [reason]`", false);
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
    public String getDescription() {
        return "Mute a member";
    }

    @Override
    public Type getType() {
        return super.getType();
    }

    @Override
    public Collection<net.dv8tion.jda.api.Permission> requiredDiscordPermission() {
        return Arrays.asList(net.dv8tion.jda.api.Permission.MANAGE_ROLES, net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }

}
