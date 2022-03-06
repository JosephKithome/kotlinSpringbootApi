package com.joseph.kotSpringApi

import com.joseph.kotSpringApi.models.Message
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class KotSpringApiApplication

fun main(args: Array<String>) {
	runApplication<KotSpringApiApplication>(*args)
}

@RestController
class MessageResource(val service: MessageService){

	//retrieving all the messages
	@GetMapping
	fun index(): List<Message> = service.findMessages()

	//post messages
	@PostMapping
	fun post(@RequestBody message: Message){
		service.post(message)
	}
}

interface  MessageRepository: CrudRepository<Message, String>{

	@Query("SELECT * FROM messages")
	fun findMessages() : List<Message>
}

//Create a service
@Service
class MessageService(val db: MessageRepository){
	//retrieving all messages
	fun findMessages(): List<Message> =db.findMessages()

	//performing a post
	fun post(message: Message){
		db.save(message)
	}
}
