package com.example.ticketing.service.performance;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@Sql(scripts = {"classpath:data.sql"})
class PerformanceServiceTest {


}