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
	<version>0.5</version>
    </dependency>
</dependencies>
```
Or simply download the latest version of the library jar from the release page.

## Changes
There has been some new features in version 0.5, but nothing that is breaking backwards compability. New features include:
* Whispers (This was removed in 0.4, but is now back. Twitch said they'd remove it , but they haven't so far so...)
* Support for new sub-streak logic
* Updated the example
* Added even more tests
* Touched up on some JavaDoc

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

