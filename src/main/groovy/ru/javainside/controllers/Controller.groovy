package ru.javainside.controllers

import com.vaadin.server.VaadinServlet
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import ru.javainside.entity.Device
import ru.javainside.repositories.DeviceRepository

import javax.servlet.annotation.WebServlet


@RestController
@RequestMapping("/contact")
public class Controller {


    @Autowired
    DeviceRepository contactRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Device> getContacts(){
        return contactRepository.findAll()
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Device getContact(@PathVariable Integer id){
        return contactRepository.findOne(id)
    }

    @RequestMapping(value = "/" , method = RequestMethod.POST)
    public void saveContact(@RequestBody  Device contact){
        contactRepository.save(contact)
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void deleteContact(@RequestBody Device contact){
        contactRepository.delete(contact)
    }
}