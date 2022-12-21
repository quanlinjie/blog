package com.study.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.study.blog.model.RoleType;
import com.study.blog.model.User;
import com.study.blog.repository.UserRepository;

@RestController
public class DummyControllerTest {
	
	@Autowired // 의존성 주입, 메모리에 뜸
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id ) {
		try {
		userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제 실패, 존재하지 않는 ID입니다.";
		}
		return "삭제되었습니다." +id;
	}
	
	@Transactional
	@PutMapping("/dummy/user/{id}")
	public User updateUser(@PathVariable int id, @RequestBody User requestUser ) {
		System.out.println("id: " + id);
		System.out.println("password: " + requestUser.getPassword());
		System.out.println("email: " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		// 영속성, 변경 감지, 더티 체킹
		
		// userRepository.save(user);
		// save함수는 id를 전달하지 않으면 insert를 해주고, 
		// 전달하면 update, 해당 id에 대한 데이터가 없으면 insert
		//@Transactional을 걸면, save를 하지 않아도 update가 된다. = 더티체킹
		return user; 
	}
	
	@GetMapping("/dummy/user")
	public List<User> list() {
		return userRepository.findAll();
	}
	
	// 한 페이지당 5건의 데이터
	@GetMapping("/dummy/user/page")
	public Page<User> pageList(@PageableDefault
														(size=5, sort="id", 
														direction=Sort.Direction.DESC) 
														Pageable pageable) {
		Page<User> users = userRepository.findAll(pageable);
		return users;
	}
	
	// {id}주소로 파라미터를 전달받을 수 있음
	//http://localhost:8000/blog/dummy/user/1
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		//optional로 user객체를 감싸서 가져오니 null 여부를 판단해서 return
		User user = userRepository.findById(id).orElseThrow(new Supplier <IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("존재하지 않는 사용자입니다. " +id);
			}
		});
		
		//람다식
//		User user = userRepository.findById(id).orElseThrow(()-> {
//				return new IllegalArgumentException("존재하지 않는 사용자입니다. " +id);
//		});
		// user객체=java object
		// 변환 (웹브라우저가 이해할 수 있는 데이터) -> json
		// 스프링부터 = MessageConverter가 응답시 자동 작동
		//만약 java object를 리턴하게 되면 MessageConverter가 Jackson라이브러리를 호출해서
		// user object를 json으로 변환해서 브라우저에 던짐.
		return user;	
	}
	
	//http의 body에 username. password, email 데이터를 가지고 (요청)
	@PostMapping("/dummy/join")
	public String join(User user) {
		System.out.println("id: "+user.getId());
		System.out.println("username: "+user.getUsername());
		System.out.println("password: "+user.getPassword());
		System.out.println("email: "+user.getEmail());
		System.out.println("role: "+user.getRole());
		System.out.println("createDate: "+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";	
	}
}