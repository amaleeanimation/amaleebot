package yt.amalee.animation.discord.bot;

import java.util.Map;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

/**
 * Hello world!
 *
 */
public class App {
	
	public static JDA api;

	public static void main(String[] args) {
		Map<String, String> env = System.getenv();

		String botToken = env.get("BOT_TOKEN");
		
		try {
			api = new JDABuilder(AccountType.BOT).setToken(botToken)
					.addEventListener(new CreateEventChannel()).addEventListener(new ArchiveEventChannel())
					.buildBlocking();
		} catch (IllegalArgumentException e) {
			System.out.println("The config was not populated. Please enter an email and password.");
		} catch (LoginException e) {
			System.out
					.println("The provided email / password combination was incorrect. Please provide valid details.");
		} catch (InterruptedException e) {
			System.out.println("Interrupt");
		}
	}
}
