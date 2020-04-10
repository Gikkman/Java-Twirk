# Java-Twirk
[![](https://jitpack.io/v/Gikkman/Java-Twirk.svg)](https://jitpack.io/#Gikkman/Java-Twirk)
You can contact us via the [Discord Server](https://discord.gg/8NXaEyV)

Small, library for creating an IRC connection to the Twitch chat.

The library is intended to make communication via Twitch chat as easy as possible, and uses Java objects to represent most events that can occur in Twitch chat. 

Java 8 compatible.

## Installation
Include the following in your pom.xml

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
<dependencies>
    <dependency>
        <groupId>com.github.gikkman</groupId>
	<artifactId>Java-Twirk</artifactId>
	<version>0.6.1</version>
    </dependency>
</dependencies>
```
Or simply download the latest version of the library jar from the release page.

## Changes
### 0.6.1
Hotfix release since some emote IDs were still not parsed correctly (see #22). This hotfix should hopefully fix this issue.
Please report any further issues with parsing emotes.

### 0.6
There has only been minor changes between 0.5 and 0.6. Nothing that should break backwards compatibility. Fixes include:
* Fixed NumberFormatException on modified emotes
  * Twitch changed the emote IDs from always being a number to sometimes being a number_string -.-' Its fixed now
* Fixed SockerClosedException stacktrace printing on some locales
  * I think I fixed it at least, but this one is hard to test since there are so many locales.
* Updated the emotes parse for a safer and faster implementation.
  * This deprecates a previously public method (`EmoteParse.parseEmote(String, String)`), and the new method is package private. There isn't really any need to call these methods from outside the library
* Twirk will not only show the "User X was not online" or "User X was already online", when in verbose mode.
  * This happened when we see a leave/part message but didn't track the user correctly. I felt like it was not needed unless you want the verbose output
* Updated the example a bit
* Started to move towards proper JUnit tests
  * My home rolled test setup wasn't very user friendly, so now I started moving to user regular `@Test` tests. I'll eventually convert all tests to this format 

And probably some more...

# Usage
#### Basic usage
```Java
  final Twirk twirk = new TwirkBuilder(channel, SETTINGS.MY_NICK, SETTINGS.MY_PASS).build();
  twirk.connect();
  ...
  twirk.close();
```
#### Events
All events which can be reacted to are listed in [TwirkListener.java](https://github.com/Gikkman/Java-Twirk/blob/master/twirc/src/main/java/com/gikk/twirk/events/TwirkListener.java) This snippet will make your bot respond to any channel
message with a "pong USER_NAME".
```Java
  twirk.addIrcListener( new TwirkListener() { 
    public void onPrivMsg( TwitchUser sender, TwitchMessage message) {
      twirk.channelMessage("pong " + sender.getDisplayName() );
    }
  } );
```

For a more complex example, which shows how to connect properly and how to write simple bot commands, check out the example code in `src/example/java`

#### Extendable
You can make Twirk use your own implementation of all event types by using custom builder classes. By extending the types Builder interface, and then passing an instance of your custom builder to the TwirkBuilder, you can use your own custom implementation of whichever type you want.
```Java
  final Twirk twirk = new TwirkBuilder(channel, SETTINGS.MY_NICK, SETTINGS.MY_PASS)
    .setClearChatBuilder( new MyClearChatBuilder() )
    .build();
```
This will make the Twirk instance build instances of your custom implementation of `ClearChat` events

# Contribute
If you find any issues, or have suggestions for features (which does not clutter the library), feel free to submit an [Issue](https://github.com/Gikkman/Java-Twirk/issues) or make a pull request. You can also reach me on [Twitter](https://twitter.com/gikkman) or on [Twitch](http://twitch.com/gikkman)


# License
This library is licensed under the [MIT License](https://tldrlegal.com/license/mit-license). If you use it, a link to this GitHub page is also greatly appriciated, if possible :)

