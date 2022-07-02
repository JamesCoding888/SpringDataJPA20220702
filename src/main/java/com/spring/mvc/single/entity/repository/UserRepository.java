package com.spring.mvc.single.entity.repository;
import java.util.Date; 
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.spring.mvc.single.entity.User;

/*
 * 方法命名規則
 * 1. 查詢方法以 find | read | get 開頭
 * 2. 涉及條件查詢時，條件的屬性 (首字幕打寫) 
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	// 根據 name 來去得 User
	List<User> getByName(String name);
	
	// Where name LIKE ? AND id >= ?
	List<User> getByNameStartingWithAndIdGreaterThanEqual(String name, Long id);
	
	// Where id in (?, ?, ?)
	List<User> getByIdIn(List<Long> ids);
		
	// Where birth < ?
	List<User> getByBirthLessThan(Date birth);
	
	// Where birth >= ? AND birth <= ?
	// Where birth between ?(含) AND ?(含)
	List<User> getByBirthBetween(Date birthBegin, Date birthEnd);

	
}
