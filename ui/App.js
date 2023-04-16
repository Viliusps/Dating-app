import {Image, View, Text, Button } from 'react-native';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import Hobbies from './pages/Hobbies';

const ScreenOne = props => {

  const onPress = () => {
    props.navigation.navigate('Hobbies');
  };
  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <Button
        title="Hobbies"
        onPress={() => onPress()}
      />
    </View>
  );
};

const ScreenTwo = () => {
  return (
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
      <Text>Welcome to hobbies page</Text>
      <Hobbies/>
    </View>
  );
};

const App = () => {
  const Stack = createStackNavigator();
  return (
    <>
      <Image
        source={require('./assets/logo.jpg')}
      />
    <NavigationContainer>
      <Stack.Navigator>
        <Stack.Screen name="Home" component={ScreenOne} />
        <Stack.Screen name="Hobbies" component={ScreenTwo} />
      </Stack.Navigator>
    </NavigationContainer>
    </>
  );
};

export default App;