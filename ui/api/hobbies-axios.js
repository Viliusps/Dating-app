import axios from 'axios';
import {REACT_APP_API_URL} from '@env'

export const getHobbies = async () => {
  const response = await axios.get(REACT_APP_API_URL + '/hobbies');
  return response.data;
};

export const postHobbies = async (name) => {
  const response = await axios.post(REACT_APP_API_URL + '/hobbies', {name: name});
  return response.data;
};

export const deleteHobbies = async (id) => {
  const response = await axios.delete(REACT_APP_API_URL + `/hobbies/${id}`);
  return response.data;
}

export const putHobby = async (id, name) => {
  const response = await axios.put(REACT_APP_API_URL + `/hobbies/${id}`, {
      name: name
      },);
  return response.data;
}