import { REACT_APP_API_URL } from '@env';

export const getSentSongsByChat = async (id) => {
  
  const response = await fetch(REACT_APP_API_URL + `/sent_songs/byChat/${id}`);
  return await response.json();
};
