import { REACT_APP_API_URL } from '@env';

export const getSentQuestionsByChat = async (id) => {
  const response = await fetch(REACT_APP_API_URL + `/messages/getSentQuestions/${id}`);
  return await response.json();
};

export const getRandomQuestion = async (id) => {
  const response = await fetch(REACT_APP_API_URL + `/messages/getRandomQuestion/${id}`);
  return await response.json();
};
