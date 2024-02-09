<template>
  <div id="layout">
    <div id="sidebar">
        Longitude: {{ location.lng.toFixed(4) }} |
        Latitude: {{ location.lat.toFixed(4) }} |
        Zoom: {{ location.zoom.toFixed(2) }} |
        <template v-if="location.bearing"> Bearing: {{ location.bearing.toFixed(2) }} | </template>
        <template v-if="location.pitch"> Pitch: {{ location.pitch.toFixed(2) }} | </template>
        <button @click="location = { lng: 4.855866496970945, lat: 45.76443495455354, zoom: 13, pitch: 0, bearing: 0 }" style="border-radius: 8px;">Reset</button>
        <br>
        <label class="container">
          <input
          v-model="checked_fire_stations"
          class="input"
          type="checkbox"
          id="checkbox_fire_stations"
            true-value="yes"
            false-value="no"
            @change="showHideController(checked_fire_stations, 'fire-station')"
          />
          <span class="switch"></span>
          <label for="checkbox_fire_stations" style="margin-left: 2%;">Casernes</label>
        </label>
        <label class="container">
          <input
            v-model="checked_incidents"
            class="input"
            type="checkbox"
            id="checkbox_incidents"
            true-value="yes"
            false-value="no"
            @change="showHideController(checked_incidents, 'incident')"
          />
          <span class="switch"></span>
          <label for="checkbox_incidents" style="margin-left: 2%;">Incidents</label>
        </label>
    </div>
    <Map v-model="location"/>
  </div>
</template>

<script>
    import Map from '@/components/Map.vue'
    import "../../node_modules/mapbox-gl/dist/mapbox-gl.css"

    export default {
      components: {
        Map
      },
      data: () => ({
        location: {
            lng: 4.855866496970945,
            lat: 45.76443495455354,
            bearing: 0,
            pitch: 0,
            zoom: 13
        },
        checked_fire_stations: "yes",
        checked_incidents: "yes",
      }),
      methods: {
        showHideController(state, type){
          let markers = document.getElementsByClassName("marker "+type);
          if (state == "yes"){
            for (let i = 0; i < markers.length; i++) {
                markers[i].style.visibility = "visible";
            }
          } else {
            for (let i = 0; i < markers.length; i++) {
                markers[i].style.visibility = "hidden";
            }
          }
        }
      }
    };
</script>

<style>
#layout {
  flex: 1;
  display: flex;
  position: relative;
}

#sidebar {
  background-color: rgba(35, 55, 75, 0.9);
  color: white;
  padding: 6px 12px;
  font-family: monospace;
  z-index: 1;
  position: absolute;
  top: 0;
  left: 0;
  margin: 12px;
  border-radius: 4px;
}

.container {
  cursor: pointer;
  display: flex;
  align-items: center;
}
.label {
  margin-left: 12px;
  color: rgb(26, 32, 44);
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.input {
  position: absolute;
  width: 1px;
  height: 1px;
  padding: 0;
  margin: -1px;
  overflow: hidden;
  clip: rect(0, 0, 0, 0);
  white-space: nowrap;
  border-width: 0;
}
.switch {
  --switch-container-width: 40px;
  --switch-size: calc(var(--switch-container-width) / 2);
  --light-gray: rgb(226, 232, 240);
  --gray: rgb(203, 213, 224);
  --dark-gray: rgb(160, 174, 192);
  --teal: rgb(79, 209, 197);
  --dark-teal: rgb(49, 151, 149);
  display: flex;
  align-items: center;
  position: relative;
  height: var(--switch-size);
  flex-basis: var(--switch-container-width);
  border-radius: var(--switch-size);
  background-color: var(--light-gray);
  flex-shrink: 0;
  transition: background-color 0.25s ease-in-out;
}
.switch::before {
  content: "";
  position: absolute;
  left: 1px;
  height: calc(var(--switch-size) - 4px);
  width: calc(var(--switch-size) - 4px);
  border-radius: 9999px;
  background-color: white;
  border: 2px solid var(--light-gray);
  transition: transform 0.375s ease-in-out;
}
.input:checked + .switch {
  background-color: var(--teal);
}
.input:checked + .switch::before {
  border-color: var(--teal);
  transform: translateX(
    calc(var(--switch-container-width) - var(--switch-size))
  );
}
.input:focus + .switch::before {
  border-color: var(--gray);
}
.input:focus:checked + .switch::before {
  border-color: var(--dark-teal);
}
.input:disabled + .switch {
  background-color: var(--gray);
}
.input:disabled + .switch::before {
  background-color: var(--dark-gray);
  border-color: var(--dark-gray);
}
</style>