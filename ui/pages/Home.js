import React from 'react';
import { View, Text, Switch } from 'react-native';
import ScreenWrapper from '../styles/ScreenWrapper';
import StyledButton from '../styles/StyledButton';
import { useState } from 'react';

const Home = (props) => {
  const [isEnabled, setIsEnabled] = useState(false);
  const [userId, setUserId] = useState(1);
  const toggleSwitch = () => {
    setIsEnabled((previousState) => !previousState);
    if (userId === 1) setUserId(2);
    else setUserId(1);
  };
  return (
    <ScreenWrapper>
      <Text>Temporary user id: {userId}</Text>
      <View
        style={{
          flex: 1,
          justifyContent: 'center',
          alignItems: 'center',
          backgroundColor: 'transparent'
        }}>
        <StyledButton title="Hobbies" onPress={() => props.navigation.navigate('Hobbies')} />
        <StyledButton
          title="Chat"
          onPress={() => props.navigation.navigate('ChatPage', { userId: userId })}
        />
        <Text>Temp user selection</Text>
        <Switch
          trackColor={{ false: '#767577', true: '#81b0ff' }}
          thumbColor={isEnabled ? '#f5dd4b' : '#f4f3f4'}
          ios_backgroundColor="#3e3e3e"
          onValueChange={toggleSwitch}
          value={isEnabled}
        />
      </View>
    </ScreenWrapper>
  );
};

export default Home;
