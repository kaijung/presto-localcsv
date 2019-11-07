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

public class LocalCsvRecordCursor
        implements RecordCursor
{
    private final File csvFile;

    private String curLine;
    private String[] fields;

    private BufferedReader reader;

    public LocalCsvRecordCursor(File csvFile)
    {
        reader = null;
        this.csvFile = csvFile;
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
        if (reader == null) {
            try {
                reader = Files.newBufferedReader(csvFile.toPath());
                reader.readLine();
            }
            catch (IOException e) {
                throw new RuntimeException();
            }
        }
        try {
            curLine = reader.readLine();
            if (curLine != null) {
                fields = curLine.split("\\s*,\\s*");
            }
        }
        catch (IOException e) {
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
        if (reader != null) {
            try {
                reader.close();
            }
            catch (IOException e) {
                //
            }
        }
    }
}
