package com.fiap.checkpoint.login.controllers;

import java.util.Optional;

import javax.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fiap.checkpoint.login.dto.LoginDto;
import com.fiap.checkpoint.login.model.Login;
import com.fiap.checkpoint.login.repository.LoginRepository;

@Controller
public class LoginController {
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping("/login")
	public ModelAndView login(LoginDto login) {
		ModelAndView modelAndView = new ModelAndView("login/home");
		return  modelAndView;
	}
	
	@PostMapping("/login")
	public ModelAndView logar(@Valid LoginDto login, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ModelAndView("login/home");
		}
		
		Login loginEntity  = modelMapper.map(login, Login.class);
		Optional<Login> user = loginRepository.findById(loginEntity.getUsername());
		if(!user.isEmpty()) {
			ModelAndView modelAndView = new ModelAndView("redirect:/conta");
			System.out.println(user.get().getUsername());
			modelAndView.addObject("username", user.get().getUsername());
			modelAndView.addObject("password", user.get().getPassword());
			return modelAndView;	
		}
		
		ModelAndView modelAndView = new ModelAndView("login/home");
		modelAndView.addObject("error", "Nenhum usuário encontrado!");
		return modelAndView;
	}
	
	@GetMapping("/cadastro")
	public ModelAndView cadastrar(LoginDto login) {
		ModelAndView modelAndView = new ModelAndView("login/cadastro");
		return  modelAndView;
	}
	
	@PostMapping("/cadastro")
	public ModelAndView inserir(@Valid LoginDto login, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ModelAndView("login/cadastro");
		}
		
		Login loginEntity  = modelMapper.map(login, Login.class);
		Optional<Login> user = loginRepository.findById(loginEntity.getUsername());
		if(user.isEmpty()) {
			loginRepository.save(loginEntity);
			return new ModelAndView("redirect:/login");
		}
		
		ModelAndView modelAndView = new ModelAndView("login/cadastro");
		modelAndView.addObject("error", "Usuário já cadastrado!");
		return modelAndView;
	}
	
	@GetMapping("/conta")
	public ModelAndView conta(LoginDto login) {
		ModelAndView modelAndView = new ModelAndView("home/home");
		return  modelAndView;
	}
}
