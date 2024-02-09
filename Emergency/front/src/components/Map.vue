<template>
    <div ref="mapContainer" class="map-container"></div>
    <div class="position-fixed bottom-0 end-0 p-3" style="z-index: 11">
      <div id="liveToast" class="toast align-items-center text-bg-info border-0" role="alert" aria-live="assertive" aria-atomic="true" ref="toast">
        <div class="d-flex">
            <div class="toast-body" style="color: white;">Un nouvel incident a été déclaré !</div>
            <button
              type="button"
              class="btn-close btn-close-white me-2 m-auto"
              data-bs-dismiss="toast"
              aria-label="Close"
            ></button>
        </div>
      </div>
    </div>
</template>

<script>
    import mapboxgl from "mapbox-gl"
    import { Toast } from "bootstrap"
    import "bootstrap/dist/css/bootstrap.min.css";
    import { getONGOINGIncident, getManagementIncident } from '@/service/emergencyService.js'
    import { ref } from "vue";
    mapboxgl.accessToken = import.meta.env.VITE_MAPBOX_ACCESS_TOKEN

    export default {
        props: ['modelValue'],
        data() {
            return {
                fireStationsMarkers: ref([]),
                incidentsMarkers: ref([]),
            }
        },
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

            const updateLocation = () =>
            this.$emit('update:modelValue', this.getLocation())

            map.on('move', updateLocation)
            map.on('zoom', updateLocation)
            map.on('rotate', updateLocation)
            map.on('pitch', updateLocation)

            this.map = map;

            this.init_ws();

            setInterval(() => {
                self.refreshIncidents(this.map)
            }, 15000)
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

            init_ws(){
                this.websocket           = new WebSocket("ws://127.0.0.1:8080/websocket");
                this.websocket.onopen    = this.onSocketOpen;
                this.websocket.onmessage = this.onSocketMessage;
                this.websocket.onerror   = this.onSockerError;
            },

            onSocketOpen(event){
                this.connection_ready = true;
            },

            onSocketMessage(event){
                if (event.data.startsWith("INCIDENTS")){
                    let received = JSON.parse(event.data.replace('INCIDENTS',''))
                    received.forEach(element => {
                        let el = document.createElement('div')
                        el.className = 'marker incident'
                        if(element.severity <= 3){
                            el.className += ' low-fire'
                        } else if(element.severity <= 6) {
                            el.className += ' medium-fire'
                        } else {
                            el.className += ' high-fire'
                        }

                        let html = `<h5>Incident N°${element.id}</h5>
                                            <p>Type: ${element.type}</p>
                                            <p>Sévérité: ${element.severity}</p>
                                            <p>Date: ${element.date_start}</p>
                                            <p>Status: ${element.status}</p>
                                        `
    
                        let marker = new mapboxgl.Marker(el)
                            .setLngLat([element.lon, element.lat])
                            .setPopup(
                                new mapboxgl.Popup({ offset: 25 })
                                    .setHTML(html)
                            )
                            .addTo(this.map);

                        this.incidentsMarkers.push({"id": "incident-"+element.id, "el": el, "marker": marker, "html": html})
                        const toast = new Toast(this.$refs.toast);
                        toast.show();
                    })
                    
                } else if (event.data.startsWith("FIRE_STATIONS")){
                    let received = JSON.parse(event.data.replace('FIRE_STATIONS',''))
                    received.forEach(element => {
                        let el = document.createElement('div')
                        el.className = 'marker fire-station'

                        let html = `<h5>Caserne ${element.name}</h5>
                                            <p>Type: ${element.site_type}</p>
                                            <p>Nombre de camions: ${element.trucks_number}</p>
                                        `
    
                        let marker = new mapboxgl.Marker(el)
                            .setLngLat([element.lon, element.lat])
                            .setPopup(
                                new mapboxgl.Popup({ offset: 25 })
                                    .setHTML(html)
                            )
                            .addTo(this.map);

                        this.fireStationsMarkers.push({"id": "fire_station-"+element.name, "el": el, "marker": marker, "html": html})
                    })
                } else if (event.data.startsWith("MANAGEMENT_INCIDENTS")){
                    let received = JSON.parse(event.data.replace('MANAGEMENT_INCIDENTS',''))
                    received.forEach(element => {
                        // let index = this.currentMarkers.map((el) => el.id).indexOf({"id": "incident-"+element.id_incident})
                        let index = null
                        for(let i = 0; i < this.incidentsMarkers.length; i++) {
                            if(this.incidentsMarkers[i].id == "incident-"+element.id_incident) {
                                index = i;
                            }
                        }
                        let old_marker = this.incidentsMarkers[index]
                        old_marker.marker.remove()


                        let el = old_marker.el

                        let html = old_marker.html + `<p>Camions affectés: ${element.nb_trucks}</p>
                                                        <p>Temps de trajet: ${Math.round(element.travel_time/60)}min</p>
                                                        <p>Camions arrivés: ${element.on_site}</p>
                                                        `
    
                        let new_marker = new mapboxgl.Marker(el)
                            .setLngLat(old_marker.marker.getLngLat())
                            .setPopup(
                                new mapboxgl.Popup({ offset: 25 })
                                    .setHTML(html)
                            )
                            .addTo(this.map);
                        
                        this.incidentsMarkers.splice(index, 1)
                        this.incidentsMarkers.push({"id": "incident-"+element.id, "el": el, "marker": new_marker, "html": html})
                    })
                }

            },

            onSockerError(event){
                this.connection_error = true;
            },

            send_message() {
                var to_send = { from: "front", message: this.new_message };
                this.websocket.send( JSON.stringify(to_send) );
                this.messages.push( { from: "me"   , message: this.new_message } );
                this.new_message = "";
            },

            async getONGOINGIncident() {
                try {
                    return await getONGOINGIncident()
                } catch (error) {
                    console.log('Error fetching incident:', error);
                    return false;
                }
            },

            async getManagementIncident() {
                try {
                    return await getManagementIncident()
                } catch (error) {
                    console.log('Error fetching managementIncident:', error);
                    return false;
                }
            },

            removeMarkers() {
                this.incidentsMarkers.forEach(element => {
                    element.marker.remove()
                });
            },
            
            refreshIncidents(map) {
                this.removeMarkers()
                this.incidentsMarkers = ref([])

                this.getONGOINGIncident().then((list) => {
                    list.forEach(element => {
                        let el = document.createElement('div')
                        el.className = 'marker incident'
                        if(element.severity <= 3){
                            el.className += ' low-fire'
                        } else if(element.severity <= 6) {
                            el.className += ' medium-fire'
                        } else {
                            el.className += ' high-fire'
                        }

                        const date = new Date(element.date_start)
                        element.date_start = date.getFullYear()+'-'+date.getMonth()+'-'+date.getDate()+' '+date.getHours()+':'+date.getMinutes()+':'+date.getSeconds()
    
                        let html = `<h5>Incident N°${element.id}</h5>
                                            <p>Type: ${element.type}</p>
                                            <p>Sévérité: ${element.severity}</p>
                                            <p>Date: ${element.date_start}</p>
                                            <p>Status: ${element.status}</p>
                                        `
    
                        let marker = new mapboxgl.Marker(el)
                            .setLngLat([element.lon, element.lat])
                            .setPopup(
                                new mapboxgl.Popup({ offset: 25 })
                                    .setHTML(html)
                            )
                            .addTo(map);

                            this.incidentsMarkers.push({"id": "incident-"+element.id, "el": el, "marker": marker, "html": html})
                    })
                })
                this.getManagementIncident().then((list) => {
                    console.log
                    list.forEach(element => {
                        let index = null
                        for(let i = 0; i < this.incidentsMarkers.length; i++) {
                            if(this.incidentsMarkers[i].id == "incident-"+element.id_incident) {
                                index = i;
                            }
                        }
                        let old_marker = this.incidentsMarkers[index]
                        old_marker.marker.remove()
    
    
                        let el = old_marker.el
    
                        let html = old_marker.html + `<p>Camions affectés: ${element.nb_trucks}</p>
                                                        <p>Temps de trajet: ${Math.round(element.travel_time/60)}min</p>
                                                        <p>Camions arrivés: ${element.on_site}</p>
                                                        `
    
                        let new_marker = new mapboxgl.Marker(el)
                            .setLngLat(old_marker.marker.getLngLat())
                            .setPopup(
                                new mapboxgl.Popup({ offset: 25 })
                                    .setHTML(html)
                            )
                            .addTo(map);
                        
                        this.incidentsMarkers.splice(index, 1)
                        this.incidentsMarkers.push({"id": "incident-"+element.id, "el": el, "marker": new_marker, "html": html})
                    })
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
    
    .low-fire {
        background-image: url('@/assets/fire_low.png');
    }
    
    .medium-fire {
        background-image: url('@/assets/fire_medium.png');
    }
    
    .high-fire {
        background-image: url('@/assets/fire_high.png');
    }

    .fire-station {
        background-image: url('@/assets/fire_station.png');
    }

    .mapboxgl-popup {
        max-width: 200px;
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