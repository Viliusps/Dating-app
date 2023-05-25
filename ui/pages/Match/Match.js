import { ScrollView, StyleSheet, View } from 'react-native';
import ScreenWrapper from '../../styles/ScreenWrapper';
import React, { useState, useMemo, useEffect} from 'react';
import TinderCard from 'react-tinder-card';
import { ImageBackground, Text, Image, TouchableOpacity } from 'react-native';
import { getMatch, setDislike, setLike} from '../../api/matches-axios';
import { useIsFocused } from '@react-navigation/core';



const Match = (props) => {
  const [characters, setCharacters] = useState([]);
  const isFocused = useIsFocused();
  const currentId = 3;

  const LoveLanguages = {
    WORDS_OF_AFFIRMATION: 'Words of Affirmation',
    ACTS_OF_SERVICE: 'Acts of Service',
    RECEIVING_GIFTS: 'Receiving Gifts',
    QUALITY_TIME: 'Quality Time',
    PHYSICAL_TOUCH: 'Physical Touch'
  };

  useEffect(() => {
    getMatch(currentId)
      .then((data) => {
        if (Array.isArray(data)) {
          setCharacters(data)
        }
        else {
          setCharacters([data])
        }
      })
      .catch((error) => {
        alert(error.message);
      });
  }, [isFocused]);

  const alreadyRemoved = [];
  let charactersState = characters;


  const ThreeDotsButton = ({ onPress }) => (
    <TouchableOpacity onPress={onPress}>
      <View style={styles.customButtonDots}>
        <View key="0" style={[styles.dot, { left: 0 }]} />
        <View key="1" style={[styles.dot, { left: 26 }]} />
        <View key="2" style={[styles.dot, { left: 52 }]} />
      </View>
    </TouchableOpacity>
  );

  const swiped = async (direction, nameToDelete, character, charactersState) => 
  {
    console.log(direction)
    if (direction == 'left') {
      try {
        const response = await setDislike(currentId, character.id);
        setCharacters(response);
      } catch (error) {
        console.log('Error setting dislike:', error);
      }
    }
    else if (direction == "right") {
        try {
          const response = await setLike(currentId, character.id);
          if (response == true) {
            props.navigation.navigate('ChatPage', { userId: currentId });
          }
        } catch (error) {
          console.log('Error setting like:', error);
        }
    }
    alreadyRemoved.push(nameToDelete);
  };

  const outOfFrame = (name) => {
    setCharacters(prevCharacters => prevCharacters.filter(character => character.name !== name));

  };

  return (
    <ScreenWrapper>
      <ScrollView>
      <View style={styles.container}>
      <View style={styles.cardContainer}> 
        {characters?.length ? characters?.map((character, index) => {
          const birthDate = new Date(character.birthDate)
          const currentDate = new Date();
          let age = currentDate.getFullYear() - birthDate.getFullYear();
          let items = [character.personalityType, `${age} years old`, character.starSign, LoveLanguages[character.loveLanguage], character.gender, `${character.matchPurpose}-TERM`];
          return (
          <TinderCard
            key={character.id}
            onSwipe={(dir) => swiped(dir, character.name, character, charactersState)}
            onCardLeftScreen={() => outOfFrame(character.name)}>
            <View style={styles.card}>
              <ImageBackground style={styles.cardImage} source={character.picture} />
              <Text style={styles.cardTitle}>{character.name}</Text>
              <View style={styles.row}>
                 {items.map((item) => (
                  <View style={styles.rowItems}>
                    <Text>{item}</Text>
                  </View>
                ))}
              </View>
              <Text style={styles.description}>{character.description}</Text>
              <ThreeDotsButton />
            </View>
          </TinderCard>
        )}) : (<Text style={styles.noCardsText}>You swiped all users in your area</Text>)}
      </View>
    </View>
      </ScrollView>
    </ScreenWrapper>
  );
};

export default Match;

const styles = {
  container: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%'
  },
  header: {
    color: '#000',
    fontSize: 30,
    marginBottom: 30
  },
  cardContainer: {
    width: '90%',
    height: 300
  },
  card: {
    position: 'absolute',
    backgroundColor: '#EE7972',
    width: '100%',
    height: 560,
    borderRadius: 47,
    alignItems: 'center',
    justifyContent: 'space-between',
  },
  cardImage: {
    width: 270,
    height: 255,
    overflow: 'hidden',
    borderRadius: 20,
    alignSelf: 'center',
    marginTop: 10,
    margin: 10
  },
  cardTitle: {
    position: 'absolute',
    marginTop: '78%',
    margin: 10,
    alignSelf: 'center',
    color: '#fff',
    fontSize: 25,
    fontWeight: 'bold'
  },
  buttons: {
    margin: 20,
    zIndex: 1
  },
  infoText: {
    height: 28,
    justifyContent: 'center',
    display: 'flex',
    zIndex: -100
  },
  buttonContainer: {
    position: 'relative',
    flexDirection: 'row',
    justifyContent: 'space-between',
    width: '90%',
    marginTop: 225,
    paddingHorizontal: 20
  },
  buttonStyle_left: {
    position: 'absolute',
    width: 69,
    height: 69,
    backgroundColor: '#E73636',
    borderRadius: 180,
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: 60,
    marginLeft: 40
  },
  buttonStyle_right: {
    position: 'absolute',
    width: 69,
    height: 69,
    backgroundColor: '#B5DA79',
    borderRadius: 180,
    alignItems: 'center',
    justifyContent: 'center',
    marginTop: 60,
    marginLeft: 240
  },
  xSign: {
    position: 'absolute',
    width: 50,
    height: 50
  },
  buttonRow: {
    flexDirection: 'row'
  },
  buttonGap: {
    marginHorizontal: 275
  },
  description_1: {
    height: 25.83,
    maxWidth: '80%',
    minWidth: '35%',
    backgroundColor: '#FFFFFF',
    borderRadius: 24,
    bottom: 170,
    left: 10,
    alignItems: 'center',
    justifyContent: 'center'
  },
  description: {
    position: 'relative',
    height: 'auto',
    maxWidth: '80%',
    minWidth: '35%',
    backgroundColor: '#FFFFFF',
    borderRadius: 24,
    marginTop: 10,
    marginBottom: 20,
    padding: 10,
    alignItems: 'center',
    justifyContent: 'center',
    overflow: 'hidden'
  },
  rowItems: {
    backgroundColor: '#FFFFFF',
    borderRadius: 24,
    height: 25.83,
    maxWidth: '80%',
    minWidth: '35%',
    alignItems: 'center',
    justifyContent: 'center',
    margin: 5,
    flexBasis: '30%'
  },
  row: {
    marginTop: 40,
    width: '90%',
    flexDirection: 'row',
    flexWrap: 'wrap',
    alignItems: 'center',
    justifyContent: 'space-evenly'
  },
  dot: {
    backgroundColor: '#B3AEAE',
    position: 'absolute',
    width: 13,
    height: 13,
    left: 0,
    borderRadius: 180,
    marginHorizontal: 5
  },
  customButtonDots: {
    position: 'absolute',
    width: 74,
    bottom: 3,
    height: 24,
    backgroundColor: 'white',
    borderRadius: 10,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
    alignSelf: 'center',
  },
  noCardsText: {
    textAlign: 'center', 
    fontSize: 24, 
    color: '#333', 
    marginTop: 50, 
  }
};
