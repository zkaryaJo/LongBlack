package com.longblack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.longblack.config.Role;
import com.longblack.domain.Member;
import com.longblack.domain.Order;
import com.longblack.domain.OrderItem;
import com.longblack.domain.Platform;
import com.longblack.domain.Product;
import com.longblack.domain.Store;
import com.longblack.domain.StorePlatform;
import com.longblack.repository.MemberRepository;
import com.longblack.repository.OrderRepository;
import com.longblack.repository.PlatformRepository;
import com.longblack.repository.ProductRepository;
import com.longblack.repository.StorePlatformRepository;
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
    									StoreRepository storeRepository, 
    									StorePlatformRepository storePlatformRepository,
    									ProductRepository productRepository, 
    									PlatformRepository platformRepository,
    									BCryptPasswordEncoder passwordEncoder) {
        return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				this.createMember();
				this.createPlatform();
				this.createStores();
				this.createStorePlatform();
				this.createProduct();
				this.createOrder();
			}
			
			private void createStorePlatform() {
				
				Store store = storeRepository.findById(1L).orElseThrow();
				List<Platform> platforms = platformRepository.findAll();
				
				for(Platform p : platforms) {
					StorePlatform sp = StorePlatform.builder()
							.store(store)
							.platform(p)
							.useYn("Y")
							.build();
					storePlatformRepository.save(sp);
				}
				
			}
			
			private void createPlatform() {
				Platform platform1 = Platform.builder()
						.name("배달의민족")
						.build();
				Platform platform2 = Platform.builder()
						.name("쿠팡이츠")
						.build();
				Platform platform3 = Platform.builder()
						.name("요기요")
						.build();
				Platform platform4 = Platform.builder()
						.name("일반대행")
						.build();
				
				List<Platform> list = new ArrayList<>();
				list.add(platform1);
				list.add(platform2);
				list.add(platform3);
				list.add(platform4);
				platformRepository.saveAll(list);
			}
			
			private void createOrder() {
				
				Member member = memberRepository.findById(1L).orElseThrow();
				Product product = productRepository.findById(1L).orElseThrow();
				
				
				OrderItem orderItem = OrderItem.builder()
						.product(product)
						.price(product.getPrice()*2)
						.quantity(2)
						.build();
				
				List<OrderItem> list = new ArrayList<>();
				list.add(orderItem);
				
				Order customOrder = Order.builder()
						.status("ORDER")
						.totalAmount(100)
						.orderItems(list)
						.member(member)
						.orderDate(new Date())
						.build();
				
				customOrderRepository.save(customOrder);
			}
			
			private void createStores() {
				
				Member owner = memberRepository.findById(1L).orElseThrow();
				
				Store store = Store.builder()
						.name("롱블랙 테스트점")
						.phone("010-1111-2222")
						.address("서울시 어쩌구 저쩌구")
						.owner(owner)
						.build();
				
				storeRepository.save(store);
			}
			
			private void createProduct() {
				
				Store store = storeRepository.findById(1L).orElseThrow();
				Product product1 = Product.builder()
						.category("커피")
						.name("롱블랙")
						.store(store)
						.price(1500)
						.stock(100)
						.description("롱블랙-설명")
						.build();
				
				Product product2 = Product.builder()
						.category("커피")
						.store(store)
						.name("숏블랙")
						.price(1000)
						.stock(100)
						.description("숏블랙-설명입니다")
						.build(); 
				
				Product product3 = Product.builder()
						.category("커피")
						.store(store)
						.name("카페라떼")
						.price(2000)
						.stock(100)
						.description("카페라떼-설명입니다")
						.build(); 
				
				List<Product> list = new ArrayList<>();
				list.add(product1);
				list.add(product2);
				list.add(product3);
				
				productRepository.saveAll(list);
			}
			
			
			private void createMember() {
				Member customer = Member.builder()
						.email("customer")
						.password(passwordEncoder.encode("1"))
						.name("customer")
						.address("서울특별시 금천구 가산로 99 110동 XXX호")
						.phone("010-111-2222")
						.role(Role.ROLE_CUSTOMER)
						.build();
				
				Member owner = Member.builder()
						.email("owner")
						.password(passwordEncoder.encode("1"))
						.name("owner")
						.address("서울특별시 금천구 가산로 99 110동 XXX호")
						.phone("010-111-2222")
						.role(Role.ROLE_OWNER)
						.build();
				
				Member admin = Member.builder()
						.email("admin")
						.password(passwordEncoder.encode("1"))
						.name("admin")
						.address("서울특별시 금천구 가산로 99 110동 XXX호")
						.phone("010-111-2222")
						.role(Role.ROLE_ADMIN)
						.build();
				
				List<Member> list= new ArrayList<>();
				list.add(owner);
				list.add(customer);
				list.add(admin);
				
				memberRepository.saveAll(list);
				
			}
			
			
        	
        };
	}
}
