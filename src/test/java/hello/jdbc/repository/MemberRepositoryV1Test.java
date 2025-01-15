package hello.jdbc.repository;

import com.zaxxer.hikari.HikariDataSource;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 repository;

    @BeforeEach
    void beforeEach(){

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        dataSource.setMaximumPoolSize(10);
        dataSource.setPoolName("MyPool");
        repository = new MemberRepositoryV1(dataSource);

    }
    @Test
    void crud() throws SQLException, InterruptedException {
        Member memberV0 = new Member("memberV4", 10000);
        repository.save(memberV0);

        Member findMember = repository.findById(memberV0.getMemberId());
        org.assertj.core.api.Assertions.assertThat(findMember).isEqualTo(memberV0);

        repository.update(memberV0.getMemberId(), 20000);
        Member updatedMember = repository.findById(memberV0.getMemberId());
        org.assertj.core.api.Assertions.assertThat(updatedMember.getMoney()).isEqualTo(20000);

        repository.delete(memberV0.getMemberId());
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> repository.findById(memberV0.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);

        Thread.sleep(1000);
    }
}