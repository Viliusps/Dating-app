import { REACT_APP_API_URL } from '@env';

export const getUser = async (id) => {
  const response = await fetch(REACT_APP_API_URL + `/users/${id}`);
  const data = await response.json();
  return data;
};

export const login = async (email, password) => {
  const response = await fetch(REACT_APP_API_URL + '/users/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      email: email,
      password: password
    })
  });
  const data = await response.json();
  return data;
};

export const register = async (
  birthDate,
  phone,
  name,
  surname,
  email,
  password,
  height,
  radius,
  gender,
  searchGender,
  purpose
) => {
  const response = await fetch(REACT_APP_API_URL + '/users', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      birthDate: birthDate,
      phone: phone,
      name: name,
      surname: surname,
      email: email,
      password: password,
      role: 'USER',
      blocked: false,
      picture: null,
      description: null,
      points: 0,
      starSign: null,
      personalityType: null,
      loveLanguage: null,
      height: height,
      radius: radius,
      gender: gender,
      searchGender: searchGender,
      matchPurpose: purpose
    })
  });
  if (!response.ok) {
    throw new Error('Request failed');
  }
  const data = await response.json();
  return data;
};
