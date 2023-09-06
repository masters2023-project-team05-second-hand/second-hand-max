package team05a.secondhand.status.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import team05a.secondhand.status.data.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
}
