package ru.javainside.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.javainside.entity.Device

@Repository
interface DeviceRepository extends CrudRepository<Device,String> {
}
