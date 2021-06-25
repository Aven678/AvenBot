package fr.aven.bot.commands.util;

import fr.aven.bot.util.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.interactions.components.Button;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class TestCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        event.getMessage().reply("Oui, tu veux tester un truc ?").setActionRow(Button.primary("salut", "Bonjour !")).queue();
    }

    @Override
    public Type getType() {
        return Type.UTIL;
    }

    @Override
    public Permission getPermission() {
        return Permission.USER;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("", "", false);
    }

    @Override
    public String getInvoke() {
        return "test";
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
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_WRITE);
    }
}
