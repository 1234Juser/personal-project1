package com.example.TestProject.service;

import com.example.TestProject.dto.MemberJoinDTO;

public interface MemberService {
    static class MidExistException extends Exception {

    }

    void join(MemberJoinDTO memberJoinDTO)throws MidExistException ;

}
