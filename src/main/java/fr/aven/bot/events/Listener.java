package fr.aven.bot.events;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import fr.aven.bot.CommandManager;
import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.commands.music.LyricsCommand;
import fr.aven.bot.jda.JDAManager;
import fr.aven.bot.music.GuildMusicManager;
import fr.aven.bot.music.PlayerManager;
import fr.aven.bot.util.DBList;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.explodingbush.ksoftapi.entities.Lyric;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Listener extends ListenerAdapter
{
        private final CommandManager manager;
        private static final Logger logger = LoggerFactory.getLogger(Listener.class);
        private TextChannel channel;

        public Listener(CommandManager manager)
        {
            this.manager = manager;
        }

        @Override
        public void onGenericEvent(@Nonnull GenericEvent event) {
            for (ICommand command : manager.getCommandsWithEvent()) {
                command.onEvent(event);
            }
        }

        @Override
        public void onReady(ReadyEvent event)
        {
            logger.info(String.format("Logged in as %#s", event.getJDA().getSelfUser()));
            channel = event.getJDA().getTextChannelById(412908508590243840L);

            Main.getDbl().createDBL(event.getJDA().getSelfUser().getId(), Main.getConfiguration().getString("dblToken", "default"));



            for (Guild guild : JDAManager.getShardManager().getGuilds())
            {
                //addOwnerInDb(guild);
                checkInDB(guild);
            }

            //event.getJDA().getTextChannelById(704766888407990282L).sendMessage("tes blagues sont pas drôles meemerde").queue();
        }

        private void addOwnerInDb(Guild guild)
        {
            Main.getDatabase().addOwner(guild);
        }

    private void checkInDB(Guild guild) {
            if (!Main.getDatabase().checkGuildIfExists(guild))
                createMutedRole(guild);
    }

    @Override
        public void onGuildMessageReceived(GuildMessageReceivedEvent event)
        {
            if (event.getAuthor().isBot()) return;
            if (event.getMessage().isWebhookMessage()) return;
            String rw = event.getMessage().getContentRaw();

            if (rw.equalsIgnoreCase(Constants.PREFIX + "shutdown") && (event.getAuthor().getIdLong() == Constants.OWNER || event.getAuthor().getIdLong() == Constants.COOWNER))
            {
                Main.stop();
                logger.info("Bot stop");
                return;
            }

            String prefix = Main.getDatabase().getGuildPrefix(event.getGuild());//Constants.PREFIXES.computeIfAbsent(event.getGuild().getIdLong(), (l) -> Constants.PREFIX);

            if (rw.startsWith(prefix) || rw.startsWith(Constants.PREFIX))
            {
                new Thread(() -> {
                    manager.handleCommand(event, rw.startsWith(Constants.PREFIX));
                }).start();
                //event.getMessage().delete().queue();
            } else {
                if (!checkMusic(event)) checkLyric(event);
            }
        }

        @Override
        public void onGuildJoin(GuildJoinEvent event)
        {
            Guild joinedGuild = event.getGuild();
            TextChannel channel = joinedGuild.getDefaultChannel();

            logger.info(joinedGuild.getName() + " joined");

            EmbedBuilder builder = new EmbedBuilder();
            builder.setAuthor("AvenBot", "https://discord.gg/ntbdKjv");
            builder.setDescription("It's a JDA multifonction bot, do `" + Constants.PREFIX + "help` to have all the commands");
            builder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());
            builder.addField("Developed by :", "Aven#1000 and HashDG#0698", true);
            builder.addField("Exists since: ", "Tue, 26 Sep 2017 16:12:02 GMT", true); //event.getJDA().getSelfUser().getTimeCreated().format(Constants.FORMATTER)
            builder.addField("Support server :", "Click on the name" , false);
            builder.setFooter("Automatic Message", event.getJDA().getSelfUser().getAvatarUrl());
            builder.setTimestamp(new Date().toInstant());
            channel.sendMessage(builder.build()).queue();

            createMutedRole(joinedGuild);

            status("join", event.getGuild());
        }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        status("leave", event.getGuild());
    }

    private void status(String nameEvent, Guild guild) {
        String owner = guild.getOwner().getUser().getAsTag();

        switch (nameEvent) {
            case "join":
                channel.sendMessage("New guild joined : " + guild.getName() + " (" + guild.getMembers().size() + " members) owned by " + owner).queue();
                break;

            case "leave":
                channel.sendMessage("Guild leave : " + guild.getName()).queue();
                break;
        }

        Main.getDbl().setServerNumber();
    }

    private boolean checkMusic(GuildMessageReceivedEvent e) {
        GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(e.getGuild(), e.getChannel());
        Map<Integer, AudioTrack> search = PlayerManager.getInstance().getGuildMusicManager(e.getGuild(), e.getChannel()).scheduler.search;
        if (search.size() == 0) return false;
        if (e.getMessage().getContentDisplay().equalsIgnoreCase("cancel")) {
            search.clear();
            return true;
        }

        try {
            int choix = Integer.parseInt(e.getMessage().getContentDisplay());
            if (!search.containsKey(choix)) return false;
            PlayerManager.getInstance().loadAndPlay(e.getMessage(), search.get(choix).getInfo().uri);
            search.clear();
            if (musicManager.scheduler.lastMessageSearch != null)
                e.getChannel().deleteMessageById(musicManager.scheduler.lastMessageSearch.getId()).queue();
            e.getMessage().delete().queue();
            return true;

        } catch (NumberFormatException nfe) {
        }

        return false;
    }

    private void checkLyric(GuildMessageReceivedEvent e)
    {
        GuildMusicManager musicManager = PlayerManager.getInstance().getGuildMusicManager(e.getGuild(), e.getChannel());
        Map<Integer, Lyric> lyrics = musicManager.scheduler.lyrics;
        if (lyrics.size() == 0) return;
        if (e.getMessage().getContentDisplay().equalsIgnoreCase("cancel")) {
            lyrics.clear();
            return;
        }

        try {
            int choix = Integer.parseInt(e.getMessage().getContentDisplay());
            if (!lyrics.containsKey(choix)) return;
            LyricsCommand.sendLyrics(e, lyrics.get(choix));
            lyrics.clear();
            if (musicManager.scheduler.lastMessageLyrics != null) e.getChannel().deleteMessageById(musicManager.scheduler.lastMessageLyrics.getId()).queue();
            e.getMessage().delete().queue();
        } catch (NumberFormatException nfe)
        {
            return;
        }
    }

        public static void createMutedRole(Guild guild) {
            List<Role> roles = guild.getRolesByName("Muted", true);
            if (roles.size() != 0)
                Main.getDatabase().insertGuild(guild, roles.get(0).getId());
            else
                if (guild.getSelfMember().hasPermission(Permission.MANAGE_ROLES) && guild.getRoles().size() <= 249)
                {
                    guild.createRole()
                            .setColor(Color.GRAY)
                            .setName("Muted")
                            .setMentionable(false)
                            .setPermissions(Permission.EMPTY_PERMISSIONS) //put off all perms
                            .setPermissions(Permission.MESSAGE_READ, Permission.VIEW_CHANNEL)
                            .queue(mutedRole -> {

                                        for (TextChannel channel : guild.getTextChannels()) {

                                            channel.createPermissionOverride(mutedRole)
                                                    .setAllow(
                                                            Permission.VIEW_CHANNEL,
                                                            Permission.MESSAGE_READ
                                                    ).setDeny(
                                                    Permission.MESSAGE_WRITE,
                                                    Permission.MESSAGE_ADD_REACTION,
                                                    Permission.ADMINISTRATOR
                                            ).reason("Establishing Muted Role")
                                                    .queue();
                                        }

                                        Main.getDatabase().checkGuild(guild, mutedRole.getId());
                                        logger.info("Muted Role created on server: " + guild.getName());
                                    }
                            );
                }

        }

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        if (event.getUser().isBot()) return;

        //checkMusic
        if (!event.getReactionEmote().isEmoji()) return;
        if (!event.getReactionEmote().getEmoji().equalsIgnoreCase("❌")) return;
        if (!PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.search.isEmpty()) {
            PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.search.clear();
            event.getChannel().editMessageById(event.getMessageId(), Main.getDatabase().getTextFor("music.canceled", event.getGuild()) + event.getUser().getAsTag()).override(true).queue();
        }

        else if (!PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.lyrics.isEmpty()) {
            PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.clearLyricsMap();
            event.getChannel().editMessageById(event.getMessageId(), Main.getDatabase().getTextFor("music.canceled", event.getGuild()) + event.getUser().getAsTag()).override(true).queue();
        }

    }
}