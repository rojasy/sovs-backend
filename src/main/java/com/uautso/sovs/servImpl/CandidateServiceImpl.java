package com.uautso.sovs.servImpl;

import com.uautso.sovs.dto.CandidateDto;
import com.uautso.sovs.model.Candidates;
import com.uautso.sovs.model.Election;
import com.uautso.sovs.model.UserAccount;
import com.uautso.sovs.repository.CandidatesRepository;
import com.uautso.sovs.repository.ElectionRepository;
import com.uautso.sovs.repository.UserAccountRepository;
import com.uautso.sovs.repository.VotesRepository;
import com.uautso.sovs.service.CandidateService;
import com.uautso.sovs.utils.Response;
import com.uautso.sovs.utils.ResponseCode;
import com.uautso.sovs.utils.userextractor.LoggedUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static io.leangen.graphql.util.ClassFinder.log;

@Service
public class CandidateServiceImpl implements CandidateService {
    private static final Logger logger = LoggerFactory.getLogger(CandidateServiceImpl.class);
    private  final UserAccountRepository accountRepository;

    private final VotesRepository votesRepository;

    private final CandidatesRepository candidatesRepository;

    private final ElectionRepository electionRepository;

    private final LoggedUser loggedUser;

    public CandidateServiceImpl(UserAccountRepository accountRepository, VotesRepository votesRepository, CandidatesRepository candidatesRepository, ElectionRepository electionRepository, LoggedUser loggedUser) {
        this.accountRepository = accountRepository;
        this.votesRepository = votesRepository;
        this.candidatesRepository = candidatesRepository;
        this.electionRepository = electionRepository;
        this.loggedUser = loggedUser;
    }

    @Override
    public Page<Candidates> getAllCandidates(Pageable pageable) {
        return null;
    }

    @Override
    public Response<Candidates> createCandidate(CandidateDto candidateDto) {

        try {

            UserAccount user = loggedUser.getUser();

            if(user == null){
                logger.info("UNAUTHENTICATED USER TRYING TO CREATE CANDIDATE, REJECTED");
            }

            UserAccount userAccount = new UserAccount();

            Candidates candidates = new Candidates();

            if (candidateDto.getUuid() != null){
                Optional<Candidates> optionalCandidates = candidatesRepository.findFirstByUuid(candidateDto.getUuid());
                if(optionalCandidates.isPresent()){
                    candidates = optionalCandidates.get();
                }
            }

//            else {
//                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "No User available with this ID");
//            }

            if(candidateDto.getTitle() == null || candidateDto.getTitle().isBlank()){
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Title Cannot be Empty");
            }

            if(candidateDto.getDescription() == null || candidateDto.getDescription().isBlank()){
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Description Cannot be Empty");
            }


            Optional<UserAccount> optionalUserAccount = accountRepository.findFirstByUuid(candidateDto.getUserUuid());

            if(optionalUserAccount.isEmpty()){
               return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "No user found with specified uuid");
            }

            userAccount = optionalUserAccount.get();

            Election election = new Election();

            if (candidateDto.getElectionUuid() == null)
                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "Please specify election");

            Optional<Election> optionalElection = electionRepository.findFirstByUuid(candidateDto.getElectionUuid());
            if(optionalElection.isEmpty()){
                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "No election  found with specified uuid");
            }

            election = optionalElection.get();

            candidates.setTitle(candidateDto.getTitle());
            candidates.setDescription(candidateDto.getDescription());
            candidates.setUserAccount(userAccount);
            candidates.setElection(election);


            Candidates candidatesSaved = candidatesRepository.save(candidates);

            return new Response<>(false, ResponseCode.SUCCESS,candidatesSaved, "CANDIDATES CREATED SUCCESSFULLY");


        }catch (Exception e){
            log.error("FAILED TO CREATE CANDIDATES");
            e.printStackTrace();
        }

        return new Response<>(true, ResponseCode.FAIL, "FAILED TO CREATE CANDIDATES");
    }

    @Override
    public Response<Candidates> deleteCandidate(String uuid) {
        return null;
    }

    @Override
    public Response<Candidates> updateCandidate(CandidateDto candidateDto) {
        return null;
    }

    @Override
    public Response<Candidates> getCandidateByUuid(String uuid) {
        return null;
    }
}
