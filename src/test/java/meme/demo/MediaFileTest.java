package meme.demo;

import meme.demo.model.MediaFile;
import meme.demo.repository.MediaFileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

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

    @Test
    @Transactional
    public void check() throws Exception {
        MediaFile mediaFile = new MediaFile();
        mediaFile.setName("test media file");
        mediaFile.setUuid("abcdef");
        mediaFile.setDuration(123);
        mediaFileRepository.save(mediaFile);

        mvc.perform(get("/mediaFiles").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$._embedded.mediaFiles", hasSize(1)))
                .andExpect(jsonPath("$._embedded.mediaFiles[0].duration", is(equalTo(123))));

        mvc.perform(get("/mediaFiles/1").accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.duration", is(equalTo(123))));
    }
}
