import { REACT_APP_API_URL } from '@env';

export const getQuestionById = async (id) => {
  const response = await fetch(REACT_APP_API_URL + `/questions/${id}`);
  return await response.json();
};
