<template>
    <div ref="mapContainer" class="map-container"></div>
</template>

<script>
    import mapboxgl from "mapbox-gl"
    import { getSensor } from "../service/emergencyService"

    mapboxgl.accessToken = import.meta.env.VITE_MAPBOX_ACCESS_TOKEN

    export default {
        props: ['modelValue'],
        mounted() {
            let self = this
            const { lng, lat, zoom, bearing, pitch } = this.modelValue
            const map = new mapboxgl.Map({
                container: this.$refs.mapContainer,
                style: "mapbox://styles/mapbox/streets-v12", // Replace with your preferred map style
                center: [lng, lat],
                bearing,
                pitch,
                zoom,
            });
    
            this.sensorInterval = setInterval(() => {
                self.getSensor().then(function(sensors) {
                    self.addSensorOnMap(sensors)
                })
            }, 5000);

            const updateLocation = () =>
            this.$emit('update:modelValue', this.getLocation())

            map.on('move', updateLocation)
            map.on('zoom', updateLocation)
            map.on('rotate', updateLocation)
            map.on('pitch', updateLocation)

            this.map = map;
        },
        unmounted() {
            this.map.remove();
            this.map = null;
        },
        watch: {
            modelValue(next) {
                const curr = this.getLocation()
                const map = this.map

                if (curr.lng != next.lng || curr.lat != next.lat)
                map.setCenter({ lng: next.lng, lat: next.lat })
                if (curr.pitch != next.pitch) map.setPitch(next.pitch)
                if (curr.bearing != next.bearing) map.setBearing(next.bearing)
                if (curr.zoom != next.zoom) map.setZoom(next.zoom)
            }
        },
        methods: {
            getLocation() {
                return {
                ...this.map.getCenter(),
                bearing: this.map.getBearing(),
                pitch: this.map.getPitch(),
                zoom: this.map.getZoom(),
                }
            },
            async getSensor() {
                try {
                    return await getSensor()
                } catch (error) {
                    console.log('Error fetching incident:', error);
                    return false;
                }
            },
            addSensorOnMap(sensors){
                sensors.forEach(element => {
                    let el = document.createElement('div')
                    el.className = 'marker sensor'

                    new mapboxgl.Marker(el)
                        .setLngLat([element.lon, element.lat])
                        .setPopup(
                            new mapboxgl.Popup({ offset: 25 })
                                .setHTML(
                                    `<h3>Capteur N°${element.id}</h3>
                                    <p>Valeur: ${element.value}</p>
                                    <p>Date: ${element.time}</p>  
                                    `
                            )
                        )
                        .addTo(this.map);
                })

            }
        },
    }

</script>

<style>
    .map-container {
        flex: 1;
    }

    .marker {
        background-image: url('@/assets/default_icon.png');
        background-size: cover;
        width: 50px;
        height: 50px;
        border-radius: 10%;
        cursor: pointer;
    }

    .sensor {
        background-image: url('@/assets/sensor.png');
    }
    .mapboxgl-popup-content {
        text-align: center;
        font-family: 'Open Sans', sans-serif;
        color: black;
    }

    .mapboxgl-popup-close-button{
        color: black;
    }
</style>
