import { REACT_APP_API_URL } from '@env';
import AsyncStorage from '@react-native-async-storage/async-storage';

export const getRecommendation = async () => {
  return AsyncStorage.getItem('loggedInUser').then(async (userId) => {
    const response = await fetch(REACT_APP_API_URL + `/time/${userId}`);
    return await response.json();
  });
};

export const selectBadTimes = async (selectedDates, recommendedDates) => {
  return AsyncStorage.getItem('loggedInUser').then(async (userId) => {
    const response = await fetch(REACT_APP_API_URL + '/time/compare', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        selectedDates: selectedDates,
        recommendedDates: recommendedDates,
        userId: userId
      })
    });
    return await response.json();
  });
};

export const vote = async (date) => {
  return AsyncStorage.getItem('loggedInUser').then(async (userId) => {
    const response = await fetch(REACT_APP_API_URL + '/time/vote', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        date: date,
        userId: userId
      })
    });
    return await response.json();
  });
};
