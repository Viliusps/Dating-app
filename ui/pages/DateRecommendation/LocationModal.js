import React from 'react';
import { View, Text, Modal } from 'react-native';
import StyledButton from '../../styles/StyledButton';

const LocationModal = ({ showModal, handleOptionSelection, closeModal }) => {
  return (
    <Modal visible={showModal} animationType="slide">
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text>Please select an option:</Text>
        <StyledButton title="Your location" onPress={() => handleOptionSelection('yourLocation')} />
        <StyledButton title="Match location" onPress={() => handleOptionSelection('matchLocation')} />
        <StyledButton title="Midpoint" onPress={() => handleOptionSelection('midpoint')} />
        <StyledButton title="Close" onPress={closeModal} />
      </View>
    </Modal>
  );
};

export default LocationModal;