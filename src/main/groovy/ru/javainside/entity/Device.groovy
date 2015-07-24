package ru.javainside.entity
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@Table(name = 'device')
class Device implements Serializable {

    public static final Long serialVersionUID = 1L

    @Id
    @Column(name = "device_id")
    String deviceID

    @Column(name = "Lat")
    double  lat

    @Column(name = "Lng")
    double  lng

    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    Date date

    @Override
    String toString() {
        return deviceID + ' ' + ' lat: ' + lat + ' lng: ' + lng + '\ntime: ' + date
    }
}
