package com.spring.mvc.single.entity.controller;
import java.util.Arrays;   
import java.util.Date;
import java.util.List; 
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.javafaker.Faker;
import com.spring.mvc.single.entity.User;
import com.spring.mvc.single.entity.repository.UserRepository;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	// User 資料維護首頁
	@GetMapping(value = { "/", "/index" })
	public String index(Model model) {
		List<User> users = userRepository.findAll();
		model.addAttribute("user", new User());
		model.addAttribute("users", users);
		model.addAttribute("_method", "POST");
		return "user/index"; // 重導到 /WEB-INF/view/user/index.jsp
	}
	
	// User 新增 或 修改 
	// SpringData 的機制，若新增的欄位有資料，則修改; 反之，則新增
	@PostMapping(value = "/")
	public String create(User user) {
		userRepository.save(user);
		return "redirect: ./";
	}
	// User 修改
	@PutMapping(value = "/")
	public String update(User user) {
		userRepository.saveAndFlush(user);
		return "redirect: ./";
	}
	// 根據 id 查詢單筆資料
	@GetMapping("/{id}")
	public String getUserById(Model model, @PathVariable Long id) {
		User user = userRepository.findOne(id);
		List<User> users = userRepository.findAll();
		model.addAttribute("user", user);
		model.addAttribute("users", users);
		model.addAttribute("_method", "PUT");
		return "user/index"; // 重導到 /WEB-INF/view/user/index.jsp
	}
	// 根據 id 查詢給 delete 使用
	@GetMapping(value = "/delete/{id}")
	public String getUserById4Del(Model model, @PathVariable Long id) {
		User user = userRepository.findOne(id);
		List<User> users = userRepository.findAll();
		model.addAttribute("user", user);
		model.addAttribute("users", users);
		model.addAttribute("_method", "DELETE");
		return "user/index"; // 重導到 /WEB_INF/view/user/index.jsp
	}
	
	// User 刪除
	@DeleteMapping(value = "/")
	public String delete(User user) {
		userRepository.delete(user.getId());
		return "redirect: ./";
	}
	
	// 查詢分頁
	// 路徑範例: /page, /page?no=1, /page?no=10 etc...
	@GetMapping("/page")
	public String userPage(Model model,
			@RequestParam(name = "no", required = false, defaultValue="0") Integer no){
		int pageNo = no;
		int pageSize = 10;
		// 排序
		Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id"); // id 由大到小
		Sort sort = new Sort(order);
		// 分頁請求
		PageRequest pageRequest = new PageRequest(pageNo, pageSize, sort);
		Page<User> page = userRepository.findAll(pageRequest);
		model.addAttribute("users", page.getContent());
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("pageNo", no);
		return "user/page"; // 重導到 /WEB_INF/view/user/page.jsp
	}
	
	// ---------------------------------------------------------------------------------------------
	// 以下是測試 User 的程式
	
	// 新增範例資料
	@GetMapping("/test/create_sample_data")
	@ResponseBody
	public String testCreateSampleData() { 
		Faker faker = new Faker();
		int count = 150;
		for(int i=0;i<count;i++) {
			Random r = new Random();
			User user = new User();
			user.setName(faker.name().lastName());
			user.setPassword(String.format("%04d", r.nextInt(10000))); // %04d 意思是不足四位補 0
			user.setBirth(faker.date().birthday());
			// 儲存到資料庫
			userRepository.saveAndFlush(user);
			
		}
		return "Create sample data ok !";
	}
	// 查詢範例資料 1 
	@GetMapping("/test/findAll")
	@ResponseBody
	public List<User> testFindAll(){
		List<User> users = userRepository.findAll();
		return users; 
	}
	//查詢範例資料 2
	@GetMapping("/test/findall_sort")
	@ResponseBody
	public List<User> testFindallSort() {
		// ASC 小->大
		Sort sortByASC = new Sort(Sort.Direction.ASC, "name");
		// DESC 大->小
		// Sort sortByDESC = new Sort(Sort.Direction.DESC, "name");
		List<User> users = userRepository.findAll(sortByASC);
		return users;
	}
	// 查詢範例資料 3
	@GetMapping("/test/findall_ids")
	@ResponseBody
	public List<User> testFindallIds() { 
		Iterable<Long> ids = Arrays.asList(1L, 3L, 5L);
		List<User> users = userRepository.findAll(ids);
		return users;
	}	
	// 查詢範例資料 4
	@GetMapping("/test/findall_example")
	@ResponseBody
	public List<User> testFindallExample() {
		User user = new User();
		user.setId(1L);
		user.setPassword("3749");
		Example<User> example = Example.of(user);
		List<User> users = userRepository.findAll(example);
		return users;
	}
	// 查詢範例資料 5
	@GetMapping("/test/findall_example2")
	@ResponseBody
	public List<User> testFindallExample2() {
		User user = new User();
		user.setName("a");
		// 欄位 name 的內容是否有包含 "a"
		// 建立 ExampleMatcher 比對器
		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name",ExampleMatcher.GenericPropertyMatchers.contains());
		Example<User> example = Example.of(user, matcher);
		List<User> users = userRepository.findAll(example);
		return users;
	}
	// 查詢範例資料 6
	@GetMapping("/test/find_one")
	@ResponseBody
	public User findOne() {		
		return userRepository.findOne(3L);
	}
	// 查詢分頁
	@GetMapping("/test/page/{no}")
	@ResponseBody
	public List<User> testPage(@PathVariable("no") Integer no) {
		int pageNo = no;
		int pageSize = 10;
		// 排序
		Sort.Order order1 = new Sort.Order(Sort.Direction.ASC, "name"); // name 由小到大
		Sort.Order order2 = new Sort.Order(Sort.Direction.DESC, "id"); // id 由大到小
		Sort sort = new Sort(order1, order2);
		// 分頁請求
		PageRequest pageRequest = new PageRequest(pageNo, pageSize, sort);
		Page<User> page = userRepository.findAll(pageRequest);
		return page.getContent();
	}
	// 查詢 JPQL 1
	@GetMapping("/test/name")
	@ResponseBody
	public List<User> getByName(@RequestParam("name") String name){
		return userRepository.getByName(name); 
	}
	// 查詢 JPQL 2	
	// 測試 url: /mvc/user/test/name/id/S/100
	@GetMapping("test/name/id/{name}/{id}")
	@ResponseBody
	public List<User> getByNameAndID(@PathVariable("name") String name,    
									 @PathVariable("id") Long id){
		return userRepository.getByNameStartingWithAndIdGreaterThanEqual(name, id);
		
	}
	//	 查詢 JPQL 3	
	// 測試 url: /mvc/user/test/ids?ids=5,10,20,30,50
	@GetMapping("/test/ids")
	@ResponseBody
	public List<User> getByIds(@RequestParam("ids") List<Long> ids) {
		return userRepository.getByIdIn(ids);
	}
	// 查詢 JPQL 4	
	// 測試 url: /mvc/user/test/birth?birth=1958-9-9
	@GetMapping("test/birth")
	@ResponseBody
	public List<User> getByBirthLessThan(@RequestParam("birth") @DateTimeFormat(iso = ISO.DATE) Date birth) {
		return userRepository.getByBirthLessThan(birth);
	}
	// 查詢 JPQL 5
	// 測試 url: /mvc/user/test/birth_between?begin=1965-1-1&end=1966-12-31
	@GetMapping("/test/birth_between")
	@ResponseBody
	public List<User> getByBirthBetween(@RequestParam("begin") @DateTimeFormat(iso = ISO.DATE) Date begin,
										@RequestParam("end") @DateTimeFormat(iso = ISO.DATE) Date end) {
		return userRepository.getByBirthBetween(begin, end);
	}
		
}


