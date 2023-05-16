import { REACT_APP_API_URL } from '@env';

export const getSongIdById = async (id) => {
  
  const response = await fetch(REACT_APP_API_URL + `/songs/${id}`);
  return await response.json();
};

export const findSongRecommendation = async (userID, otherID, chatID) => {
  const response = await fetch(REACT_APP_API_URL + '/songs/recommend', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      userID: userID,
      otherID: otherID,
      chatID: chatID
    })
  });
  return await response.json();
};
