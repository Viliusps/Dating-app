import React from 'react';
import ScreenWrapper from '../styles/ScreenWrapper';
import StyledButton from '../styles/StyledButton';
import { sync } from '../api/calendar-axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Alert } from 'react-native';

const ProfilePage = () => {
  const synchronise = () => {
    AsyncStorage.getItem('loggedInUser').then((userId) => {
      sync(userId)
        .then(() => {
          Alert.alert(
            'Success!',
            'Calendar synchronisation succesful.',
            [
              {
                text: 'Close',
                style: 'cancel'
              }
            ],
            { cancelable: false }
          );
        })
        .catch(() => {
          Alert.alert(
            'Error!',
            'Calendar synchronisation failed.',
            [
              {
                text: 'Close',
                style: 'cancel'
              }
            ],
            { cancelable: false }
          );
        });
    });
  };

  return (
    <ScreenWrapper>
      <StyledButton title="Synchronise calendar" onPress={synchronise} />
    </ScreenWrapper>
  );
};

export default ProfilePage;
