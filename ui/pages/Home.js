import React, { useEffect } from 'react';
import { View, Text, Modal } from 'react-native';
import ScreenWrapper from '../styles/ScreenWrapper';
import StyledButton from '../styles/StyledButton';
import { useState } from 'react';
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useIsFocused } from '@react-navigation/native';
import { putRecommendation } from '../api/date-recommendation-axios';
import DateModal from './DateRecommendation/DateModal';
import LocationModal from './DateRecommendation/LocationModal';

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
  const [showDateModal, setShowDateModal] = useState(false); // State to control the visibility of the date modal

  const generateDateRecommendation = (selectedOption) => {
    const fetchUrl = `http://localhost:8080/api/v1/dateRecommendation/${userId}`;
      
    fetch(fetchUrl)
      .then(response => {
          console.log(response);
          return response.json();
        })
        .catch(error => {
          console.error('Error retrieving date recommendation:', error);
        })
        .then(dateRecommendation => {
          // Handle the date recommendations here
          console.log("dateRecommendation");
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
    generateDateRecommendation(option);
    setShowDateModal(true);
  };
  
  const handleDateOptionSelection = (dateType) => {
    // Handle the date type selection here
    console.log('Selected date type:', dateType);
    setShowDateModal(false);
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

      {/* Location Modal */}
      {dateRecommendation && (
        <LocationModal
          showModal={showModal}
          handleOptionSelection={handleOptionSelection}
        />
      )}

      {/* Date Modal */}
      {showDateModal && (
        <DateModal
          showDateModal={showDateModal}
          handleDateOptionSelection={handleDateOptionSelection}
          setShowDateModal={setShowDateModal}
          closeModal={closeModal}
        />
      )}

    </ScreenWrapper>
  );
};

export default Home;
