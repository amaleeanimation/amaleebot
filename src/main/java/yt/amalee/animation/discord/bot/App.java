package yt.amalee.animation.discord.bot;

import java.util.Map;

import javax.security.auth.login.LoginException;

/**
 * Hello world!
 *
 */
public class App {
	
	public static net.dv8tion.jda.api.JDA api;

	public static void main(String[] args) {
		Map<String, String> env = System.getenv();

		String botToken = env.get("BOT_TOKEN");
		
		try {
			api = net.dv8tion.jda.api.JDABuilder.createDefault(botToken)
					.addEventListeners(new CreateEventChannel(), new ArchiveEventChannel())
					.build();
		} catch (IllegalArgumentException e) {
			System.out.println("The config was not populated. Please enter an email and password.");
		} catch (LoginException e) {
			System.out.println(e.getMessage());
			System.out
					.println("The provided email / password combination was incorrect. Please provide valid details.");
		}
	}
}
