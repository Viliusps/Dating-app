import axios from 'axios';
import { REACT_APP_API_URL } from '@env';

export const getCouples = async () => {
  const response = await axios.get(REACT_APP_API_URL + '/couples');
  return response.data;
};

export const postCouples = async (date, weigh_diff, first, second, status) => {
  const response = await axios.post(REACT_APP_API_URL + '/couples', {
    date: date,
    weightDiff: weigh_diff,
    first: first,
    second: second,
    status: status
  });
  return response.data;
};

export const deleteCouples = async (id) => {
  const response = await axios.delete(REACT_APP_API_URL + `/couples/${id}`);
  return response.data;
};

export const getCoupleByUserId = async (userId) => {
  const response = await axios.get(REACT_APP_API_URL + `/couples/byUser/${userId}`);
  return response.data;
};
