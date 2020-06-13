package fr.aven.bot.commands.music;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.music.PlayerManager;
import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class ShuffleCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        PlayerManager.getInstance().getGuildMusicManager(event.getGuild(), event.getChannel()).scheduler.shuffleQueue();

        event.getChannel().sendMessage(Main.getDatabase().getTextFor("shuffle.confirm", event.getGuild())).queue();
    }

    @Override
    public Type getType() {
        return Type.MUSIC;
    }

    @Override
    public Permission getPermission() {
        return Permission.DJ;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Shuffle the queue.", "`Usage : "+ Constants.PREFIX + getInvoke() + "`", false);
    }

    @Override
    public String getInvoke() {
        return "shuffle";
    }

    @Override
    public boolean haveEvent() {
        return false;
    }

    @Override
    public void onEvent(GenericEvent event) {

    }
}
