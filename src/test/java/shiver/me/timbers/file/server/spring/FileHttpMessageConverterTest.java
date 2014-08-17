package shiver.me.timbers.file.server.spring;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import shiver.me.timbers.file.io.File;
import shiver.me.timbers.file.io.JavaFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.file.io.FileConstants.FILE_ONE;

public class FileHttpMessageConverterTest {

    private static final HttpMessageConverter<File> MESSAGE_CONVERTER = new FileHttpMessageConverter<>();

    @Test
    public void I_can_check_that_the_file_message_converter_supports_files_only() {

        assertFalse("the File type should not be supported for read.", MESSAGE_CONVERTER.canRead(File.class, null));
        assertTrue("the File type should be supported for write.", MESSAGE_CONVERTER.canWrite(File.class, null));

        assertFalse("the String type should not be supported for read.", MESSAGE_CONVERTER.canRead(String.class, null));
        assertFalse("the String type should not be supported for write.",
                MESSAGE_CONVERTER.canWrite(String.class, null));

        assertFalse("the Object type should not be supported for read.", MESSAGE_CONVERTER.canRead(Object.class, null));
        assertFalse("the Object type should not be supported for write.",
                MESSAGE_CONVERTER.canWrite(Object.class, null));
    }

    @Test
    public void I_can_check_that_the_file_message_converter_supports_all_media_types() {

        assertThat("all media types should be supported.", MESSAGE_CONVERTER.getSupportedMediaTypes(),
                contains(MediaType.ALL));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void I_cannot_read_a_file() throws IOException {

        MESSAGE_CONVERTER.read(File.class, mock(HttpInputMessage.class));
    }

    @Test
    public void I_can_write_a_file() throws IOException {

        final OutputStream output = new ByteArrayOutputStream();

        final HttpOutputMessage message = mockHttpOutputMessage(output);

        MESSAGE_CONVERTER.write(new JavaFile(FILE_ONE.getAbsolutePath()), null, message);

        assertEquals("the files content should be correct.", FILE_ONE.getContent(), output.toString());
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_write_a_null_file() throws IOException {

        MESSAGE_CONVERTER.write(null, null, mockHttpOutputMessage(new ByteArrayOutputStream()));
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_write_a_file_to_a_null_output_stream() throws IOException {

        final HttpHeaders headers = mock(HttpHeaders.class);

        final HttpOutputMessage message = mock(HttpOutputMessage.class);
        when(message.getHeaders()).thenReturn(headers);

        MESSAGE_CONVERTER.write(new JavaFile(FILE_ONE.getAbsolutePath()), null, message);
    }

    @Test(expected = NullPointerException.class)
    public void I_cannot_write_a_file_to_a_null_message() throws IOException {

        MESSAGE_CONVERTER.write(new JavaFile(FILE_ONE.getAbsolutePath()), null, null);
    }

    private static HttpOutputMessage mockHttpOutputMessage(OutputStream output) throws IOException {

        final HttpHeaders headers = mock(HttpHeaders.class);

        final HttpOutputMessage message = mock(HttpOutputMessage.class);
        when(message.getHeaders()).thenReturn(headers);
        when(message.getBody()).thenReturn(output);
        return message;
    }
}