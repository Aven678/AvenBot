package fr.aven.bot.modules.jda.events

import fr.aven.bot.util.MessageUtil
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.channel.category.CategoryCreateEvent
import net.dv8tion.jda.api.events.channel.category.CategoryDeleteEvent
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdateNameEvent
import net.dv8tion.jda.api.events.channel.category.update.CategoryUpdatePositionEvent
import net.dv8tion.jda.api.events.channel.store.StoreChannelCreateEvent
import net.dv8tion.jda.api.events.channel.store.StoreChannelDeleteEvent
import net.dv8tion.jda.api.events.channel.store.update.StoreChannelUpdateNameEvent
import net.dv8tion.jda.api.events.channel.store.update.StoreChannelUpdatePositionEvent
import net.dv8tion.jda.api.events.channel.text.TextChannelCreateEvent
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent
import net.dv8tion.jda.api.events.channel.text.update.*
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelCreateEvent
import net.dv8tion.jda.api.events.channel.voice.VoiceChannelDeleteEvent
import net.dv8tion.jda.api.events.channel.voice.update.*
import net.dv8tion.jda.api.events.emote.EmoteAddedEvent
import net.dv8tion.jda.api.events.emote.EmoteRemovedEvent
import net.dv8tion.jda.api.events.emote.update.EmoteUpdateNameEvent
import net.dv8tion.jda.api.events.emote.update.EmoteUpdateRolesEvent
import net.dv8tion.jda.api.events.guild.GuildBanEvent
import net.dv8tion.jda.api.events.guild.GuildUnbanEvent
import net.dv8tion.jda.api.events.guild.invite.GuildInviteCreateEvent
import net.dv8tion.jda.api.events.guild.member.*
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdatePendingEvent
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideCreateEvent
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideDeleteEvent
import net.dv8tion.jda.api.events.guild.override.PermissionOverrideUpdateEvent
import net.dv8tion.jda.api.events.guild.update.*
import net.dv8tion.jda.api.events.guild.voice.*
import net.dv8tion.jda.api.events.message.guild.GuildMessageDeleteEvent
import net.dv8tion.jda.api.events.message.guild.GuildMessageUpdateEvent
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveAllEvent
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent
import net.dv8tion.jda.api.events.role.RoleCreateEvent
import net.dv8tion.jda.api.events.role.RoleDeleteEvent
import net.dv8tion.jda.api.events.role.update.*
import net.dv8tion.jda.api.hooks.ListenerAdapter

class LogsListener: ListenerAdapter()
{
    fun createBasicEmbed(): EmbedBuilder {
        return EmbedBuilder()
            .setColor(MessageUtil.getRandomColor())
            .setFooter("AvenBot by Aven#1000")
    }

    override fun onGuildInviteCreate(event: GuildInviteCreateEvent) {
        super.onGuildInviteCreate(event)
    }

    //Mouvements
    override fun onGuildBan(event: GuildBanEvent) {
        super.onGuildBan(event)
    }
    override fun onGuildMemberJoin(event: GuildMemberJoinEvent) {
        super.onGuildMemberJoin(event)
    }
    override fun onGuildMemberRemove(event: GuildMemberRemoveEvent) {
        super.onGuildMemberRemove(event)
    }
    override fun onGuildUnban(event: GuildUnbanEvent) {
        super.onGuildUnban(event)
    }

    //Guild Member Update
    override fun onGuildMemberRoleAdd(event: GuildMemberRoleAddEvent) {
        super.onGuildMemberRoleAdd(event)
    }
    override fun onGuildMemberRoleRemove(event: GuildMemberRoleRemoveEvent) {
        super.onGuildMemberRoleRemove(event)
    }
    override fun onGuildMemberUpdateNickname(event: GuildMemberUpdateNicknameEvent) {
        super.onGuildMemberUpdateNickname(event)
    }
    override fun onGuildMemberUpdatePending(event: GuildMemberUpdatePendingEvent) {
        super.onGuildMemberUpdatePending(event)
    }

