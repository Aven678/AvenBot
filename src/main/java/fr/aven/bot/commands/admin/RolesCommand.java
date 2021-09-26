package fr.aven.bot.commands.admin;

import fr.aven.bot.Constants;
import fr.aven.bot.modules.core.CommandEvent;
import fr.aven.bot.modules.core.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RolesCommand implements ICommand
{
    @Override
    public void handle(List<String> args, CommandEvent event) {
        if (args.isEmpty())
        {
            sendHelpMessage(event.getChannel());
            return;
        }

        String request = args.get(0);
        Guild guild = event.getGuild();
        TextChannel channel = event.getChannel();
        User author = event.getAuthor();
        EmbedBuilder builder = new EmbedBuilder();

        Color color = null;
        Role role = null;
        boolean hoisted = false;
        boolean mentionned = false;
        String choice = "";

        switch (request)
        {
            case "list":
                builder.setAuthor("List of roles in "+guild.getName(), "https://justaven.xyz", guild.getIconUrl());
                builder.setDescription(guild.getRoles().stream().map(Role::getAsMention).collect(Collectors.joining(", ")));
                builder.setFooter("Command executed by "+author.getAsTag(), author.getAvatarUrl());

                channel.sendMessageEmbeds(builder.build()).queue();
                break;

            case "add":
                if (event.message().getMentionedRoles().isEmpty())
                {
                    sendHelpMessage(channel);
                    return;
                }

                role = event.message().getMentionedRoles().get(0);
                if (args.size() == 2) {
                    sendHelpMessage(channel);
                    return;
                }

                Role finalRole = role;
                if (args.get(2).equalsIgnoreCase("all"))
                {
                    event.getChannel().sendMessage("Progress...").queue(msg -> {
                        for (int i = 0; i < guild.getMembers().size(); i++)
                        {
                            guild.addRoleToMember(guild.getMembers().get(i), finalRole).reason("Command executed by "+author.getAsTag()).queue();
                            msg.editMessage("Progress... "+(i+1)+"/"+guild.getMembers().size()).queue();
                            if ((i+1) == guild.getMembers().size()) msg.editMessage("Role has been added to "+guild.getMembers().size()+ "members.").queue();
                        };
                    });
                }
                else if (args.get(2).equalsIgnoreCase("bot"))
                {
                    event.getChannel().sendMessage("Progress...").queue(msg -> {
                        for (int i = 0; i < guild.getMembers().size(); i++)
                        {
                            if (guild.getMembers().get(i).getUser().isBot())
                            {
                                guild.addRoleToMember(guild.getMembers().get(i), finalRole).reason("Command executed by " + author.getAsTag()).queue();
                                msg.editMessage("Progress... " + (i+1) + "/" + guild.getMembers().size()).queue();
                                if ((i+1) == guild.getMembers().size()) msg.editMessage("Role has been added to bots.").queue();

                            }
                        }
                    });
                }
                else if (args.get(2).equals("humans"))
                {
                    event.getChannel().sendMessage("Progress...").queue(msg ->
                    {
                        int humans = 0;
                        for (int i = 0; i < guild.getMembers().size(); i++)
                        {
                            if (!guild.getMembers().get(i).getUser().isBot())
                            {
                                humans += 1;
                                guild.addRoleToMember(guild.getMembers().get(i), finalRole).reason("Command executed by " + author.getAsTag()).queue();
                                msg.editMessage("Progress... " + humans + "/" + guild.getMembers().size()).queue();
                                if ((i+1) == guild.getMembers().size()) msg.editMessage("Role has been added to all humans.").queue();
                            }
                        }
                    });
                }
                else if (event.message().getMentionedRoles().size() > 0)
                {
                    List<Role> destinationRoles = event.message().getMentionedRoles();
                    destinationRoles.remove(event.message().getMentionedRoles().get(0));
                    event.getChannel().sendMessage("Progress...").queue(msg ->
                    {
                        for (int i = 0; i < guild.getMembersWithRoles(destinationRoles).size(); i++)
                        {
                            guild.addRoleToMember(guild.getMembersWithRoles(destinationRoles).get(i), finalRole).reason("Command executed by " + author.getAsTag()).queue();
                            msg.editMessage("Progress... " + (i+1) + "/" + guild.getMembersWithRoles(destinationRoles).size()).queue();

                            if ((i+1) == guild.getMembersWithRoles(destinationRoles).size()) msg.editMessage("Role has been added to "+guild.getMembersWithRoles(destinationRoles).size()+ "members.").queue();
                        }
                    });
                }
                else if (!event.message().getMentionedMembers().isEmpty())
                {
                    event.getChannel().sendMessage("Progress...").queue(msg ->
                    {
                        for (int i = 0; i < event.message().getMentionedMembers().size(); i++)
                        {
                            guild.addRoleToMember(event.message().getMentionedMembers().get(i), finalRole).reason("Command executed by "+author.getAsTag()).queue();
                            msg.editMessage("Progress... " + (i+1) + "/" + event.message().getMentionedMembers().size()).queue();

                            if ((i+1) == event.message().getMentionedMembers().size()) msg.editMessage("Role has been added to "+event.message().getMentionedMembers().size()+ "members.").queue();
                        }
                    });

                } else {
                    sendHelpMessage(channel);
                    return;
                }

                break;

            case "remove":
                if (event.message().getMentionedRoles().isEmpty())
                {
                    sendHelpMessage(channel);
                    return;
                }

                role = event.message().getMentionedRoles().get(0);
                if (args.size() == 2) {
                    sendHelpMessage(channel);
                    return;
                }

                Role removeRole = role;
                if (args.get(2).equalsIgnoreCase("all")) {
                    guild.getMembers().forEach(member -> guild.removeRoleFromMember(member, removeRole).reason("Command executed by "+author.getAsTag()).queue());
                } else if (args.get(2).equalsIgnoreCase("bot")) {
                    guild.getMembers().forEach(member -> { if (member.getUser().isBot()) guild.removeRoleFromMember(member, removeRole).reason("Command executed by "+author.getAsTag()).queue(); });
                } else if (args.get(2).equals("humans")) {
                    guild.getMembers().forEach(member -> { if (!member.getUser().isBot()) guild.removeRoleFromMember(member, removeRole).reason("Command executed by "+author.getAsTag()).queue(); });
                } else if (event.message().getMentionedRoles().size() > 0) {
                    List<Role> destinationRoles = event.message().getMentionedRoles();
                    destinationRoles.remove(event.message().getMentionedRoles().get(0));
                    guild.getMembersWithRoles(destinationRoles).forEach(member -> guild.removeRoleFromMember(member, removeRole).reason("Command executed by "+author.getAsTag()).queue());
                } else if (!event.message().getMentionedMembers().isEmpty()) {
                    event.message().getMentionedMembers().forEach(member -> guild.removeRoleFromMember(member, removeRole).reason("Command executed by "+author.getAsTag()).queue());
                } else {
                    sendHelpMessage(channel);
                    return;
                }

                break;

            case "create":
                if (args.size() == 1)
                {
                    sendHelpMessage(channel);
                    return;
                }

                String name = args.get(1);

                if (args.size() > 2)
                {
                    if (!args.get(2).startsWith("#"))
                    {
                        sendHelpMessage(channel);
                        return;
                    }

                    String hex = args.get(2).replace("#", "");

                    color = new Color(Integer.valueOf(hex.substring(0, 2), 16),
                            Integer.valueOf(hex.substring(2, 4), 16),
                            Integer.valueOf(hex.substring(4, 6), 16));

                    hoisted = args.contains("hoist");
                    mentionned = args.contains("mentionable");
                }

                guild.createRole()
                        .setName(name)
                        .setColor(color)
                        .setHoisted(hoisted)
                        .setMentionable(mentionned)
                        .reason("Command role executed by "+author.getAsTag())
                        .queue(role1 -> channel.sendMessage(role1.getAsMention()+" has been successfully created!").queue());
                break;

            case "delete":
                if (event.message().getMentionedRoles().isEmpty())
                {
                    sendHelpMessage(channel);
                    return;
                }

                event.message().getMentionedRoles().get(0).delete().reason("Command role executed by "+author.getAsTag()).queue(success -> channel.sendMessage("Role successfully deleted!").queue());
                break;

            case "info":
                if (event.message().getMentionedRoles().isEmpty())
                {
                    sendHelpMessage(channel);
                    return;
                }

                role = event.message().getMentionedRoles().get(0);
                builder.setAuthor("Roleinfo", "https://justaven.xyz", author.getAvatarUrl());
                builder.addField("Name", role.getName(), true);
                builder.addField("ID", role.getId(), true);
                builder.addField("Hoisted", role.isHoisted() ? "Yes": "No", true);
                builder.addField("Mentionable", role.isMentionable() ? "Yes": "No", true);
                builder.addField("Managed", role.isManaged() ? "Yes": "No", true);
                builder.addField("Public role", role.isPublicRole() ? "Yes": "No", true);

                builder.addField("Permissions", role.getPermissions().stream().map(net.dv8tion.jda.api.Permission::name).collect(Collectors.joining(", ")), false);
                builder.setColor(role.getColor());
                builder.setFooter("Command executed by "+author.getAsTag(), author.getAvatarUrl());

                channel.sendMessageEmbeds(builder.build()).queue();
                break;

            case "color":
                if (event.message().getMentionedRoles().isEmpty())
                {
                    sendHelpMessage(channel);
                    return;
                }

                role = event.message().getMentionedRoles().get(0);

                if (args.size() == 2)
                {
                    String hex = "#"+Integer.toHexString(role.getColorRaw());
                    builder.setColor(role.getColor());
                    builder.setDescription("Color of "+role.getName()+" is "+hex);

                    channel.sendMessageEmbeds(builder.build()).queue();
                    return;
                }

                String hex = args.get(2).replace("#", "");

                color = new Color(Integer.valueOf(hex.substring(0, 2), 16),
                        Integer.valueOf(hex.substring(2, 4), 16),
                        Integer.valueOf(hex.substring(4, 6), 16));

                role.getManager().setColor(color).reason("Command roles executed by "+author.getAsTag()).queue();

                builder.setDescription("Color has been successfully changed!");
                builder.setColor(color);
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                break;

            case "hoist":
                if (event.message().getMentionedRoles().isEmpty())
                {
                    sendHelpMessage(channel);
                    return;
                }

                role = event.message().getMentionedRoles().get(0);

                if (args.size() == 2)
                {
                    builder.setDescription(role.getName()+(role.isHoisted() ? "is hoisted": "isn't hoisted"));

                    channel.sendMessageEmbeds(builder.build()).queue();
                    return;
                }

                choice = args.get(2);
                if (choice.equalsIgnoreCase("yes")) hoisted = true;
                else if (choice.equalsIgnoreCase("no")) hoisted = false;
                else {
                    sendHelpMessage(channel);
                    return;
                }

                role.getManager().setHoisted(hoisted).reason("Command roles executed by "+author.getAsTag()).queue();

                builder.setDescription("Role has been successfully changed!");
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                break;

            case "mentionable":
                if (event.message().getMentionedRoles().isEmpty())
                {
                    sendHelpMessage(channel);
                    return;
                }

                role = event.message().getMentionedRoles().get(0);

                if (args.size() == 2)
                {
                    builder.setDescription(role.getName()+(role.isMentionable() ? "is mentionable": "isn't mentionable"));

                    channel.sendMessageEmbeds(builder.build()).queue();
                    return;
                }

                choice = args.get(2);
                if (choice.equalsIgnoreCase("yes")) mentionned = true;
                else if (choice.equalsIgnoreCase("no")) mentionned = false;
                else {
                    sendHelpMessage(channel);
                    return;
                }

                role.getManager().setMentionable(hoisted).reason("Command roles executed by "+author.getAsTag()).queue();

                builder.setDescription("Role has been successfully changed!");
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                break;

            case "name":
                if (event.message().getMentionedRoles().isEmpty())
                {
                    sendHelpMessage(channel);
                    return;
                }

                role = event.message().getMentionedRoles().get(0);

                if (args.size() == 2)
                {
                    builder.setDescription("The role name is: "+role.getName());

                    channel.sendMessageEmbeds(builder.build()).queue();
                    return;
                }

                choice = String.join(" ", args).replace(args.get(0), "").replace(args.get(1), "");

                role.getManager().setName(choice).reason("Command roles executed by "+author.getAsTag()).queue();

                builder.setDescription("Role name has been successfully changed!");
                event.getChannel().sendMessageEmbeds(builder.build()).queue();
                break;

            default:
                sendHelpMessage(channel);
        }
    }

    private void sendHelpMessage(TextChannel channel)
    {
        channel.sendMessageEmbeds(new EmbedBuilder().addField(getHelp()).build()).queue();
    }

    @Override
    public Type getType() {
        return Type.ADMIN;
    }

    @Override
    public Permission getPermission() {
        return Permission.ADMIN;
    }

    @Override
    public MessageEmbed.Field getHelp() {
        return new MessageEmbed.Field("Manage roles in the server","Usage: \n`"+ Constants.PREFIX + getInvoke() +" list " +
                "\n"+Constants.PREFIX + getInvoke()+" add <@role> <all/bots/humans/@role/@user>" +
                "\n"+Constants.PREFIX + getInvoke()+" remove <@role> <all/bots/humans/@role/@user>" +
                "\n"+Constants.PREFIX + getInvoke()+" create <name> [#hex color] [hoist] [mentionable]" +
                "\n"+Constants.PREFIX + getInvoke()+" delete <@role>" +
                "\n"+Constants.PREFIX + getInvoke()+" info <@role>" +
                "\n"+Constants.PREFIX + getInvoke()+" color <@role> [hex color]" +
                "\n"+Constants.PREFIX + getInvoke()+" hoist <@role> [yes/no]" +
                "\n"+Constants.PREFIX + getInvoke()+" mentionable <@role> [yes/no]" +
                "\n"+Constants.PREFIX + getInvoke()+" name <@role> [new name]" +
                "`", false);
    }

    @Override
    public String getInvoke() {
        return "roles";
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
        return Arrays.asList(net.dv8tion.jda.api.Permission.MANAGE_ROLES);
    }
}
