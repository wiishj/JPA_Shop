package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class) //junit-spring
@SpringBootTest //test with springboot (test in spring containter)
@Transactional //default : rollBack=true
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    public void join() throws Exception{
        //given
        Member member = new Member();
        member.setName("gimijin");
        //when
        Long saveId = memberService.join(member);
        //then
        em.flush();
        assertThat(saveId).isEqualTo(member.getId());
        assertThat(member).isEqualTo(memberRepository.findOne(saveId));
    }

    @org.junit.Test(expected = IllegalStateException.class)
    public void duplicated_exception() throws Exception{
        //given
        Member member1 = new Member();
        member1.setName("mijin");
        Member member2 = new Member();
        member2.setName("mijin");
        //when
        memberService.join(member1);
//        try{
//            memberService.join(member2);
//        }catch(IllegalStateException e) {
//            return;
//        }
        memberService.join(member2); //exception
        //then
        Assert.fail("Exception");
    }
}