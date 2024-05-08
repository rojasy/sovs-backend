package com.uautso.sovs.servImpl;

import com.uautso.sovs.dto.VotesDto;
import com.uautso.sovs.model.Candidates;
import com.uautso.sovs.model.Election;
import com.uautso.sovs.model.UserAccount;
import com.uautso.sovs.model.Votes;
import com.uautso.sovs.repository.CandidatesRepository;
import com.uautso.sovs.repository.ElectionRepository;
import com.uautso.sovs.repository.UserAccountRepository;
import com.uautso.sovs.repository.VotesRepository;
import com.uautso.sovs.service.VotesService;
import com.uautso.sovs.utils.Response;
import com.uautso.sovs.utils.ResponseCode;
import com.uautso.sovs.utils.userextractor.LoggedUser;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
@Slf4j
public class VotesServiceImpl implements VotesService {

    private  final UserAccountRepository accountRepository;

    private final VotesRepository votesRepository;

    private final CandidatesRepository candidatesRepository;

    private final ElectionRepository electionRepository;

    private final LoggedUser loggedUser;

    private final Logger logger = LoggerFactory.getLogger(VotesServiceImpl.class);

    @Autowired
    public VotesServiceImpl(UserAccountRepository accountRepository, VotesRepository votesRepository, CandidatesRepository candidatesRepository, ElectionRepository electionRepository, LoggedUser loggedUser) {
        this.accountRepository = accountRepository;
        this.votesRepository = votesRepository;
        this.candidatesRepository = candidatesRepository;
        this.electionRepository = electionRepository;
        this.loggedUser = loggedUser;
    }

    @Override
    public Page<Votes> getAllVotes(Pageable pageable) {
        try {
            UserAccount user = loggedUser.getUser();
            if(user == null) {
                logger.info("UNAUTHENTICATED USER TRYING TO FETCH VOTES, REJECTED");
                return  new PageImpl<>(new ArrayList<>(),pageable,0);
            }

            return votesRepository.findAll(pageable);

        }catch (Exception e) {
            log.error("FAILED TO GET ALL VOTES");
        }
        return new PageImpl<>(new ArrayList<>(), pageable, 0);
    }

    @Override
    public Response<Votes> addVote(VotesDto votesDto) {
        try {

            UserAccount user = loggedUser.getUser();

            System.out.println("logged User id "+user.getUuid());

            if(user == null){
                logger.info("UNAUTHENTICATED USER TRYING TO CREATE VOTES, REJECTED");
            }

            UserAccount userAccount = new UserAccount();

            if(user.getUuid() != null){
                Optional<UserAccount> userAccountOptional = accountRepository.findFirstByUuid(user.getUuid());
                if(userAccountOptional.isPresent()){
                    userAccount = userAccountOptional.get();
                }
            }

            Votes votes = new Votes();
            Optional<Votes> optionalVotes = votesRepository.findFirstByCandidatesUuid(votesDto.getCandidateUuid());

            if(optionalVotes.isPresent()){
                votes = optionalVotes.get();
            }

            if(votesDto.getCandidateUuid() == null){
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Candidate UUid Cannot be Empty");
            }

            if(votesDto.getElectionYear() == null){
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Election Year Cannot be Empty");
            }

//            if(!votesDto.getCandidateUuid().isBlank()){
//                votes.setCandidateUuid(votesDto.getCandidateUuid());
//            }

            if(!votesDto.getElectionYear().toString().isBlank()){
                votes.setYear(votesDto.getElectionYear());
            }

            Candidates candidates = new Candidates();

            Optional<Candidates> candidatesOptional = candidatesRepository.findFirstByUuid(candidates.getUuid());

            if(candidatesOptional.isPresent()){
                candidates = candidatesOptional.get();
            }

            Election election = new Election();

            Optional<Election> electionOptional = electionRepository.findFirstByUuid(election.getUuid());

            if(electionOptional.isPresent()){
                election = electionOptional.get();
            }

//            Votes votes1 = new Votes();


//            UserAccount userAccount1 = new UserAccount();

            votes.setCandidates(candidates);
            votes.setUserAccount(userAccount);
            votes.setElection(election);

            Votes savedVotes = votesRepository.save(votes);

            return new Response<>(false, ResponseCode.SUCCESS, savedVotes, "Vote Added successful");


        }catch (Exception e){
            log.error("FAILED TO ADD VOTES");
        }
        return new Response<>(true, ResponseCode.FAIL, "Failed to add vote");
    }

}
