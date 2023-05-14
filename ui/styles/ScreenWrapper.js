import React from 'react';
import { View, StyleSheet } from 'react-native';
import { LinearGradient } from 'expo-linear-gradient';

const ScreenWrapper = ({ children }) => {
  return (
    <LinearGradient
      colors={['#FA9F79', '#E97171', '#000000']}
      style={StyleSheet.absoluteFill}
      locations={[0.1875, 0.7344, 0.9896]}>
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
