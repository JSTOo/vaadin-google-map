package client

import javax.persistence.*

class Device implements Serializable{

    public static final Long serialVersionUID = 1L

    String deviceID
    double  lat
    double  lng
    Date date

}
