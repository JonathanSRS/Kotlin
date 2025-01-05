package br.com.alura.forum

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping

@RestController
@RequestMapping("/hello")
class HelloController {
	
	@GetMapping
	fun hello(): String{
		return "Hello Word!"
	}
}