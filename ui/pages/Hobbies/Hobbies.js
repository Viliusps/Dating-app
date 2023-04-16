import { useEffect, useState } from 'react';
import { Text, ScrollView, StyleSheet, View } from 'react-native';
import { getHobbies } from '../../api/hobbies-axios';
import { useIsFocused } from "@react-navigation/native";
import ScreenWrapper from '../../styles/ScreenWrapper';
import StyledButton from '../../styles/StyledButton';

const Hobbies = props =>  {
    const isFocused = useIsFocused();

    const [hobbies, setHobbies] = useState([]);
    useEffect(()=>{
        getHobbies()
        .then((data)=>{
            setHobbies(data);
        })
        .catch((error)=>{
            console.log(error);
            alert(error.message);
        });
    }, [isFocused])

    return (
    <ScreenWrapper>
      <ScrollView>
        <View style={{flex: 1, justifyContent: 'center', alignItems: 'center', backgroundColor: 'transparent'}}>
          <StyledButton title="Create a new hobby" onPress={()=>props.navigation.navigate('CreateHobbies')} />
          <Text style={styles.header}>Hobbies from the database</Text>
          {hobbies.map((hobby) => (
              <Text key={hobby.id} style={styles.content}>{hobby.name}</Text>
          ))}
        </View>
      </ScrollView>
    </ScreenWrapper>
  );
}

const styles = StyleSheet.create({
  header: {
    color: 'white',
    fontSize: 25
  },
  content: {
    color: 'white',
    fontSize: 15
  }
});

export default Hobbies;