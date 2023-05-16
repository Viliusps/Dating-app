import { REACT_APP_API_URL } from '@env';
import AsyncStorage from '@react-native-async-storage/async-storage';

export const getRecommendation = async () => {
  return AsyncStorage.getItem('loggedInUser').then(async (userId) => {
    console.log(userId);
    const response = await fetch(REACT_APP_API_URL + `/time/${userId}`);
    return await response.json();
  });
};
