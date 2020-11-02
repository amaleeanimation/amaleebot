package yt.amalee.animation.discord.bot;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.PermissionOverride;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ArchiveEventChannel extends AbstractEvent {

	Random random;

	public ArchiveEventChannel() {
		random = new Random();
	}

	@Override
	protected String commandString() {
		return "!archive";
	}

	@Override
	protected void runEvent() {
		
	}

	@Override
	protected void runEvent(MessageReceivedEvent event) {
		
		EnumSet<Permission> perms = event.getMember().getPermissions();
		
		if(!perms.contains(Permission.ADMINISTRATOR)) {
			System.out.println("not admin");
			return;
		}
		
		String msg =  getEvent().getMessage().getContentDisplay();

		String projectName = msg.substring(msg.indexOf(' '));
		String channelString = projectName.trim().replace(" ", "-");
		String roleName = "project - " + projectName;
		
		Guild guild = event.getGuild();
		
		List<Role> roleList = guild.getRolesByName(roleName, true);
		Role projectRole;
		if(roleList.isEmpty()) {
			return;
		} else {
			projectRole = roleList.get(0);
		}	
		List<Category> categoryList = guild.getCategoriesByName("archived projects", true);
		if(categoryList.isEmpty()) {
			return;
		}
		
		Category archiveCategory = categoryList.get(0);
		
		List<TextChannel> channelList = guild.getTextChannelsByName(channelString, true);
		TextChannel channel;
		if(channelList.isEmpty()) {
			return;
		} else {
			channel = channelList.get(0);
		}	

		channel.getManager().setParent(archiveCategory).complete();
		
		PermissionOverride override = channel.getPermissionOverride(projectRole);
		if(null != override) {
			override.getManager().clear(Permission.MESSAGE_WRITE).complete();
		} else {
			channel.createPermissionOverride(projectRole).setAllow(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY).complete();
		}
	}

}