    //Guild Message Update
    override fun onGuildMessageDelete(event: GuildMessageDeleteEvent) {
        super.onGuildMessageDelete(event)
    }
    override fun onGuildMessageReactionAdd(event: GuildMessageReactionAddEvent) {
        super.onGuildMessageReactionAdd(event)
    }
    override fun onGuildMessageReactionRemove(event: GuildMessageReactionRemoveEvent) {
        super.onGuildMessageReactionRemove(event)
    }
    override fun onGuildMessageReactionRemoveAll(event: GuildMessageReactionRemoveAllEvent) {
        super.onGuildMessageReactionRemoveAll(event)
    }
    override fun onGuildMessageUpdate(event: GuildMessageUpdateEvent) {
        super.onGuildMessageUpdate(event)
    }

    //Guild Update Events
    override fun onGuildUpdateAfkChannel(event: GuildUpdateAfkChannelEvent) {
        super.onGuildUpdateAfkChannel(event)
    }
    override fun onGuildUpdateAfkTimeout(event: GuildUpdateAfkTimeoutEvent) {
        super.onGuildUpdateAfkTimeout(event)
    }
    override fun onGuildUpdateBanner(event: GuildUpdateBannerEvent) {
        super.onGuildUpdateBanner(event)
    }
    override fun onGuildUpdateBoostCount(event: GuildUpdateBoostCountEvent) {
        super.onGuildUpdateBoostCount(event)
    }
    override fun onGuildUpdateBoostTier(event: GuildUpdateBoostTierEvent) {
        super.onGuildUpdateBoostTier(event)
    }
    override fun onGuildUpdateCommunityUpdatesChannel(event: GuildUpdateCommunityUpdatesChannelEvent) {
        super.onGuildUpdateCommunityUpdatesChannel(event)
    }
    override fun onGuildUpdateDescription(event: GuildUpdateDescriptionEvent) {
        super.onGuildUpdateDescription(event)
    }
    override fun onGuildUpdateExplicitContentLevel(event: GuildUpdateExplicitContentLevelEvent) {
        super.onGuildUpdateExplicitContentLevel(event)
    }
    override fun onGuildUpdateFeatures(event: GuildUpdateFeaturesEvent) {
        super.onGuildUpdateFeatures(event)
    }
    override fun onGuildUpdateIcon(event: GuildUpdateIconEvent) {
        super.onGuildUpdateIcon(event)
    }
    override fun onGuildUpdateLocale(event: GuildUpdateLocaleEvent) {
        super.onGuildUpdateLocale(event)
    }
    override fun onGuildUpdateMFALevel(event: GuildUpdateMFALevelEvent) {
        super.onGuildUpdateMFALevel(event)
    }
    override fun onGuildUpdateMaxMembers(event: GuildUpdateMaxMembersEvent) {
        super.onGuildUpdateMaxMembers(event)
    }
    override fun onGuildUpdateMaxPresences(event: GuildUpdateMaxPresencesEvent) {
        super.onGuildUpdateMaxPresences(event)
    }
    override fun onGuildUpdateName(event: GuildUpdateNameEvent) {
        super.onGuildUpdateName(event)
    }
    override fun onGuildUpdateNotificationLevel(event: GuildUpdateNotificationLevelEvent) {
        super.onGuildUpdateNotificationLevel(event)
    }
    override fun onGuildUpdateOwner(event: GuildUpdateOwnerEvent) {
        super.onGuildUpdateOwner(event)
    }
    override fun onGuildUpdateRulesChannel(event: GuildUpdateRulesChannelEvent) {
        super.onGuildUpdateRulesChannel(event)
    }
    override fun onGuildUpdateSplash(event: GuildUpdateSplashEvent) {
        super.onGuildUpdateSplash(event)
    }
    override fun onGuildUpdateSystemChannel(event: GuildUpdateSystemChannelEvent) {
        super.onGuildUpdateSystemChannel(event)
    }
    override fun onGuildUpdateVanityCode(event: GuildUpdateVanityCodeEvent) {
        super.onGuildUpdateVanityCode(event)
    }
    override fun onGuildUpdateVerificationLevel(event: GuildUpdateVerificationLevelEvent) {
        super.onGuildUpdateVerificationLevel(event)
    }

