package com.natwit442.example.voteapp.voteapp.repository

import com.natwit442.example.voteapp.voteapp.model.VoteData
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface VoteRepository : CrudRepository<VoteData, String> {

}