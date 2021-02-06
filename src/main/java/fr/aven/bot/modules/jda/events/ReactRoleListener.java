package fr.aven.bot.modules.jda.events;

import fr.aven.bot.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReactRoleListener extends ListenerAdapter
{
    @Override
    public void onGuildMessageReactionAdd(@NotNull GuildMessageReactionAddEvent event) {
        if (!Main.getDatabase().isMessageReactRole(event.getMessageId())) return;

        Role role = Main.getDatabase().getRoleWithReact(event.getMessageId(), event.getReactionEmote().isEmote() ? event.getReactionEmote().getId() : event.getReactionEmote().getName());
        if (role == null) return;
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES)) return;

        event.getGuild().addRoleToMember(event.getMember(), role).reason("Reaction Role by AvenBot").queue();
        super.onGuildMessageReactionAdd(event);
    }

    @Override
    public void onGuildMessageReactionRemove(@NotNull GuildMessageReactionRemoveEvent event) {
        if (!Main.getDatabase().isMessageReactRole(event.getMessageId())) return;

        Role role = Main.getDatabase().getRoleWithReact(event.getMessageId(), event.getReactionEmote().isEmote() ? event.getReactionEmote().getId() : event.getReactionEmote().getName());
        if (role == null) return;
        if (!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_ROLES)) return;

        event.getGuild().removeRoleFromMember(event.getMember(), role).reason("Reaction Role by AvenBot").queue();
        super.onGuildMessageReactionRemove(event);
    }
}
