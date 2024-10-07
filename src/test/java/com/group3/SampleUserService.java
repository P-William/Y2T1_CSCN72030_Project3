package com.group3;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SampleUserService {
    private final SampleUserRepo sampleUserRepo;

    public SampleUser getUserById(Long id) {
        return sampleUserRepo.findById(id);
    }

    public SampleUser createUser(String name) {
        SampleUser user = new SampleUser(5L, name);
        return sampleUserRepo.save(user);
    }
}
