import React from 'react';
import { View, Text } from 'react-native';
import ScreenWrapper from '../styles/ScreenWrapper';
import StyledButton from '../styles/StyledButton';

const Home = (props) => {
  const onPress = (page) => {
    props.navigation.navigate(page);
  };

  return (
    <ScreenWrapper>
      <Text>Laikinai prisijungio vartotojo id: 1</Text>
      <View
        style={{
          flex: 1,
          justifyContent: 'center',
          alignItems: 'center',
          backgroundColor: 'transparent'
        }}>
        <StyledButton title="Hobbies" onPress={() => onPress('Hobbies')} />
        <StyledButton title="Chat" onPress={() => onPress('ChatPage')} />
      </View>
    </ScreenWrapper>
  );
};

export default Home;
