import { useEffect, useState } from 'react';
import { Text } from 'react-native';
import { getHobbies } from '../api/hobbies-axios';

export default function Hobbies() {
    const [hobbies, setHobbies] = useState([]);
    useEffect(()=>{
        getHobbies().then((data)=>{
            setHobbies(data);
        }).catch((error)=>{
     console.log(error);
     alert(error.message);
  });
    }, [])
    return (
    <>
        <Text>This is hobbies</Text>
        {hobbies.map((hobby) => (
            <Text key={hobby.id}>{hobby.name}</Text>))}
    </>
  );
}
