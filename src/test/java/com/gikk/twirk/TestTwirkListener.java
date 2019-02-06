package com.gikk.twirk;

import com.gikk.twirk.events.TwirkListener;
import com.gikk.twirk.types.clearChat.ClearChat;
import com.gikk.twirk.types.hostTarget.HostTarget;
import com.gikk.twirk.types.mode.Mode;
import com.gikk.twirk.types.notice.Notice;
import com.gikk.twirk.types.roomstate.Roomstate;
import com.gikk.twirk.types.twitchMessage.TwitchMessage;
import com.gikk.twirk.types.usernotice.Usernotice;
import com.gikk.twirk.types.users.TwitchUser;
import com.gikk.twirk.types.users.Userstate;
import java.util.Collection;

/**
 *
 * @author Gikkman
 */
public class TestTwirkListener implements TwirkListener{
    TestConsumer<String>    anythingTest = new TestConsumer<>();
    TestConsumer<String>    joinTest = new TestConsumer<>();
    TestConsumer<String>    partTest = new TestConsumer<>();
    TestConsumer<Void>      connectTest = new TestConsumer<>();
    TestConsumer<Void>      reconnectTest = new TestConsumer<>();
    TestConsumer<String>    disconnectTest = new TestConsumer<>();
    TestConsumer<Notice>    noticeTest = new TestConsumer<>();
    TestConsumer<HostTarget> hostTest = new TestConsumer<>();
    TestConsumer<Mode>      modeTest = new TestConsumer<>();
    TestConsumer<Userstate> userstateTest = new TestConsumer<>();
    TestConsumer<Roomstate> roomstateTest = new TestConsumer<>();
    TestConsumer<ClearChat> clearchatTest = new TestConsumer<>();
    TestConsumer<String>    unknownTest = new TestConsumer<>();
    TestConsumer<Collection<String>> namelistTest = new TestConsumer<>();
    TestBiConsumer<TwitchUser, TwitchMessage> privMsgTest = new TestBiConsumer<>();
    TestBiConsumer<TwitchUser, Usernotice> usernoticeTest = new TestBiConsumer<>();

    @Override
    public void onAnything( String unformatedMessage ) {
        anythingTest.consume(unformatedMessage);
    }

    @Override
    public void onPrivMsg( TwitchUser  sender, TwitchMessage  message ) {
        privMsgTest.consume(sender, message);
    }

    @Override
    public void onJoin( String joinedNick ) {
        joinTest.consume(joinedNick);
    }

    @Override
    public void onPart( String partedNick ) {
        partTest.consume(partedNick);
    }

    @Override
    public void onConnect() {
        connectTest.consume(null);
    }

    @Override
    public void onReconnect() {
        reconnectTest.consume(null);
    }

    @Override
    public void onDisconnect() {
        disconnectTest.consume(null);
    }

    @Override
    public void onNotice( Notice  notice ) {
        noticeTest.consume(notice);
    }

    @Override
    public void onHost( HostTarget  hostNotice ) {
        hostTest.consume(hostNotice);
    }

    @Override
    public void onMode( Mode  mode ) {
        modeTest.consume(mode);
    };

    @Override
    public void onUserstate( Userstate  userstate ) {
        userstateTest.consume(userstate);
    }

    @Override
    public void onRoomstate( Roomstate  roomstate ) {
        roomstateTest.consume(roomstate);
    }

    @Override
    public void onClearChat( ClearChat  clearChat ) {
        clearchatTest.consume(clearChat);
    }

    @Override
    public void onNamesList( Collection<String> namesList ) {
        namelistTest.consume(namesList);
    }

    @Override
    public void onUsernotice(TwitchUser user, Usernotice usernotice) {
        usernoticeTest.consume(user, usernotice);
    }

    @Override
    public void onUnknown( String unformatedMessage ) {

    }
}
