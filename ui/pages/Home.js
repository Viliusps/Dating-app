import React, { useEffect } from 'react';
import { View, Text } from 'react-native';
import ScreenWrapper from '../styles/ScreenWrapper';
import StyledButton from '../styles/StyledButton';
import { useState } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useIsFocused } from '@react-navigation/native';

const Home = (props) => {
  const [userId, setUserId] = useState(null);
  const isFocused = useIsFocused();

  useEffect(() => {
    AsyncStorage.getItem('loggedInUser').then((response) => {
      setUserId(response);
    });
  }, [isFocused]);

  const handleLogout = () => {
    AsyncStorage.clear().then(() => {
      setUserId(null);
    });
  };

  return (
    <ScreenWrapper>
      <Text>Logged in user id: {userId}</Text>
      <View
        style={{
          flex: 1,
          justifyContent: 'center',
          alignItems: 'center',
          backgroundColor: 'transparent'
        }}>
        {userId ? (
          <>
            <StyledButton title="Logout" onPress={() => handleLogout()} />
            <StyledButton title="Match" onPress={() => props.navigation.navigate('Match')} />
            <StyledButton title="Hobbies" onPress={() => props.navigation.navigate('Hobbies')} />
            <StyledButton
              title="Chat"
              onPress={() => props.navigation.navigate('ChatPage', { userId: userId })}
            />
            <StyledButton title="Profile" onPress={() => props.navigation.navigate('Profile')} />
          </>
        ) : (
          <>
            <StyledButton title="Register" onPress={() => props.navigation.navigate('Register')} />
            <StyledButton title="Login" onPress={() => props.navigation.navigate('Login')} />

          </>
        )}
      </View>
    </ScreenWrapper>
  );
};

export default Home;
