import React, { useState } from 'react';
import ScreenWrapper from '../styles/ScreenWrapper';
import { Text } from 'react-native';
import StyledButton from '../styles/StyledButton';
import { vote } from '../api/date-time-axios';
import BouncyCheckbox from 'react-native-bouncy-checkbox';

const TimeVote = ({ route, navigation }) => {
  const { times } = route.params;
  const handleClick = (time) => {
    vote(time).then((response) => {
      if (response.page === 'OPTIONS') {
        navigation.navigate('ChatOptionsPage');
      }
    });
  };
  return (
    <ScreenWrapper>
      <Text>Multiple matching times found. Please select one</Text>
      {times.map((time, index) => (
        <BouncyCheckbox
          key={index}
          size={25}
          fillColor="green"
          unfillColor="#FFFFFF"
          text={new Date(time).toLocaleString()}
          textStyle={{
            textDecorationLine: 'none'
          }}
          iconStyle={{ borderColor: 'green' }}
          innerIconStyle={{ borderWidth: 2 }}
          onPress={(isChecked) => {
            isChecked && handleClick(time);
          }}
        />
      ))}
    </ScreenWrapper>
  );
};

export default TimeVote;
