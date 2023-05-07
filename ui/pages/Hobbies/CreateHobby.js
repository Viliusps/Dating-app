import React, { useState } from 'react';
import { Text, TextInput, StyleSheet, KeyboardAvoidingView } from 'react-native';
import { postHobbies } from '../../api/hobbies-axios';
import ScreenWrapper from '../../styles/ScreenWrapper';
import StyledButton from '../../styles/StyledButton';

const CreateHobbies = (props) => {
  const [name, setName] = useState('');

  const createHobby = () => {
    if (name !== '') {
      postHobbies(name).then(() => {
        props.navigation.navigate('Hobbies');
      });
    }
  };

  return (
    <ScreenWrapper>
      <KeyboardAvoidingView
        behavior="position"
        style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text style={styles.header}>Create a new hobby</Text>
        <TextInput style={styles.input} placeholder="New hobby" onChangeText={setName} />
        <StyledButton title="Create" onPress={createHobby} />
      </KeyboardAvoidingView>
    </ScreenWrapper>
  );
};

const styles = StyleSheet.create({
  header: {
    color: 'white',
    fontSize: 18
  },
  input: {
    height: 40,
    width: 200,
    margin: 12,
    padding: 10,
    borderRadius: 20,
    backgroundColor: 'white'
  }
});

export default CreateHobbies;
