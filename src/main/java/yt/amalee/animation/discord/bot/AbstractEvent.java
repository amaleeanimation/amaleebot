package yt.amalee.animation.discord.bot;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class AbstractEvent extends ListenerAdapter {

	String[] words;
	MessageReceivedEvent event;
	
	public void onMessageReceived(MessageReceivedEvent event) {
		this.event = event;
		this.words = getEvent().getMessage().getContentDisplay().split(" ");
		if(!words[0].equalsIgnoreCase(commandString())){
			return;
		}
		runEvent(this.event);
	}
	
	protected abstract String commandString();
	protected abstract void runEvent();
	protected abstract void runEvent(MessageReceivedEvent event);
	
	protected String[] getWords(){
		return words;
	}
	
	protected MessageReceivedEvent getEvent(){
		return event;
	}
	
	protected void sendMessage(Message msg){
		System.out.println("Send Prep");
		System.out.println(getEvent().getChannel().getName());
		getEvent().getChannel().sendMessage(msg).complete();
		System.out.println("Send Complete");
	}
}
