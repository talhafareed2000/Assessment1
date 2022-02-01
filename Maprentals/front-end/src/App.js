import './App.css';
import axios from "axios";
import React, {
    useRef,
    useMemo,
    useState,
    useCallback
} from "react";
import {render} from "@testing-library/react";
import App from './App';

import { MapContainer, TileLayer, Marker, Popup, Circle} from 'react-leaflet'

const center = {
    lat: 32.92495, //Dallas
    lng:  -97.03985,
}
const api= axios.create({
    baseURL: "http://localhost:8080"
})

var pos=null;

// circle marker
function DraggableMarker() {
    const [draggable, setDraggable] = useState(false)
    const [position,setPosition] = useState(center)
    const markerRef = useRef(null)

    const eventHandlers = useMemo(
        () => ({
            dragend() {
                const marker = markerRef.current;

                if (marker != null) {

                    setPosition(marker.getLatLng())

                }
                location=(marker.getLatLng());
                console.log(location);
                const obj =  JSON.parse( JSON.stringify(location));
                console.log(obj.lat);
                console.log(obj.lng);
                var result = [];
                for(var i in obj.lat)
                    result.push([i, obj.lat [i]]);

                console.log(result);



                return axios.post("http://localhost:8080/getAvg" + "/location", {
                    lat: obj.lat,
                   lng: obj.lng
                });


            },
        }),
        [],
    )
    const toggleDraggable = useCallback(() => {
        setDraggable((d) => !d)
    }, [])

    return (
        <div>

            <Marker
                draggable={draggable}
                eventHandlers={eventHandlers}
                position={position}
                ref={markerRef}>
                <Popup minWidth={90}>
        <span onClick={toggleDraggable}>
          {draggable
              ? '' +location
              : 'Click here to make marker draggable'}
        </span>
                </Popup>
                <Circle
                    center={position}
                    fillColor="blue"
                    radius={500}/>



            </Marker>



        </div>

    )
}



var location;





render(




    <MapContainer center={center} zoom={13} scrollWheelZoom={false}>
        <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
        />
        <div>
            <DraggableMarker />



        </div>

    </MapContainer>,
)


export default App;
