package campusconnect.service;
import java.util.*;
@FunctionalInterface public interface Repository<T,K> {
    Optional<T> findByKey(K key);
}
