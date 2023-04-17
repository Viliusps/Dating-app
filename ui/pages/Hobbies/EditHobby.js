import { useState } from 'react';
import { Text, TextInput, StyleSheet, KeyboardAvoidingView } from 'react-native';
import { putHobby } from '../../api/hobbies-axios';
import ScreenWrapper from '../../styles/ScreenWrapper';
import StyledButton from '../../styles/StyledButton';

const EditHobby = props => {
    const hobby = props.route.params.hobby;
    const [name, setName] = useState(hobby.name);
    const editHobby = () => {
    if(name != ''){
        putHobby(hobby.id, name).then(()=>{
        props.navigation.navigate('Hobbies');
    })
    }
  };

    return (
    <ScreenWrapper>
        <KeyboardAvoidingView behavior='position' style={{flex: 1, justifyContent: 'center', alignItems: 'center'}}>
            <Text style={styles.header}>Edit hobby {hobby.name}</Text>
            <TextInput style={styles.input} value={name} onChangeText={setName}/>
            <StyledButton title="Save" onPress={editHobby} />
        </KeyboardAvoidingView>
    </ScreenWrapper>
  );
}

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
    backgroundColor: 'white',
  }
});

export default EditHobby;