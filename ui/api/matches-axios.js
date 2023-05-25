import { REACT_APP_API_URL } from '@env';

export const getMatch = async (id) => {
  const response = await fetch(REACT_APP_API_URL + `/matches/getMatch/${id}`);
  return await response.json();
};

export const setDislike = async (userId, otherId) => {
  const response = await fetch(REACT_APP_API_URL + `/matches/setDislike/user/${userId}/otherUser/${otherId}`);
  return await response.json();
};

export const setLike = async (userId, otherId) => {
    const response = await fetch(REACT_APP_API_URL + `/matches/setLike/user/${userId}/otherUser/${otherId}`);
    return await response.json();
  };
