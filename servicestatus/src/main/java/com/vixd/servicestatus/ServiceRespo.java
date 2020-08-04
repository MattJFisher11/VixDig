package com.vixd.servicestatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRespo extends JpaRepository<Service, Integer> {


}
