package shiver.me.timbers.file;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_NAME;
import static shiver.me.timbers.Constants.CURRENT_DIRECTORY_PATH;
import static shiver.me.timbers.Constants.DIRECTORY_FOUR_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.DIRECTORY_FOUR_NAME;
import static shiver.me.timbers.Constants.DIRECTORY_FOUR_PATH;
import static shiver.me.timbers.Constants.DIRECTORY_ONE_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.DIRECTORY_ONE_NAME;
import static shiver.me.timbers.Constants.DIRECTORY_ONE_PATH;
import static shiver.me.timbers.Constants.DIRECTORY_THREE_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.DIRECTORY_THREE_NAME;
import static shiver.me.timbers.Constants.DIRECTORY_THREE_PATH;
import static shiver.me.timbers.Constants.DIRECTORY_TWO_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.DIRECTORY_TWO_NAME;
import static shiver.me.timbers.Constants.DIRECTORY_TWO_PATH;
import static shiver.me.timbers.Constants.FILE_EXTENSION;
import static shiver.me.timbers.Constants.FILE_FOUR_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.FILE_FOUR_NAME;
import static shiver.me.timbers.Constants.FILE_FOUR_PATH;
import static shiver.me.timbers.Constants.FILE_ONE_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.FILE_ONE_NAME;
import static shiver.me.timbers.Constants.FILE_ONE_PATH;
import static shiver.me.timbers.Constants.FILE_THREE_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.FILE_THREE_NAME;
import static shiver.me.timbers.Constants.FILE_THREE_PATH;
import static shiver.me.timbers.Constants.FILE_TWO_MODIFICATION_DATE;
import static shiver.me.timbers.Constants.FILE_TWO_NAME;
import static shiver.me.timbers.Constants.FILE_TWO_PATH;

public class FileSystemElementSteps {

