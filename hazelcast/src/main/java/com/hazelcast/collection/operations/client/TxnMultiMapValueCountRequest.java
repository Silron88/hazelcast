/*
 * Copyright (c) 2008-2013, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.collection.operations.client;

import com.hazelcast.collection.CollectionPortableHook;
import com.hazelcast.nio.serialization.Data;
import com.hazelcast.nio.serialization.PortableReader;
import com.hazelcast.nio.serialization.PortableWriter;
import com.hazelcast.transaction.TransactionContext;

import java.io.IOException;

/**
 * @author ali 6/10/13
 */
public class TxnMultiMapValueCountRequest extends TxnMultiMapRequest {

    Data key;

    public TxnMultiMapValueCountRequest() {
    }

    public TxnMultiMapValueCountRequest(String name, Data key) {
        super(name);
        this.key = key;
    }

    public Object call() throws Exception {
        final TransactionContext context = getEndpoint().getTransactionContext();
        return context.getMultiMap(name).valueCount(key);
    }

    public int getClassId() {
        return CollectionPortableHook.TXN_MM_VALUE_COUNT;
    }

    public void writePortable(PortableWriter writer) throws IOException {
        super.writePortable(writer);
        key.writeData(writer.getRawDataOutput());
    }

    public void readPortable(PortableReader reader) throws IOException {
        super.readPortable(reader);
        key = new Data();
        key.readData(reader.getRawDataInput());
    }
}
