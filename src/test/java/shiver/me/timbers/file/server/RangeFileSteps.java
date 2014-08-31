package shiver.me.timbers.file.server;

import shiver.me.timbers.file.io.StreamFile;

import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.file.io.StreamFileSteps.The_files_input_stream_should_contain;

public class RangeFileSteps {

    public static <S extends StreamFile> void I_can_read_a_files_partial_input_stream(AbstractPartialFileCreator<S> creator) {

        final int start = 5;
        final int end = 10;

        creator.setRange(start, end);

        The_files_input_stream_should_contain(new RangeFileContentGetter(start, end), creator);
    }

    public static <S extends StreamFile> void I_cannot_skip_an_invalid_input_stream(AbstractPartialFileCreator<S> creator)
            throws IOException {

        final InputStream input = mock(InputStream.class);
        when(input.skip(anyLong())).thenThrow(new IOException("test IO exception."));

        final StreamFile file = mock(StreamFile.class);
        when(file.getInputStream()).thenReturn(input);
        when(file.getSize()).thenReturn(2L);

        creator.create(file, 0, 1).getInputStream();
    }
}
