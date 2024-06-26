package com.longblack;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.longblack.config.Role;
import com.longblack.domain.Member;
import com.longblack.repository.MemberRepository;

@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@SpringBootApplication
public class LongBlackApplication {

	public static void main(String[] args) {
		SpringApplication.run(LongBlackApplication.class, args);
	}

	@Bean
    public CommandLineRunner dataLoader(MemberRepository memberRepository, BCryptPasswordEncoder passwordEncoder) {
        return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				
				this.createMember();
			}
			
			private void createMember() {
				Member member = Member.builder()
						.email("gudwls1029@naver.com")
						.password(passwordEncoder.encode("zxcv0123!A"))
						.name("longblackAdm")
						.role(Role.USER)
						.build();
				
				memberRepository.save(member);
				
			}
        	
        };
	}
}
