package com.sudipta.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sudipta.order.modal.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
