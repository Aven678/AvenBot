package fr.aven.bot.commands.util;

import fr.aven.bot.Constants;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class PatchnoteCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setAuthor("AvenBot's patchnotes. (only in French)", "https://www.justaven.xyz", event.getJDA().getSelfUser().getAvatarUrl());
        builder.addField("2.5.1 (30/04/21):", "• Ajout commande **=betrayal**, **=poker** et **=fishington**.", false);
        builder.addField("2.5 (27/04/21):", "• Ajout commande **=youtube**.", false);
        builder.addField("2.4 (18/04/21):", "• Nouveau design.\n• Prise en charge de **Kotlin** et mise à jour des librairies.\n• Ajout de la commande **=notstonks**. \n• Ajout de la commande **=confusedstonks**.\n• Ajout de la commande **=issou**.\n• Ajout de la commande **=fakewarn**.\n• Ajout de la commande **=seek**.\n• Corrections de bugs.", false);
        builder.addField("2.3 (12/02/21):", "• Préparation pour l'arrivée de la fonctionnalité **Réaction-rôles**. \n• Ajout de la commande **patchnote**. \n• Le bot peut désormais répéter l'entièreté de la queue.\n• Ajout de la commande **role**.\n• Corrections de bugs dans la commande **bingo**.", false);
        builder.addField("2.2 (22/01/21):", "• Ajout de la commande **=autorole**. \n• Corrections de bugs mineurs.", false);
        builder.addField("2.1 (17/01/21):", "• Ajout de la commande **=afk**. \n• Ajout de la commande **=base64**. \n• Ajout de la commande **=omdb**. \n• Ajout de la commande **=roll**. \n• Ajout de la commande **=bingo**. \n• Ajout de la commande **=search**. \n• Le bot prend désormais en charge l’envoi de fichier et les liens de titre Deezer pour la commande **=play**. \n• Ajout de la commande **=faeban**. \n• Corrections de bugs.", false);
        builder.addField("2.0.3 (14/01/21):", "• Corrections de bugs.\n" +
                "• Ajout d’une commande **=cfun**.", false);
        builder.addField("2.0.2 (15/11/20):", "• Ajout d’une commande **=bass**.", false);
        builder.addField("2.0.1 (13/11/20):", "• Le bot prend désormais en charge les liens de titre Spotify pour la commande **=play**.", false);
        builder.addField("2.0 (07/2020):", "• Refonte entière du bot.", false);

        builder.setFooter("AvenBot by Aven#1000");
        event.getChannel().sendMessage(builder.build()).queue();
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
        return new MessageEmbed.Field(getDescription(), "Usage: `"+ Constants.PREFIX + getInvoke() +"`", false);
    }

    @Override
    public String getInvoke() {
        return "patchnote";
    }

    @Override
    public String getDescription() {
        return "Get lasts updates of AvenBot. (ONLY FRENCH)";
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
        return Arrays.asList(net.dv8tion.jda.api.Permission.MESSAGE_EMBED_LINKS);
    }
}
