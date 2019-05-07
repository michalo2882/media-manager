package mediamanager;

import mediamanager.component.AuthenticationDetailsProvider;
import mediamanager.model.MediaFile;
import mediamanager.model.User;
import mediamanager.repository.MediaFileRepository;
import mediamanager.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MediaFileTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private MediaFileRepository mediaFileRepository;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private AuthenticationDetailsProvider authenticationDetailsProviderMock;

    @Test
    @Transactional
    public void check() throws Exception {
        User owner = new User();
        owner.setName("test-owner");
        userRepository.save(owner);

        MediaFile mediaFile = new MediaFile();
        mediaFile.setName("test media file");
        mediaFile.setUuid("abcdef");
        mediaFile.setDuration(123);
        mediaFile.setOwner(owner);
        mediaFileRepository.save(mediaFile);

        given(authenticationDetailsProviderMock.getCurrentUser()).willReturn(owner);

        mvc.perform(get("/api/v1/mediaFiles").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$._embedded.mediaFiles", hasSize(1)))
                .andExpect(jsonPath("$._embedded.mediaFiles[0].duration", is(equalTo(123))));

        mvc.perform(get("/api/v1/mediaFiles/1").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.duration", is(equalTo(123))));
    }
}
