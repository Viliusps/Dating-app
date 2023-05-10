import React from 'react';
import { Text, StyleSheet, TextInput, ScrollView, Alert } from 'react-native';
import ScreenWrapper from '../../styles/ScreenWrapper';
import StyledButton from '../../styles/StyledButton';
import { useState } from 'react';
import { KeyboardAwareScrollView } from 'react-native-keyboard-aware-scroll-view';
import DateTimePicker from '@react-native-community/datetimepicker';
import SelectDropdown from 'react-native-select-dropdown';
import NumericInput from 'react-native-numeric-input';
import FontAwesome from 'react-native-vector-icons/FontAwesome';
import { register } from '../../api/users-axios';

function getAge(dateString) {
  var today = new Date();
  var birthDate = new Date(dateString);
  var age = today.getFullYear() - birthDate.getFullYear();
  var m = today.getMonth() - birthDate.getMonth();
  if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
    age--;
  }
  return age;
}

const RegistrationPage = (props) => {
  const [errorMessage, setErrorMessage] = useState('');
  const [birthDate, setBirthDate] = useState(new Date());
  const [phone, setPhone] = useState(null);
  const [name, setName] = useState(null);
  const [surname, setSurname] = useState(null);
  const [email, setEmail] = useState(null);
  const [password, setPassword] = useState(null);
  const [repeatPassword, setRepeatPassword] = useState(null);
  const [gender, setGender] = useState(null);
  const [searchGender, setSearchGender] = useState(null);
  const [height, setHeight] = useState(170);
  const [radius, setRadius] = useState(10);
  const [purpose, setPurpose] = useState(null);

  const genders = [
    { value: 'MALE', label: 'Male' },
    { value: 'FEMALE', label: 'Female' },
    { value: 'OTHER', label: 'Other' }
  ];

  const searchGenders = [
    { value: 'MALE', label: 'Male' },
    { value: 'FEMALE', label: 'Female' },
    { value: 'ANY', label: 'Any' },
    { value: 'OTHER', label: 'Other' }
  ];

  const purposes = [
    { value: 'LONG', label: 'Long' },
    { value: 'SHORT', label: 'Short' }
  ];

  const validate = () => {
    let regEmail = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w\w+)+$/;
    let regPhone = /^(\+\d{1,2}\s?)?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$/;
    let regName = /^[^\d\s!@#$%^&*()+\[\]{};:'"|\\,.\/<>?`~=_]+$/;

    let valid = true;
    //Birth date validation
    if (birthDate === null) {
      valid = false;
      setErrorMessage('Birth date cannot be empty!');
    } else {
      const enteredAge = getAge(birthDate);
      if (enteredAge < 18) {
        setErrorMessage('You must be at least 18 years old to register!');
        valid = false;
      }
    }

    //Phone validation
    if (phone === null) {
      valid = false;
      setErrorMessage('Phone Nr. cannot be empty!');
    } else if (!regPhone.test(phone)) {
      setErrorMessage('Incorrect Phone Nr. format!');
      valid = false;
    }

    //Name and surname validation
    if (name === null) {
      setErrorMessage('Name cannot be empty!');
      valid = false;
    } else if (!regName.test(name)) {
      setErrorMessage('Name cannot contain numbers or special characters!');
      valid = false;
    }
    if (surname === null) {
      setErrorMessage('Surname cannot be empty!');
      valid = false;
    } else if (!regName.test(surname)) {
      setErrorMessage('Surname cannot contain numbers or special characters!');
      valid = false;
    }

    //Email validation
    if (email === null) {
      setErrorMessage('Email cannot be empty!');
      valid = false;
    } else if (!regEmail.test(email)) {
      setErrorMessage('Incorrect email format!');
      valid = false;
    }
    //Password validation
    if (password === null) {
      setErrorMessage('Password cannot be empty!');
      valid = false;
    } else {
      if (password !== repeatPassword) {
        valid = false;
        setErrorMessage('Passwords do not match!');
      } else if (password.length < 6) {
        setErrorMessage('Password must contain more than 6 characters!');
        valid = false;
      }
    }

    //Enums validation
    if (gender === null || searchGender === null || purpose === null) {
      valid = false;
    }
    return valid;
  };

  const submit = () => {
    if (validate()) {
      setErrorMessage('');
      register(
        birthDate,
        phone,
        name,
        surname,
        email,
        password,
        height,
        radius,
        gender,
        searchGender,
        purpose
      )
        .then((data) => {
          Alert.alert(
            'Success!',
            'Account created successfully!',
            [
              {
                text: 'Close',
                style: 'cancel',
                onPress: () => {
                  props.navigation.navigate('Home');
                }
              }
            ],
            { cancelable: false }
          );
        })
        .catch((error) => {
          Alert.alert(
            'Error',
            'This email is already in use!',
            [
              {
                text: 'Close',
                style: 'cancel'
              }
            ],
            { cancelable: false }
          );
        });
    }
  };

  return (
    <ScreenWrapper>
      <ScrollView>
        <KeyboardAwareScrollView
          behavior="position"
          contentContainerStyle={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
          <Text style={styles.header}>Birth Date</Text>
          <DateTimePicker
            value={birthDate}
            mode={'date'}
            onChange={(event, selectedDate) => {
              setBirthDate(selectedDate);
            }}
          />
          <Text style={styles.header}>Phone Nr.</Text>
          <TextInput style={styles.input} onChangeText={setPhone} error={true} />
          <Text style={styles.header}>Name</Text>
          <TextInput style={styles.input} onChangeText={setName} />
          <Text style={styles.header}>Surname</Text>
          <TextInput style={styles.input} onChangeText={setSurname} />
          <Text style={styles.header}>Email</Text>
          <TextInput style={styles.input} onChangeText={setEmail} />
          <Text style={styles.header}>Height (cm.)</Text>
          <NumericInput value={height} onChange={(value) => setHeight(value)} rounded />
          <Text style={styles.header}>Radius (km.)</Text>
          <NumericInput value={radius} onChange={(value) => setRadius(value)} rounded />
          <Text style={styles.header}>Gender</Text>
          <SelectDropdown
            style={styles.dropdown}
            data={genders}
            onSelect={(selectedItem, index) => {
              setGender(selectedItem.value);
            }}
            buttonTextAfterSelection={(selectedItem, index) => {
              return selectedItem.label;
            }}
            rowTextForSelection={(item, index) => {
              return item.label;
            }}
            buttonStyle={styles.dropdown1BtnStyle}
            buttonTextStyle={styles.dropdown1BtnTxtStyle}
            renderDropdownIcon={(isOpened) => {
              return (
                <FontAwesome
                  name={isOpened ? 'chevron-up' : 'chevron-down'}
                  color={'#444'}
                  size={18}
                />
              );
            }}
            dropdownIconPosition={'right'}
            dropdownStyle={styles.dropdown1DropdownStyle}
            rowStyle={styles.dropdown1RowStyle}
            rowTextStyle={styles.dropdown1RowTxtStyle}
          />
          <Text style={styles.header}>Preferred gender</Text>
          <SelectDropdown
            data={searchGenders}
            onSelect={(selectedItem, index) => {
              setSearchGender(selectedItem.value);
            }}
            buttonTextAfterSelection={(selectedItem, index) => {
              return selectedItem.label;
            }}
            rowTextForSelection={(item, index) => {
              return item.label;
            }}
            buttonStyle={styles.dropdown1BtnStyle}
            buttonTextStyle={styles.dropdown1BtnTxtStyle}
            renderDropdownIcon={(isOpened) => {
              return (
                <FontAwesome
                  name={isOpened ? 'chevron-up' : 'chevron-down'}
                  color={'#444'}
                  size={18}
                />
              );
            }}
            dropdownIconPosition={'right'}
            dropdownStyle={styles.dropdown1DropdownStyle}
            rowStyle={styles.dropdown1RowStyle}
            rowTextStyle={styles.dropdown1RowTxtStyle}
          />
          <Text style={styles.header}>Match purpose</Text>
          <SelectDropdown
            data={purposes}
            onSelect={(selectedItem, index) => {
              setPurpose(selectedItem.value);
            }}
            buttonTextAfterSelection={(selectedItem, index) => {
              return selectedItem.label;
            }}
            rowTextForSelection={(item, index) => {
              return item.label;
            }}
            buttonStyle={styles.dropdown1BtnStyle}
            buttonTextStyle={styles.dropdown1BtnTxtStyle}
            renderDropdownIcon={(isOpened) => {
              return (
                <FontAwesome
                  name={isOpened ? 'chevron-up' : 'chevron-down'}
                  color={'#444'}
                  size={18}
                />
              );
            }}
            dropdownIconPosition={'right'}
            dropdownStyle={styles.dropdown1DropdownStyle}
            rowStyle={styles.dropdown1RowStyle}
            rowTextStyle={styles.dropdown1RowTxtStyle}
          />
          <Text style={styles.header}>Password</Text>
          <TextInput style={styles.input} secureTextEntry={true} onChangeText={setPassword} />
          <Text style={styles.header}>Repeat password</Text>
          <TextInput style={styles.input} secureTextEntry={true} onChangeText={setRepeatPassword} />
          <Text style={styles.error}>{errorMessage}</Text>
          <StyledButton title="Register" onPress={() => submit()} />
        </KeyboardAwareScrollView>
      </ScrollView>
    </ScreenWrapper>
  );
};

export default RegistrationPage;

const styles = StyleSheet.create({
  error: {
    color: 'red'
  },
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
  },
  dropdownsRow: { flexDirection: 'row', width: '100%', paddingHorizontal: '5%' },

  dropdown1BtnStyle: {
    flex: 1,
    height: 50,
    backgroundColor: '#FFF',
    borderRadius: 8,
    borderWidth: 1,
    borderColor: '#444'
  },
  dropdown1BtnTxtStyle: { color: '#444', textAlign: 'left' },
  dropdown1DropdownStyle: { backgroundColor: '#EFEFEF' },
  dropdown1RowStyle: { backgroundColor: '#EFEFEF', borderBottomColor: '#C5C5C5' },
  dropdown1RowTxtStyle: { color: '#444', textAlign: 'left' },
  divider: { width: 12 }
});
