package com.uautso.sovs.servImpl;

import com.uautso.sovs.dto.ElectionDto;
import com.uautso.sovs.model.Election;
import com.uautso.sovs.model.UserAccount;
import com.uautso.sovs.model.Votes;
import com.uautso.sovs.repository.CandidatesRepository;
import com.uautso.sovs.repository.ElectionRepository;
import com.uautso.sovs.repository.UserAccountRepository;
import com.uautso.sovs.repository.VotesRepository;
import com.uautso.sovs.service.ElectionService;
import com.uautso.sovs.utils.Response;
import com.uautso.sovs.utils.ResponseCode;
import com.uautso.sovs.utils.userextractor.LoggedUser;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ElectionServiceImpl implements ElectionService {

    private static final Logger logger = LoggerFactory.getLogger(ElectionServiceImpl.class);

    private  final UserAccountRepository accountRepository;

    private final VotesRepository votesRepository;

    private final CandidatesRepository candidatesRepository;

    private final ElectionRepository electionRepository;

    private final LoggedUser loggedUser;

    public ElectionServiceImpl(UserAccountRepository accountRepository, VotesRepository votesRepository, CandidatesRepository candidatesRepository, ElectionRepository electionRepository, LoggedUser loggedUser) {
        this.accountRepository = accountRepository;
        this.votesRepository = votesRepository;
        this.candidatesRepository = candidatesRepository;
        this.electionRepository = electionRepository;
        this.loggedUser = loggedUser;
    }


    @Override
    public Page<Election> getAllElection(Pageable pageable) {
        try {
            UserAccount user = loggedUser.getUser();
            if(user == null){
                logger.info("UNAUTHENTICATED USER TRYING TO FETCH VOTES, REJECTED");
                return new PageImpl<>(new ArrayList<>(),pageable,0);
            }

            return electionRepository.findAll(pageable);

        }catch (Exception e){
            log.info("FAILED TO GET ELECTION");
        }
        return new PageImpl<>(new ArrayList<>(),pageable,0);
    }

    @Override
    public Response<Election> createElection(ElectionDto electionDto) {

        try {

            log.info(electionDto.toString());

            UserAccount user = loggedUser.getUser();

            if(user == null){
                logger.info("UNAUTHENTICATED USER TRYING TO ADD ELECTION, REJECTED");
            }

            Election election = new Election();


            Optional<Election> electionOptional = electionRepository.findFirstByName(electionDto.getName());

            if(electionOptional.isPresent()){
//                election = electionOptional.get();
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Election is already available");
            }

            if(electionDto.getName() == null || electionDto.getName().isBlank()){
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Name Cannot be Empty");
            }

            if(electionDto.getDescription() == null || electionDto.getDescription().isBlank()){
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Description Cannot be Empty");
            }

//
//            Votes votes = new Votes();
//            Optional<Votes> optionalVotes = votesRepository.findFirstByCandidatesUuid(votes.getUuid());
//
//            if(optionalVotes.isPresent()){
//                votes = optionalVotes.get();
//            }


            election.setName(electionDto.getName());
            election.setDescription(electionDto.getDescription());
            election.setYear(electionDto.getYear());
            election.setCategory(electionDto.getCategory());

            List<Votes> votesList = new ArrayList<>();
//            votesList.add(votes);
//            election.setVotes(votesList);


            Election savedElection = electionRepository.save(election);

            return new Response<>(false, ResponseCode.SUCCESS,savedElection, "Election created successfully");


        }catch (Exception e){
            log.info("FAILED TO CREATE ELECTION");
        }

        return new Response<>(true, ResponseCode.FAIL, "Failed to create Election");
    }

    @Override
    public Response<Election> getElectionByUuid(String uuid) {
        return null;
    }
}
