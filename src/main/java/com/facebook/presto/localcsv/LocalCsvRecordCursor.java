/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.facebook.presto.localcsv;

import com.facebook.presto.spi.RecordCursor;
import com.facebook.presto.spi.type.Type;
import com.facebook.presto.spi.type.VarcharType;
import io.airlift.slice.Slice;
import io.airlift.slice.Slices;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocalCsvRecordCursor
        implements RecordCursor
{
    private final File csvFile;

    private String curLine;
    private String[] fields;

    private BufferedReader reader;
    private FileHandler fileHandler;
    private static Logger logger = Logger.getLogger(LocalCsvRecordCursor.class.getName());

    public LocalCsvRecordCursor(File csvFile)
    {
        logger.info("LocalCsvRecordCursor::LocalCsvRecordCursor()");
        try {
            fileHandler = new FileHandler("test.log");
        }
        catch (IOException e) {
            logger.info("LocalCsvRecordCursor::LocalCsvRecordCursor() IOException" + e.getMessage());
            throw new RuntimeException();
        }
        fileHandler.setLevel(Level.INFO);
        logger.addHandler(fileHandler);

        reader = null;
        this.csvFile = csvFile;
        try {
            reader = Files.newBufferedReader(csvFile.toPath());
            reader.readLine();
        }
        catch (IOException e) {
            logger.info("LocalCsvRecordCursor::LocalCsvRecordCursor() IOException" + e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public long getCompletedBytes()
    {
        return 0;
    }

    @Override
    public long getReadTimeNanos()
    {
        return 0;
    }

    @Override
    public Type getType(int field)
    {
        return VarcharType.VARCHAR;
    }

    @Override
    public boolean advanceNextPosition()
    {
        logger.info("LocalCsvRecordCursor::advanceNextPosition()");
        try {
            curLine = reader.readLine();
            if (curLine != null) {
                fields = curLine.split("\\s*,\\s*");
            }
        }
        catch (IOException e) {
            logger.info("LocalCsvRecordCursor::advanceNextPosition() IOException" + e.getMessage());
            throw new RuntimeException(e);
        }
        return curLine != null;
    }

    @Override
    public boolean getBoolean(int field)
    {
        return false;
    }

    @Override
    public long getLong(int field)
    {
        return 0;
    }

    @Override
    public double getDouble(int field)
    {
        return 0;
    }

    @Override
    public Slice getSlice(int field)
    {
        return Slices.utf8Slice(fields[field]);
    }

    @Override
    public Object getObject(int field)
    {
        return null;
    }

    @Override
    public boolean isNull(int field)
    {
        return false;
    }

    @Override
    public void close()
    {
        logger.info("LocalCsvRecordCursor::close()");

        if (reader != null) {
            try {
                reader.close();
            }
            catch (IOException e) {
                //
                logger.info("LocalCsvRecordCursor::close() IOException" + e.getMessage());
            }
        }
    }
}