    public static void The_files_name_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_name_should_be_correct(FILE_ONE_NAME, creator.create(FILE_ONE_PATH));
        The_file_system_elements_name_should_be_correct(FILE_TWO_NAME, creator.create(FILE_TWO_PATH));
        The_file_system_elements_name_should_be_correct(FILE_THREE_NAME, creator.create(FILE_THREE_PATH));
        The_file_system_elements_name_should_be_correct(FILE_FOUR_NAME, creator.create(FILE_FOUR_PATH));
    }

    public static void The_directories_name_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_name_should_be_correct(CURRENT_DIRECTORY_NAME, creator.create(CURRENT_DIRECTORY_PATH));
        The_file_system_elements_name_should_be_correct(DIRECTORY_ONE_NAME, creator.create(DIRECTORY_ONE_PATH));
        The_file_system_elements_name_should_be_correct(DIRECTORY_TWO_NAME, creator.create(DIRECTORY_TWO_PATH));
        The_file_system_elements_name_should_be_correct(DIRECTORY_THREE_NAME, creator.create(DIRECTORY_THREE_PATH));
        The_file_system_elements_name_should_be_correct(DIRECTORY_FOUR_NAME, creator.create(DIRECTORY_FOUR_PATH));
    }

    public static void The_file_system_elements_name_should_be_correct(String name, FileSystemElement element) {

        assertEquals("the name of the file system element should be correct.", name, element.getName());
    }

    public static void The_files_extension_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_extension_should_be_correct(FILE_EXTENSION, creator.create(FILE_ONE_PATH));
        The_file_system_elements_extension_should_be_correct(FILE_EXTENSION, creator.create(FILE_TWO_PATH));
        The_file_system_elements_extension_should_be_correct(FILE_EXTENSION, creator.create(FILE_THREE_PATH));
        The_file_system_elements_extension_should_be_correct(FILE_EXTENSION, creator.create(FILE_FOUR_PATH));
    }

    public static void The_directories_extension_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_extension_should_be_correct("", creator.create(CURRENT_DIRECTORY_PATH));
        The_file_system_elements_extension_should_be_correct("", creator.create(DIRECTORY_ONE_PATH));
        The_file_system_elements_extension_should_be_correct("", creator.create(DIRECTORY_TWO_PATH));
        The_file_system_elements_extension_should_be_correct("", creator.create(DIRECTORY_THREE_PATH));
        The_file_system_elements_extension_should_be_correct("", creator.create(DIRECTORY_FOUR_PATH));
    }

    public static void The_file_system_elements_extension_should_be_correct(String extension,
                                                                            FileSystemElement element) {

        assertEquals("the extension of the file system element should be correct.", extension, element.getExtension());
    }

    public static void The_files_modification_date_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_modification_date_should_be_correct(FILE_ONE_MODIFICATION_DATE,
                creator.create(FILE_ONE_PATH));
        The_file_system_elements_modification_date_should_be_correct(FILE_TWO_MODIFICATION_DATE,
                creator.create(FILE_TWO_PATH));
        The_file_system_elements_modification_date_should_be_correct(FILE_THREE_MODIFICATION_DATE,
                creator.create(FILE_THREE_PATH));
        The_file_system_elements_modification_date_should_be_correct(FILE_FOUR_MODIFICATION_DATE,
                creator.create(FILE_FOUR_PATH));
    }

    public static void The_directories_modification_date_should_be_correct(FileSystemElementCreator creator) {

        The_file_system_elements_modification_date_should_be_correct(CURRENT_DIRECTORY_MODIFICATION_DATE,
                creator.create(CURRENT_DIRECTORY_PATH));
        The_file_system_elements_modification_date_should_be_correct(DIRECTORY_ONE_MODIFICATION_DATE,
                creator.create(DIRECTORY_ONE_PATH));
        The_file_system_elements_modification_date_should_be_correct(DIRECTORY_TWO_MODIFICATION_DATE,
                creator.create(DIRECTORY_TWO_PATH));
        The_file_system_elements_modification_date_should_be_correct(DIRECTORY_THREE_MODIFICATION_DATE,
                creator.create(DIRECTORY_THREE_PATH));
        The_file_system_elements_modification_date_should_be_correct(DIRECTORY_FOUR_MODIFICATION_DATE,
                creator.create(DIRECTORY_FOUR_PATH));
    }

    public static void The_file_system_elements_modification_date_should_be_correct(Date modified,
                                                                                    FileSystemElement element) {

        assertEquals("the modification date of the file system element should be correct.", modified,
                element.getModified());
    }

    public static void The_file_should_have_correct_equality(FileSystemElementCreator creator) {

        The_file_system_element_should_have_correct_equality(creator.create(FILE_ONE_PATH),
                creator.create(FILE_ONE_PATH));
    }

    public static void The_directory_should_have_correct_equality(FileSystemElementCreator creator) {

        The_file_system_element_should_have_correct_equality(creator.create(CURRENT_DIRECTORY_PATH),
                creator.create(CURRENT_DIRECTORY_PATH));
    }

    public static void The_file_system_element_should_have_correct_equality(FileSystemElement left,
                                                                            final FileSystemElement right) {

        assertEquals("the file system elements should be equal.", left, right);

        assertEquals("the file system element hash codes should be equal.", left.hashCode(), right.hashCode());

        assertNotEquals("the file system element should not be equal to an element with a different name.", left,
                mockFileSystemElement("different", left.getExtension(), left.getModified()));

        assertNotEquals("the file system element should not be equal to an element with a different extension.", left,
                mockFileSystemElement(left.getName(), "different", left.getModified()));

        assertNotEquals("the file system element should not be equal to an element with a different modified date.",
                left, mockFileSystemElement(left.getName(), left.getExtension(), new Date()));

        assertNotEquals("the file system element should not be equal to object.", left, new Object());

        assertNotEquals("the file system element should not be equal to null.", left, null);
    }

    private static FileSystemElement mockFileSystemElement(String name, String extension, Date modified) {

        final FileSystemElement mock = mock(FileSystemElement.class);
        when(mock.getName()).thenReturn(name);
        when(mock.getExtension()).thenReturn(extension);
        when(mock.getModified()).thenReturn(modified);

        return mock;
    }

    public static void The_file_should_have_the_correct_to_string_value(FileSystemElementCreator creator) {

        The_file_system_element_should_have_the_correct_to_string_value(FILE_ONE_NAME, creator.create(FILE_ONE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_TWO_NAME, creator.create(FILE_TWO_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_THREE_NAME,
                creator.create(FILE_THREE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(FILE_FOUR_NAME, creator.create(FILE_FOUR_PATH));
    }

    public static void The_directory_should_have_the_correct_to_string_value(FileSystemElementCreator creator) {

        The_file_system_element_should_have_the_correct_to_string_value(CURRENT_DIRECTORY_NAME,
                creator.create(CURRENT_DIRECTORY_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(DIRECTORY_ONE_NAME,
                creator.create(DIRECTORY_ONE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(DIRECTORY_TWO_NAME,
                creator.create(DIRECTORY_TWO_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(DIRECTORY_THREE_NAME,
                creator.create(DIRECTORY_THREE_PATH));
        The_file_system_element_should_have_the_correct_to_string_value(DIRECTORY_FOUR_NAME,
                creator.create(DIRECTORY_FOUR_PATH));
    }

    public static void The_file_system_element_should_have_the_correct_to_string_value(String string,
                                                                                       FileSystemElement element) {

        assertEquals("the file system elements toString value should be correct.", string, element.toString());
    }
}
