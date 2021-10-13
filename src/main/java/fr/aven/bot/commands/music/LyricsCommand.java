package fr.aven.bot.commands.music;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.music.GuildMusicManager;
import fr.aven.bot.modules.music.PlayerManager;
import fr.aven.bot.modules.core.ICommand;
import fr.aven.bot.modules.music.lyrics.GeniusAPI;
import fr.aven.bot.modules.music.lyrics.Lyrics;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.explodingbush.ksoftapi.entities.Lyric;

import java.awt.*;
import java.util.*;
import java.util.List;

public class LyricsCommand implements ICommand
{

    public static void sendLyrics(GuildMessageReceivedEvent event, Lyrics lyrics)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Main.getLanguage().getTextFor("lyrics.title", event.getGuild()) + lyrics.getTitle(), "https://justaven.xyz", lyrics.getCoverURL());

        if (lyrics.getText().length() > MessageEmbed.TEXT_MAX_LENGTH)
        {
            String[] lyricsLine = lyrics.getText().split("\n");
            int totalLine = lyricsLine.length;
            StringBuilder firstLyrics = new StringBuilder();
            StringBuilder secondPart = new StringBuilder();

            System.out.println(lyricsLine.length);
            int i1 = 0;
            while (firstLyrics.length() < 2048) {
                if (!firstLyrics.toString().equalsIgnoreCase(""))
                    firstLyrics.append("\n");
                firstLyrics.append(lyricsLine[i1]);
                i1 = i1 + 1;
            }

            if (firstLyrics.length() > 2048){
                i1 = i1 - 1;
                firstLyrics.setLength(firstLyrics.length() - lyricsLine[i1].length());
            }

            while (secondPart.length() < 2048)
            {
                if (i1 < totalLine)
                {

                if (!secondPart.toString().equalsIgnoreCase(""))
                    secondPart.append("\n");
                    secondPart.append(lyricsLine[i1]);
                i1 = i1 + 1;
                } else {
                    break;
                }
            }

            EmbedBuilder secondPartBuilder = new EmbedBuilder();
            EmbedBuilder thirdBuilder = new EmbedBuilder();

            if (secondPart.length() > 2048)
            {
                StringBuilder thirdPart = new StringBuilder();
                i1 = i1 -1;
                secondPart.setLength(secondPart.length() - lyricsLine[i1].length());

                while (thirdPart.length() < 2048)
                {
                    if (i1 < totalLine)
                    {

                        if (!thirdPart.toString().equalsIgnoreCase(""))
                            thirdPart.append("\n");
                        thirdPart.append(lyricsLine[i1]);
                        i1 = i1 + 1;
                    } else {
                        break;
                    }
                }

                thirdBuilder.setDescription(thirdPart);
                thirdBuilder.setFooter("Lyrics by Genius");
            } else {
                secondPartBuilder.setFooter("Lyrics by Genius");
            }

            builder.setDescription(firstLyrics.toString());
            secondPartBuilder.setDescription(secondPart.toString());

            sendLyrics(event.getChannel(), builder, secondPartBuilder, thirdBuilder);


        } else {
            builder.setDescription(lyrics.getText());
            builder.setFooter("Lyrics by Genius");
            sendLyrics(event.getChannel(), builder);
        }
    }

    private void sendLyrics(CommandEvent event, Lyrics lyrics)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Main.getLanguage().getTextFor("lyrics.title", event.getGuild()) + lyrics.getTitle(), "https://justaven.xyz", lyrics.getCoverURL());

        if (lyrics.getText().length() > MessageEmbed.TEXT_MAX_LENGTH)
        {
            String[] lyricsLine = lyrics.getText().split("\n");
            int totalLine = lyricsLine.length;
            StringBuilder firstLyrics = new StringBuilder();
            StringBuilder secondPart = new StringBuilder();

            System.out.println(lyricsLine.length);
            int i1 = 0;
            while (firstLyrics.length() < 2048) {
                if (!firstLyrics.toString().equalsIgnoreCase(""))
                    firstLyrics.append("\n");
                firstLyrics.append(lyricsLine[i1]);
                i1 = i1 + 1;
            }

            if (firstLyrics.length() > 2048){
                i1 = i1 - 1;
                firstLyrics.setLength(firstLyrics.length() - lyricsLine[i1].length());
            }

            while (secondPart.length() < 2048)
            {
                if (i1 < totalLine)
                {

                    if (!secondPart.toString().equalsIgnoreCase(""))
                        secondPart.append("\n");
                    secondPart.append(lyricsLine[i1]);
                    i1 = i1 + 1;
                } else {
                    break;
                }
            }

            EmbedBuilder secondPartBuilder = new EmbedBuilder();
            EmbedBuilder thirdBuilder = new EmbedBuilder();

            if (secondPart.length() > 2048)
            {
                StringBuilder thirdPart = new StringBuilder();
                i1 = i1 -1;
                secondPart.setLength(secondPart.length() - lyricsLine[i1].length());

                while (thirdPart.length() < 2048)
                {
                    if (i1 < totalLine)
                    {

                        if (!thirdPart.toString().equalsIgnoreCase(""))
                            thirdPart.append("\n");
                        thirdPart.append(lyricsLine[i1]);
                        i1 = i1 + 1;
                    } else {
                        break;
                    }
                }

                thirdBuilder.setDescription(thirdPart);
                thirdBuilder.setFooter("Lyrics by Genius");
            } else {
                secondPartBuilder.setFooter("Lyrics by Genius");
            }

            builder.setDescription(firstLyrics.toString());
            secondPartBuilder.setDescription(secondPart.toString());

            sendLyrics(event.getChannel(), builder, secondPartBuilder, thirdBuilder);


        } else {
            builder.setDescription(lyrics.getText());
            builder.setFooter("Lyrics by Genius");
            sendLyrics(event.getChannel(), builder);
        }
    }

    public static void sendLyrics(InteractionHook buttonMessage, Lyrics lyrics)
    {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Main.getLanguage().getTextFor("lyrics.title", buttonMessage.getInteraction().getGuild()) + lyrics.getTitle(), "https://justaven.xyz", lyrics.getCoverURL());

        if (lyrics.getText().length() > MessageEmbed.TEXT_MAX_LENGTH)
        {
            String[] lyricsLine = lyrics.getText().split("\n");
            int totalLine = lyricsLine.length;
            StringBuilder firstLyrics = new StringBuilder();
            StringBuilder secondPart = new StringBuilder();

            System.out.println(lyricsLine.length);
            int i1 = 0;
            while (firstLyrics.length() < 2048) {
                if (!firstLyrics.toString().equalsIgnoreCase(""))
                    firstLyrics.append("\n");
                firstLyrics.append(lyricsLine[i1]);
                i1 = i1 + 1;
            }

            if (firstLyrics.length() > 2048){
                i1 = i1 - 1;
                firstLyrics.setLength(firstLyrics.length() - lyricsLine[i1].length());
            }

            while (secondPart.length() < 2048)
            {
                if (i1 < totalLine)
                {

                    if (!secondPart.toString().equalsIgnoreCase(""))
                        secondPart.append("\n");
                    secondPart.append(lyricsLine[i1]);
                    i1 = i1 + 1;
                } else {
                    break;
                }
            }

            EmbedBuilder secondPartBuilder = new EmbedBuilder();
            EmbedBuilder thirdBuilder = new EmbedBuilder();

            if (secondPart.length() > 2048)
            {
                StringBuilder thirdPart = new StringBuilder();
                i1 = i1 -1;
                secondPart.setLength(secondPart.length() - lyricsLine[i1].length());

                while (thirdPart.length() < 2048)
                {
                    if (i1 < totalLine)
                    {

                        if (!thirdPart.toString().equalsIgnoreCase(""))
                            thirdPart.append("\n");
                        thirdPart.append(lyricsLine[i1]);
                        i1 = i1 + 1;
                    } else {
                        break;
                    }
                }

                thirdBuilder.setDescription(thirdPart);
                thirdBuilder.setFooter("Lyrics by Genius");
            } else {
                secondPartBuilder.setFooter("Lyrics by Genius");
            }

            builder.setDescription(firstLyrics.toString());
            secondPartBuilder.setDescription(secondPart.toString());

            replyLyrics(buttonMessage, builder, secondPartBuilder, thirdBuilder);


        } else {
            builder.setDescription(lyrics.getText());
            builder.setFooter("Lyrics by Genius");
            replyLyrics(buttonMessage, builder);
        }
    }

    public static void replyLyrics(InteractionHook buttonMessage, EmbedBuilder... builders)
    {
        for (int i = 0; i < builders.length; i++)
        {
            EmbedBuilder builder = builders[i];
            if (builder.isEmpty()) continue;
            builder.setColor(Color.ORANGE);

            if (i==0) buttonMessage.editOriginal(new MessageBuilder().setEmbeds(builder.build()).build()).queue();
            else buttonMessage.getInteraction().getTextChannel().sendMessageEmbeds(builder.build()).queue();
        }
    }

    public static void sendLyrics(TextChannel channel, EmbedBuilder... builders)
    {
        for (EmbedBuilder builder :  builders)
        {
            if (builder.isEmpty()) continue;
            builder.setColor(Color.ORANGE);
            channel.sendMessageEmbeds(builder.build()).queue();
        }
    }

    @Override
    public void handle(List<String> args, CommandEvent event) {
        GuildMusicManager manager = PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel());

        if (args.size() == 0)
        {
            if (!manager.scheduler.getQueue().isEmpty()) {
                sendLyrics(event, GeniusAPI.search(PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel()).player.getPlayingTrack().getInfo().title).get(0));
            } else{
                event.getChannel().sendMessage(Main.getLanguage().getTextFor("argsNotFound", event.getGuild())).queue();
            }

            return;
        }

        String input = String.join(" ", args);

        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor(Main.getLanguage().getTextFor("lyrics.searchTitle", event.getGuild()), "https://justaven.xyz", event.getAuthor().getAvatarUrl());
        builder.setColor(event.getMember().getColor());
        builder.setFooter(Main.getLanguage().getTextFor("music.searchFooter", event.getGuild()), event.getJDA().getSelfUser().getAvatarUrl());

        List<Lyrics> lyricsList = GeniusAPI.search(input);
        for (int i = 0; i < lyricsList.size() && i < 5; i++)
        {
            Lyrics lyrics = GeniusAPI.fromURL(lyricsList.get(i).getURL(), lyricsList.get(i).getArtist(), lyricsList.get(i).getTitle());
            int nbTrack = i;
            nbTrack++;
            builder.appendDescription("\n`" + nbTrack + "`: **"+ lyrics.getTitle() + "** | " + Main.getLanguage().getTextFor("music.author", event.getGuild()) + " : " + lyrics.getArtist());
            manager.scheduler.putLyricsMap(nbTrack, lyrics);
        }



        event.getChannel().sendMessageEmbeds(builder.build()).queue(msg -> {
            msg.addReaction("❌").queue();
            manager.scheduler.lastMessageLyrics = msg;
        });
    }

    @Override
    public Type getType() {
        return Type.MUSIC;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Get lyrics of a song.", "Usage: `" + Constants.PREFIX + getInvoke() + " <query>`", false);
    }

    @Override
    public String getInvoke() {
        return "lyrics";
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
