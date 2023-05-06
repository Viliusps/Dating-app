import React from 'react';
import { Text, StyleSheet, TextInput, Alert, KeyboardAvoidingView } from 'react-native';
import ScreenWrapper from '../../styles/ScreenWrapper';
import StyledButton from '../../styles/StyledButton';
import { useState } from 'react';
import { login } from '../../api/users-axios';
import AsyncStorage from '@react-native-async-storage/async-storage';

const LoginPage = (props) => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const handleLogin = () => {
    login(email, password)
      .then((response) => {
        AsyncStorage.setItem('loggedInUser', String(response.id)).then(() => {
          props.navigation.navigate('Home');
        });
      })
      .catch(() => {
        Alert.alert(
          'Try again',
          'Email or password is incorrect',
          [
            {
              text: 'Close',
              style: 'cancel'
            }
          ],
          { cancelable: false }
        );
      });
  };

  return (
    <ScreenWrapper>
      <KeyboardAvoidingView
        behavior="position"
        style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text style={styles.header}>Email</Text>
        <TextInput style={styles.input} onChangeText={setEmail} />
        <Text style={styles.header}>Password</Text>
        <TextInput style={styles.input} secureTextEntry={true} onChangeText={setPassword} />
        <StyledButton title="Login" onPress={() => handleLogin()} />
      </KeyboardAvoidingView>
    </ScreenWrapper>
  );
};

export default LoginPage;

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
