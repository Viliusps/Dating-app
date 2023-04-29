import axios from 'axios';
import { REACT_APP_API_URL } from '@env';

export const getUser = async (id) => {
  const response = await axios.get(REACT_APP_API_URL + `/users/${id}`);
  return response.data;
};
