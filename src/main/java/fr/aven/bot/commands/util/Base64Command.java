package fr.aven.bot.commands.util;

import fr.aven.bot.Constants;
import fr.aven.bot.util.ICommand;
import fr.aven.bot.util.MessageUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

public class Base64Command implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        if (args.size() == 0)
        {
            event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
            return;
        }

        try {
            if (args.get(0).equalsIgnoreCase("encode")) {

                event.getChannel().sendMessage(new EmbedBuilder()
                        .setColor(MessageUtil.getRandomColor())
                        .addField("Base64 Encode result", Base64.getEncoder().encodeToString(StringUtils.join(args, " ").replaceFirst("encode ", "").getBytes("UTF-8")), false)
                        .setFooter("AvenBot by Aven#1000")
                        .build()).queue();

            } else if (args.get(0).equalsIgnoreCase("decode")) {
                event.getChannel().sendMessage(new EmbedBuilder()
                        .setColor(MessageUtil.getRandomColor())
                        .addField("Base64 Decode result", new String(Base64.getDecoder().decode(StringUtils.join(args, " ").replaceFirst("decode ", "").getBytes()), StandardCharsets.UTF_8), false)
                        .setFooter("AvenBot by Aven#1000")
                        .build()).queue();
            } else {
                event.getChannel().sendMessage(new EmbedBuilder().addField(getHelp()).build()).queue();
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
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
        return new MessageEmbed.Field("Returns your text encoded/decoded in Base 64.", "Usage: `"+ Constants.PREFIX + getInvoke() +" <encode/decode> <something>`", false);
    }

    @Override
    public String getInvoke() {
        return "base64";
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
