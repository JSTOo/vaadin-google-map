package ru.javainside.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import ru.javainside.entity.Device
import ru.javainside.repositories.DeviceRepository

@RestController
@RequestMapping('/device')
class DeviceRestController{

    @Autowired
    DeviceRepository repository

    @RequestMapping(method=RequestMethod.GET,value = '/')
    public List<Device> getAll() {
        println 'get all'
        return repository.findAll()
    }

    @RequestMapping(method=RequestMethod.POST,value = '/')
    public Device create(@RequestBody Device device) {
        println 'add new ' + device
        def save = repository.save(device)
        synchronized (repository) {
            repository.notify()
        }
        return save
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/{id}")
    public void delete(@PathVariable String id) {
        println 'delete ' + id
        repository.delete(id)
    }

    @RequestMapping(method=RequestMethod.PUT, value="/{id}")
    public Device update(@PathVariable String id, @RequestBody Device device) {
        println 'update old: ' + repository.findOne(id)
        println 'new: ' + device
        def save = repository.save(device)
        synchronized (repository) {
            repository.notify()
        }
        return save
    }
}
