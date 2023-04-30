import axios from 'axios';
import { REACT_APP_API_URL } from '@env';

export const getMessagesByChat = async (id) => {
  const response = await axios.get(REACT_APP_API_URL + `/messages/byChat/${id}`);
  return response.data;
};

export const postMessage = async (content, date, sender, chat) => {
  const response = await axios.post(REACT_APP_API_URL + '/messages', {
    content: content,
    date: date,
    sender: sender,
    chat: chat
  });
  return response.data;
};
