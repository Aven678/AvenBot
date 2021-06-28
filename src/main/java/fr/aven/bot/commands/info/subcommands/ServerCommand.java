package fr.aven.bot.commands.info.subcommands;

import fr.aven.bot.Constants;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ServerCommand implements ICommand {

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        Guild guild = event.getGuild();

        int humans = 0, bots = 0, total = 0;
        String createTime = guild.getTimeCreated().format(Constants.FORMATTER);
        //int online = 0, dnd = 0, idle = 0, invisible = 0;
        for (int i = 0; i < guild.getMembers().size(); i++) {
            if (guild.getMembers().get(i).getUser().isBot()) {
                bots++;
            } else {
                /*switch (guild.getMembers().get(i).getOnlineStatus()) {
                    case ONLINE:
                        online++;
                        break;
                    case IDLE:
                        idle++;
                        break;
                    case DO_NOT_DISTURB:
                        dnd++;
                        break;
                    case INVISIBLE:
                        invisible++;
                        break;
                }*/
                humans++;
            }
            total++;
        }

        //REGION
        String region;
        switch (guild.getRegion()) {
            case EU_WEST:
                region = "Western Europe";
                break;
            case EU_CENTRAL:
                region = "Central Europe";
                break;
            case US_WEST:
                region = "Western United-States";
                break;
            case US_EAST:
                region = "Eastern United-States";
                break;
            case US_SOUTH:
                region = "Southern United-States";
                break;
            case US_CENTRAL:
                region = "Central United-States";
                break;
            default:
                String regionTemp = guild.getRegion().toString().toLowerCase();
                char firstchar = guild.getRegion().toString().charAt(0);

                region = regionTemp.replace(regionTemp.charAt(0), firstchar);
                break;
        }

        //VERIFICATION
        String verif = "";
        switch (guild.getVerificationLevel()) {
            case VERY_HIGH:
                verif = "Very High";
                break;
            case HIGH:
                verif = "High";
                break;
            case MEDIUM:
                verif = "Medium";
                break;
            case LOW:
                verif = "Low";
                break;
            case NONE:
                verif = "None";
                break;
        }

        EmbedBuilder builder = new EmbedBuilder();
        builder.setThumbnail(guild.getIconUrl());
        builder.setAuthor(guild.getName(), null , guild.getIconUrl());
        builder.addField("General Informations:",
                "Owner ❱ " + guild.getOwner().getAsMention() +
                        "\nRegion Of Server ❱ " + region +
                        "\nID ❱ " + guild.getIdLong() +
                        "\nVerification Level ❱ " + verif +
                        "\nCreation Date ❱ " + createTime,
                false);

        builder.addField("Channels: ",
                "Total ❱ " + guild.getChannels().size() +
                        "\nCategories ❱ " + guild.getCategories().size() +
                        "\nStore ❱ " + guild.getStoreChannels().size() +
                        "\nText ❱ " + guild.getTextChannels().size() +
                        "\nVoice ❱ " + guild.getVoiceChannels().size(),
                true);

        builder.addField("Members: ",
                "Total ❱ " + total +
                        "\nHumans ❱ " + humans +
                        "\nBots ❱ " + bots,
                true);

        /*builder.addField("Members Status: ",
                "Online ❱ " + online +
                        "\nDo Not Disturb ❱ " + dnd +
                        "\nIdle ❱ " + idle +
                        "\nInvisible ❱ " + invisible,
                true);*/


        //String roles_collect = guild.getRoles().stream().map(Role::getAsMention).limit(10).collect(Collectors.joining(", "));
        //System.out.println(roles_collect.length());
        //builder.addField("Roles: ", guild.getRoles().size() == 0 ? "No roles found" : guild.getRoles().stream().map(Role::getAsMention).collect(Collectors.joining(", ")), true);
        //builder.addField("Public role: ", guild.getPublicRole().getAsMention(), true);

        builder.addField("Other informations: ", "Roles ❱ "+ guild.getRoles().size() +
                "\nEmojis ❱ " + (guild.getEmotes().size() == 0 ? "No emojis found" : String.valueOf(guild.getEmotes().size())) +
                "\nBoosts ❱ " + guild.getBoostCount() + " Boosts (Level " + guild.getBoostTier().getKey() + ")", true);
        //builder.addField("Emojis: ", ), true);

        event.getMessage().replyEmbeds(builder.build()).queue();
    }

    @Override
    public String getInvoke() {
        return "serverinfo";
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Get informations about the server", "Usage: `" + Constants.PREFIX + "info " + getInvoke() + "`", false);
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
        return Arrays.asList("si");
    }
}
