package ru.javainside

import com.vaadin.annotations.Theme
import com.vaadin.server.VaadinRequest
import com.vaadin.tapio.googlemaps.GoogleMap
import com.vaadin.tapio.googlemaps.client.LatLon
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker
import com.vaadin.ui.Button
import com.vaadin.ui.HorizontalLayout
import com.vaadin.ui.Label
import com.vaadin.ui.UI
import com.vaadin.ui.VerticalLayout
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.vaadin.spring.annotation.VaadinUI
import ru.javainside.repositories.DeviceRepository

@VaadinUI
@Theme('valo')
class MapUI extends UI implements Runnable {

    @Autowired
    Environment environment

    @Autowired
    DeviceRepository repository

    private GoogleMap googleMap
    private HashMap<String, GoogleMapMarker> markerMap = []
    private GoogleMapMarker kakolaMarker = new GoogleMapMarker(
            "DRAGGABLE: Kakolan vankila", new LatLon(60.44291, 22.242415),
            true, null);
    private Label latlon

    @Override
    protected void init(VaadinRequest request) {
        def layout = new VerticalLayout()
        googleMap = new GoogleMap(environment.getProperty('google.map.api.key'), null, 'russian')
        googleMap.setSizeFull()
        googleMap.setZoom(10)
        googleMap.setCenter(new LatLon(55.754816, 37.625628))
        repository.findAll().each {
            markerMap.put(
                    it.deviceID,
                    new GoogleMapMarker(
                            caption: it.deviceID,
                            position: new LatLon(it.lat, it.lng),
                            draggable: true,
                            animationEnabled: false
                    )
            )
        }
        markerMap.each { k, v ->
            googleMap.addMarker(v)
        }
        layout.addComponent(googleMap)
        Button button = new Button('Update')
        button.addClickListener({
            e->
                repository.findAll().each {
                    def marker = markerMap.get(it.deviceID)
                    println 'old marker position ' + marker.position.lat + ' ' + marker.position.lon
                    if (marker == null)
                        markerMap.put(
                                it.deviceID,
                                new GoogleMapMarker(
                                        caption: it.deviceID,
                                        position: new LatLon(it.lat, it.lng),
                                        draggable: true,
                                        animationEnabled: false
                                )
                        )
                    else
                        marker.position = new LatLon(it.lat, it.lng)
                    println 'new marker position ' + marker.position.lat + ' ' + marker.position.lon
                }
                markerMap.each { k, v ->
                    googleMap.addMarker(v)
                }
        })
        button.setSizeFull()
        layout.addComponent(button)
        Button button2 = new Button('Remove')
        button2.addClickListener({
            e->
                googleMap.clearMarkers()
        })
        button2.setSizeFull()
        Button moveMarkerButton = new Button("Move kakola marker",
                new Button.ClickListener() {

                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        kakolaMarker.setPosition(new LatLon(60.3, 22.242415));
                        googleMap.addMarker(kakolaMarker);
                    }
                });
        Button clearMarkersButton = new Button("Remove all markers", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                googleMap.clearMarkers();
            }
        });
        layout.addComponent(button2)
        layout.addComponent(moveMarkerButton)
        layout.addComponent(clearMarkersButton)
        layout.addComponent(latlon = new Label())
        latlon.setSizeFull()
        layout.setSizeFull()
        content = layout
        UI.getCurrent().setPollInterval(500)
        //layout
        new Thread(this).start()
    }



    @Override
    void run() {
        while (true) {
            try {
                repository.findAll().each {
                    def marker = markerMap.get(it.deviceID)
                    if (marker == null) {
                        markerMap.put(
                                it.deviceID,
                                new GoogleMapMarker(
                                        caption: it.deviceID,
                                        position: new LatLon(it.lat, it.lng),
                                        draggable: true,
                                        animationEnabled: false
                                )
                        )
                    }
                    else
                        marker.position = new LatLon(it.lat, it.lng)
                    latlon.caption = it.lat + ' ' + it.lng
                }
                googleMap.clearMarkers()
                Thread.sleep(environment.getProperty('vaadin.ui.map.delay') as long)
                markerMap.each { k, v ->
                    googleMap.addMarker(v)
                }/*
                markerMap.each { k, v ->
                    println v
                    googleMap.addMarker(v)
                }*/
                println 'update ui'
                synchronized (repository) {
               //     repository.wait()
                }
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
    }
}
