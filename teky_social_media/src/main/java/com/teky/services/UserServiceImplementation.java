package com.teky.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.teky.models.User;
import com.teky.repository.UserRepository;


@Service
public class UserServiceImplementation implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public User registerUser(User user) {
		User newUser = new User();
		newUser.setEmail(user.getEmail());
		newUser.setFirstname(user.getFirstname());
		newUser.setLastname(user.getLastname());
		newUser.setPassword(user.getPassword());
		newUser.setId(user.getId());
		
		User savedUser = userRepository.save(newUser);
		return savedUser; 
	}

	@Override
	public User findUserById(Integer userId) throws Exception {
		Optional<User> user = userRepository.findById(userId);
		
		if(user.isPresent())
		{
			return user.get();
		}
		
		throw new Exception("user not exist userid" + userId);
	}

	@Override
	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public User followUser(Integer userId1, Integer userId2) throws Exception {
		
		User user1 = findUserById(userId1);
		
		User user2 = findUserById(userId2);
		
		user2.getFollowers().add(user1.getId()); 
		user1.getFollowing().add(user2.getId());

		userRepository.save(user1); 
		userRepository.save(user2);

		return user1;
	}

	@Override
	public User updateUser(User user,Integer userId) throws Exception {
Optional<User> user1 = userRepository.findById(userId);
		
		if(user1.isEmpty())
		{
			throw new Exception("user not exit with id" + userId);
		}
		
		User oldUser = user1.get();
		
		if(user.getFirstname()!=null)
		{
			oldUser.setFirstname(user.getFirstname());
		}
		
		if(user.getLastname()!=null)
		{
			oldUser.setLastname(user.getLastname());
		}
		
		if(user.getEmail()!=null)
		{
			oldUser.setEmail(user.getEmail());
		}
		
		User updatedUser = userRepository.save(oldUser);
	
		return updatedUser;
	}
	
	

	@Override
	public List<User> searchUser(String query) 
	{
		return userRepository.searchUser(query);
	}

	@Override
	public String deleteUser(Integer userId) {
			return "user deleted successfully id : " + userId;
	}

}
