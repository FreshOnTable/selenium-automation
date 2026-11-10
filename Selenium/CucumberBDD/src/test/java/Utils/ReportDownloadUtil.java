package Utils;

import java.io.File;
import java.time.Duration;

public class ReportDownloadUtil {

    public static File waitForExcelDownload(
            String downloadDirectory,
            int timeoutSeconds) {

        File downloadFolder =
                new File(downloadDirectory);

        long endTime =
                System.currentTimeMillis()
                        + Duration
                        .ofSeconds(timeoutSeconds)
                        .toMillis();

        while (System.currentTimeMillis()
                < endTime) {

            File[] files =
                    downloadFolder.listFiles(
                            (dir, name) ->
                                    name.toLowerCase()
                                            .endsWith(".xlsx")
                                            &&
                                    !name.toLowerCase()
                                            .endsWith(".crdownload")
                    );

            if (files != null &&
                    files.length > 0) {

                File latest =
                        getLatestFile(files);

                /*
                 * Make sure the file is no longer
                 * being written.
                 */
                long size1 =
                        latest.length();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                long size2 =
                        latest.length();

                if (size1 == size2 &&
                        size2 > 0) {

                    System.out.println(
                            "✅ Excel downloaded:"
                    );

                    System.out.println(
                            latest.getAbsolutePath()
                    );

                    return latest;
                }
            }

            try {

                Thread.sleep(500);

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();

                throw new RuntimeException(
                        "Download wait interrupted",
                        e
                );
            }
        }

        throw new RuntimeException(
                "❌ Excel report was not downloaded within "
                        + timeoutSeconds
                        + " seconds"
        );
    }

    private static File getLatestFile(
            File[] files) {

        File latest = files[0];

        for (File file : files) {

            if (file.lastModified()
                    > latest.lastModified()) {

                latest = file;
            }
        }

        return latest;
    }
}


