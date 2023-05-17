import { REACT_APP_API_URL } from '@env';

export const getMessagesByChat = async (id) => {
  const response = await fetch(REACT_APP_API_URL + `/messages/byChat/${id}`);
  return await response.json();
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
  return await response.json();
};
