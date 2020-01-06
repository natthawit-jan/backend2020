package com.natwit442.example.voteapp.voteapp.controller

import com.natwit442.example.voteapp.voteapp.model.Vote
import com.natwit442.example.voteapp.voteapp.model.VoteData
import com.natwit442.example.voteapp.voteapp.repository.VoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class VoteController {

    @Autowired
    lateinit var repository : VoteRepository

    @GetMapping("/status")
    fun getAllVote() : Iterable<VoteData> {
        return repository.findAll()
    }

    @PostMapping("/vote")
    fun vote(
            @RequestBody body : Vote?
    ) : ResponseEntity<HttpStatus> {
        if (body != null) {
            val result = repository.findById(body.voteFor)
            if (result.isEmpty) {
                repository.save(VoteData(body.voteFor))
            }
            else {
                val newResult = result.get().voteScore!!.plus(1)
                repository.save(VoteData(body.voteFor, newResult))
            }
            return ResponseEntity.ok(HttpStatus.OK);
        }
        return ResponseEntity.ok(HttpStatus.BAD_REQUEST);


    }





}