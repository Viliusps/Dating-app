import React, { useEffect, useState } from 'react';
import { Text, StyleSheet, View, TextInput, ScrollView, KeyboardAvoidingView } from 'react-native';
import { useIsFocused } from '@react-navigation/native';
import ScreenWrapper from '../../styles/ScreenWrapper';
import { getUser } from '../../api/users-axios';
import { getCoupleByUserId } from '../../api/couples-axios';
import { Icon } from '@rneui/themed';
import { useHeaderHeight } from '@react-navigation/elements';

const ChatPage = (props) => {
  const [couple, setCouple] = useState(null);
  const [otherPerson, setOtherPerson] = useState(null);
  const [Mesage, setMessage] = useState('');
  const isFocused = useIsFocused();
  const userId = 1;
  const height = useHeaderHeight();

  useEffect(() => {
    getCoupleByUserId(userId).then((couple) => {
      setCouple(couple);
      if (userId === couple.first) {
        getUser(couple.second).then((user) => {
          setOtherPerson(user);
        });
      } else if (userId === couple.second) {
        getUser(couple.first).then((user) => {
          setOtherPerson(user);
        });
      }
    });
  }, [isFocused]);

  return (
    <ScreenWrapper>
      <KeyboardAvoidingView
        style={{ position: 'absolute', left: 0, right: 0, bottom: 50 }}
        keyboardVerticalOffset={height + 47}
        behavior="position">
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
            <TextInput style={styles.input} onChangeText={setMessage} />
            <Icon
              style={styles.icon}
              name="ios-send-sharp"
              type="ionicon"
              color="black"
              onPress={() => console.log(Mesage)}
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
  icon: {
    paddingRight: 10
  },
  footer: {
    flexDirection: 'row',
    alignItems: 'center',
    flex: 1,
    justifyContent: 'center'
  }
});
