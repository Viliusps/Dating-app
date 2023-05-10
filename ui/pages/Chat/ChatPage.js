import React, { useEffect, useState, useRef } from 'react';
import {
  Text,
  StyleSheet,
  View,
  TextInput,
  KeyboardAvoidingView,
  Linking,
  TouchableOpacity,
  ActivityIndicator
} from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { useIsFocused } from '@react-navigation/native';
import ScreenWrapper from '../../styles/ScreenWrapper';
import { getCoupleByUserId } from '../../api/couples-axios';
import { Icon } from '@rneui/themed';
import { useHeaderHeight } from '@react-navigation/elements';
import { getMessagesByChat, postMessage } from '../../api/messages-axios';
import { getSentSongsByChat } from '../../api/sent-song-axios';
import { findSongRecommendation, getSongIdById } from '../../api/songs-axios';

const ChatPage = (props) => {
  const [couple, setCouple] = useState(null);
  const [messages, setMessages] = useState([]);
  const [Message, setMessage] = useState('');
  const [loader, setLoader] = useState(false);
  const isFocused = useIsFocused();
  const userId = props.route.params.userId;
  const height = useHeaderHeight();
  const scrollViewRef = useRef();

  useEffect(() => {
    getCoupleByUserId(userId).then((couple) => {
      setCouple(couple);
      getMessagesByChat(couple.chat).then((messages) => {
        getSentSongsByChat(couple.chat).then((songs) => {
          var merged = messages.concat(songs);
          merged.songID = undefined;
          merged.sort(function (a, b) {
            const dateA = new Date(a.date);
            const dateB = new Date(b.date);
            return dateA - dateB;
          });
          setMessages(merged);
        });
      });
    });
  }, [isFocused]);

  const updateChat = () => {
    getMessagesByChat(couple.chat).then((messages) => {
      getSentSongsByChat(couple.chat).then((songs) => {
        var merged = messages.concat(songs);
        merged.songID = undefined;
        merged.sort(function (a, b) {
          const dateA = new Date(a.date);
          const dateB = new Date(b.date);
          return dateA - dateB;
        });
        merged.map((e, i) => (e.keyID = i));
        setMessages(merged);
      });
    });
    this.textInput.clear();
    setMessage('');
    scrollViewRef.current.scrollToEnd({ animated: true });
  };

  const writeMessage = () => {
    setLoader(true);
    if (Message === '!song') {
      findSongRecommendation(userId, couple.second, couple.chat).then(() => {
        updateChat();
        setLoader(false);
      });
    } else {
      postMessage(Message, new Date(), userId, couple.chat).then(() => {
        updateChat();
        setLoader(false);
      });
    }
  };

  const getSongURL = async (id) => {
    return await getSongIdById(id).then((value) => {
      return `https://open.spotify.com/track/${value.songID}?utm_source=generator`;
    });
  };

  return (
    <ScreenWrapper>
      <KeyboardAwareScrollView
        ref={scrollViewRef}
        style={{ maxHeight: '87%' }}
        onContentSizeChange={() => scrollViewRef.current.scrollToEnd({ animated: true })}>
        {messages.length > 0 ? (
          messages.map((message) =>
            message.sender.toString() === userId ? (
              message.songID === undefined ? (
                <View key={message.keyID} style={styles.myMessage}>
                  <Text>{message.content}</Text>
                </View>
              ) : (
                <TouchableOpacity
                  activeOpacity={0.8}
                  key={message.keyID}
                  style={styles.myMessage}
                  onPress={async () => {
                    Linking.openURL(await getSongURL(message.songID));
                  }}>
                  <Icon name="play-circle" type="ionicon" size={32} />
                  <Text style={styles.songLink}>Recommended Song</Text>
                </TouchableOpacity>
              )
            ) : message.songID === undefined ? (
              <View key={message.keyID} style={styles.otherMessage}>
                <Text>{message.content}</Text>
              </View>
            ) : (
              <TouchableOpacity
                activeOpacity={0.8}
                key={message.keyID}
                style={styles.otherMessage}
                onPress={async () => Linking.openURL(await getSongURL(message.songID))}>
                <Icon name="play-circle" type="ionicon" size={32} />
                <Text style={styles.songLink}>Recommended Song</Text>
              </TouchableOpacity>
            )
          )
        ) : (
          <View style={styles.view}>
            <Text style={styles.Empty}>You have no messages yet. Start chatting!</Text>
          </View>
        )}
      </KeyboardAwareScrollView>
      <KeyboardAvoidingView
        style={{ position: 'absolute', left: 0, right: 0, bottom: 50, flex: 1 }}
        keyboardVerticalOffset={height + 47}
        behavior="padding">
        <View behavior="height" style={styles.footer}>
          <Icon
            name="camera-outline"
            type="ionicon"
            color="white"
            size={40}
            onPress={() => console.log('camera')}
          />
          <Icon
            name="mic"
            type="ionicon"
            color="white"
            size={40}
            onPress={() => console.log('mic')}
          />
          <Icon
            name="images-outline"
            type="ionicon"
            color="white"
            size={40}
            onPress={() => console.log('images')}
          />
          <View style={styles.searchSection}>
            <TextInput
              placeholder="Try '!song' ..."
              ref={(input) => {
                this.textInput = input;
              }}
              style={styles.input}
              onFocus={() => {
                setTimeout(() => {
                  scrollViewRef.current.scrollToEnd({ animated: true });
                }, 200);
              }}
              onChangeText={setMessage}
            />
            {loader ? (
              <ActivityIndicator size="large" />
            ) : (
              <Icon
                disabled={loader}
                style={styles.icon}
                name="ios-send-sharp"
                type="ionicon"
                color="black"
                onPress={() => writeMessage()}
              />
            )}
          </View>
        </View>
      </KeyboardAvoidingView>
    </ScreenWrapper>
  );
};
export default ChatPage;

const styles = StyleSheet.create({
  header: {
    color: 'white',
    fontSize: 18
  },
  input: {
    height: 40,
    width: 200,
    margin: 12,
    padding: 10,
    borderRadius: 20,
    backgroundColor: 'white',
    flex: 1
  },
  searchSection: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderRadius: 50,
    height: 40,
    width: '65%',
    marginLeft: 10
  },
  myMessage: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderRadius: 50,
    minHeight: 40,
    marginLeft: 10,
    marginTop: 10,
    alignSelf: 'flex-end',
    padding: 15,
    marginRight: 20,
    maxWidth: '80%'
  },
  otherMessage: {
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
    borderRadius: 50,
    minHeight: 40,
    marginLeft: 10,
    marginTop: 10,
    alignSelf: 'flex-start',
    padding: 15,
    maxWidth: '80%'
  },
  icon: {
    paddingRight: 10
  },
  footer: {
    flexDirection: 'row',
    alignItems: 'center',
    flex: 1,
    justifyContent: 'center'
  },
  Empty: {
    color: 'white',
    textAlign: 'center',
    fontSize: 18
  },
  songLink: { textDecorationLine: 'underline' }
});
