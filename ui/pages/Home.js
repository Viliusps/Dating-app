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

  const [dateRecommendation, setDateRecommendation] = useState(null);
  const [showModal, setShowModal] = useState(false); // State to control the visibility of the modal
  const [selectedOption, setSelectedOption] = useState(null); // State to track the selected option

  const generateDateRecommendation = () => {
    fetch(`/api/v1/dateRecommendation/${userId}`)
      .then(response => {
        if (response.ok) {
          console.log(response);
          return response.json();
        } else {
          throw new Error('Error retrieving date recommendation');
        }
      })
      .then(dateRecommendation => {
        // Handle the date recommendations here
        console.log(dateRecommendation);
        setDateRecommendation(dateRecommendation); // Set the state with the received data
        setShowModal(true);
      })
      .catch(error => {
        // Handle the error here
        console.error(error);
      });
  };

  const closeModal = () => {
    setShowModal(false);
    setSelectedOption(null); // Reset the selected option when closing the modal
  };

  const handleOptionSelection = (option) => {
    setSelectedOption(option);
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
            <StyledButton title="Hobbies" onPress={() => props.navigation.navigate('Hobbies')} />
            <StyledButton
              title="Chat"
              onPress={() => props.navigation.navigate('ChatPage', { userId: userId })}
            />
            <StyledButton title="Profile" onPress={() => props.navigation.navigate('Profile')} />
            <StyledButton title="Generate date recommendation" onPress={() => generateDateRecommendation()} />
          </>
        ) : (
          <>
            <StyledButton title="Register" onPress={() => props.navigation.navigate('Register')} />
            <StyledButton title="Login" onPress={() => props.navigation.navigate('Login')} />
          </>
        )}
      </View>

      {/* Modal */}
      {dateRecommendation && (
        <Modal visible={showModal} animationType="slide">
          <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
            <Text>Please select an option:</Text>
            <Button
              title="Your location"
              onPress={() => handleOptionSelection('yourLocation')}
            />
            <Button
              title="Match location"
              onPress={() => handleOptionSelection('matchLocation')}
            />
            <Button
              title="Midpoint"
              onPress={() => handleOptionSelection('midpoint')}
            />
            <Button title="Close" onPress={() => closeModal()} />
          </View>
        </Modal>
      )}
    </ScreenWrapper>
  );
};

export default Home;
