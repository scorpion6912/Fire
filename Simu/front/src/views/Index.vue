<template>
  <div id="layout">
    <div id="sidebar">
        Longitude: {{ location.lng.toFixed(4) }} |
        Latitude: {{ location.lat.toFixed(4) }} |
        Zoom: {{ location.zoom.toFixed(2) }} |
        <template v-if="location.bearing"> Bearing: {{ location.bearing.toFixed(2) }} | </template>
        <template v-if="location.pitch"> Pitch: {{ location.pitch.toFixed(2) }} | </template>
        <input id="range" v-model="customSeverity" type="range" max="9" min="1" step="1"/>
        <span>| Severity: {{ customSeverity }} | </span>
        <span>Heatwave: </span>
        <input type="checkbox" value="heatwave" v-model="heatwave">
        <span> | </span>
        <button @click="startWithSeverity">Start</button>
        <button @click="location = { lng: 4.855866496970945, lat: 45.76443495455354, zoom: 13, pitch: 0, bearing: 0 }">Reset</button>
    </div>
    <Map v-model="location"/>
  </div>
</template>

<script>
    import Map from '@/components/Map.vue'
    import "../../node_modules/mapbox-gl/dist/mapbox-gl.css"
    import { getSeverity } from '../service/emergencyUriService';

    export default {
        components: {
            Map
        },
        data: () => ({
            customSeverity:1,
            heatwave:false,
            location: {
                lng: 4.855866496970945,
                lat: 45.76443495455354,
                bearing: 0,
                pitch: 0,
                zoom: 13
            }
        }),
        methods: {
            startWithSeverity() {
                getSeverity(this.customSeverity, this.heatwave);
            }
          },
    };
</script>

<style>
#layout {
  flex: 1;
  display: flex;
  position: relative;
}

#sidebar {
  background-color: rgb(35 55 75 / 90%);
  color: #fff;
  padding: 6px 12px;
  font-family: monospace;
  z-index: 1;
  position: absolute;
  top: 0;
  left: 0;
  margin: 12px;
  border-radius: 4px;
}

#range{
  height: 5px;
}


</style>
