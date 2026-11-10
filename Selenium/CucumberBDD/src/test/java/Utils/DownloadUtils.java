package Utils;

import java.io.File;
import java.time.Duration;
import java.util.Arrays;

public class DownloadUtils {

    public static File waitForDownload(
            String downloadDirectory,
            int timeoutSeconds) throws InterruptedException {

        File downloadFolder = new File(downloadDirectory);

        System.out.println(
                "📁 Checking download folder: "
                        + downloadFolder.getAbsolutePath()
        );

        long endTime = System.currentTimeMillis()
                + Duration.ofSeconds(timeoutSeconds).toMillis();

        while (System.currentTimeMillis() < endTime) {

            File[] files = downloadFolder.listFiles();

            if (files != null) {

                for (File file : files) {

                    System.out.println(
                            "📄 File found: " + file.getName()
                    );

                    // Ignore Chrome temporary download files
                    if (file.getName().endsWith(".tmp")
                            || file.getName().endsWith(".crdownload")) {

                        continue;
                    }

                    // Excel report
                    if (file.getName().toLowerCase().endsWith(".xlsx")
                            || file.getName().toLowerCase().endsWith(".xls")) {

                        System.out.println(
                                "✅ Report downloaded: "
                                        + file.getAbsolutePath()
                        );

                        return file;
                    }
                }
            }

            Thread.sleep(1000);
        }

        throw new RuntimeException(
                "Report was not downloaded within "
                        + timeoutSeconds + " seconds"
        );
    }

    
    public static void clearDownloadFolder(String downloadPath) {

        File folder = new File(downloadPath);

        if (!folder.exists()) {
            folder.mkdirs();
            return;
        }

        File[] files = folder.listFiles();

        if (files != null) {

            for (File file : files) {

                if (file.isFile()) {
                    file.delete();
                }
            }
        }

        System.out.println("🧹 Download folder cleared");
    }
}