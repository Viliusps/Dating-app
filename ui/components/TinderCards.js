import React, { useState, useMemo } from 'react';
import { ImageBackground, Text, View, Image, TouchableOpacity } from 'react-native';
import TinderCard from 'react-tinder-card';

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
    height: 528,
    borderRadius: 47,
    alignItems: 'center'
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
    marginRight: 40,
    flexWrap: 'wrap',
    marginLeft: 10,
    marginRigh: 10,
    alignItems: 'center',
    justifyContent: 'center'
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
    height: 24,
    bottom: -30,
    backgroundColor: 'white',
    borderRadius: 10,
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'space-between',
    paddingHorizontal: 20,
    alignSelf: 'center'
  }
};

let db = [
  {
    name: 'Pana1',
    img: require('../assets/img.png')
  },
  {
    name: 'Pana2',
    img: require('../assets/img.png')
  },
  {
    name: 'Pana3',
    img: require('../assets/img.png')
  },
  {
    name: 'Pana4',
    img: require('../assets/img.png')
  },
  {
    name: 'Pana5',
    img: require('../assets/img.png')
  }
];

const alreadyRemoved = [];
let charactersState = db;

const TinderCards = () => {
  const [characters, setCharacters] = useState(db);

  let city = 'Lives in Kaunas';
  let description =
    'Hey there! Im a lover of all things outdoors, from hiking and camping to beach days and picnics in the...';
  let hobbies = ['hobis1', 'hobis2'];
  let zodiac = 'Virgo';

  let items = [city, ...hobbies, zodiac];
  const childRefs = useMemo(
    () =>
      Array(db.length)
        .fill(0)
        .map(() => React.createRef()),
    []
  );

  const CustomButton = ({ onPress, image, style }) => (
    <TouchableOpacity onPress={onPress} style={style}>
      <Image source={image}></Image>
    </TouchableOpacity>
  );

  const ThreeDotsButton = ({ onPress }) => (
    <TouchableOpacity onPress={onPress}>
      <View style={styles.customButtonDots}>
        <View key="0" style={[styles.dot, { left: 0 }]} />
        <View key="1" style={[styles.dot, { left: 26 }]} />
        <View key="2" style={[styles.dot, { left: 52 }]} />
      </View>
    </TouchableOpacity>
  );

  const swiped = (direction, nameToDelete) => {
    alreadyRemoved.push(nameToDelete);
  };

  const outOfFrame = (name) => {
    charactersState = charactersState.filter((character) => character.name !== name);
    setCharacters(charactersState);
  };

  const swipe = (dir) => {
    const cardsLeft = characters.filter((person) => !alreadyRemoved.includes(person.name));
    if (cardsLeft.length) {
      const toBeRemoved = cardsLeft[cardsLeft.length - 1].name;
      const index = db.map((person) => person.name).indexOf(toBeRemoved);
      alreadyRemoved.push(toBeRemoved);
      childRefs[index].current.swipe(dir);
    }
  };

  return (
    <View style={styles.container}>
      <View style={styles.cardContainer}>
        {characters.map((character, index) => (
          <TinderCard
            ref={childRefs[index]}
            key={character.name}
            onSwipe={(dir) => swiped(dir, character.name)}
            onCardLeftScreen={() => outOfFrame(character.name)}>
            <View style={styles.card}>
              <ImageBackground style={styles.cardImage} source={character.img} />
              <Text style={styles.cardTitle}>{character.name}</Text>
              <View style={styles.row}>
                <View tyle={styles.rowItems} />
                {items.map((item) => (
                  <View style={styles.rowItems}>
                    <Text>{item}</Text>
                  </View>
                ))}
              </View>
              <Text style={styles.description}>{description}</Text>
              <ThreeDotsButton />
            </View>
          </TinderCard>
        ))}
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton
          onPress={() => swipe('left')}
          image={require('../assets/icons8-close-50.png')}
          style={styles.buttonStyle_left}
        />
        <CustomButton
          onPress={() => swipe('right')}
          image={require('../assets/icons8-chat-room-50.png')}
          style={styles.buttonStyle_right}
        />
      </View>
    </View>
  );
};

export default TinderCards;
