import { REACT_APP_API_URL } from '@env';

export const getHobbies = async () => {
  const response = await fetch(REACT_APP_API_URL + '/hobbies');
  const data = await response.json();
  return data;
};

export const postHobbies = async (name) => {
  const response = await fetch(REACT_APP_API_URL + '/hobbies', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ name: name })
  });
  const data = await response.json();
  return data;
};

export const deleteHobbies = async (id) => {
  const response = await fetch(REACT_APP_API_URL + `/hobbies/${id}`, {
    method: 'DELETE'
  });
  const data = await response.json();
  return data;
};

export const putHobby = async (id, name) => {
  const response = await fetch(REACT_APP_API_URL + `/hobbies/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ name: name })
  });
  const data = await response.json();
  return data;
};
