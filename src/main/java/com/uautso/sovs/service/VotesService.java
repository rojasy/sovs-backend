package com.uautso.sovs.service;

import com.uautso.sovs.model.Votes;
import com.uautso.sovs.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VotesService {

    Page<Votes> getAllVotes(Pageable pageable);

    Response<Votes> addVote();

}
