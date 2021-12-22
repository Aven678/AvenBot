package fr.aven.bot.commands.fun;

import fr.aven.bot.Constants;
import fr.aven.bot.Main;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BingoCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        if (Main.getBingoMap().channelHasBingo(event.getChannel()))
        {
            event.getChannel().sendMessage("A bingo has already started!").queue();
            return;
        }

        Random random = new Random();

        int limit = random.nextInt(100);

        try {
            if (args.size() > 0) limit = random.nextInt(Integer.parseInt(args.get(0)));
        } catch (Exception e) {
            event.getChannel().sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
        }


        Main.getBingoMap().addBingoChannel(event.getChannel(), limit);
        event.getChannel().sendMessage("A bingo has been started.").queue();
        int finalLimit = limit;
        event.getAuthor().openPrivateChannel().queue(pc -> {
            System.out.println("suce");
            pc.sendMessage("You started a bingo, the winning number is: "+ finalLimit).queue();
        });
    }

    @Override
    public Type getType() {
        return Type.FUN;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field(getDescription(), "Usage: `"+ Constants.PREFIX + getInvoke() + " [limit]`", false);
    }

    @Override
    public String getInvoke() {
        return "bingo";
    }

    @Override
    public String getDescription() {
        return "Launch a bingo (limit is default 100)";
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
        return Collections.singletonList(net.dv8tion.jda.api.Permission.MESSAGE_WRITE);
    }
}
