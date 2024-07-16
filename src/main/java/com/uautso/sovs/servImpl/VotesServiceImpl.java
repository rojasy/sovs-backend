package com.uautso.sovs.servImpl;

import com.uautso.sovs.dto.DashboardResponse;
import com.uautso.sovs.dto.TotalVotesDto;
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
import java.util.List;
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

//    @Override
//    public Response<Votes> addVote(VotesDto votesDto) {
//        try {
//
//            UserAccount user = loggedUser.getUser();
//
////            System.out.println("logged User id "+user.getUuid());
//
//            if(user == null){
//                logger.info("UNAUTHENTICATED USER TRYING TO CREATE VOTES, REJECTED");
//                return new Response<>(true, ResponseCode.UNAUTHORIZED, "Unauthenticated!");
//
//            }
//
//            UserAccount userAccount = new UserAccount();
//
//            if(user.getUuid() != null){
//                Optional<UserAccount> userAccountOptional = accountRepository.findFirstByUuid(user.getUuid());
//                if(userAccountOptional.isEmpty()){
////                    userAccount = userAccountOptional.get();
//                    return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "No user found with specified uuid");
//
//                }
//                userAccount = userAccountOptional.get();
//
//            }
//
//
//
//            Votes votes = new Votes();
//            Optional<Votes> optionalVotes = votesRepository.findFirstByCandidatesUuid(votesDto.getCandidateUuid());
//
//            if(optionalVotes.isPresent()){
////                votes = optionalVotes.get();
//                return new Response<>(true, ResponseCode.FAIL, "User already exist with specified uuid");
//
//            }
//
//            if(votesDto.getCandidateUuid() == null){
//                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Candidate UUid Cannot be Empty");
//            }
//
//            if(votesDto.getElectionYear() == null){
//                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Election Year Cannot be Empty");
//            }
//
////            if(!votesDto.getCandidateUuid().isBlank()){
////                votes.setCandidateUuid(votesDto.getCandidateUuid());
////            }
//
//            if(!votesDto.getElectionYear().toString().isBlank()){
//                votes.setYear(votesDto.getElectionYear());
//            }
//
//            Candidates candidates = new Candidates();
//
//            Optional<Candidates> candidatesOptional = candidatesRepository.findFirstByUuid(votesDto.getCandidateUuid());
//
//            if(candidatesOptional.isEmpty()){
//                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "No Candidate found with specified uuid");
//            }
//
//            candidates = candidatesOptional.get();
//
//            Election election = new Election();
//
//            Optional<Election> electionOptional = electionRepository.findFirstByUuid(votesDto.getElectionUuid());
//
//            if(electionOptional.isEmpty()){
//                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "No Election found with specified uuid");
//
//            }
//
//            election = electionOptional.get();
//
////            Votes votes1 = new Votes();
//
//            Optional<Votes> existingVotes = votesRepository.findByUserAccountUuidAndCandidatesUuidAndElectionUuid(user.getUuid(),votesDto.getCandidateUuid(),votesDto.getElectionUuid());
//            if(existingVotes.isPresent()){
//                return new Response<>(true, ResponseCode.FAIL, "User has already voted for this candidate in the specified election");
//            }
//
//
////            UserAccount userAccount1 = new UserAccount();
//
//            votes.setCandidates(candidates);
//            votes.setUserAccount(userAccount);
//            votes.setElection(election);
//
//            Votes savedVotes = votesRepository.save(votes);
//
//            return new Response<>(false, ResponseCode.SUCCESS, savedVotes, "Vote Added successful");
//
//
//        }catch (Exception e){
//            log.error("FAILED TO ADD VOTES");
//        }
//        return new Response<>(true, ResponseCode.FAIL, "Failed to add vote");
//    }




