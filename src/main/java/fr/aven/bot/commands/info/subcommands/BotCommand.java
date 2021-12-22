package fr.aven.bot.commands.info.subcommands;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.*;

public class BotCommand implements ICommand {

    @Override
    public void handle(List<String> args, CommandEvent event) {
        RuntimeMXBean rb = ManagementFactory.getRuntimeMXBean();

        Runtime rt = Runtime.getRuntime();
        long usedMB = (rt.totalMemory() - rt.freeMemory()) / 1024 / 1024;

        int users = event.getJDA().getUsers().size();

        /*for (Guild server : event.getJDA().getGuilds()) {
            for (Member member : server.getMembers()) {
                if (!member.getUser().isBot()) {
                    users++;
                }
            }
        }*/

        EmbedBuilder botBuilder = new EmbedBuilder();
        botBuilder.setAuthor(event.getJDA().getSelfUser().getName(), "https://www.justaven.xyz", event.getJDA().getSelfUser().getAvatarUrl());
        botBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
        botBuilder.addField("Bot Informations: ",
                "Creators ❱ " + event.getJDA().getUserById(Constants.OWNER).getAsMention()+
                        "\nUptime ❱ " + getTimeDiff(new Date(), Main.lastRestart) +
                        "\nVersion ❱ " + Constants.VERSION +
                        "\nLanguages ❱ Java, Kotlin" +
                        "\nLibraries used ❱ " + "JDA, OMDB, restcountries.eu, [deezer-api](https://github.com/alex-massa/deezer-api)",
                false);
        botBuilder.addField("Some Figures: ",
                "Servers ❱ " + event.getJDA().getGuilds().size() +
                        "\nUsers ❱ " + users +
                        "\nMemory Used ❱ " + usedMB + " MB" +
                        "\nPing ❱ " + event.getJDA().getRestPing().complete().intValue() + " ms",
                false);
        botBuilder.addField("Links: ",
                "Support Server ❱ " + "https://discord.gg/ntbdKjv" +
                        "\nInvite Link ❱ " + "https://invite.justaven.xyz" +
                        "\nWebsite ❱ " + "https://www.justaven.xyz",
                false);
        event.message().replyEmbeds(botBuilder.build()).queue();
    }

    private String getTimeDiff(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays + " d, " + parseTimeNumbs(diffHours) + " h, " + parseTimeNumbs(diffMinutes) + " min, " + parseTimeNumbs(diffSeconds) + " sec";
    }

    private String parseTimeNumbs(long time) {
        String timeString = time + "";
        if (timeString.length() < 2)
            timeString = "0" + time;
        return timeString;
    }

    @Override
    public String getInvoke() {
        return "botinfo";
    }

    @Override
    public String getDescription() {
        return "Get informations about the bot";
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field(getDescription(), "Usage: `" + Constants.PREFIX + "info " + getInvoke() + "`", false);
    }

    @Override
    public void onEvent(GenericEvent event) {}

    @Override
    public Collection<net.dv8tion.jda.api.Permission> requiredDiscordPermission() {
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }

    @Override
    public boolean haveEvent() {
        return false;
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
    public Collection<String> getAlias() {
        return Collections.emptyList();
    }

}
