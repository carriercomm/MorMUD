package language;

import java.util.Arrays;

import main.Main;

public abstract class Parser {
    public static void parseConsole(String s) {
	s.trim();
	String[] c = s.split(" ");
	for (int i = 0; i < c.length; i++) {
	    if (c[i].toLowerCase().equals("quit") || c[i].toLowerCase().equals("stop")) {
		Main.shutdown();
		return;
	    }
	}
    }
}
