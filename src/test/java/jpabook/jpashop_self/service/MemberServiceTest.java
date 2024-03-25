package jpabook.jpashop_self.service;

import jpabook.jpashop_self.domain.Member;
import jpabook.jpashop_self.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");


        // when
        Long savedId = memberService.join(member);

        // then
        Assertions.assertEquals(member,memberRepository.findMember(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim1");

        Member member2 = new Member();
        member2.setName("kim1");
        // when
        memberService.join(member);
        memberService.join(member2);

        // then
        fail("예외가 발생헤야 한다.");
    }

}

