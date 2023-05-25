import React from 'react';
import { View, Text, Modal } from 'react-native';
import StyledButton from '../../styles/StyledButton';

const DateModal = ({ showDateModal, handleDateOptionSelection, setShowDateModal }) => {
  return (
    <Modal visible={showDateModal} animationType="slide">
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text>Please select a date type:</Text>
        <StyledButton title="Active" onPress={() => handleDateOptionSelection('Active')} />
        <StyledButton title="Food" onPress={() => handleDateOptionSelection('Food')} />
        <StyledButton title="Nature" onPress={() => handleDateOptionSelection('Nature')} />
        <StyledButton title="Close" onPress={() => setShowDateModal(false)} />
      </View>
    </Modal>
  );
};

export default DateModal;