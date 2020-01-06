package com.natwit442.example.voteapp.voteapp.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
open class VoteData {
    @Id
    @Column(name = "candidate_id")
    var candidateId: String? = null

    @Column(name = "vote_score")
    var voteScore : Long? = null

    protected constructor()

    constructor(id: String, voteScore : Long? = 1) {
        this.candidateId = id;
        this.voteScore = voteScore;
    }
}