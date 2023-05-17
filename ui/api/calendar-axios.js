import { REACT_APP_API_URL } from '@env';

export const sync = async (userId) => {
  
  const response = await fetch(REACT_APP_API_URL + `/sync/${userId}`);
  return await response.json();
};
