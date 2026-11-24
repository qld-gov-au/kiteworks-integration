package au.gov.qld.ssq.kiteworks.helpers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Path;

public class TempFileResource implements AutoCloseable {
    private final Path path;

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public TempFileResource(String fileName, byte[] content) throws IOException {
        Path tempDir = Files.createTempDirectory("kiteworks-temp-upload");
        this.path = tempDir.resolve(fileName);
        Files.write(this.path, content);
        this.path.toFile().deleteOnExit(); // deletes file on exit if onclose has not done it
        tempDir.toFile().deleteOnExit();   // best effort cleanup
    }

    public Path getPath() {
        return path;
    }

    @Override
    public void close() throws IOException {
        try {
            Files.deleteIfExists(path);
            Files.deleteIfExists(path.getParent()); // clean up the temp dir too
        } catch (IOException e) {
            LOGGER.error("Failed to delete file: {}", path, e);
        }
    }
}