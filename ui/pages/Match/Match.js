import React, { useEffect, useState } from 'react';
import { Text, ScrollView, StyleSheet, View, Image } from 'react-native';
import { getHobbies, deleteHobbies } from '../../api/hobbies-axios';
import { useIsFocused } from '@react-navigation/native';
import ScreenWrapper from '../../styles/ScreenWrapper';
import StyledButton from '../../styles/StyledButton';
import Swiper from 'react-native-deck-swiper';
import { Alert } from 'react-native';
import pic from "../../assets/img.png"
import TinderCards from '../../components/TinderCards.js';

const Match = (props) => {

  return (
    <ScreenWrapper>
      <ScrollView>
        <View style={styles.view}>
          <TinderCards></TinderCards>
        </View>
      </ScrollView>
    </ScreenWrapper>
  );
};

export default Match;

const styles = StyleSheet.create({
  view: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center'
  },
  card: {
    /* Rectangle 8 */


    position: 'absolute',
    width: 327,
    height: 528,
    left: 32,
    top: 67,

    backgroundColor: '#EE7972',
    borderRadius: 47
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
