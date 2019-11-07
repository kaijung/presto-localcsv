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

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;
import io.airlift.configuration.ConfigBinder;

public class LocalCsvModule implements Module
{
    @Override
    public void configure(Binder binder)
    {
        binder.bind(LocalCsvConnector.class).in(Scopes.SINGLETON);
        binder.bind(LocalCsvMetadata.class).in(Scopes.SINGLETON);
        binder.bind(LocalCsvSplitManager.class).in(Scopes.SINGLETON);
        binder.bind(LocalCsvRecordSetProvider.class).in(Scopes.SINGLETON);
        binder.bind(LocalCsvUtils.class).in(Scopes.SINGLETON);
        binder.bind(LocalCsvHandleResolver.class).in(Scopes.SINGLETON);

        ConfigBinder.configBinder(binder).bindConfig(LocalCsvConfig.class);
    }
}
