package com.longblack;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.longblack.config.Role;
import com.longblack.domain.CoffeeProduct;
import com.longblack.domain.Order;
import com.longblack.domain.Member;
import com.longblack.domain.OrderItem;
import com.longblack.domain.Store;
import com.longblack.repository.CoffeeProductRepository;
import com.longblack.repository.MemberRepository;
import com.longblack.repository.OrderItemRepository;
import com.longblack.repository.OrderRepository;
import com.longblack.repository.StoreRepository;

@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@SpringBootApplication
public class LongBlackApplication {

	public static void main(String[] args) {
		SpringApplication.run(LongBlackApplication.class, args);
	}

	@Bean
    public CommandLineRunner dataLoader(MemberRepository memberRepository, 
    									OrderRepository customOrderRepository, 
    									OrderItemRepository orderItemRepository,  
    									StoreRepository storeRepository, 
    									CoffeeProductRepository coffeeProductRepository, 
    									BCryptPasswordEncoder passwordEncoder) {
        return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				
				this.createMember();
				this.createStores();
				this.createCoffeeProduct();
				this.createOrder();
				
			}
			
			private void createOrder() {
				
				Member member = memberRepository.findById(1L).orElseThrow();
				CoffeeProduct coffeeProduct = coffeeProductRepository.findById(1L).orElseThrow();
				
				
				OrderItem orderItem = OrderItem.builder()
						.coffeeProduct(coffeeProduct)
						.price(coffeeProduct.getPrice()*2)
						.quantity(2)
						.build();
				orderItemRepository.save(orderItem);
				
				List<OrderItem> list = new ArrayList<>();
				list.add(orderItem);
				
				Order customOrder = Order.builder()
						.status("ORDER")
						.totalAmount(100)
						.orderItems(list)
						.member(member)
						.build();
				
				customOrderRepository.save(customOrder);
			}
			
			private void createCoffeeProduct() {
				
				Store store = storeRepository.findById(1L).orElseThrow();
				
				CoffeeProduct coffeeProduct1 = CoffeeProduct.builder()
						.store(store)
						.name("롱블랙")
						.price(1500)
						.stock(100)
						.description("롱블랙-설명")
						.build();
				
				CoffeeProduct coffeeProduct2 = CoffeeProduct.builder()
						.store(store)
						.name("숏블랙")
						.price(1000)
						.stock(100)
						.description("숏블랙-설명입니다")
						.build(); 
				
				CoffeeProduct coffeeProduct3 = CoffeeProduct.builder()
						.store(store)
						.name("카페라떼")
						.price(2000)
						.stock(100)
						.description("카페라떼-설명입니다")
						.build(); 
				
				List<CoffeeProduct> list = new ArrayList<>();
				list.add(coffeeProduct1);
				list.add(coffeeProduct2);
				list.add(coffeeProduct3);
				
				coffeeProductRepository.saveAll(list);
			}
			
			private void createStores() {
				
				Store store = Store.builder()
						.name("롱블랙 테스트점")
						.phone("010-1111-2222")
						.address("서울시 어쩌구 저쩌구")
						.build();
				
				storeRepository.save(store);
			}
			
			private void createMember() {
				Member member = Member.builder()
						.email("gudwls1029@naver.com")
						.password(passwordEncoder.encode("zxcv0123!A"))
						.name("longblackAdm")
						.address("서울특별시 금천구 가산로 99 110동 XXX호")
						.phone("010-111-2222")
						.role(Role.USER)
						.build();
				
				memberRepository.save(member);
				
			}
			
			
        	
        };
	}
}
