import React from 'react';
import ScreenWrapper from '../../styles/ScreenWrapper';
import StyledButton from '../../styles/StyledButton';
import { getRecommendation } from '../../api/date-time-axios';

const ChatOptionsPage = () => {
  const handleClick = () => {
    getRecommendation().then((response) => {
      console.log(response);
    });
  };
  return (
    <ScreenWrapper>
      <StyledButton
        title="Get date time recommendation"
        onPress={() => {
          handleClick();
        }}></StyledButton>
    </ScreenWrapper>
  );
};

export default ChatOptionsPage;
