import React from 'react';
import { Image, StyleSheet, View } from 'react-native';
import { NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import Hobbies from './pages/Hobbies/Hobbies';
import Match from './pages/Match/Match';
import CreateHobbies from './pages/Hobbies/CreateHobby';
import Home from './pages/Home';
import ScreenWrapper from './styles/ScreenWrapper';
import EditHobby from './pages/Hobbies/EditHobby';
import ChatPage from './pages/Chat/ChatPage';
import RegistrationPage from './pages/Auth/RegistrationPage';
import LoginPage from './pages/Auth/LoginPage';
import ProfilePage from './pages/ProfilePage';
import TimeVote from './pages/TimeVote';
import ChatOptionsPage from './pages/Chat/ChatOptionsPage';

const App = () => {
  const Stack = createStackNavigator();

  const screenOptions = {
    headerTransparent: true,
    headerLeft: () => <Image source={require('./assets/logo.jpg')} style={styles.image} />
  };

  return (
    <ScreenWrapper>
      <View style={{ flex: 1 }}>
        <NavigationContainer>
          <Stack.Navigator screenOptions={screenOptions}>
            <Stack.Screen name="Home" component={Home} />
            <Stack.Screen name="Match" component={Match} />
            <Stack.Screen name="Hobbies" component={Hobbies} />
            <Stack.Screen name="CreateHobbies" component={CreateHobbies} />
            <Stack.Screen name="EditHobby" component={EditHobby} />
            <Stack.Screen name="ChatPage" component={ChatPage} />
            <Stack.Screen name="Register" component={RegistrationPage} />
            <Stack.Screen name="Login" component={LoginPage} />
            <Stack.Screen name="Profile" component={ProfilePage} />
            <Stack.Screen name="TimeVote" component={TimeVote} />
            <Stack.Screen name="ChatOptionsPage" component={ChatOptionsPage} />
          </Stack.Navigator>
        </NavigationContainer>
      </View>
    </ScreenWrapper>
  );
};

const styles = StyleSheet.create({
  image: {
    width: 100,
    height: 100,
    marginTop: 15
  }
});

export default App;
