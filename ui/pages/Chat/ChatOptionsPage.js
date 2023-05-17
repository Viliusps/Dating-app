import React, { useState, useEffect } from 'react';
import ScreenWrapper from '../../styles/ScreenWrapper';
import StyledButton from '../../styles/StyledButton';
import { getRecommendation, selectBadTimes } from '../../api/date-time-axios';
import BouncyCheckbox from 'react-native-bouncy-checkbox';
import { Text } from 'react-native';

const ChatOptionsPage = (props) => {
  const [recommendedTimes, setRecommendedTimes] = useState([]);
  const [selectedValues, setSelectedValues] = useState([]);

  const handleClick = () => {
    getRecommendation().then((response) => {
      setRecommendedTimes(response);
    });
  };

  const handleSubmit = () => {
    selectBadTimes(selectedValues, recommendedTimes).then((response) => {
      setRecommendedTimes([]);
      setSelectedValues([]);
      if (response.page === 'VOTE') {
        props.navigation.navigate('TimeVote', { times: response.dates });
      }
    });
  };
  return (
    <ScreenWrapper>
      <StyledButton
        title="Get date time recommendation"
        onPress={() => {
          handleClick();
        }}
      />
      {recommendedTimes.length > 0 && (
        <>
          <Text>Times recommended for you:</Text>
          {recommendedTimes.map((time, index) => (
            <BouncyCheckbox
              key={index}
              size={25}
              fillColor="red"
              unfillColor="#FFFFFF"
              text={new Date(time).toLocaleString()}
              iconStyle={{ borderColor: 'red' }}
              innerIconStyle={{ borderWidth: 2 }}
              onPress={(isChecked) => {
                isChecked
                  ? setSelectedValues([...selectedValues, time])
                  : setSelectedValues(selectedValues.filter((value) => value !== time));
              }}
            />
          ))}
          <Text>
            <Text>Please select times that are</Text>
            <Text style={{ fontWeight: 'bold' }}> not</Text>
            <Text> right for you</Text>
          </Text>
          <StyledButton
            title="Submit selection"
            onPress={() => {
              handleSubmit();
            }}
          />
        </>
      )}
    </ScreenWrapper>
  );
};

export default ChatOptionsPage;
