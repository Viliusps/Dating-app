import { REACT_APP_API_URL } from '@env';

export const getCouples = async () => {
  const response = await fetch(REACT_APP_API_URL + '/couples');
  const data = await response.json();
  return data;
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
  const data = await response.json();
  return data;
};

export const deleteCouples = async (id) => {
  const response = await fetch(REACT_APP_API_URL + `/couples/${id}`, {
    method: 'DELETE'
  });
  const data = await response.json();
  return data;
};

export const getCoupleByUserId = async (userId) => {
  const response = await fetch(REACT_APP_API_URL + `/couples/byUser/${userId}`);
  const data = await response.json();
  return data;
};
