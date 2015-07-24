package client

import com.vaadin.tapio.googlemaps.client.LatLon
import org.springframework.web.client.RestTemplate


class Client {


    public static void main(String[] args) {
        List<LatLon> positions = [new LatLon(55.696700, 37.744012), new LatLon(55.812576, 37.501725), new LatLon(55.738689, 37.608194)]
        String uri = 'http://localhost:8080/device/'
        int i = 0
        RestTemplate template = new RestTemplate()
      /*  template.postForObject(
                uri,
                new Device(
                        deviceID: 'TEST_DEVICE',
                        lat: 50.812576,
                        lng: 37.501725,
                        date: new Date(System.currentTimeMillis())
                ),
                Void.class)*/
        while (true) {
            if (i == positions.size()) i = 0;
            template.put(uri + '{id}',
                    new Device(
                            deviceID: 'TEST_DEVICE',
                            lat: positions[i].lat,
                            lng: positions[i].lon,
                            date: new Date(System.currentTimeMillis())
                    ), 'TEST_DEVICE')
            i++
            Thread.sleep(10000)
        }
    }

}
