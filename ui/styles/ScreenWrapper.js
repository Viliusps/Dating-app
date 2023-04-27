import React from 'react';
import { View, StyleSheet } from 'react-native';
import { LinearGradient } from 'expo-linear-gradient';

const ScreenWrapper = ({ children }) => {
  return (
    <LinearGradient colors={['#FA9F79', '#FF8282', '#000000']} style={StyleSheet.absoluteFill}>
      <View style={styles.container}>{children}</View>
    </LinearGradient>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingTop: 50
  }
});

export default ScreenWrapper;
