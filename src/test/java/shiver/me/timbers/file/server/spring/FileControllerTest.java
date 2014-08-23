package shiver.me.timbers.file.server.spring;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import shiver.me.timbers.file.io.JavaFile;
import shiver.me.timbers.file.io.TestFile;
import shiver.me.timbers.file.server.servlet.AcceptRangesFilter;

import static org.springframework.http.HttpMethod.HEAD;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_XML_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG;
import static org.springframework.http.MediaType.TEXT_PLAIN;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shiver.me.timbers.file.io.FileConstants.FILE_EIGHT;
import static shiver.me.timbers.file.io.FileConstants.FILE_FIVE;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;
import static shiver.me.timbers.file.io.FileConstants.FILE_SEVEN;
import static shiver.me.timbers.file.io.FileConstants.FILE_SIX;
import static shiver.me.timbers.file.server.spring.Requests.FILE;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FilesConfiguration.class)
@WebAppConfiguration("classpath:")
public class FileControllerTest {

    private static final String NO_FILE_ERROR_MESSAGE = "No file provided.";

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(new AcceptRangesFilter()).build();
    }

    @Test
    public void I_can_check_a_file() throws Exception {

        mockMvcForFile(request(HEAD, "/file").requestAttr(FILE, new JavaFile(FILE_ONE.getAbsolutePath())), FILE_ONE)
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string(""));
    }

    @Test
    public void I_can_request_a_file() throws Exception {

        mockMvcForFile(FILE_ONE)
                .andExpect(content().contentType(TEXT_PLAIN))
                .andExpect(content().string(FILE_ONE.getContent()));
    }

    @Test
    public void I_can_request_a_json_file() throws Exception {

        mockMvcForFile(FILE_FIVE)
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(content().string(FILE_FIVE.getContent()));
    }

    @Test
    public void I_can_request_an_xml_file() throws Exception {

        mockMvcForFile(FILE_SIX)
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_XML_VALUE))
                .andExpect(content().string(FILE_SIX.getContent()));
    }

    @Test
    public void I_can_request_a_png_file() throws Exception {

        mockMvcForFile(FILE_SEVEN)
                .andExpect(content().contentTypeCompatibleWith(IMAGE_PNG))
                .andExpect(content().bytes(FILE_SEVEN.getContent()));
    }

    @Test
    public void I_can_request_a_video_file() throws Exception {

        mockMvcForFile(FILE_EIGHT)
                .andExpect(content().contentTypeCompatibleWith("video/mp4"))
                .andExpect(content().bytes(FILE_EIGHT.getContent()));
    }

    @Test
    public void I_cannot_request_a_file_without_a_path() throws Exception {

        mockMvc.perform(get("/file"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.error").value(NO_FILE_ERROR_MESSAGE));
    }

    private ResultActions mockMvcForFile(TestFile file) throws Exception {

        return mockMvcForFile(get("/file").requestAttr(FILE, new JavaFile(file.getAbsolutePath())), file);
    }

    private ResultActions mockMvcForFile(MockHttpServletRequestBuilder requestBuilder, TestFile file) throws Exception {

        return mockMvcHeadersForFile(requestBuilder, file)
                .andExpect(status().isOk());
    }

    private ResultActions mockMvcHeadersForFile(MockHttpServletRequestBuilder requestBuilder, TestFile file)
            throws Exception {

        return Controllers.mockMvcHeadersForFile(mockMvc, requestBuilder, file);
    }
}
