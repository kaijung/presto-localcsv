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

//import com.facebook.presto.spi.connector.*;
import com.facebook.presto.spi.connector.Connector;
import com.facebook.presto.spi.connector.ConnectorMetadata;
import com.facebook.presto.spi.connector.ConnectorRecordSetProvider;
import com.facebook.presto.spi.connector.ConnectorSplitManager;
import com.facebook.presto.spi.connector.ConnectorTransactionHandle;
import com.facebook.presto.spi.transaction.IsolationLevel;
import com.google.inject.Inject;

import static com.facebook.presto.localcsv.LocalCsvTransactionHandle.INSTANCE;

public class LocalCsvConnector
        implements Connector
{
    private LocalCsvMetadata metadata;
    private LocalCsvSplitManager splitManager;
    private LocalCsvRecordSetProvider recordSetProvider;

    @Inject
    public LocalCsvConnector(LocalCsvMetadata metadata, LocalCsvSplitManager splitManager, LocalCsvRecordSetProvider recordSetProvider)
    {
        this.metadata = metadata;
        this.splitManager = splitManager;
        this.recordSetProvider = recordSetProvider;
    }

    @Override
    public ConnectorTransactionHandle beginTransaction(IsolationLevel isolationLevel, boolean readOnly)
    {
        return INSTANCE;
    }

    @Override
    public ConnectorMetadata getMetadata(ConnectorTransactionHandle transactionHandle)
    {
        return metadata;
    }

    @Override
    public ConnectorRecordSetProvider getRecordSetProvider()
    {
        return recordSetProvider;
    }

    @Override
    public ConnectorSplitManager getSplitManager()
    {
        return splitManager;
    }
}
