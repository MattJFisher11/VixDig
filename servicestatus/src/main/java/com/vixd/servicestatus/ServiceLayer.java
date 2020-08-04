package com.vixd.servicestatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Service
public class ServiceLayer {

    @Autowired
    private ServiceRespo repository;

    public List<com.vixd.servicestatus.Service> getServices(){
        return repository.findAll();
    }
    public void addService(com.vixd.servicestatus.Service service){
         repository.save(service);
    }
    public void updateService(com.vixd.servicestatus.Service service){
        repository.save(service);
    }

    public void removeService(Integer id){
        repository.deleteById(id);

    }



}
