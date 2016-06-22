package com.samuel.io;

import com.samuel.quality_metrics.QualityMetricsOutput;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by Samuel on 6/22/2016.
 */
public class ResultsWriter {

    public void writeResults(String path, String fileName, List<QualityMetricsOutput> results) {
        try {
            String filePath = path + "/" + fileName + ".txt";
            File file = new File(filePath);
            FileWriter writer = new FileWriter(file);

            for (QualityMetricsOutput result : results) {
                writer.write(result.toString());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
