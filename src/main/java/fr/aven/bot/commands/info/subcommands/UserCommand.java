package fr.aven.bot.commands.info.subcommands;

import fr.aven.bot.Constants;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static net.dv8tion.jda.api.entities.Activity.ActivityType.STREAMING;

public class UserCommand extends InfoSubCommands {

    private User _target;
    private Message _msg;
    private Guild _guild;

    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        Message message = event.getMessage();
        Guild guild = event.getGuild();
        _guild = guild;
        //STATUS
        if (!message.getMentionedUsers().isEmpty() || args.size() < 3) {
            System.out.println(args.toString());
            User target = message.getMentionedUsers().isEmpty() ? ( args.isEmpty() ? message.getAuthor() : event.getJDA().getUserById(args.get(0))) : message.getMentionedUsers().get(0);
            _target = target;
            String status;

            switch (guild.getMember(target).getOnlineStatus()) {
                case ONLINE:
                    status = "Online";
                    break;
                case IDLE:
                    status = "Idle";
                    break;
                case DO_NOT_DISTURB:
                    status = "Do Not Disturb";
                    break;
                case INVISIBLE:
                    status = "Invisible";
                    break;
                default:
                    status = "Offline";
                    break;
            }

            //GAME
            String activityTypeS;
            String finalActivity;
            if (!guild.getMember(target).getActivities().isEmpty()) {
                Activity.ActivityType activityType = guild.getMember(target).getActivities().get(0).getType();
                switch (activityType) {
                    case STREAMING:
                        activityTypeS = "Streaming ";
                        break;
                    case WATCHING:
                        activityTypeS = "Watching ";
                        break;
                    case LISTENING:
                        activityTypeS = "Listening ";
                        break;
                    case DEFAULT:
                        activityTypeS = "Playing ";
                        break;
                    default:
                        activityTypeS = "Playing Nothing";
                        break;
                }
                String activity = guild.getMember(target).getActivities().get(0).getName();

                if (activityType.equals(STREAMING)) {
                    finalActivity = "Activity ❱ " + activityTypeS + activity + " for joining : " + guild.getMember(target).getActivities().get(0).getUrl();
                } else {
                    finalActivity = "Activity ❱ " + activityTypeS + activity;
                }
            } else {
                finalActivity = "Activity ❱ Playing Nothing";
            }

            //NICKNAME
            String nickname = guild.getMember(target).getNickname();
            String nick;
            if (nickname == null || nickname.isEmpty()) {
                nick = "No Nickname";
            } else {
                nick = guild.getMember(target).getNickname();
            }



            EmbedBuilder userBuilder = new EmbedBuilder();
            userBuilder.setAuthor(target.getName(), null, target.getAvatarUrl());
            userBuilder.setThumbnail(target.getAvatarUrl());
            userBuilder.addField("User Informations: ",
                    "Nickname ❱ " + nick +
                            "\nID ❱ " + target.getId() +
                            "\nAccount Type ❱ " + (target.isBot() ? "Bot" : "Human"),
                    true);
            Emote rpEmote = event.getJDA().getGuildById("403193778233409536").getEmoteById("603529803609407499");
            userBuilder.addField("Status: ",
                    "Status ❱ " + status +
                            "\n" + finalActivity +
                            (guild.getMember(target).getActivities() == null || guild.getMember(target).getActivities().isEmpty() ? "" : (guild.getMember(target).getActivities().get(0).isRich() ? "\n*Click the " + rpEmote.getAsMention() + " emote for more informations*" : "")),
                    false);
            userBuilder.addField("Dates: ",
                    "Creation Date ❱ " + target.getTimeCreated().format(Constants.FORMATTER) +
                            "\nJoin Date ❱ " + guild.getMember(target).getTimeJoined().format(Constants.FORMATTER),
                    false);
            userBuilder.addField("Roles ❱  ", guild.getMember(target).getRoles().size() == 0 ? "No roles found" : guild.getMember(target).getRoles().stream().map(Role::getAsMention).collect(Collectors.joining(", ")), true);



            event.getChannel().sendMessage(userBuilder.build()).queue(msg -> {


                //RICHPRESENCE
                if (!target.isBot() && !guild.getMember(target).getActivities().isEmpty() && guild.getMember(target).getActivities().get(0).isRich()) {
                    /*msg.addReaction(event.getJDA().getGuildById("403193778233409536").getEmoteById("603529803609407499")).queue();
                    _msg = msg;*/
                }
            });
        } else {
            event.getChannel().sendMessage("Please provide an ID or a mention").queue();
        }
    }

    @Override
    public String getInvoke() {
        return "user";
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Get informations about a user", "Usage: `" + Constants.PREFIX + "info " + getInvoke() + " <@user/id>`", false);
    }

    @Override
    public void onEvent(GenericEvent event) {
        //if (event instanceof MessageReactionAddEvent) onReac((MessageReactionAddEvent) event);
    }

    private void onReac(MessageReactionAddEvent event) {
        if (event.getUser().isBot()) return;
        if (_msg == null) return;
        if (!event.getMessageId().equals(_msg.getId())) return;
        if (event.getReactionEmote().getEmote().equals(event.getJDA().getGuildById("403193778233409536").getEmoteById("603529803609407499"))) {
            RichPresence rp = _guild.getMember(_target).getActivities().get(0).asRichPresence();

            EmbedBuilder rpBuilder = new EmbedBuilder();
            if (rp.getLargeImage() != null) {
                rpBuilder.setThumbnail(rp.getLargeImage().getUrl());
            } else {
                rpBuilder.setThumbnail("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1024px-No_image_available.svg.png");
            }
            rpBuilder.setAuthor(_target.getName(), null, _target.getAvatarUrl());
            if (rp.getType().equals(Activity.ActivityType.DEFAULT)) {
                rpBuilder.addField("General Informations: ",
                        "Software ❱ " + rp.getName() +
                                "\nSoftware ID ❱ " + rp.getApplicationId() +
                                "\nActivity ❱ " + (rp.getState() == null || rp.getState().isEmpty() ? "No Activity given" : rp.getState()) +
                                "\nDetails ❱ " + (rp.getDetails() == null || rp.getDetails().isEmpty() ? "No Details given" : rp.getDetails()) +
                                (rp.getUrl() == null || rp.getUrl().isEmpty() ? "" : "\nURL ❱ " + rp.getUrl()),
                        true);
                if (rp.getTimestamps() != null) {
                    if (rp.getTimestamps().getElapsedTime(ChronoUnit.MINUTES) < 59) {
                        rpBuilder.addField("Playing for: ", rp.getTimestamps().getElapsedTime(ChronoUnit.MINUTES) + " minutes", true);
                    } else if (rp.getTimestamps().getElapsedTime(ChronoUnit.HOURS) < 23) {
                        rpBuilder.addField("Playing for: ", rp.getTimestamps().getElapsedTime(ChronoUnit.HOURS) + " hours", true);
                    } else if (rp.getTimestamps().getElapsedTime(ChronoUnit.DAYS) < 30) {
                        rpBuilder.addField("Playing for: ", rp.getTimestamps().getElapsedTime(ChronoUnit.DAYS) + " days", true);
                    }
                }

                if (rp.getParty() != null) {
                    rpBuilder.addField("Party: ",
                            "Party Size ❱ " + rp.getParty().getSize() +
                                    "Max Players ❱ " + rp.getParty().getMax() +
                                    "Party ID ❱ " + rp.getParty().getId(),
                            false);
                }
            } else if (rp.getType().equals(Activity.ActivityType.LISTENING)) {
                rpBuilder.addField("General Informations: ",
                        "Software ❱ " + rp.getName() +
                                "\nSoftware ID ❱ " + rp.getApplicationId() +
                                "\nActivity ❱ " + (rp.getState() == null || rp.getState().isEmpty() ? "No Activity given" : rp.getState()) +
                                "\nDetails ❱ " + (rp.getDetails() == null || rp.getDetails().isEmpty() ? "No Details given" : rp.getDetails()) +
                                (rp.getUrl() == null || rp.getUrl().isEmpty() ? "" : "\nURL ❱ " + rp.getUrl()),
                        true);
                if (rp.getTimestamps().getElapsedTime(ChronoUnit.MINUTES) < 59) {
                    rpBuilder.addField("Listening for: ", rp.getTimestamps().getElapsedTime(ChronoUnit.MINUTES) + " minutes", true);
                } else if (rp.getTimestamps().getElapsedTime(ChronoUnit.HOURS) < 23) {
                    rpBuilder.addField("Listening for: ", rp.getTimestamps().getElapsedTime(ChronoUnit.HOURS) + " hours", true);
                } else if (rp.getTimestamps().getElapsedTime(ChronoUnit.DAYS) < 30) {
                    rpBuilder.addField("Listening for: ", rp.getTimestamps().getElapsedTime(ChronoUnit.DAYS) + " days", true);
                }
            }
            event.getChannel().sendMessage(rpBuilder.build()).queue();

            _msg = null;
        }

    }

    @Override
    public boolean haveEvent() {
        return true;
    }

    @Override
    public ICommand.Type getType() {
        return super.getType();
    }
}