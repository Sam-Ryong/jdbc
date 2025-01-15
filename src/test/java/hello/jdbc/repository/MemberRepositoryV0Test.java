package hello.jdbc.repository;

import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();
    @Test
    void crud() throws SQLException {
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
    }
}