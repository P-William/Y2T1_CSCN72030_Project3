package com.group3;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

// Test class and methods have no public/private specifier
@ExtendWith(MockitoExtension.class) // Enables Mockito for this test class
class SampleTest {

    // We create a dummy repo, so we can stub any calls to it.
    @Mock
    private SampleUserRepo sampleUserRepo;

    // We inject the dummy repo in the service we are testing so when it calls the repo we can stub.
    @InjectMocks
    private SampleUserService sampleUserService;

    // Test class and methods have no public/private specifier
    @Test
    void getUserById_shouldReturnUser() {
        // Arrange
        Long userId = 5L;
        SampleUser mockUser = new SampleUser(userId, "Jeff");

        // This is how we stub with mockito
        when(sampleUserRepo.findById(userId))
            .thenReturn(mockUser);

        // Act
        SampleUser result = sampleUserService.getUserById(userId);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getName()).isEqualTo("Jeff");

        // We can even verify how many times a method is called on a mock
        verify(sampleUserRepo, times(1)).findById(userId);
    }

    @Test
    void createUser_shouldReturnUser() {
        // Arrange
        String name = "Jeff";
        SampleUser mockUser = new SampleUser(5L, name);

        // This is how we stub with mockito
        when(sampleUserRepo.save(any(SampleUser.class)))
            .thenReturn(mockUser);

        // Act
        SampleUser result = sampleUserService.createUser(name);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(5L);
        assertThat(result.getName()).isEqualTo(name);

        // We can even verify how many times a method is called on a mock
        verify(sampleUserRepo, times(1)).save(any(SampleUser.class));
    }


}
