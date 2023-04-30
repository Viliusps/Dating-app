import { REACT_APP_API_URL } from '@env';

export const getMessagesByChat = async (id) => {
  const response = await fetch(REACT_APP_API_URL + `/messages/byChat/${id}`);
  const data = await response.json();
  return data;
};

export const postMessage = async (content, date, sender, chat) => {
  const response = await fetch(REACT_APP_API_URL + '/messages', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      content: content,
      date: date,
      sender: sender,
      chat: chat
    })
  });
  const data = await response.json();
  return data;
};
