package team05a.secondhand.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.address.data.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
