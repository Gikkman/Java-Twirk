# Java-Twirk
[![](https://jitpack.io/v/Gikkman/Java-Twirk.svg)](https://jitpack.io/#Gikkman/Java-Twirk)
[![](https://img.shields.io/gitter/room/gitterHQ/gitter.svg)](https://gitter.im/Java-Twirk/Twirk#)
You can also contact us via the [Discord Server](https://discord.gg/8NXaEyV)

Small, basic library for creating an IRC connection to the Twitch chat.

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
	<version>LATEST_RELEASE_VERION_HERE</version>
    </dependency>
</dependencies>
```
Or simply download the latest version of the library jar from the release page.

## Changes
There has beenmajor changes from Java-Twirk 0.3 to Java-Twirk 0.4. I am sorry for the hassle, but it was a necessity. Changes include (but is not limited to)
* Added support for Subscription Gift
* Added support for Raid
* Encapsulated all default factories (previously all public under GikkDefault_<NAME HERE>)
* TwirkListenerBaseImpl has been removed, please implement TwirkListener instead
* Mode-events has been depricated (please use the TwitchUser.isMod() instead)
* Subscriber-event has been removed. It is now found under Usernotice-event instead (Twitch has changed their grouping)
* USER_TYPES now has category SUBSCRIBER, which is given to regular subs and turbo subs.
* USER_TYPES now has ranks, which allows you to do if( user.getUserType().value >= USER_TYPE.SUBSCRIBER.value )
* Added the isOwner() status on TwichUser

And probably some more. Thankfully, Java is a strongly typed language :D

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

