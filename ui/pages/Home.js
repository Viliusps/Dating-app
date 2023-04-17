import { View } from 'react-native';
import ScreenWrapper from '../styles/ScreenWrapper';
import StyledButton from '../styles/StyledButton';
const Home = props => {

  const onPress = () => {
    props.navigation.navigate('Hobbies');
  };
  
  return (
    <ScreenWrapper>
    <View style={{flex: 1, justifyContent: 'center', alignItems: 'center', backgroundColor: 'transparent'}}>
      <StyledButton title="Hobbies" onPress={()=>onPress()} />
    </View>
    </ScreenWrapper>
  );
};

export default Home;