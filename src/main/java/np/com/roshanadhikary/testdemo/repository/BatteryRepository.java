package np.com.roshanadhikary.testdemo.repository;

import np.com.roshanadhikary.testdemo.entity.Battery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatteryRepository extends JpaRepository<Battery, Integer> {
}
