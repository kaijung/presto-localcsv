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
import com.facebook.presto.spi.RecordSet;
import com.facebook.presto.spi.type.Type;

import java.io.File;
import java.util.List;

public class LocalCsvRecordSet
        implements RecordSet
{
    private File csvFile;

    public LocalCsvRecordSet(File csvFile)
    {
        this.csvFile = csvFile;
    }

    @Override
    public List<Type> getColumnTypes()
    {
        return LocalCsvUtils.tableColumnTypes(csvFile.toPath());
    }

    @Override
    public RecordCursor cursor()
    {
        return new LocalCsvRecordCursor(csvFile);
    }
}