//    @Override
//    public Response<Votes> addVote(VotesDto votesDto) {
//        try {
//            UserAccount user = loggedUser.getUser();
//
//            if (user == null) {
//                logger.info("UNAUTHENTICATED USER TRYING TO CREATE VOTES, REJECTED");
//                return new Response<>(true, ResponseCode.UNAUTHORIZED, "Unauthenticated!");
//            }
//
//            Optional<UserAccount> userAccountOptional = accountRepository.findFirstByUuid(user.getUuid());
//            if (userAccountOptional.isEmpty()) {
//                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "No user found with specified uuid");
//            }
//            UserAccount userAccount = userAccountOptional.get();
//
//            if (votesDto.getCandidateUuid() == null) {
//                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Candidate UUid Cannot be Empty");
//            }
//
//
//            Optional<Candidates> candidatesOptional = candidatesRepository.findFirstByUuid(votesDto.getCandidateUuid());
//            if (candidatesOptional.isEmpty()) {
//                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "No Candidate found with specified uuid");
//            }
//            Candidates candidates = candidatesOptional.get();
//
//            Optional<Election> electionOptional = electionRepository.findFirstByUuid(votesDto.getElectionUuid());
//            if (electionOptional.isEmpty()) {
//                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "No Election found with specified uuid");
//            }
//            Election election = electionOptional.get();
//
//            // Check if the user has already voted for the same candidate in the same election
//            Optional<Votes> existingVotes = votesRepository.findByUserAccountUuidAndCandidatesUuidAndElectionUuid(
//                    user.getUuid(), votesDto.getCandidateUuid(), votesDto.getElectionUuid());
//            if (existingVotes.isPresent()) {
//                return new Response<>(true, ResponseCode.FAIL, "User has already voted for this candidate in the specified election");
//            }
//
//            Votes votes = new Votes();
//            votes.setCandidates(candidates);
//            votes.setUserAccount(userAccount);
//            votes.setElection(election);
//
//            Votes savedVotes = votesRepository.save(votes);
//
//            // Update total votes for the candidate
//            candidates.setTotalVotes(candidates.getTotalVotes() + 1);
//            candidatesRepository.save(candidates);
//
//            return new Response<>(false, ResponseCode.SUCCESS, savedVotes, "Vote Added successfully");
//
//        } catch (Exception e) {
//            logger.error("FAILED TO ADD VOTES", e);
//            return new Response<>(true, ResponseCode.FAIL, "Failed to add vote");
//        }
//    }


    @Override
    public Response<Votes> addVote(VotesDto votesDto) {
        try {
            UserAccount user = loggedUser.getUser();

            if (user == null) {
                logger.info("UNAUTHENTICATED USER TRYING TO CREATE VOTES, REJECTED");
                return new Response<>(true, ResponseCode.UNAUTHORIZED, "Unauthenticated!");
            }

            Optional<UserAccount> userAccountOptional = accountRepository.findFirstByUuid(user.getUuid());
            if (userAccountOptional.isEmpty()) {
                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "No user found with specified uuid");
            }
            UserAccount userAccount = userAccountOptional.get();

            if (votesDto.getCandidateUuid() == null) {
                return new Response<>(true, ResponseCode.NULL_ARGUMENT, "Candidate UUID Cannot be Empty");
            }

            Optional<Candidates> candidatesOptional = candidatesRepository.findFirstByUuid(votesDto.getCandidateUuid());
            if (candidatesOptional.isEmpty()) {
                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "No Candidate found with specified uuid");
            }
            Candidates candidates = candidatesOptional.get();

            Optional<Election> electionOptional = electionRepository.findFirstByUuid(votesDto.getElectionUuid());
            if (electionOptional.isEmpty()) {
                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "No Election found with specified uuid");
            }
            Election election = electionOptional.get();

            // Check if the user is the same as the candidate they are trying to vote for
            if (userAccount.getId().equals(candidates.getUserAccount().getId())) {
                return new Response<>(true, ResponseCode.FAIL, "Candidates cannot vote for themselves");
            }

            // Check if the user has already voted in the same election
            List<Votes> existingVotes = votesRepository.findByUserAccountUuidAndElectionUuid(
                    userAccount.getUuid(), votesDto.getElectionUuid());
            if (!existingVotes.isEmpty()) {
                return new Response<>(true, ResponseCode.FAIL, "User has already voted in the specified election");
            }

            Votes votes = new Votes();
            votes.setCandidates(candidates);
            votes.setUserAccount(userAccount);
            votes.setElection(election);

            Votes savedVotes = votesRepository.save(votes);

            // Update total votes for the candidate
            candidates.setTotalVotes(candidates.getTotalVotes() + 1);
            candidatesRepository.save(candidates);

            return new Response<>(false, ResponseCode.SUCCESS, savedVotes, "Vote Added successfully");

        } catch (Exception e) {
            logger.error("FAILED TO ADD VOTES", e);
            return new Response<>(true, ResponseCode.FAIL, "Failed to add vote");
        }
    }




    @Override
    public Response<Candidates> getTotalVotes(TotalVotesDto totalVotesDto) {
        try {

            UserAccount user = loggedUser.getUser();

            if (user == null) {
                logger.info("UNAUTHENTICATED USER TRYING TO COUNT VOTES, REJECTED");
                return new Response<>(true, ResponseCode.UNAUTHORIZED, "Unauthenticated!");
            }


            Optional<Candidates> candidatesOptional = candidatesRepository.findFirstByUuid(totalVotesDto.getCandidateUuid());
            if (candidatesOptional.isEmpty()) {
                return new Response<>(true, ResponseCode.NO_RECORD_FOUND, "No candidate found with specified UUID");
            }
            Candidates candidate = candidatesOptional.get();

            Long totalVotes = votesRepository.countByCandidatesUuidAndElectionUuid(totalVotesDto.getCandidateUuid(), totalVotesDto.getElectionUuid());

            candidate.setTotalVotes(totalVotes);

            Candidates saveCandidate = candidatesRepository.save(candidate);

            return new Response<>(false, ResponseCode.SUCCESS, saveCandidate, "Total votes counted successfully");
        } catch (Exception e) {
            logger.error("FAILED TO COUNT VOTES", e);
            return new Response<>(true, ResponseCode.FAIL, "Failed to count votes");
        }
    }

    @Override
    public Response<DashboardResponse> getDashboard() {
        try {

            UserAccount user = loggedUser.getUser();

            if (user == null) {
                logger.info("UNAUTHENTICATED USER TRYING TO GET DASHBOARD, REJECTED");
                return new Response<>(true, ResponseCode.UNAUTHORIZED, "Unauthenticated!");
            }


            Long users = accountRepository.countAllByDeletedFalse();
            Long votes = votesRepository.countVotesByDeletedFalse();
            Long candidates = candidatesRepository.countAllByDeletedFalse();
            Long elections = electionRepository.countAllByDeletedFalse();

            DashboardResponse dashboardResponse = new DashboardResponse();

            dashboardResponse.setUsers(users);
            dashboardResponse.setVotes(votes);
            dashboardResponse.setCandidates(candidates);
            dashboardResponse.setElections(elections);


            return new Response<>(false, ResponseCode.SUCCESS, dashboardResponse, "Dashboard data retrieved successfully");


        }catch (Exception e){
            logger.error("FAILED TO GET DASHBOARD");
            return new Response<>(true, ResponseCode.FAIL, "Failed to count votes");

        }
    }


}
