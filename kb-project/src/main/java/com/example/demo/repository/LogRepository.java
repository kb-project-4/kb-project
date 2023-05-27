package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.example.demo.entity.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {


}
