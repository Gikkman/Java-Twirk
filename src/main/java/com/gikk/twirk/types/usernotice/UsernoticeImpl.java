package com.gikk.twirk.types.usernotice;

import com.gikk.twirk.types.emote.Emote;
import com.gikk.twirk.types.usernotice.subtype.Raid;
import com.gikk.twirk.types.usernotice.subtype.Ritual;
import com.gikk.twirk.types.usernotice.subtype.Subscription;
import java.util.List;
import java.util.Optional;

class UsernoticeImpl implements Usernotice{

    private final String raw;

    private final Optional<Raid> raid;
    private final Optional<Subscription> subscription;
    private final Optional<Ritual> ritual;

    private final String systemMessage;
    private final String message;
    private final List<Emote> emotes;

    private final long sentTimestamp;
    private final int roomID;
    private final String messageID;

    UsernoticeImpl(DefaultUsernoticeBuilder builder){
        this.raw = builder.rawLine;

        this.raid = Optional.ofNullable(builder.raid);
        this.subscription = Optional.ofNullable(builder.subscription);
        this.ritual = Optional.ofNullable(builder.ritual);

        this.systemMessage = builder.systemMessage;
        this.message = builder.message;
        this.emotes = builder.emotes;

        this.sentTimestamp = builder.sentTimestamp;
        this.roomID = builder.roomID;
        this.messageID = builder.messageID;
    }

    @Override
    public String getSystemMessage() {
        return systemMessage;
    }

    @Override
    public boolean isSubscription() {
        return subscription.isPresent();
    }

    @Override
    public Optional<Subscription> getSubscription() {
        return subscription;
    }

    @Override
    public boolean isRaid() {
        return raid.isPresent();
    }

    @Override
    public Optional<Raid> getRaid() {
        return raid;
    }

    @Override
    public boolean isRitual() {
        return ritual.isPresent();
    }

    @Override
    public Optional<Ritual> getRitual() {
        return ritual;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean hasEmotes() {
        return !emotes.isEmpty();
    }

    @Override
    public List<Emote> getEmotes() {
        return emotes;
    }

    @Override
    public long getSentTimestamp() {
        return sentTimestamp;
    }

    @Override
    public long getRoomID() {
        return roomID;
    }

    @Override
    public String getMessageID() {
        return messageID;
    }

    @Override
    public String getRaw() {
        return raw;
    }

}