    //Guild Voice Events
    override fun onGuildVoiceDeafen(event: GuildVoiceDeafenEvent) {
        super.onGuildVoiceDeafen(event)
    }
    override fun onGuildVoiceGuildDeafen(event: GuildVoiceGuildDeafenEvent) {
        super.onGuildVoiceGuildDeafen(event)
    }
    override fun onGuildVoiceGuildMute(event: GuildVoiceGuildMuteEvent) {
        super.onGuildVoiceGuildMute(event)
    }
    override fun onGuildVoiceJoin(event: GuildVoiceJoinEvent) {
        super.onGuildVoiceJoin(event)
    }
    override fun onGuildVoiceLeave(event: GuildVoiceLeaveEvent) {
        super.onGuildVoiceLeave(event)
    }
    override fun onGuildVoiceMove(event: GuildVoiceMoveEvent) {
        super.onGuildVoiceMove(event)
    }
    override fun onGuildVoiceMute(event: GuildVoiceMuteEvent) {
        super.onGuildVoiceMute(event)
    }
    override fun onGuildVoiceRequestToSpeak(event: GuildVoiceRequestToSpeakEvent) {
        super.onGuildVoiceRequestToSpeak(event)
    }
    override fun onGuildVoiceSuppress(event: GuildVoiceSuppressEvent) {
        super.onGuildVoiceSuppress(event)
    }
    override fun onGuildVoiceUpdate(event: GuildVoiceUpdateEvent) {
        super.onGuildVoiceUpdate(event)
    }

    //Permissions Events
    override fun onPermissionOverrideCreate(event: PermissionOverrideCreateEvent) {
        super.onPermissionOverrideCreate(event)
    }
    override fun onPermissionOverrideDelete(event: PermissionOverrideDeleteEvent) {
        super.onPermissionOverrideDelete(event)
    }
    override fun onPermissionOverrideUpdate(event: PermissionOverrideUpdateEvent) {
        super.onPermissionOverrideUpdate(event)
    }

    //Roles Events
    override fun onRoleCreate(event: RoleCreateEvent) {
        super.onRoleCreate(event)
    }
    override fun onRoleDelete(event: RoleDeleteEvent) {
        super.onRoleDelete(event)
    }

    //Roles Updates Events
    override fun onRoleUpdateColor(event: RoleUpdateColorEvent) {
        super.onRoleUpdateColor(event)
    }
    override fun onRoleUpdateHoisted(event: RoleUpdateHoistedEvent) {
        super.onRoleUpdateHoisted(event)
    }
    override fun onRoleUpdateMentionable(event: RoleUpdateMentionableEvent) {
        super.onRoleUpdateMentionable(event)
    }
    override fun onRoleUpdateName(event: RoleUpdateNameEvent) {
        super.onRoleUpdateName(event)
    }
    override fun onRoleUpdatePermissions(event: RoleUpdatePermissionsEvent) {
        super.onRoleUpdatePermissions(event)
    }
    override fun onRoleUpdatePosition(event: RoleUpdatePositionEvent) {
        super.onRoleUpdatePosition(event)
    }

    //Store Channel Events
    override fun onStoreChannelCreate(event: StoreChannelCreateEvent) {
        super.onStoreChannelCreate(event)
    }
    override fun onStoreChannelDelete(event: StoreChannelDeleteEvent) {
        super.onStoreChannelDelete(event)
    }
    override fun onStoreChannelUpdateName(event: StoreChannelUpdateNameEvent) {
        super.onStoreChannelUpdateName(event)
    }
    override fun onStoreChannelUpdatePosition(event: StoreChannelUpdatePositionEvent) {
        super.onStoreChannelUpdatePosition(event)
    }

