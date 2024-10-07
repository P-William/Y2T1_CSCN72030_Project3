package com.group3;

// Repos are normally used for saving to database. We don't have a DB in this app just showing an example.
public interface SampleUserRepo {
    SampleUser findById(Long id);
    SampleUser save(SampleUser user);
}
