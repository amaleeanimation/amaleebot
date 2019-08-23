package yt.amalee.animation.discord.bot;

import java.util.List;
import java.util.Random;

import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Category;
import net.dv8tion.jda.core.entities.Channel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.PermissionOverride;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class CreateEventChannel extends AbstractEvent {

	Random random;

	public CreateEventChannel() {
		random = new Random();
	}

	@Override
	protected String commandString() {
		return "!create";
	}

	@Override
	protected void runEvent() {

	}

	@Override
	protected void runEvent(MessageReceivedEvent event) {
		
		List<Permission> perms = event.getMember().getPermissions();
		
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
			System.out.println("role list empty");
			projectRole = guild.getController().createRole().setName(roleName).complete();
		} else {
			projectRole = roleList.get(0);
		}	
		List<Category> categoryList = guild.getCategoriesByName("projects", true);
		
		if(categoryList.isEmpty()) {
			return;
		}
		
		Category projectCategory = categoryList.get(0);
		
		List<TextChannel> channelList = guild.getTextChannelsByName(channelString, true);
		Channel channel;
		if(channelList.isEmpty()) {
			System.out.println("channel list empty");
			channel =  projectCategory.createTextChannel(channelString).complete();
		} else {
			channel = channelList.get(0);
		}	

		PermissionOverride override = channel.getPermissionOverride(projectRole);
		if(null != override) {
			override.delete();
		}
		channel.createPermissionOverride(projectRole).setAllow(Permission.MESSAGE_READ, Permission.MESSAGE_HISTORY, Permission.MESSAGE_WRITE).complete();
	}
}
