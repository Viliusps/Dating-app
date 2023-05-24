import { REACT_APP_API_URL } from '@env';

export const getHobbies = async () => {
  const response = await fetch(REACT_APP_API_URL + '/hobbies');

  

  return await response.json();
};

export const postHobbies = async (name) => {
  const response = await fetch(REACT_APP_API_URL + '/hobbies', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ name: name })
  });
  return await response.json();
};

export const deleteHobbies = async (id) => {
  const response = await fetch(REACT_APP_API_URL + `/hobbies/${id}`, {
    method: 'DELETE'
  });
  return await response.json();
};

export const putHobby = async (id, name) => {
  const response = await fetch(REACT_APP_API_URL + `/hobbies/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ name: name })
  });
  return await response.json();
};
