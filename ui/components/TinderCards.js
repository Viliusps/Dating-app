import React, { useState, useMemo } from 'react'
import { ImageBackground, Text, View, Button, Image, TouchableOpacity} from 'react-native'
import TinderCard from 'react-tinder-card'

const styles = {
  container: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    width: '100%',
  },
  header: {
    color: '#000',
    fontSize: 30,
    marginBottom: 30,
  },
  cardContainer: {
    width: '90%',
    height: 300,
  },
  card: {
    position: 'absolute',
    backgroundColor: '#EE7972',
    width: '100%',
    height: 528,
    borderRadius: 47,
    alignItems: 'center',
  },
  cardImage: {
    width: 270,
    height: 255,
    overflow: 'hidden',
    borderRadius: 20,
    alignSelf: 'center',
    marginTop: 10,
    margin: 10,
  },
  cardTitle: {
    position: 'absolute',
    marginTop: '78%',
    margin: 10,
    color: '#fff',
    fontSize: 25,
    fontWeight: 'bold',
  },
  buttons: {
    margin: 20,
    zIndex: 1,
  },
  infoText: {
    height: 28,
    justifyContent: 'center',
    display: 'flex',
    zIndex: -100,
  },
  buttonContainer: {
    position: 'relative',
    flexDirection: 'row',
    justifyContent: 'space-between',
    width: '90%',
    marginTop: 225,
    paddingHorizontal: 20,
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
    marginLeft: 40,
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
    marginLeft: 240,

  },
  xSign: {
    position: 'absolute',
    width: 50,
    height: 50
  },
  buttonRow: {
    flexDirection: 'row',
  },
  buttonGap: {
    marginHorizontal: 275,
  },
  description: {
    margin: 10,
    position: 'absolute',
    height: 25.83,
    maxWidth: '80%',
    minWidth: '35%',
    backgroundColor: '#FFFFFF',
    borderRadius: 24,
    bottom: 170,
    left: 10,
    alignItems: 'center',
    justifyContent: 'center',
  }
}

let db = [
  {

    name: 'Richard Hendricks',
    img: require('../assets/img.png')
  },
  {
    name: 'Erlich Bachman',
    img: require('../assets/img.png')
  },
  {
    name: 'Monica Hall',
    img: require('../assets/img.png')
  },
  {
    name: 'Jared Dunn',
    img: require('../assets/img.png')
  },
  {
    name: 'Dinesh Chugtai',
    img: require('../assets/img.png')
  }
]

const alreadyRemoved = []// This fixes issues with updating characters state forcing it to use the current state and not the state that was active when the card was created.
let charactersState = db

const TinderCards = () => {
  const [characters, setCharacters] = useState(db)
  const [lastDirection, setLastDirection] = useState()

  let city = 'Kaunas'

  const childRefs = useMemo(() => Array(db.length).fill(0).map(i => React.createRef()), [])

  const CustomButton = ({ onPress, image, style }) => (
    <TouchableOpacity onPress={onPress} style={style}>
      <Image source={image}></Image>

    </TouchableOpacity>
  );

  const swiped = (direction, nameToDelete) => {
    setLastDirection(direction)
    alreadyRemoved.push(nameToDelete)
  }

  const outOfFrame = (name) => {
    charactersState = charactersState.filter(character => character.name !== name)
    setCharacters(charactersState)
  }

  const swipe = (dir) => {
    const cardsLeft = characters.filter(person => !alreadyRemoved.includes(person.name))
    if (cardsLeft.length) {
      const toBeRemoved = cardsLeft[cardsLeft.length - 1].name // Find the card object to be removed
      const index = db.map(person => person.name).indexOf(toBeRemoved) // Find the index of which to make the reference to
      alreadyRemoved.push(toBeRemoved) // Make sure the next card gets removed next time if this card do not have time to exit the screen
      childRefs[index].current.swipe(dir) // Swipe the card!
    }
  }

  return (
    <View style={styles.container}>
      <View style={styles.cardContainer}>
        {characters.map((character, index) =>
          <TinderCard ref={childRefs[index]} key={character.name} onSwipe={(dir) => swiped(dir, character.name)} onCardLeftScreen={() => outOfFrame(character.name)}>
            <View style={styles.card}>
              <ImageBackground style={styles.cardImage} source={character.img}/>
              <Text style={styles.cardTitle}>{character.name}</Text>
              <View style={styles.description}><Text>Lives in {city}</Text></View>
            </View>
          </TinderCard>
        )}
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton onPress={() => swipe('left')} image={require('../assets/icons8-close-50.png')} style={styles.buttonStyle_left}/>
        <CustomButton onPress={() => swipe('right')} image={require('../assets/icons8-chat-room-50.png')} style={styles.buttonStyle_right}/>
    </View>
    </View>
  )
}

export default TinderCards