    //TextChannel Events
    override fun onTextChannelCreate(event: TextChannelCreateEvent) {
        super.onTextChannelCreate(event)
    }
    override fun onTextChannelDelete(event: TextChannelDeleteEvent) {
        super.onTextChannelDelete(event)
    }
    override fun onTextChannelUpdateNSFW(event: TextChannelUpdateNSFWEvent) {
        super.onTextChannelUpdateNSFW(event)
    }
    override fun onTextChannelUpdateName(event: TextChannelUpdateNameEvent) {
        super.onTextChannelUpdateName(event)
    }
    override fun onTextChannelUpdateNews(event: TextChannelUpdateNewsEvent) {
        super.onTextChannelUpdateNews(event)
    }
    override fun onTextChannelUpdateParent(event: TextChannelUpdateParentEvent) {
        super.onTextChannelUpdateParent(event)
    }
    override fun onTextChannelUpdatePosition(event: TextChannelUpdatePositionEvent) {
        super.onTextChannelUpdatePosition(event)
    }
    override fun onTextChannelUpdateSlowmode(event: TextChannelUpdateSlowmodeEvent) {
        super.onTextChannelUpdateSlowmode(event)
    }
    override fun onTextChannelUpdateTopic(event: TextChannelUpdateTopicEvent) {
        super.onTextChannelUpdateTopic(event)
    }

    //Category Events
    override fun onCategoryCreate(event: CategoryCreateEvent) {
        super.onCategoryCreate(event)
    }
    override fun onCategoryDelete(event: CategoryDeleteEvent) {
        super.onCategoryDelete(event)
    }
    override fun onCategoryUpdateName(event: CategoryUpdateNameEvent) {
        super.onCategoryUpdateName(event)
    }
    override fun onCategoryUpdatePosition(event: CategoryUpdatePositionEvent) {
        super.onCategoryUpdatePosition(event)
    }

    //Emotes Events
    override fun onEmoteAdded(event: EmoteAddedEvent) {
        super.onEmoteAdded(event)
    }
    override fun onEmoteRemoved(event: EmoteRemovedEvent) {
        super.onEmoteRemoved(event)
    }
    override fun onEmoteUpdateName(event: EmoteUpdateNameEvent) {
        super.onEmoteUpdateName(event)
    }
    override fun onEmoteUpdateRoles(event: EmoteUpdateRolesEvent) {
        super.onEmoteUpdateRoles(event)
    }

    //VoiceChannels Events
    override fun onVoiceChannelCreate(event: VoiceChannelCreateEvent) {
        super.onVoiceChannelCreate(event)
    }
    override fun onVoiceChannelDelete(event: VoiceChannelDeleteEvent) {
        super.onVoiceChannelDelete(event)
    }
    override fun onVoiceChannelUpdateBitrate(event: VoiceChannelUpdateBitrateEvent) {
        super.onVoiceChannelUpdateBitrate(event)
    }
    override fun onVoiceChannelUpdateName(event: VoiceChannelUpdateNameEvent) {
        super.onVoiceChannelUpdateName(event)
    }
    override fun onVoiceChannelUpdateParent(event: VoiceChannelUpdateParentEvent) {
        super.onVoiceChannelUpdateParent(event)
    }
    override fun onVoiceChannelUpdatePosition(event: VoiceChannelUpdatePositionEvent) {
        super.onVoiceChannelUpdatePosition(event)
    }
    override fun onVoiceChannelUpdateRegion(event: VoiceChannelUpdateRegionEvent) {
        super.onVoiceChannelUpdateRegion(event)
    }
    override fun onVoiceChannelUpdateUserLimit(event: VoiceChannelUpdateUserLimitEvent) {
        super.onVoiceChannelUpdateUserLimit(event)
    }
}