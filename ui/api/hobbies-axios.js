import axios from 'axios';
import {REACT_APP_API_URL} from '@env'

export const getHobbies = async () => {
  const response = await axios.get(REACT_APP_API_URL + '/hobbies');
  return response.data;
};