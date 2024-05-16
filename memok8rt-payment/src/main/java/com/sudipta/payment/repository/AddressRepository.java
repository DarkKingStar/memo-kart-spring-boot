package com.sudipta.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sudipta.payment.modal.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
