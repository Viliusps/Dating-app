import { REACT_APP_API_URL } from '@env';

export const getCouples = async () => {
  
  const response = await fetch(REACT_APP_API_URL + '/couples');
  return await response.json();
};

export const postCouples = async (date, weigh_diff, first, second, status) => {
  const response = await fetch(REACT_APP_API_URL + '/couples', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({
      date: date,
      weightDiff: weigh_diff,
      first: first,
      second: second,
      status: status
    })
  });
  return await response.json();
};

export const deleteCouples = async (id) => {
  const response = await fetch(REACT_APP_API_URL + `/couples/${id}`, {
    method: 'DELETE'
  });
  return await response.json();
};

export const getCoupleByUserId = async (userId) => {
  const response = await fetch(REACT_APP_API_URL + `/couples/byUser/${userId}`);
  return await response.json();
};
