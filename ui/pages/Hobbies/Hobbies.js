import React, { useEffect, useState } from 'react';
import { Text, ScrollView, StyleSheet, View } from 'react-native';
import { getHobbies, deleteHobbies } from '../../api/hobbies-axios';
import { useIsFocused } from '@react-navigation/native';
import ScreenWrapper from '../../styles/ScreenWrapper';
import StyledButton from '../../styles/StyledButton';
import { Alert } from 'react-native';

const Hobbies = (props) => {
  const isFocused = useIsFocused();
  const [hobbies, setHobbies] = useState([]);

  useEffect(() => {
    getHobbies()
      .then((data) => {
        setHobbies(data);
      })
      .catch((error) => {
        alert(error.message);
      });
  }, [isFocused]);

  function handleDeleteButtonPress(hobby) {
    Alert.alert(
      'Confirm Deletion',
      'Are you sure you want to delete hobby "' + hobby.name + '"?',
      [
        {
          text: 'Cancel',
          style: 'cancel'
        },
        {
          text: 'Delete',
          onPress: () => {
            deleteHobbies(hobby.id);
            const updatedHobbies = hobbies.filter((item) => item.id !== hobby.id);
            setHobbies(updatedHobbies);
          },
          style: 'destructive'
        }
      ],
      { cancelable: false }
    );
  }

  return (
    <ScreenWrapper>
      <ScrollView>
        <View style={styles.view}>
          <StyledButton
            title="Create a new hobby"
            onPress={() => props.navigation.navigate('CreateHobbies')}
          />
        </View>
        {hobbies.length > 0 ? (
          <>
            <View style={styles.tableHeader}>
              <Text style={[styles.cellText, styles.header]}>Id</Text>
              <Text style={[styles.cellText, styles.header]}>Hobby</Text>
              <Text style={[styles.cellText, styles.header]}>Actions</Text>
            </View>
            {hobbies.map((hobby) => (
              <View key={hobby.id} style={styles.tableRow}>
                <View style={styles.tableCell}>
                  <Text style={styles.cellText}>{hobby.id}</Text>
                </View>
                <View style={styles.tableCell}>
                  <Text style={styles.cellText}>{hobby.name}</Text>
                </View>
                <View style={styles.tableCell}>
                  <StyledButton title="Delete" onPress={() => handleDeleteButtonPress(hobby)} />
                  <StyledButton
                    title="Edit"
                    onPress={() => props.navigation.navigate('EditHobby', { hobby: hobby })}
                  />
                </View>
              </View>
            ))}
          </>
        ) : (
          <View style={styles.view}>
            <Text style={styles.Empty}>The list is empty!</Text>
          </View>
        )}
      </ScrollView>
    </ScreenWrapper>
  );
};

export default Hobbies;

const styles = StyleSheet.create({
  view: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center'
  },
  header: {
    fontWeight: 'bold',
    flex: 1,
    marginHorizontal: 10
  },
  content: {
    color: 'white',
    fontSize: 15
  },
  tableHeader: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginHorizontal: 20,
    marginBottom: 10,
    borderBottomWidth: 1,
    borderBottomColor: '#ccc',
    paddingBottom: 5
  },
  tableRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginHorizontal: 20,
    marginVertical: 5,
    borderBottomWidth: 1,
    borderBottomColor: '#ccc',
    paddingBottom: 5
  },
  tableCell: {
    flex: 1,
    marginHorizontal: 10
  },
  cellText: {
    fontSize: 16,
    textAlign: 'center',
    color: 'white'
  },
  Empty: {
    color: 'white',
    fontSize: 20,
    fontWeight: 'bold'
  }
});
