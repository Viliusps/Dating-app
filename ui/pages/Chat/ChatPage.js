import React, { useEffect, useState, useRef } from 'react';
import { Text, StyleSheet, View, TextInput, KeyboardAvoidingView, ScrollView } from 'react-native';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import { useIsFocused } from '@react-navigation/native';
import ScreenWrapper from '../../styles/ScreenWrapper';
import { getUser } from '../../api/users-axios';
import { getCoupleByUserId } from '../../api/couples-axios';
import { Icon } from '@rneui/themed';
import { useHeaderHeight } from '@react-navigation/elements';
import { getMessagesByChat, postMessage } from '../../api/messages-axios';
import { Keyboard } from 'react-native';

const ChatPage = (props) => {
  const [couple, setCouple] = useState(null);
  const [otherPerson, setOtherPerson] = useState(null);
  const [messages, setMessages] = useState([]);
  const [Message, setMessage] = useState('');
  const isFocused = useIsFocused();
  const userId = props.route.params.userId;
  const height = useHeaderHeight();
  const scrollViewRef = useRef();

  useEffect(() => {
    getCoupleByUserId(userId).then((couple) => {
      setCouple(couple);
      let otherUser = null;
      if (userId === couple.first) {
        otherUser = couple.second;
      } else if (userId === couple.second) {
        otherUser = couple.first;
      }
      getUser(otherUser).then((user) => {
        setOtherPerson(user);
      });
      getMessagesByChat(couple.chat).then((messages) => {
        setMessages(messages);
      });
    });
  }, [isFocused]);

  const sendMessage = () => {
    postMessage(Message, new Date(), userId, couple.chat).then(() => {
      getMessagesByChat(couple.chat).then((messages) => {
        setMessages(messages);
      });
      this.textInput.clear();
      scrollViewRef.current.scrollToEnd({ animated: true });
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
            message.sender == userId ? (
              <View key={message.id} style={styles.myMessage}>
                <Text>{message.content}</Text>
              </View>
            ) : (
              <View key={message.id} style={styles.otherMessage}>
                <Text>{message.content}</Text>
              </View>
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
            <Icon
              style={styles.icon}
              name="ios-send-sharp"
              type="ionicon"
              color="black"
              onPress={() => sendMessage()}
            />
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
  }
});
